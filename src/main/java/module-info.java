module fr.g1b.sae201 {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.opencsv;


    opens fr.g1b.sae201 to javafx.fxml;
    exports fr.g1b.sae201;
}