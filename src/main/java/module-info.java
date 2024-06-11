module org.inter.interpolation {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.inter.interpolation to javafx.fxml;
    exports org.inter.interpolation;
}