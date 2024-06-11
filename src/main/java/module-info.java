module org.inter.interpolation {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;


    opens org.inter.interpolation to javafx.fxml;
    exports org.inter.interpolation;
}