package org.inter.interpolation;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.*;

public class MainApp extends Application {

    private File selectedFile;
    private TextField monthsField;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Hospital Supply Forecast");

        Button openButton = new Button("Open Excel File");
        openButton.setOnAction(e -> openFileChooser());

        Button generateButton = new Button("Generate Report");
        generateButton.setOnAction(e -> generateReport());

        VBox vbox = new VBox(openButton, monthsField, generateButton);
        Scene scene = new Scene(vbox, 300, 200);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void openFileChooser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Files", "*.xlsx"));
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        selectedFile = fileChooser.showOpenDialog(null);
    }

    private void generateReport() {
        if (selectedFile != null && !monthsField.getText().isEmpty()) {
            int months = Integer.parseInt(monthsField.getText());

            ExcelReader reader = new ExcelReader();
            Map<String, List<Integer>> data = reader.readExcel(selectedFile);

            // Out in console to debug
            for (Map.Entry<String, List<Integer>> entry : data.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }

            Forecast forecast = new Forecast();
            Map<String, List<Integer>> forecastData = forecast.forecast(data, months);

            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialFileName("forecast.xlsx");
            File outputFile = fileChooser.showSaveDialog(null);
            if (outputFile != null) {
                ExcelWriter writer = new ExcelWriter();
                writer.writeExcel(forecastData, outputFile);
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

