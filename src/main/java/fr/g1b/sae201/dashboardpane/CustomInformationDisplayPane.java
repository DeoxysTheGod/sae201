package fr.g1b.sae201.dashboardpane;

import com.gluonhq.maps.MapLayer;
import com.gluonhq.maps.MapPoint;
import com.gluonhq.maps.MapView;
import fr.g1b.sae201.AffichageCarte;
import fr.g1b.sae201.DataGetter;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CustomInformationDisplayPane extends VBox {

    private int width;
    private int heigth;
    private List<String[]> dataset;

    public CustomInformationDisplayPane(int width, int height, List<String[]> dataset) {
        setPrefWidth(width);
        setPrefHeight(height);
        this.width = width;
        this.heigth = height;
        this.dataset = dataset;
        this.getStyleClass().add("dashboardItem");
    }

    public void addingMaxIntensity() {
        this.getChildren().clear();

        int dateColumnIndex = DataGetter.findIndexColumnWithColumnName("Date", dataset);
        int intensityColumnIndex = DataGetter.findIndexColumnWithColumnName("Intensité épicentrale", dataset);
        int regionColumnIndex = DataGetter.findIndexColumnWithColumnName("Région", dataset);

        int maxIntensityIndex = 1;
        int minIntensityIndex = 1;

        for (int i = 1; i < dataset.size(); i++) {
            if (Double.parseDouble(dataset.get(i)[intensityColumnIndex])
                    >= Double.parseDouble(dataset.get(maxIntensityIndex)[intensityColumnIndex])) {
                maxIntensityIndex = i;
            }
            if (Double.parseDouble(dataset.get(i)[intensityColumnIndex])
                    <= Double.parseDouble(dataset.get(maxIntensityIndex)[intensityColumnIndex])) {
                minIntensityIndex = i;
            }
        }

        Label dateMaxLabel = new Label(dataset.get(maxIntensityIndex)[dateColumnIndex]);
        Label intensityMaxLabel = new Label(dataset.get(maxIntensityIndex)[intensityColumnIndex]);
        intensityMaxLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 24px");
        Label regionMaxNameLabel = new Label(dataset.get(maxIntensityIndex)[regionColumnIndex]);

        Separator sep = new Separator();

        Label dateMinLabel = new Label(dataset.get(minIntensityIndex)[dateColumnIndex]);
        Label intensityMinLabel = new Label(dataset.get(minIntensityIndex)[intensityColumnIndex]);
        intensityMinLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 24px");
        Label regionMinNameLabel = new Label(dataset.get(minIntensityIndex)[regionColumnIndex]);

        VBox labelContainer = new VBox();
        labelContainer.setAlignment(Pos.CENTER);

        labelContainer.getStyleClass().add("minAndMaxPane");

        labelContainer.getChildren().addAll(dateMaxLabel, intensityMaxLabel, regionMaxNameLabel, sep,
                dateMinLabel, intensityMinLabel, regionMinNameLabel);

        this.getChildren().addAll(labelContainer);
    }

    public void addingMap() {
        this.getChildren().clear();
        AffichageCarte map = new AffichageCarte(dataset);
        map.dessinepoint();
        map.getMap().setPrefWidth(width);
        map.getMap().setPrefHeight(heigth);
        this.getChildren().add(map.getMap());
    }

    public void addingTable() {
        this.getChildren().clear();

        TableView tableView = new TableView<>();
        boolean isFirstLine = true;
        ObservableList<String[]> data = FXCollections.observableArrayList();
        for (String[] row : dataset) {
            if (isFirstLine) {
                for (int i = 0; i < row.length; i++) {
                    final int colIndex = i;
                    TableColumn<String[], String> column = new TableColumn<>(row[i]);
                    column.setCellValueFactory(cellData -> Bindings.createStringBinding(
                            () -> cellData.getValue()[colIndex]));
                    tableView.getColumns().add(column);
                }
                isFirstLine = false;
            } else {
                data.add(row);
            }
        }
        tableView.setItems(data);

        tableView.setPrefWidth(width);
        tableView.setPrefHeight(heigth);

        this.getChildren().add(tableView);
    }

    public void addingBarChartEarthQuakePerYear() {
        this.getChildren().clear();

        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();

        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);

        Map<String, Integer> yearsCounts = new LinkedHashMap<>();

        int dateColumnIndex = DataGetter.findIndexColumnWithColumnName("Date", dataset);

        if (dateColumnIndex == -1) {
            System.out.println("Error : the column you are searching for does not exist.");
            return;
        }

        for (int i = 1; i < dataset.size(); i++) {
            String year = dataset.get(i)[dateColumnIndex].substring(0, 4);
            if (yearsCounts.containsKey(year)) {
                yearsCounts.put(year, yearsCounts.get(year) + 1);
            } else {
                yearsCounts.put(year, 1);
            }
        }

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for (String year : yearsCounts.keySet()) {
            series.getData().add(new XYChart.Data<>(year, yearsCounts.get(year)));
        }

        barChart.getData().add(series);

        barChart.setPrefWidth(width);
        barChart.setPrefHeight(heigth);

        barChart.setTitle("Nombre de séismes par année");

        xAxis.setLabel("Année");
        yAxis.setLabel("Nombre de seisme");

        this.getChildren().add(barChart);
    }

    public void addingBarChartEarthQuakeIntensityPerRegion() {
        this.getChildren().clear();

        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();

        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);

        Map<String, Double> regionsName = new LinkedHashMap<>();
        Map<String, Integer> nbSeisme = new LinkedHashMap<>();

        int regionColumnIndex = DataGetter.findIndexColumnWithColumnName("Région épicentrale", dataset);
        int intensityColumnIndex = DataGetter.findIndexColumnWithColumnName("Intensité épicentrale", dataset);

        if (regionColumnIndex == -1 || intensityColumnIndex == -1) {
            System.out.println("Error the column you are searching for does not exist");
            return;
        }

        for (int i = 1; i < dataset.size(); i++) {
            String region = dataset.get(i)[regionColumnIndex];
            double intensity = Double.parseDouble(dataset.get(i)[intensityColumnIndex]);
            if (regionsName.containsKey(region)) {
                regionsName.put(region, regionsName.get(region) + intensity);
                nbSeisme.put(region, nbSeisme.get(region) + 1);
            } else {
                regionsName.put(region, intensity);
                nbSeisme.put(region, 1);
            }
        }

        for (String region : regionsName.keySet()) {
            regionsName.put(region, regionsName.get(region) / nbSeisme.get(region));
        }

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for (String year : regionsName.keySet()) {
            series.getData().add(new XYChart.Data<>(year, regionsName.get(year)));
        }

        barChart.getData().add(series);

        barChart.setPrefWidth(width);
        barChart.setPrefHeight(heigth);

        barChart.setTitle("Intensité moyenne des séismes par région");

        xAxis.setLabel("Région");
        yAxis.setLabel("Intensité");

        this.getStyleClass().add("dashboardItem");
        this.getChildren().add(barChart);
    }
}
