package fr.g1b.sae201;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import java.io.File;

public class SisAppController {
    @FXML
    private Pane hiddenMenuContainer;
    @FXML
    private Button addButton;

    private boolean booleanMenu;

    public SisAppController() {
        booleanMenu = false;
    }

    public void initialize() {
        System.out.println("App launched successfully!");
    }

    public void hideAndShowMenu() {
        booleanMenu = !booleanMenu;
        hiddenMenuContainer.setManaged(booleanMenu);
        hiddenMenuContainer.setVisible(booleanMenu);
    }

    @FXML
    public void addCSV() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Sélectionner un fichier CSV");
        fileChooser.getExtensionFilters().add(new ExtensionFilter("Fichiers CSV", "*.csv"));

        // Afficher la boîte de dialogue de sélection de fichier
        File selectedFile = fileChooser.showOpenDialog(hiddenMenuContainer.getScene().getWindow());

        if (selectedFile != null) {
            // Traiter le fichier CSV sélectionné ici
            System.out.println("Fichier sélectionné : " + selectedFile.getAbsolutePath());

            // Mettre à jour le texte du bouton avec le nom du fichier sélectionné
            addButton.setText(selectedFile.getName());
        }
    }
}
