package org.example.orientierungprojekt.logik;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import org.example.orientierungprojekt.util.Vector;

public class AirfoilObstacle extends Obstacle {

    private final float width;
    private final float height;

    public AirfoilObstacle(float x, float y, float size) {
        super(x, y, size);
        this.width = size * 3;
        this.height = size * 3;
    }

    @Override
    public void draw(GraphicsContext gc) {
        int steps = 60;
        double[] xPoints = new double[steps * 2];
        double[] yPoints = new double[steps * 2];

        for (int i = 0; i < steps; i++) {
            double x = (double) i / (steps - 1);
            double yt = 5 * 0.12 * (
                    0.2969 * Math.sqrt(x)
                            - 0.1260 * x
                            - 0.3516 * Math.pow(x, 2)
                            + 0.2843 * Math.pow(x, 3)
                            - 0.1015 * Math.pow(x, 4)
            );

            xPoints[i] = position.getX() + x * width;
            yPoints[i] = position.getY() - yt * height;
            xPoints[2 * steps - 1 - i] = position.getX() + x * width;
            yPoints[2 * steps - 1 - i] = position.getY() + yt * height;
        }

        gc.setFill(Color.DARKTURQUOISE);
        gc.fillPolygon(xPoints, yPoints, xPoints.length);
    }

    @Override
    public void applyRepulsion(Particle particle) {
        RepulsionHandler.applyAirfoilRepulsion(particle, this);
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    @Override
    public float getDragCoefficient() {
        return 0.04f; // stromlinienförmig, sehr geringer Widerstand
    }
}