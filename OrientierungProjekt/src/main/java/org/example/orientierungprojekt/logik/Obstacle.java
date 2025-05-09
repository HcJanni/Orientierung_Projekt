package org.example.orientierungprojekt.logik;

import org.example.orientierungprojekt.util.Vector;
import javafx.scene.canvas.GraphicsContext;

public abstract class Obstacle {

    protected Vector position;
    private float radius;
    private float size;

    public Obstacle(float x, float y) {
        this(x, y, 50.0f); // Default radius and repel force
    }

    public Obstacle(float x, float y, float radius) {
        this.radius = radius;
        this.position = new Vector(x, y); // Center the obstacle at the given position
    }

    public Vector getPosition() {
        return position;
    }

    public float getX() {
        return position.getX();
    }

    public float getY() {
        return position.getY();
    }

    public float getRadius() {
        return radius;
    }

    public float getSize() {
        return size;
    }

    public float getInfluenceRadius() {
        return radius * 1.5f;
    }

    public abstract void draw(GraphicsContext gc);

    public void applyRepulsion(Particle particle) {
        if (this instanceof CircleObstacle) {
            RepulsionHandler.applyCircleRepulsion(particle, this);
        } else if (this instanceof SquareObstacle) {
            RepulsionHandler.applySquareRepulsion(particle, (SquareObstacle) this);
        } else if (this instanceof TriangleObstacle) {
            RepulsionHandler.applyTriangleRepulsion(particle, (TriangleObstacle) this);
        } else if (this instanceof AirfoilObstacle) {
            RepulsionHandler.applyAirfoilRepulsion(particle, (AirfoilObstacle) this);
        } else if (this instanceof LeftTriangleObstacle) {
            RepulsionHandler.applyLeftTriangleRepulsion(particle, (LeftTriangleObstacle) this);
        } else if (this instanceof DiamondObstacle) {
            RepulsionHandler.applyDiamondRepulsion(particle, (DiamondObstacle) this);
        }
    }

    public float getDragCoefficient() {
        return 1.0f; // Default für runde Körper
    }
}