package fr.g1b.sae201;

import com.gluonhq.maps.MapLayer;
import com.gluonhq.maps.MapPoint;
import com.gluonhq.maps.MapView;

import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.util.List;

public class AffichageCarte extends Application {
    private MapPoint dernierpoint;
    public static DataGetter listedeseismes = new DataGetter("src/main/resources/fr/g1b/sae201/seismes.csv");
    List<String[]> donneesSeismes = (listedeseismes.getDataset());
    int indexX = DataGetter.findIndexColumnWithColumnName("Lat", donneesSeismes);
    int indexY = DataGetter.findIndexColumnWithColumnName("Long", donneesSeismes);

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

        GridPane root = new GridPane();

        /* Création de la carte Gluon JavaFX */
        MapView carte = new MapView();

        /* Création des points avec latitude et longitude */
        for (int i = 1; i < donneesSeismes.size() - 1; i++) {
            if (!donneesSeismes.get(i)[indexX].isEmpty() && !donneesSeismes.get(i)[indexY].isEmpty()) {
                MapPoint mapPoint = new MapPoint(Double.parseDouble(donneesSeismes.get(i)[indexX]), Double.parseDouble(donneesSeismes.get(i)[indexY]));
                MapLayer mapLayer = new CustomCircleMarkerLayer(mapPoint);
                carte.addLayer(mapLayer);
                dernierpoint = mapPoint;
            } else {
                System.out.println("Erreur: Le seïsme ID " + donneesSeismes.get(i)[0] + " n'a pas de coordonnés.");
            }
        }
        carte.setZoom(5);
        carte.flyTo(0, dernierpoint, 0.1);
        root.getChildren().add(carte);
        Scene scene = new Scene(root, 800, 800);
        stage.setScene(scene);
        stage.setTitle("Carte des séismes selectionnés");
        stage.show();
    }

}