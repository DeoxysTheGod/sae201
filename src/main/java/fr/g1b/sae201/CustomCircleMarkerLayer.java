package fr.g1b.sae201;

import com.gluonhq.maps.MapLayer;
import com.gluonhq.maps.MapPoint;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class CustomCircleMarkerLayer extends MapLayer {
    private MapPoint mapPoint;
    private Circle circle;

    public CustomCircleMarkerLayer(MapPoint mapPoint, Color couleurIntensite) {
        this.mapPoint = mapPoint;
        this.circle = new Circle(5, couleurIntensite);

        /* Ajoute le cercle au MapLayer */
        this.getChildren().add(circle);
    }

    @Override
    protected void layoutLayer() {
        /* Conversion du MapPoint vers Point2D */
        Point2D point2d = this.getMapPoint(mapPoint.getLatitude(), mapPoint.getLongitude());

        /* Déplace le cercle selon les coordonnées du point */
        circle.setTranslateX(point2d.getX());
        circle.setTranslateY(point2d.getY());
    }
}
