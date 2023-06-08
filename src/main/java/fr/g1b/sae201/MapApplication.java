package fr.g1b.sae201;

import javafx.application.Application;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import com.gluonhq.maps.*;

public class MapApplication extends Application {
    @Override
    public void start(Stage primaryStage) {
        //WebView webView = new WebView();
        //webView.getEngine().load("https://giphy.com/gifs/rick-roll-g7GKcSzwQfugw"); //Ouais tant pis j'ai rien dit on peut abandonner ça
        MapView carte = new MapView();

        Circle circle = new Circle(7, Color.RED);
        circle.setCursor(Cursor.HAND);
        circle.setVisible(true);

        Scene scene = new Scene(carte, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}