package org.example.orientierungprojekt.logik;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DiamondObstacle extends Obstacle {

    public DiamondObstacle(float x, float y, float size) {
        super(x, y, size);
    }

    @Override
    public void draw(GraphicsContext gc) {
        float r = getRadius(); // halbe Diagonale
        float cx = position.getX();
        float cy = position.getY();

        double angle45 = Math.toRadians(45);
        double sin = Math.sin(angle45);
        double cos = Math.cos(angle45);

        double[] dx = { -1, 1, 1, -1 };
        double[] dy = { -1, -1, 1, 1 };

        double[] xPoints = new double[4];
        double[] yPoints = new double[4];

        for (int i = 0; i < 4; i++) {
            xPoints[i] = cx + r * (dx[i] * cos - dy[i] * sin);
            yPoints[i] = cy + r * (dx[i] * sin + dy[i] * cos);
        }

        gc.setLineWidth(1);
        gc.setFill(Color.GREY);
        gc.fillPolygon(xPoints, yPoints, 4);
        gc.strokePolygon(xPoints, yPoints, 4);
    }

    @Override
    public void applyRepulsion(Particle particle) {
        RepulsionHandler.applyDiamondRepulsion(particle, this);
    }

    @Override
    public float getDragCoefficient() {
        return 1.0f; // wie normales Quadrat
    }
}
