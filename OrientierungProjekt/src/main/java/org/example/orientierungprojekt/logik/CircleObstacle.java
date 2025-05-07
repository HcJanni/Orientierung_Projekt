package org.example.orientierungprojekt.logik;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class CircleObstacle extends Obstacle {
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
        gc.setFill(Color.RED); // Set the color for the obstacle
        gc.strokeOval(position.getX() - r, position.getY() - r, d , d);
        gc.strokeLine(this.position.getX(), this.position.getY(), this.position.getX() - r, this.position.getY());
    }


    @Override
    public void applyRepulsion(Particle particle) {
       // float influenceRadius = getRadius() * 1.5f;
        //if (getPosition().distanceTo(particle.getPosition()) < influenceRadius) {
            RepulsionHandler.applyCircleRepulsion(particle, this);
       // }
    }
}
