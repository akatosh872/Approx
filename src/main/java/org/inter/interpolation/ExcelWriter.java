package org.inter.interpolation;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ExcelWriter {

    public void writeExcel(Map<String, List<Integer>> forecastData, File file) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Forecast");

        Row headerRow = sheet.createRow(0);
        int colIndex = 1;
        for (String item : forecastData.keySet()) {
            headerRow.createCell(colIndex++).setCellValue(item);
        }

        int maxMonths = forecastData.values().stream().mapToInt(List::size).max().orElse(0);

        for (int i = 0; i < maxMonths; i++) {
            Row row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue("Month-" + (i + 1));

            colIndex = 1;
            for (String item : forecastData.keySet()) {
                List<Integer> values = forecastData.get(item);
                if (i < values.size()) {
                    row.createCell(colIndex++).setCellValue(values.get(i));
                } else {
                    row.createCell(colIndex++).setCellValue("N/A");  // Если данных не хватает, заполняем "N/A"
                }
            }
        }

        try (FileOutputStream fos = new FileOutputStream(file)) {
            workbook.write(fos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

