package org.example.orientierungprojekt.logik;

import org.example.orientierungprojekt.util.Vector;
import javafx.scene.canvas.GraphicsContext;

public class Obstacle {

    private Vector position;
    private float radius;
    private float repelForce;

    public Obstacle(float x, float y) {
        this(x, y, 100.0f); // Default radius and repel force
    }

    public Obstacle(float x, float y, float radius) {
        this.radius = radius;
        this.position = new Vector(x, y); // Center the obstacle at the given position
        }

    public void setPosition(Vector position) {
        this.position = position;
    }

    public void setPosition(float x, float y) {
        this.position.setX(x);
        this.position.setY(y);
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public void setRepelForce(float repelForce) {
        this.repelForce = repelForce;
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

    public float getRepelForce(){
        return repelForce;
    }

    public boolean maxRadius() {
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
        return (dx * dx + dy * dy) > (radius * radius);
    }

    public void draw(GraphicsContext gc) {
        gc.setFill(javafx.scene.paint.Color.RED); // Set the color for the obstacle
        gc.fillOval(position.getX() - radius / 32, position.getY() - radius / 16, radius / 8, radius / 8); // Center the circle at the obstacle's position
    }
}