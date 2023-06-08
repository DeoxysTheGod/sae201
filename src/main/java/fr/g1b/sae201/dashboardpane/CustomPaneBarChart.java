package fr.g1b.sae201.dashboardpane;

import fr.g1b.sae201.LectureBis;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiFunction;

public class CustomPaneBarChart extends Pane {

    private int width;
    private int heigth;
    private ArrayList<String[]> dataset;

    public CustomPaneBarChart(int width, int heigth, ArrayList<String[]> dataset) {
        setPrefWidth(width);
        setPrefHeight(heigth);
        this.width = width;
        this.heigth = heigth;
        this.dataset = dataset;
    }

    public void addingBarChartEarthQuakePerYear() {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();

        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);

        Map<String, Integer> yearsCounts = new LinkedHashMap<>();

        int dateColumnIndex = findIndexColumnWithColumnName("Date (AAAA/MM/JJ)");

        if (dateColumnIndex == -1) {
            System.out.println("Error the column you are searching for does not exist");
            return;
        }
        int nbSeisme = 0;
        for (int i = 1; i < dataset.size() - 1; i++) {
            String year = dataset.get(i)[dateColumnIndex].substring(0, 4);
            nbSeisme++;
            if (!year.equals(dataset.get(i + 1)[dateColumnIndex].substring(0, 4))) {
                yearsCounts.put(year, nbSeisme);
                nbSeisme = 0;
            }
        }

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for (String year : yearsCounts.keySet()) {
            series.getData().add(new XYChart.Data<>(year, yearsCounts.get(year)));
        }

        barChart.getData().add(series);

        barChart.setPrefWidth(width);
        barChart.setPrefHeight(heigth);

        xAxis.setLabel("Année");
        yAxis.setLabel("Nombre de seisme");

        this.getChildren().add(barChart);
    }

    public void addingBarChartEarthQuakeIntensityPerRegion() {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();

        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);

        Map<String, Double> regionsName = new LinkedHashMap<>();

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
            } else {
                regionsName.put(region, intensity);
            }
        }

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for (String year : regionsName.keySet()) {
            series.getData().add(new XYChart.Data<>(year, regionsName.get(year)));
        }

        barChart.getData().add(series);

        barChart.setPrefWidth(width);
        barChart.setPrefHeight(heigth);

        xAxis.setLabel("Année");
        yAxis.setLabel("Nombre de seisme");

        this.getChildren().add(barChart);
    }

    private int findIndexColumnWithColumnName(String columnName) {
        for (int i = 0; i < dataset.get(0).length; i++) {
            System.out.println(dataset.get(0)[i].toLowerCase().trim());
            if (dataset.get(0)[i].toLowerCase().trim().equals(columnName.toLowerCase().trim())) {
                return i;
            }
        }
        return -1;
    }
}
