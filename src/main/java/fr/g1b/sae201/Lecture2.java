package fr.g1b.sae201;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Lecture2 extends Application {

    private static final String CSV_FILE_PATH = "/amuhome/d22020033/IdeaProjects/sae201/src/main/resources/fr/g1b/sae201/seismes.csv";
    private static final String CSV_SPLIT_BY = ",";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Create TableView and columns
        TableView<String[]> tableView = new TableView<>();

        try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE_PATH))) {
            String line;
            boolean isFirstLine = true;
            ObservableList<String[]> data = FXCollections.observableArrayList();

            while ((line = br.readLine()) != null) {
                String[] row = line.split(CSV_SPLIT_BY);

                if (isFirstLine) {
                    // Create columns based on the first line of CSV (header)
                    for (int i = 0; i < row.length; i++) {
                        final int colIndex = i;
                        TableColumn<String[], String> column = new TableColumn<>(row[i]);
                        column.setCellValueFactory(cellData ->
                                Bindings.createStringBinding(() -> cellData.getValue()[colIndex]));
                        tableView.getColumns().add(column);
                    }
                    isFirstLine = false;
                } else {
                    data.add(row);
                }
            }

            // Set the data items of the TableView
            tableView.setItems(data);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Create the layout and scene
        VBox vbox = new VBox(tableView);
        Scene scene = new Scene(vbox, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}