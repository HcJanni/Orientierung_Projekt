package org.example.orientierungprojekt.logik;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class LeftTriangleObstacle extends Obstacle {
    private float size;

    public LeftTriangleObstacle(float x, float y, float size) {
        super(x, y, size);
        this.size = size;
    }

    @Override
    public void draw(GraphicsContext gc) {
        float r = getRadius();
        float cx = position.getX();
        float cy = position.getY();

        double[] xPoints = {
                cx - r, // linker Eckpunkt (rechter Winkel)
                cx + r, // rechter oberer Eckpunkt
                cx + r  // rechter unterer Eckpunkt
        };

        double[] yPoints = {
                cy,      // mittig links
                cy - r,  // oben rechts
                cy + r   // unten rechts
        };

        gc.setLineWidth(1);
        gc.setStroke(Color.DARKRED);
        gc.strokePolygon(xPoints, yPoints, 3);
    }

    @Override
    public void applyRepulsion(Particle particle) {
        RepulsionHandler.applyLeftTriangleRepulsion(particle, this); // this als Obstacle genügt
    }

    @Override
    public float getDragCoefficient() {
        return 0.5f;
    }
}
