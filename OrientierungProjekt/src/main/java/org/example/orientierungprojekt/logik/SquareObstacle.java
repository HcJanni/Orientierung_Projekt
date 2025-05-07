package org.example.orientierungprojekt.logik;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class SquareObstacle extends Obstacle {
    private float size;

    public SquareObstacle(float x, float y, float size) {
        super(x, y, size); // radius = half of edge length
        this.size = size;
    }

    @Override
    public void draw(GraphicsContext gc) {
        float r = getRadius();
        gc.setFill(Color.BLUE); // Set the color for the obstacle
        gc.strokeRect(position.getX() - r, position.getY() - r, r*2 , r*2);
        gc.strokeLine(this.position.getX(), this.position.getY(), this.position.getX() - r, this.position.getY());
    }

    @Override
    public void applyRepulsion(Particle particle) {
        RepulsionHandler.applySquareRepulsion(particle, this);
    }

}

