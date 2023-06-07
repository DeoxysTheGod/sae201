package fr.g1b.sae201.dashboardpane;

import fr.g1b.sae201.LectureBis;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

        this.addingBarChartEarthQuakePerYear();
    }

    public void addingBarChartEarthQuakePerYear() {

        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();

        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);

        Map<String, Integer> yearsCounts = new HashMap<>();

        int dateColumnIndex = -1;
        for (int i = 0; i < dataset.get(0).length; i++) {
            if (dataset.get(0)[i].toLowerCase().trim().equals("date (aaaa/mm/jj)")) {
                dateColumnIndex = i;
                break;
            }
        }
        if (dateColumnIndex == -1) {
            System.out.println("Error the column you are searching for does not exist");
            return;
        }
        int nbSeisme = 0;
        for (int i = 1; i < dataset.size()-1; i++) {
            String year = dataset.get(i)[dateColumnIndex].substring(0,4);
            nbSeisme++;
            if (!year.equals(dataset.get(i+1)[dateColumnIndex].substring(0,4))) {
                yearsCounts.put(year, nbSeisme);
                nbSeisme = 0;
            }
        }

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for (String year: yearsCounts.keySet()) {
            series.getData().add(new XYChart.Data<>(year, yearsCounts.get(year)));
        }

        barChart.getData().add(series);

        barChart.setPrefWidth(width);
        barChart.setPrefHeight(heigth);

        xAxis.setLabel("Years");
        yAxis.setLabel("Nombre de seisme");

        this.getChildren().add(barChart);
    }
}
