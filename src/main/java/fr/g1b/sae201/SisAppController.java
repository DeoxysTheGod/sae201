package fr.g1b.sae201;

import fr.g1b.sae201.dashboardpane.CustomInformationDisplayPane;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;


import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.controlsfx.control.RangeSlider;
import org.controlsfx.control.ToggleSwitch;

import java.io.File;
import java.util.*;

import javafx.scene.control.ComboBox;


public class SisAppController {
    // Containers
    @FXML
    private Pane mainContainer; // fenêtre principale

    // Menu de gauche
    @FXML
    private Pane leftMenuContainer;
    @FXML
    private VBox checkBoxContainer;
    @FXML
    private CheckBox pane1;
    @FXML
    private CheckBox pane2;

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
    private Label minValueDateLabel;
    @FXML
    private Label maxValueDateLabel;
    @FXML
    private VBox filterContainer;
    @FXML
    private VBox betweenTwoDatesContainer;
    @FXML
    private ToggleSwitch toggleBetweenTwoDates;
    @FXML
    private DatePicker datePicker;
    @FXML
    private ToggleSwitch togglePreciseDate;
    @FXML
    private ComboBox regionFilter;

    // Controls
    @FXML
    private Button addButton;

    @FXML
    private Button filterMenuBtn;
    @FXML
    private Button checkBoxMenuBtn;

    // Overlay
    @FXML
    private Pane overlayMenu;


    // Taille des menus
    private double rightMenuSize;
    private double leftMenuSize;

    // Dataset
    private DataGetter dataset;
    private List<String[]> filteredList;
    private String filter;
    private ObservableList<String> regions;

    public SisAppController() {
        //dataset = new DataGetter(this.getClass().getResource("seismes.csv").getFile());
        //filteredList = dataset.getDataset();
    }

    @FXML
    public void addCSV() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Sélectionner un fichier CSV");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers CSV", "*.csv"));

        // Afficher la boîte de dialogue de sélection de fichier
        File selectedFile = fileChooser.showOpenDialog(checkBoxContainer.getScene().getWindow());

        if (selectedFile != null) {
            // Traiter le fichier CSV sélectionné ici
            System.out.println("Fichier sélectionné : " + selectedFile.getAbsolutePath());

            // Mettre à jour le texte du bouton avec le nom du fichier sélectionné
            addButton.setText(selectedFile.getName());

            //Mettre le fichier selectionné en tant que DataSet
            dataset = new DataGetter(selectedFile.getAbsolutePath());
            filteredList = dataset.getDataset();
            createBindings();
            initializeRegions();
        }
    }

    private void initializeRegions() {
        int indexregion = DataGetter.findIndexColumnWithColumnName("Région", filteredList);
        Set<String> uniqueRegions = new HashSet<>();
        for (int i = 1; i < filteredList.size() - 1; i++) {
            uniqueRegions.add(filteredList.get(i)[indexregion]);
        }
        ObservableList<String> regions = FXCollections.observableArrayList(uniqueRegions);
        Collections.sort(regions);
        regionFilter.setItems(regions);
    }

    @FXML
    public void initialize() {
        System.out.println("App launched successfully!");

        // initialisation de la taille des deux menus utile pour plus tard
        rightMenuSize = leftMenuContainer.getPrefWidth();
        leftMenuSize = leftMenuContainer.getPrefWidth();
        // Initialisation de l'interface
        InterfaceInitialize();
        /**createBindings();
        initializeRegions();**/
        leftMenuContainer.setPrefWidth(0.0);
        rightMenuContainer.setPrefWidth(0.0);

    }


    @FXML
    public void showCheckBoxMenu() {
        checkBoxContainer.setManaged(!checkBoxContainer.isManaged());
        checkBoxContainer.setVisible(!checkBoxContainer.isVisible());
        if (checkBoxContainer.isVisible()) {
            setMainContainerOverlay(); // Ajouter la superposition pour l'effet d'assombrissement
            leftMenuContainer.setPrefWidth(leftMenuSize);
        } else {
            setMainContainerOverlay(); // Supprimer la superposition
            leftMenuContainer.setPrefWidth(0.0);
        }
    }

    @FXML
    public void checkButton() {
        dashboardContainer.getChildren().clear();
        if (pane1.isSelected()) {
            CustomInformationDisplayPane pane1 = new CustomInformationDisplayPane(400, 400, filteredList);
            pane1.addingBarChartEarthQuakePerYear();
            dashboardContainer.getChildren().add(pane1);
        }
        if (pane2.isSelected()) {
            CustomInformationDisplayPane pane2 = new CustomInformationDisplayPane(400, 400, filteredList);
            pane2.addingBarChartEarthQuakeIntensityPerRegion();
            dashboardContainer.getChildren().add(pane2);
        }

    }

    @FXML
    public void showFilterMenu() {
        filterContainer.setManaged(!filterContainer.isManaged());
        filterContainer.setVisible(!filterContainer.isVisible());
        if (filterContainer.isVisible()) {
            setMainContainerOverlay(); // Ajouter la superposition pour l'effet d'assombrissement
            rightMenuContainer.setPrefWidth(leftMenuSize);
        } else {
            setMainContainerOverlay(); // Supprimer la superposition
            rightMenuContainer.setPrefWidth(0.0);
        }
    }

    @FXML
    private void applyFilter() {
        StringBuilder sb = new StringBuilder();
        if (toggleBetweenTwoDates.isSelected()) {
            sb.append((int) dateRangeSlider.getLowValue() + "-" + (int) dateRangeSlider.getHighValue());
        } else {
            sb.append("none");
        }
        sb.append(",");
        if (togglePreciseDate.isSelected()) {
            sb.append(datePicker.getValue().toString().replace("-", "/"));
        } else {
            sb.append("none");
        }
        sb.append(",");
        if (regionFilter.getValue() != null && !regionFilter.getValue().toString().isEmpty()) {
            sb.append(regionFilter.getValue().toString());
        } else {
            sb.append("none");
        }


        filter = sb.toString();

        filteredList = dataset.applyFilter(filter);
        checkButton();

    }

    private void setMainContainerOverlay() {
        overlayMenu.setVisible(!overlayMenu.isVisible());
        overlayMenu.setManaged(!overlayMenu.isManaged());
    }

    public void InterfaceInitialize() {
        // Image des boutons de menu
        ImageView view = new ImageView(new Image(getClass().getResourceAsStream("menuButton.png")));
        view.setPreserveRatio(true);
        view.setFitWidth(40.0);
        checkBoxMenuBtn.setGraphic(view);

        ImageView view_two = new ImageView(new Image(getClass().getResourceAsStream("menuButton.png")));
        view_two.setPreserveRatio(true);
        view_two.setFitWidth(40.0);
        filterMenuBtn.setGraphic(view_two);

        // Overlay
        overlayMenu.setPrefWidth(mainContainer.getPrefWidth());
        overlayMenu.setPrefHeight(mainContainer.getPrefHeight());

        // Initialisation du conteneur du menu de gauche
        checkBoxMenuBtn.setLayoutX(30.0);
        checkBoxMenuBtn.setLayoutY(30.0);

        leftMenuContainer.setLayoutX(checkBoxMenuBtn.getLayoutX() / 2);
        leftMenuContainer.setLayoutY(checkBoxMenuBtn.getLayoutY() / 2);

        leftMenuContainer.setPrefHeight(mainContainer.getPrefHeight() - leftMenuContainer.getLayoutY() * 2);

        checkBoxContainer.setPrefHeight(leftMenuContainer.getPrefHeight());
        checkBoxContainer.setPrefWidth(leftMenuContainer.getPrefWidth() - checkBoxContainer.getLayoutX());

        checkBoxContainer.setPadding(new Insets(0, 0, 0, checkBoxMenuBtn.getLayoutX() - checkBoxContainer.getLayoutX()));

        // Initialisation du conteneur du menu de droite
        filterMenuBtn.setLayoutX(rightMenuContainer.getPrefWidth() - (30.0 + filterMenuBtn.getPrefWidth()));
        filterMenuBtn.setLayoutY(30.0);

        filterContainer.setLayoutY(15.0);

        rightMenuContainer.setLayoutY(filterMenuBtn.getLayoutY() / 2);
        rightMenuContainer.setPrefHeight(mainContainer.getPrefHeight() - leftMenuContainer.getLayoutX() * 2);
        rightMenuContainer.setLayoutX(mainContainer.getPrefWidth() - (rightMenuContainer.getPrefWidth() + leftMenuContainer.getLayoutX()));

        filterContainer.setPrefWidth(rightMenuContainer.getPrefWidth());
        filterContainer.setPrefHeight(rightMenuContainer.getPrefHeight());

        // Initialisation du RangeSlider pour les dates
        dateRangeSlider.setMax(2007);
        dateRangeSlider.setMin(1500);
        dateRangeSlider.setHighValue(2007);
        dateRangeSlider.setLowValue(1500);
        dateRangeSlider.setMajorTickUnit(10);
        dateRangeSlider.setShowTickLabels(true);

        // Initialisation du Dashboard
        dashboardScrollContainer.setLayoutY(checkBoxMenuBtn.getLayoutY());
        dashboardScrollContainer.setPrefWidth(mainContainer.getPrefWidth() - dashboardScrollContainer.getLayoutX() * 2);
        dashboardScrollContainer.setPrefHeight(mainContainer.getPrefHeight() - (checkBoxMenuBtn.getLayoutY() * 2));

        dashboardContainer.setPrefWidth(dashboardScrollContainer.getPrefWidth() - 40);
    }

    public void createBindings() {
        dateRangeSlider.highValueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                maxValueDateLabel.setText(String.format("%d", newValue.intValue()));
            }
        });

        dateRangeSlider.lowValueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                minValueDateLabel.setText(String.format("%d", newValue.intValue()));
            }
        });

        // filtres : Entre deux dates
        betweenTwoDatesContainer.visibleProperty().bind(toggleBetweenTwoDates.selectedProperty());
        betweenTwoDatesContainer.managedProperty().bind(toggleBetweenTwoDates.selectedProperty());
        betweenTwoDatesContainer.disableProperty().bind(toggleBetweenTwoDates.selectedProperty().not());

        // filtres : Date Précise
        datePicker.visibleProperty().bind(togglePreciseDate.selectedProperty());
        datePicker.managedProperty().bind(togglePreciseDate.selectedProperty());
        datePicker.disableProperty().bind(togglePreciseDate.selectedProperty().not());

        toggleBetweenTwoDates.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
                System.out.println(newValue);
                togglePreciseDate.setSelected(!newValue);
            }
        });

        togglePreciseDate.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
                System.out.println(newValue);
                toggleBetweenTwoDates.setSelected(!newValue);
            }
        });

    }

    @FXML
    private void clearFilter() {
        dateRangeSlider.setLowValue(1500);
        dateRangeSlider.setHighValue(2007);
        toggleBetweenTwoDates.setSelected(false);
        togglePreciseDate.setSelected(false);
        datePicker.setValue(null);
        regionFilter.getSelectionModel().clearSelection();
        regionFilter.setPromptText("Séléctionnez une région ");


        filter = "";
        filteredList = dataset.getDataset();
        checkButton();


    }


}
