module fr.g1b.sae201 {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens fr.g1b.sae201 to javafx.fxml;
    exports fr.g1b.sae201;
}