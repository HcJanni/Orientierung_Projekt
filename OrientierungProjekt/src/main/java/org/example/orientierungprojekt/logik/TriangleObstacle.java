package org.example.orientierungprojekt.logik;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class TriangleObstacle extends Obstacle {
    private float size;

    public TriangleObstacle(float x, float y, float size) {
        super(x, y, size); // radius = half of edge length
        this.size = size;
    }

    @Override
    public void draw(GraphicsContext gc) {
        float r = getRadius(); // wirkt als „Größe“ des Dreiecks
        float cx = position.getX();
        float cy = position.getY();

        double[] xPoints = {
                cx,
                cx - r,
                cx + r
        };

        double[] yPoints = {
                cy - r,
                cy + r,
                cy + r
        };


        gc.setLineWidth(1);
        gc.strokePolygon(xPoints, yPoints, 3);
    }

    @Override
    public void applyRepulsion(Particle particle) {
        RepulsionHandler.applyTriangleRepulsion(particle, this);
    }

    @Override
    public float getDragCoefficient() {
        return 0.4f; // spitze Formen, mäßiger Widerstand
    }
}

