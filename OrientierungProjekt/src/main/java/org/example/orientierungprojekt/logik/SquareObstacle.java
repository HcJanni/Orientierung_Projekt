package org.example.orientierungprojekt.logik;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class SquareObstacle extends Obstacle {

    private final float dragCoefficient = 1.1f; // hoher Widerstand bei flachen Platten

    public SquareObstacle(float x, float y, float size) {
        super(x, y, size); // radius = half of edge length
    }

    @Override
    public void draw(GraphicsContext gc) {
        float r = getRadius();
        gc.setFill(Color.GREY); // Set the color for the obstacle
      //  gc.fillRect(position.getX() - r, position.getY() - r, r*2 , r*2);
        gc.strokeRect(position.getX() - r, position.getY() - r, r*2 , r*2);
    }

    @Override
    public void applyRepulsion(Particle particle) {
        RepulsionHandler.applySquareRepulsion(particle, this);
    }

    @Override
    public float getDragCoefficient() {
        return this.dragCoefficient; 
    }
}

