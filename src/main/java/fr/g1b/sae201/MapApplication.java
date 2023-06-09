package fr.g1b.sae201;

import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import com.gluonhq.maps.*;

import java.util.List;

public class MapApplication extends Application {
    public static MapView carte;
    public static DataGetter listedeseismes = new DataGetter("/amuhome/d22020033/IdeaProjects/sae201/src/main/resources/fr/g1b/sae201/seismes.csv");

    //public static MapLayer layer;
    @Override
    public void start(Stage primaryStage) {
        carte = new MapView();
        Scene scene = new Scene(carte, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("The carte");
        primaryStage.setFullScreen(true);
        primaryStage.show();

        addPoint();
    }

    public void addPoint() {
        Circle circle = new Circle(7, Color.RED);
        circle.setVisible(true);
        List<String[]> donneesSeismes = (listedeseismes.getDataset());
        int indexX = DataGetter.findIndexColumnWithColumnName("latitude", donneesSeismes);
        int indexY = DataGetter.findIndexColumnWithColumnName("longtitude", donneesSeismes);
        MapLayer mapLayer = new MapLayer();
        carte.addLayer(mapLayer);

        //for (int i = 1; i < donneesSeismes.size(); i++) {
            /* Création du point avec latitude et longitude */
            MapPoint mapPoint = new MapPoint(67.75632215237778, -43.459301016165384);

            /* Conversion du MapPoint vers Point2D */
            Point2D point2d = carte.getMapPoint(mapPoint.getLatitude(), mapPoint.getLongitude());

            /* Déplace le cercle selon les coordonnées du point */
            circle.setTranslateX(point2d.getX());
            circle.setTranslateY(point2d.getY());


            /* Zoom de 5 */
            carte.setZoom(5);

            /* Centre la carte sur le point */
            carte.flyTo(0, mapPoint, 0.1);

        //}
    }
}

