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

    // Menu de gauche
    @FXML
    private Pane leftMenuContainer;
    @FXML
    private VBox checkBoxContainer;

    // Dashboard
    @FXML
    private FlowPane dashboardContainer;

    // Menu de droite
    @FXML
    private Pane rightMenuContainer;
    @FXML
    private VBox filterContainer;

    // Controls
    @FXML
    private Button addButton;

    @FXML
    private Button filterMenuBtn;
    @FXML
    private Button checkBoxMenuBtn;


    // Autres
    private boolean isCheckBoxContainerVisible;
    private boolean isFilterContainerVisible;

    // Taille des menus
    private double rightMenuSize;
    private double leftMenuSize;

    public SisAppController() {
        isCheckBoxContainerVisible = false;
        isFilterContainerVisible = false;
    }

    public void initialize() {
        System.out.println("App launched successfully!");

        ImageView view = new ImageView(new Image("fr/g1b/sae201/menuButton.png"));
        ImageView view_two = new ImageView(new Image("fr/g1b/sae201/menuButton.png"));
        view.setPreserveRatio(true);
        view.setFitWidth(30);
        view_two.setPreserveRatio(true);
        view_two.setFitWidth(30);
        checkBoxMenuBtn.setGraphic(view);
        filterMenuBtn.setGraphic(view_two);



        // Initialisation de l'interface
        InterfaceInitialize();

        rightMenuContainer.setPrefWidth(0.0);
        leftMenuContainer.setPrefWidth(0.0);
    }

    public void showCheckBoxMenu() {
        isCheckBoxContainerVisible = !isCheckBoxContainerVisible;
        checkBoxContainer.setManaged(isCheckBoxContainerVisible);
        checkBoxContainer.setVisible(isCheckBoxContainerVisible);
        if (isCheckBoxContainerVisible) {
            leftMenuContainer.setPrefWidth(rightMenuSize);
        } else {
            leftMenuContainer.setPrefWidth(0.0);
        }
    }

    public void showFilterMenu() {
        isFilterContainerVisible = !isFilterContainerVisible;
        filterContainer.setManaged(isFilterContainerVisible);
        filterContainer.setVisible(isFilterContainerVisible);
        if (isFilterContainerVisible) {
            leftMenuContainer.setPrefWidth(leftMenuSize);
        } else {
            leftMenuContainer.setPrefWidth(0.0);
        }
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
        checkBoxMenuBtn.setLayoutX(30.0);
        checkBoxMenuBtn.setLayoutY(30.0);

        checkBoxContainer.setLayoutX(15.0);
        checkBoxContainer.setLayoutY(15.0);

        leftMenuContainer.setPrefHeight(mainContainer.getPrefHeight());

        checkBoxContainer.setVisible(isCheckBoxContainerVisible);
        checkBoxContainer.setPrefHeight(leftMenuContainer.getPrefHeight() - checkBoxContainer.getLayoutY() * 2);
        checkBoxContainer.setPrefWidth(leftMenuContainer.getPrefWidth() - checkBoxContainer.getLayoutX());

        checkBoxContainer.setPadding(new Insets(0, 0, 0, checkBoxMenuBtn.getLayoutX() - checkBoxContainer.getLayoutX()));

        // Initialisation du conteneur du menu de droite
        filterMenuBtn.setLayoutX(rightMenuContainer.getPrefWidth()-(30.0 + filterMenuBtn.getPrefWidth()));
        filterMenuBtn.setLayoutY(30.0);

        filterContainer.setLayoutY(15.0);

        rightMenuContainer.setPrefHeight(mainContainer.getPrefHeight());
        rightMenuContainer.setLayoutX(mainContainer.getPrefWidth() - rightMenuContainer.getPrefWidth());

        filterContainer.setVisible(isFilterContainerVisible);
        filterContainer.setPrefWidth(rightMenuContainer.getPrefWidth() - 15.0);
        filterContainer.setPrefHeight(rightMenuContainer.getPrefHeight() - filterContainer.getLayoutY()*2);

        // Initialisation du Dashboard
        dashboardContainer.setLayoutY(checkBoxMenuBtn.getLayoutY());

    }
}
