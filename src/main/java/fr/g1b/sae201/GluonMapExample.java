package fr.g1b.sae201;

import com.gluonhq.maps.MapLayer;
import com.gluonhq.maps.MapPoint;
import com.gluonhq.maps.MapView;

import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.util.List;

public class GluonMapExample extends Application {

    public static DataGetter listedeseismes = new DataGetter("/amuhome/d22020033/IdeaProjects/sae201/src/main/resources/fr/g1b/sae201/seismes.csv");
    List<String[]> donneesSeismes = (listedeseismes.getDataset());
    int indexX = DataGetter.findIndexColumnWithColumnName("Latitude", donneesSeismes);
    int indexY = DataGetter.findIndexColumnWithColumnName("Longitude", donneesSeismes);

    public static void main(String[] args) {
        launch();
    }

    /**
     * Affiche un point rouge sur la carte
     */
    public class CustomCircleMarkerLayer extends MapLayer {

        private final MapPoint mapPoint;
        private final Circle circle;

        /**
         * @param mapPoint le point (latitude et longitude) où afficher le cercle
         * @see com.gluonhq.maps.MapPoint
         */
        public CustomCircleMarkerLayer(MapPoint mapPoint) {
            this.mapPoint = mapPoint;

            /* Cercle rouge de taille 5 */
            this.circle = new Circle(5, Color.RED);

            /* Ajoute le cercle au MapLayer */
            this.getChildren().add(circle);
        }

        /* La fonction est appelée à chaque rafraichissement de la carte */
        @Override
        protected void layoutLayer() {
            /* Conversion du MapPoint vers Point2D */
            Point2D point2d = this.getMapPoint(mapPoint.getLatitude(), mapPoint.getLongitude());

            /* Déplace le cercle selon les coordonnées du point */
            circle.setTranslateX(point2d.getX());
            circle.setTranslateY(point2d.getY());
        }
    }

    @Override
    public void start(Stage stage) {


        /* Définit la plate-forme pour éviter "javafx.platform is not defined" */
        System.setProperty("javafx.platform", "desktop");

        /*
         * Définit l'user agent pour éviter l'exception
         * "Server returned HTTP response code: 403"
         */
        System.setProperty("http.agent", "Gluon Mobile/1.0.3");

        VBox root = new VBox();

        /* Création de la carte Gluon JavaFX */
        MapView mapView = new MapView();

        /* Création du point avec latitude et longitude */
        for (int i = 1; i < donneesSeismes.size(); i++) {
            MapPoint mapPoint = new MapPoint(Double.parseDouble(donneesSeismes.get(i)[indexX]),
                    Double.parseDouble(donneesSeismes.get(i)[indexY]));
            //ça coince ici

            /* Création et ajoute une couche à la carte */


            //LIGNES DEBUG
            //System.out.println(donneesSeismes.get(i)[indexY]);
            //System.out.println(donneesSeismes.get(i)[indexX]);

            MapLayer mapLayer = new CustomCircleMarkerLayer(mapPoint);
            mapView.addLayer(mapLayer);

        }

        root.getChildren().add(mapView);

        /*
         * IMPORTANT mettre la taille de la fenêtre pour éviter l'erreur
         * java.lang.OutOfMemoryError
         */
        Scene scene = new Scene(root, 640, 480);

        stage.setScene(scene);
        stage.show();

    }

}