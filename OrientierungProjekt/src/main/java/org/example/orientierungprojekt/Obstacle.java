package org.example.orientierungprojekt;

import org.example.orientierungprojekt.util.Vector;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color; 

public class Obstacle {

    private Vector position;

    private float radius; //Interaktion: evtl skalierbar machen
    private float repelForce; //Interaktion: evtl skalierbar machen

    public Obstacle(float x, float y) {
        this.position = new Vector(x, y);
        this.radius = 50.f; // Default, kann angepasst werden
        this.repelForce = 0.5f; // Default, kann angepasst werden
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

    public float getRadius() {
        return radius;
    }

    public float getRepelForce() {
        return repelForce;
    }

    public void draw(GraphicsContext gc) {
        gc.setFill(Color.RED); // Farbe des Hindernisses
        gc.fillOval(position.getX() - radius, position.getY() - radius, radius * 2, radius * 2);
    }

    public boolean isInside(float x, float y) {
        float dx = position.getX() - x;
        float dy = position.getY() - y;
        return (dx * dx + dy * dy) <= (radius * radius);
    }

    public void applyRepulsion(Particle particle) {
        Vector particlePos = particle.getPosition();
        float dx = position.getX() - particlePos.getX();
        float dy = position.getY() - particlePos.getY();
        float distanceSquared = dx * dx + dy * dy;
        if (distanceSquared < radius * radius) {
            float distance = (float) Math.sqrt(distanceSquared);
            if (distance > 0) {
                float forceMagnitude = repelForce / distanceSquared;
                Vector force = new Vector(dx / distance * forceMagnitude, dy / distance * forceMagnitude);
                particle.applyForce(force);
            }
        }
    }
}
