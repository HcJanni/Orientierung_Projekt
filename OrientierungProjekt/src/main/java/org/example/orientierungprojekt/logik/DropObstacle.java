package org.example.orientierungprojekt.logik;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DropObstacle extends Obstacle {

    private final float width;
    private final float height;

    public DropObstacle(float x, float y, float size) {
        super(x, y, size);
        this.width = size * 2;
        this.height = size;
    }

    @Override
    public void draw(GraphicsContext gc) {
        int steps = 60;
        double[] xPoints = new double[steps];
        double[] yPoints = new double[steps];

        for (int i = 0; i < steps; i++) {
            double t = (double) i / (steps - 1);
            double x = t * width;
            double y = height * Math.sin(Math.PI * t) * (1 - t); // Tropfenform

            xPoints[i] = position.getX() + x;
            yPoints[i] = position.getY() - y;
        }

        // Spiegeln für Unterseite
        double[] xFull = new double[2 * steps];
        double[] yFull = new double[2 * steps];

        for (int i = 0; i < steps; i++) {
            xFull[i] = xPoints[i];
            yFull[i] = yPoints[i];
            xFull[2 * steps - 1 - i] = xPoints[i];
            yFull[2 * steps - 1 - i] = 2 * position.getY() - yPoints[i];
        }

        gc.setFill(Color.GREY);
        gc.fillPolygon(xFull, yFull, xFull.length);
        gc.strokePolygon(xFull, yFull, xFull.length);
    }

    @Override
    public void applyRepulsion(Particle particle) {
        RepulsionHandler.applyDropRepulsion(particle, this); // Oder separate Methode, wenn nötig
    }

    @Override
    public float getDragCoefficient() {
        return 0.15f; // realistischer Wert für Tropfenform
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }
}
