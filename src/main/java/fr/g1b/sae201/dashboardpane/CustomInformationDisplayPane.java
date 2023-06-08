package fr.g1b.sae201.dashboardpane;

import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CustomInformationDisplayPane extends Pane {

    private int width;
    private int heigth;
    private List<String[]> dataset;

    public CustomInformationDisplayPane(int width, int heigth, List<String[]> dataset) {
        setPrefWidth(width);
        setPrefHeight(heigth);
        this.width = width;
        this.heigth = heigth;
        this.dataset = dataset;
    }

    public void addingBarChartEarthQuakePerYear() {
        this.getChildren().clear();

        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();

        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);

        Map<String, Integer> yearsCounts = new LinkedHashMap<>();

        int dateColumnIndex = findIndexColumnWithColumnName("Date (AAAA/MM/JJ)");

        if (dateColumnIndex == -1) {
            System.out.println("Error the column you are searching for does not exist");
            return;
        }

        for (int i = 1; i < dataset.size() - 1; i++) {
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

        int regionColumnIndex = findIndexColumnWithColumnName("Région épicentrale");
        int intensityColumnIndex = findIndexColumnWithColumnName("Intensité épicentrale");

        if (regionColumnIndex == -1 || intensityColumnIndex == -1) {
            System.out.println("Error the column you are searching for does not exist");
            return;
        }

        for (int i = 1; i < dataset.size() - 1; i++) {
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
            regionsName.put(region, regionsName.get(region)/nbSeisme.get(region));
        }

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for (String year : regionsName.keySet()) {
            series.getData().add(new XYChart.Data<>(year, regionsName.get(year)));
        }

        barChart.getData().add(series);

        barChart.setPrefWidth(width);
        barChart.setPrefHeight(heigth);

        barChart.setTitle("Intensité moyenne des séismes par région");

        xAxis.setLabel("Année");
        yAxis.setLabel("Nombre de seisme");

        this.getChildren().add(barChart);
    }

    private int findIndexColumnWithColumnName(String columnName) {
        for (int i = 0; i < dataset.get(0).length; i++) {
            if (dataset.get(0)[i].toLowerCase().trim().equals(columnName.toLowerCase().trim())) {
                return i;
            }
        }
        return -1;
    }

}
