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

public class AffichageCarte {
    private MapPoint dernierpoint;
    private MapView carte;

    List<String[]> donneesSeismes;

    public static Color couleurIntensite = Color.color(0,0,0);

    public AffichageCarte(List<String[]> dataset) {
        donneesSeismes = dataset;
        carte = new MapView();
    }

    public void dessinepoint() {
        int indexX = DataGetter.findIndexColumnWithColumnName("Lat", donneesSeismes);
        int indexY = DataGetter.findIndexColumnWithColumnName("Long", donneesSeismes);
        int indexIntensite = DataGetter.findIndexColumnWithColumnName("Intens", donneesSeismes);

        if (indexX == -1 || indexY == -1) {
            return;
        }

        for (int i = 1; i < donneesSeismes.size() - 1; i++) {
            /**
             * On vérifie que l'entrée dans la colonne des coordonnés lat et long du seisme n'est pas vide
             */
            if (!donneesSeismes.get(i)[indexX].isEmpty() && !donneesSeismes.get(i)[indexY].isEmpty()) {

                /**
                 * On crée un double qui reprend l'intensité du séisme et la remet sur une échelle de 1 à 0 pour la couleur.
                 * Les seismes de faible intensité sont verts, les forts sont rouges.
                 * On suppose que l'intensité maximale est de 9.0. (NE PAS METTRE EN DESSOUS DE 7)
                 */
                double valueR = Double.parseDouble(donneesSeismes.get(i)[indexIntensite]) / 10.5;
                //LIGNE DEBUG
                //System.out.println(t);
                /**
                 * Ici, on arrondit la valeur au cas où elle dépasserait 1.0 ou serait inférieure à 0
                 */
                double valueG = 1 - valueR;

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
                couleurIntensite = Color.color(valueR, valueG, 0);
                MapPoint mapPoint = new MapPoint(Double.parseDouble(donneesSeismes.get(i)[indexX]), Double.parseDouble(donneesSeismes.get(i)[indexY]));
                CustomCircleMarkerLayer mapLayer = new CustomCircleMarkerLayer(mapPoint, couleurIntensite);
                carte.addLayer(mapLayer);
                dernierpoint = mapPoint;
            } else {
                System.out.println("Erreur: Le seisme ID " + donneesSeismes.get(i)[0] + " n'a pas de coordonnés.");
            }
        }
        carte.setZoom(5);
        carte.flyTo(0, dernierpoint, 0.1);
    }

    public MapView getMap() {
        return carte;
    }
}