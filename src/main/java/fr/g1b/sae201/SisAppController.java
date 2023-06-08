package fr.g1b.sae201;

import fr.g1b.sae201.dashboardpane.CustomInformationDisplayPane;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import javafx.scene.text.Text;
import org.controlsfx.control.RangeSlider;
import org.controlsfx.control.ToggleSwitch;

import java.util.List;

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

    // Dataset
    private DataGetter dataset;
    private List<String[]> filteredList;
    private String filter;

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

        dataset = new DataGetter(this.getClass().getResource("seismes.csv").getFile());
        CustomInformationDisplayPane cb1 = new CustomInformationDisplayPane(400, 400, dataset.getDataset());
        CustomInformationDisplayPane cb2 = new CustomInformationDisplayPane(400, 400, dataset.getDataset());

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
        filterMenuBtn.setLayoutX(rightMenuContainer.getPrefWidth() - (30.0 + filterMenuBtn.getPrefWidth()));
        filterMenuBtn.setLayoutY(30.0);

        filterContainer.setLayoutY(15.0);

        rightMenuContainer.setPrefHeight(mainContainer.getPrefHeight());
        rightMenuContainer.setLayoutX(mainContainer.getPrefWidth() - rightMenuContainer.getPrefWidth());

        filterContainer.setVisible(isFilterContainerVisible);
        filterContainer.setPrefWidth(rightMenuContainer.getPrefWidth() - 15.0);
        filterContainer.setPrefHeight(rightMenuContainer.getPrefHeight() - filterContainer.getLayoutY() * 2);

        // Initialisation du RangeSlider pour les dates
        dateRangeSlider.setMax(2023);
        dateRangeSlider.setMin(1970);
        dateRangeSlider.setHighValue(2023);
        dateRangeSlider.setLowValue(1970);
        dateRangeSlider.setMajorTickUnit(10);
        dateRangeSlider.setSnapToTicks(true);
        dateRangeSlider.setShowTickLabels(true);
        dateRangeSlider.setMinorTickCount(1);

        // Initialisation du Dashboard
        dashboardScrollContainer.setLayoutY(checkBoxMenuBtn.getLayoutY());
        dashboardScrollContainer.setPrefWidth(mainContainer.getPrefWidth() - (rightMenuSize + leftMenuSize + dashboardScrollContainer.getLayoutX() * 2));
        dashboardScrollContainer.setPrefHeight(mainContainer.getPrefHeight() - (checkBoxMenuBtn.getLayoutY() * 2));

        dashboardContainer.setPrefWidth(dashboardScrollContainer.getPrefWidth() - 40);
    }

    public void createBindings() {
        dateRangeSlider.highValueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                System.out.println(newValue);
                maxValueDateLabel.setText(String.format("%d",newValue.intValue()));
            }
        });

        dateRangeSlider.lowValueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                minValueDateLabel.setText(String.format("%d",newValue.intValue()));
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
    private void applyFilter() {
        StringBuilder sb = new StringBuilder();
        if (toggleBetweenTwoDates.isSelected()) {
            sb.append((int)dateRangeSlider.getLowValue() + "-" + (int)dateRangeSlider.getHighValue());
        } else {
            sb.append("none");
        }
        sb.append(",");
        if (togglePreciseDate.isSelected()) {
            sb.append(datePicker.getValue());
        } else {
            sb.append("none");
        }

        filter = sb.toString();

        filteredList = dataset.applyFilter(filter);

        dashboardContainer.getChildren().clear();
        CustomInformationDisplayPane c1 = new CustomInformationDisplayPane(400,400,filteredList);
        c1.addingBarChartEarthQuakePerYear();

        dashboardContainer.getChildren().add(c1);
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
