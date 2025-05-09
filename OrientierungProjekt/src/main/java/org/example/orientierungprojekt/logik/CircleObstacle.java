package org.example.orientierungprojekt.logik;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class CircleObstacle extends Obstacle {

    private final float dragCoefficient = 1.2f; // typischer Wert für Kugeln ! Quelle?!
 
    public CircleObstacle(float x, float y) {
        super(x, y, 50.0f);
    }

    public CircleObstacle(float x, float y, float radius) {
        super(x, y, radius);
    }

    @Override
    public void draw(GraphicsContext gc) {
        float r = getRadius();
        float d = getRadius() * 2;
        gc.setFill(Color.GREY);
        gc.fillOval(position.getX() - r, position.getY() - r, d , d);
        gc.strokeOval(position.getX() - r, position.getY() - r, d , d);
    }

    @Override
    public void applyRepulsion(Particle particle) {
            RepulsionHandler.applyCircleRepulsion(particle, this);
    }

    @Override
    public float getDragCoefficient() {
        return this.dragCoefficient; 
    }
}
