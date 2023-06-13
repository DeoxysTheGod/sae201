package fr.g1b.sae201;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;


public class fenetreintro {


    public static void display()
    {
        Stage popupwindow=new Stage();

        popupwindow.initModality(Modality.APPLICATION_MODAL);
        popupwindow.setTitle("Tutoriel d'utilisation");

        Label label1= new Label("Commençons par séléctionner un fichier CSV (menu de droite...)");
        label1.setWrapText(true);
        label1.setPadding(new Insets(0, 10,0,10));

        Button button1= new Button("Fermer");
        button1.setOnAction(e -> popupwindow.close());

        VBox layout= new VBox(10);
        layout.getChildren().addAll(label1, button1);
        layout.setAlignment(Pos.CENTER);
        Scene scene1= new Scene(layout, 300, 100);
        popupwindow.setScene(scene1);
        popupwindow.showAndWait();
    }
}
