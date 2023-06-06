package fr.g1b.sae201;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

import java.io.File;

public class SisAppController {
    // Containers
    @FXML
    private Pane mainContainer; // fenêtre principale
    @FXML
    private VBox checkBoxContainer; // Menu de gauche
    @FXML
    private FlowPane dashboardContainer;
    @FXML
    private Pane leftMenuContainer;

    // Controls
    @FXML
    private Button addButton;
    @FXML
    private Button checkBoxMenuBtn;


    // Autres
    private boolean booleanMenu;

    public SisAppController() {

        booleanMenu = false;
    }

    public void initialize() {
        System.out.println("App launched successfully!");
        ImageView view = new ImageView(new Image("menuButton.png"));
        view.setPreserveRatio(true);
        view.setFitWidth(30);
        checkBoxMenuBtn.setGraphic(view);


        // Initialisation de l'interface
        InterfaceInitialize();
    }

    public void hideAndShowMenu() {
        booleanMenu = !booleanMenu;
        checkBoxContainer.setManaged(booleanMenu);
        checkBoxContainer.setVisible(booleanMenu);
    }

    @FXML
    public void addCSV() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Sélectionner un fichier CSV");
        fileChooser.getExtensionFilters().add(new ExtensionFilter("Fichiers CSV", "*.csv"));

        // Afficher la boîte de dialogue de sélection de fichier
        File selectedFile = fileChooser.showOpenDialog(checkBoxContainer.getScene().getWindow());

        if (selectedFile != null) {
            // Traiter le fichier CSV sélectionné ici
            System.out.println("Fichier sélectionné : " + selectedFile.getAbsolutePath());

            // Mettre à jour le texte du bouton avec le nom du fichier sélectionné
            addButton.setText(selectedFile.getName());
        }
    }

    public void InterfaceInitialize() {

        // Initialisation du conteneur du menu de gauche
        leftMenuContainer.setPrefHeight(mainContainer.getPrefHeight());

        checkBoxContainer.setVisible(false);
        checkBoxContainer.setPrefHeight(leftMenuContainer.getPrefHeight()-checkBoxContainer.getLayoutY()*2);
        checkBoxContainer.setPrefWidth(leftMenuContainer.getPrefWidth()-checkBoxContainer.getLayoutX()*2);

        checkBoxContainer.setPadding(new Insets(0,0,0,checkBoxMenuBtn.getLayoutX()-checkBoxContainer.getLayoutX()));
        // Initialisation du Dashboard

    }
}
