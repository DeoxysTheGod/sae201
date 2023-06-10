package fr.g1b.sae201;

import com.gluonhq.maps.MapLayer;
import com.gluonhq.maps.MapPoint;
import com.gluonhq.maps.MapView;

import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.util.List;

import static java.lang.Math.pow;

public class AffichageCarte extends Application {
    private MapPoint dernierpoint;
    private MapView carte = new MapView();

    public static DataGetter listedeseismes = new DataGetter("src/main/resources/fr/g1b/sae201/seismesgrosseliste.csv");
    List<String[]> donneesSeismes = (listedeseismes.getDataset());

    int indexX = DataGetter.findIndexColumnWithColumnName("Lat", donneesSeismes);
    int indexY = DataGetter.findIndexColumnWithColumnName("Long", donneesSeismes);
    int indexIntensite = DataGetter.findIndexColumnWithColumnName("Intens", donneesSeismes);
    //un peu dangereux, il y a 2 colonnes avec Intens dans le csv donc il risque de confondre mais bon là ça marche donc...

    public static Color couleurIntensite = Color.color(0,0,0);
    public static void main(String[] args) {
        launch();
    }

    public class CustomCircleMarkerLayer extends MapLayer {
        private final MapPoint mapPoint;
        private final Circle circle;
        //public static Color couleurIntensite = Color.color(0, 0, 0);

        /**
         * @param mapPoint le point (latitude et longitude) où afficher le cercle
         * @see com.gluonhq.maps.MapPoint
         */
        public CustomCircleMarkerLayer(MapPoint mapPoint) {
            this.mapPoint = mapPoint;

            /* Cercle rouge de taille 5 */
            this.circle = new Circle(5, couleurIntensite);

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


    /**
     * Cette fonction dessine les points représentant chacun un seïsme sur la carte.
     * @param stage
     */
    public void dessinepoint(Stage stage) {
        for (int i = 1; i < donneesSeismes.size() - 1; i++) {
            /**
             * On vérifie que l'entré dans la colonne des coordonnées lat et long du seïsme n'est pas vide
             */
            if (!donneesSeismes.get(i)[indexX].isEmpty() && !donneesSeismes.get(i)[indexY].isEmpty()) {

                /**
                 * on crée un double qui reprend l'intensité du séisme et la remet sur une échelle de 1 à 0 pour la couleur.
                 * Les seïsmes de faible intensité sont verts, les forts sont rouges.
                 * On supppose que l'intensité maximale est de 9.0. (NE PAS METTRE EN DESSOUS DE 7)
                 */
                double t = Double.parseDouble(donneesSeismes.get(i)[indexIntensite]) / 10.5;
                //LIGNE DEBUG
                //System.out.println(t);
                /**
                 * Ici on arrondit la valeur au cas ou elle dépasserait 1.0 ou serait inférieure à .0
                 */
                double valueG = 1 - t;

                if (valueG < 0.0) {
                    valueG = 0.0;
                    System.out.println("Valeur ajustée");
                } else if (valueG > 1.0) {
                    valueG =  1.0;
                    System.out.println("Valeur ajustée");
                }
                /**
                 * C'est ici que les points sont placés sur la couche des points. On règle d'abord leur couleur, puis on les place sur la carte
                 * en fonction de leurs coordonnées. (MapPoint)
                 */
                couleurIntensite = Color.color(t, valueG, 0);
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
    }


/*
    public void getMapPosition(double sceneX, double sceneY) {
        double x = sceneX - this.getTranslateX();
        double y = sceneY - this.getTranslateY();
        carte.setOnMouseClicked(e -> {
                    MapPoint mapPosition = carte.getMapPosition(e.getSceneX(), e.getSceneY());
                    System.out.println("mapPosition: " + mapPosition.getLatitude() + ", " + mapPosition.getLongitude());
                }
        );
    }
*/


    @Override
    public void start(Stage stage) {

        /* Définit la plate-forme pour éviter "javafx.platform is not defined" */
        System.setProperty("javafx.platform", "desktop");

        GridPane root = new GridPane();

        /* Création des points avec latitude et longitude */

        dessinepoint(stage);

        root.getChildren().add(carte);
        Scene scene = new Scene(root, 800, 800);
        stage.setScene(scene);

        stage.setTitle("Carte des séismes selectionnés");
        stage.show();
    }
}