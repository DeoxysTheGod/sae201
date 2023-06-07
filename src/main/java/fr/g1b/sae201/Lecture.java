package fr.g1b.sae201;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import javafx.application.Application;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class Lecture extends Application {

    private static List<String> col1 = new ArrayList<>();
    private static List<String> col2 = new ArrayList<>();
    private static List<String> col3 = new ArrayList<>();
    private static List<String> col4 = new ArrayList<>();
    private static List<String> col5 = new ArrayList<>();
    private static List<String> col6 = new ArrayList<>();
    private static List<String> col7 = new ArrayList<>();
    private static List<String> col8 = new ArrayList<>();
    private static List<String> col9 = new ArrayList<>();
    private static List<String> col10 = new ArrayList<>();
    private static List<String> col11 = new ArrayList<>();
    private static List<String> col12 = new ArrayList<>();


    public static void main(String[] args) {
        String csvFile = Lecture.class.getResource("seismes.csv").getFile();
        String line;
        String csvSplitBy = ",";

        /**
         *
         * Les données CSV dans 12 listes distinctes (tableaux dynamiques)
         * Identifiant, 0
         * "Date (AAAA/MM/JJ)" 1
         * Heure, 2
         * Nom, 3
         * "Région épicentrale", 4
         * Choc, 5
         * "X RGF93/L93", 6
         * "Y RGF93/L93", 7
         * "Latitude en WGS 84", 8
         * "Longitude en WGS 84", 9
         * "Intensité épicentrale", 10
         * "Qualité intensité épicentrale" 11
         **/



        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                String[] data = line.split(csvSplitBy);
                col1.add(data[0]);
                col2.add(data[1]);
                col3.add(data[2]);
                col4.add(data[3]);
                col5.add(data[4]);
                col6.add(data[5]);
                col7.add(data[6]);
                col8.add(data[7]);
                col9.add(data[8]);
                col10.add(data[9]);
                col11.add(data[10]);
                col12.add(data[11]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(col1);
    launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        // Création du conteneur principal
        VBox vbox = new VBox();

        // Création du conteneur correspondant à la ligne de contrôle haut dessus du tableau
        HBox topControls = new HBox();
        topControls.setAlignment( Pos.BOTTOM_LEFT );
        Button btnRefresh = new Button("Refresh");

        HBox topRightControls = new HBox();
        HBox.setHgrow(topRightControls, Priority.ALWAYS );
        topRightControls.setAlignment( Pos.BOTTOM_RIGHT );
        Hyperlink signOutLink = new Hyperlink("Sign Out");
        topRightControls.getChildren().add( signOutLink );

        topControls.getChildren().addAll( btnRefresh, topRightControls );

        // Création du tableau avec les 12 colonnes
        TableView<Object> tblSeismes = new TableView<>();
        //tblSeismes.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        TableColumn<Object, String> date = new TableColumn<>("Date");
        TableColumn<Object, String> heure = new TableColumn<>("Heure");
        TableColumn<Object, String> nom = new TableColumn<>("Nom");
        TableColumn<Object, String> epicentre = new TableColumn<>("Région épicentrale");
        TableColumn<Object, String> choc = new TableColumn<>("Choc");
        TableColumn<Object, String> x = new TableColumn<>("X");
        TableColumn<Object, String> y = new TableColumn<>("Y");
        TableColumn<Object, String> intensite = new TableColumn<>("Intensité épicentrale");

        tblSeismes.getColumns().addAll( date, heure, nom, epicentre, choc, x, y, intensite );
        VBox.setVgrow( tblSeismes, Priority.ALWAYS );

        date.setCellValueFactory(data -> new SimpleStringProperty());
        heure.setCellValueFactory(data -> new SimpleStringProperty(col1.toString()));
        nom.setCellValueFactory(data -> new SimpleStringProperty(col2.toString()));
        epicentre.setCellValueFactory(data -> new SimpleStringProperty(col3.toString()));
        choc.setCellValueFactory(data -> new SimpleStringProperty(col4.toString()));
        x.setCellValueFactory(data -> new SimpleStringProperty(col5.toString()));
        y.setCellValueFactory(data -> new SimpleStringProperty(col6.toString()));
        intensite.setCellValueFactory(data -> new SimpleStringProperty(col9.toString()));

        // Création de la ligne de séparation
        Separator sep = new Separator();

        // Création du bandeau en bas de la fenêtre
        HBox bottomControls = new HBox();
        bottomControls.setAlignment(Pos.BOTTOM_RIGHT );
        Button btnClose = new Button("Close");
        bottomControls.getChildren().add( btnClose );

        // Ajout des contrôleurs au conteneur principal
        vbox.getChildren().addAll(
                topControls,
                tblSeismes,
                sep,
                bottomControls
        );

        VBox.setMargin( topControls, new Insets(10.0d) );
        VBox.setMargin( tblSeismes, new Insets(0.0d, 10.0d, 10.0d, 10.0d) );
        VBox.setMargin( bottomControls, new Insets(10.0d) );

        // Ajout du conteneur à la scene
        Scene scene = new Scene(vbox );

        // Ajout de la scene à la fenêtre et changement de ses paramètres (dimensions et titre)
        primaryStage.setScene( scene );
        primaryStage.setWidth( 800 );
        primaryStage.setHeight( 600 );
        primaryStage.setTitle("VBox and HBox App");

        // Affichage de la fenêtre

        primaryStage.show();
    }

}


