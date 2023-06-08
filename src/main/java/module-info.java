module fr.g1b.sae201 {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.opencsv;
    requires javafx.web;
    requires org.controlsfx.controls;


    opens fr.g1b.sae201 to javafx.fxml;
    exports fr.g1b.sae201;
}