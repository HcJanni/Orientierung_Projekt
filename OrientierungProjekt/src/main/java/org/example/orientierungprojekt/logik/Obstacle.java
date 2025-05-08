package org.example.orientierungprojekt.logik;

import org.example.orientierungprojekt.util.Vector;
import javafx.scene.canvas.GraphicsContext;

public abstract class Obstacle {

    protected Vector position;
    private float durchmesser;
    private float radius;
    private float size;

    public Obstacle(float x, float y) {
        this(x, y, 50.0f); // Default radius and repel force
    }

    public Obstacle(float x, float y, float radius) {
        //x -= radius;
        //y -= radius;
        //this.durchmesser = durchmesser;
        this.radius = radius;
        this.position = new Vector(x, y); // Center the obstacle at the given position
    }

    /*public void setPosition(Vector position) {
        this.position = position;
    }

    public void setPosition(float x, float y) {
        this.position.setX(x);
        this.position.setY(y);
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }*/

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


    /*public boolean maxRadius() {
        System.out.println("Radius ist groß genug: " + radius);
        return radius >= 100.0f;
    }

    public boolean isInside(Obstacle obs){
        Vector obsPos = obs.getPosition();
        float distance = obsPos.distanceTo(position);
        return distance < radius;
    }

    public boolean isInside(Particle particle) {
        Vector particlePos = particle.getPosition();
        float distance = particlePos.distanceTo(position);
        return distance < radius;
    }

    public boolean isBehindObstacle(Particle particle) {
        Vector particlePos = particle.getPosition();
        float dx = position.getX() - particlePos.getX();
        float dy = position.getY() - particlePos.getY();
        return (dx * dx + dy * dy) < (radius * radius);
    }*/

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