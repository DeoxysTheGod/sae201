package fr.g1b.sae201;

import fr.g1b.sae201.dashboardpane.CustomPaneBarChart;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.*;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import javafx.util.StringConverter;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.NumberStringConverter;
import org.controlsfx.control.RangeSlider;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

import java.io.File;
import java.util.concurrent.Callable;

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
    private ScrollPane dashboardScrollContainer;
    @FXML
    private FlowPane dashboardContainer;

    // Menu de droite
    @FXML
    private Pane rightMenuContainer;
    @FXML
    private RangeSlider dateRangeSlider;
    @FXML
    private TextField minValueDateTextField;
    @FXML
    private TextField maxValueDateTextField;
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

    private void setMainContainerOverlay(boolean overlay) {
        if (overlay) {
            mainContainer.getStyleClass().add("overlay");
        } else {
            mainContainer.getStyleClass().remove("overlay");
        }
    }

    public SisAppController() {
        isCheckBoxContainerVisible = false;
        isFilterContainerVisible = false;
    }

    public void initialize() {
        System.out.println("App launched successfully!");

        ImageView view = new ImageView(new Image(getClass().getResourceAsStream("menuButton.png")));
        view.setPreserveRatio(true);
        view.setFitWidth(40.0);
        checkBoxMenuBtn.setGraphic(view);

        ImageView view_two = new ImageView(new Image(getClass().getResourceAsStream("menuButton.png")));
        view_two.setPreserveRatio(true);
        view_two.setFitWidth(40.0);
        filterMenuBtn.setGraphic(view_two);


        // Initialisation de l'interface
        InterfaceInitialize();
        createBindings();

        rightMenuContainer.setPrefWidth(0.0);
        leftMenuContainer.setPrefWidth(0.0);

        LectureBis l1 = new LectureBis(this.getClass().getResource("seismes.csv").getFile());
        CustomPaneBarChart cb1 = new CustomPaneBarChart(400,400,l1.getDataset());
        CustomPaneBarChart cb2 = new CustomPaneBarChart(400,400,l1.getDataset());

        cb1.addingBarChartEarthQuakePerYear();
        cb2.addingBarChartEarthQuakeIntensityPerRegion();

        dashboardContainer.getChildren().addAll(cb1, cb2);
    }
    @FXML
    private void showCheckBoxMenu() {
        isCheckBoxContainerVisible = !isCheckBoxContainerVisible;
        checkBoxContainer.setManaged(isCheckBoxContainerVisible);
        checkBoxContainer.setVisible(isCheckBoxContainerVisible);
        if (isCheckBoxContainerVisible) {
            setMainContainerOverlay(true); // Ajouter la superposition pour l'effet d'assombrissement
            leftMenuContainer.setPrefWidth(rightMenuSize);
        } else {
            setMainContainerOverlay(false); // Supprimer la superposition
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

    /* pas utile pour l'instant
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
    */

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

        // Initialisation du RangeSlider pour les dates
        dateRangeSlider.setMax(2023);
        dateRangeSlider.setMin(1970);
        dateRangeSlider.setHighValue(2023);
        dateRangeSlider.setLowValue(1970);
        dateRangeSlider.setMajorTickUnit(10);
        dateRangeSlider.setBlockIncrement(10);
        dateRangeSlider.setSnapToTicks(true);
        dateRangeSlider.setShowTickLabels(true);

        // Initialisation du Dashboard
        dashboardScrollContainer.setLayoutY(checkBoxMenuBtn.getLayoutY());
        dashboardScrollContainer.setPrefWidth(mainContainer.getPrefWidth()-(rightMenuSize+leftMenuSize+dashboardScrollContainer.getLayoutX()*2));
        dashboardScrollContainer.setPrefHeight(mainContainer.getPrefHeight()-(checkBoxMenuBtn.getLayoutY()*2));

        dashboardContainer.setPrefWidth(dashboardScrollContainer.getPrefWidth()-40);
    }

    public void createBindings() {
        /*
        StringProperty dateFieldMaxValue = maxValueDateTextField.textProperty();
        IntegerProperty it = new SimpleIntegerProperty();
        DoubleProperty dateSliderMaxValue = dateRangeSlider.highValueProperty();

        it.bind(Bindings.createIntegerBinding((Callable<Integer>) () -> (int) dateSliderMaxValue.get(), (Observable) dateRangeSlider));

        dateRangeSlider.highValueProperty().bindBidirectional(dateSliderMaxValue);

        maxValueDateTextField.textProperty().bind(it.asString());

        StringConverter<Number> converter = new NumberStringConverter();
        Bindings.bindBidirectional(dateFieldMaxValue, dateSliderMaxValue, converter);
         */
    }

    @FXML
    private void setMainContainerOpacity(double opacity) {
        mainContainer.setOpacity(opacity);
    }

    @FXML
    public void mainContainerBack() {
        setMainContainerOverlay(false); // Supprimer la superposition
    }




}
