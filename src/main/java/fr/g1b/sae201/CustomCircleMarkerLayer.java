package fr.g1b.sae201;

import com.gluonhq.maps.MapLayer;
import com.gluonhq.maps.MapPoint;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Classe personnalisée pour les points de notre carte
 */
public class CustomCircleMarkerLayer extends MapLayer {
    private MapPoint mapPoint;
    private Circle circle;

    /**
     * @param mapPoint Le point à customiser
     * @param couleurIntensite La couleur du point
     */
    public CustomCircleMarkerLayer(MapPoint mapPoint, Color couleurIntensite) {
        this.mapPoint = mapPoint;
        this.circle = new Circle(5, couleurIntensite);

        /* Ajoute le cercle au MapLayer */
        this.getChildren().add(circle);
    }

    /**
     * Override de la méthode layoutLayer pour afficher les points sur la carte
     */
    @Override
    protected void layoutLayer() {
        /* Conversion du MapPoint vers Point2D */
        Point2D point2d = this.getMapPoint(mapPoint.getLatitude(), mapPoint.getLongitude());

        /* Déplace le cercle selon les coordonnées du point */
        circle.setTranslateX(point2d.getX());
        circle.setTranslateY(point2d.getY());
    }
}
