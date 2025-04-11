package org.example.orientierungprojekt;

import org.example.orientierungprojekt.util.Vector;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Particle {

    private final Vector originPosition;
    private Vector currentPosition;
    private Vector velocity;
    private Vector acceleration;

    private final float radius = 7.5f;
    private final float mass = 1.0f;
    private boolean isDead;
    private float lifespan;

    public Particle(float x, float y, float dx, float dy, float accX, float accY) {
        this.originPosition = new Vector(x, y);
        this.currentPosition = originPosition;
        this.velocity = new Vector(dx, dy);
        this.acceleration = new Vector(accX, accY);
        this.isDead = false;
        this.lifespan = 10; // Lebensdauer in Sekunden
    }

    public Vector getCurrentPosition() {
        return currentPosition;
    }

    public Vector getVelocity() {
        return velocity;
    }

    public Vector getAcceleration() {
        return acceleration;
    }

    public void setCurrentPosition(float x, float y) {
        this.currentPosition.setX(x);
        this.currentPosition.setY(y);
    }

    public void setVelocity(float dx, float dy) {
        this.velocity.setX(dx);
        this.velocity.setY(dy);
    }

    public void setAcceleration(float accX, float accY) {
        this.acceleration.setX(accX);
        this.acceleration.setY(accY);
    }

    public boolean isDead() {
        return isDead;
    }

    public void applyForce(Vector force) {
        // F = m * a => a = F / m
    
        acceleration.add(force.scaleVector(1/mass));
    }

    public void draw(GraphicsContext gc) {
        gc.setFill(Color.BLUE);
        gc.fillOval(currentPosition.getX(), currentPosition.getY(), radius, radius);
    }

    public void updateLifespan(float deltaTime) {
        lifespan -= deltaTime;
        if (lifespan <= 0) {
            isDead = true;
        }
    }

    public void updatePosition(float deltaTime) {
        // Update velocity based on acceleration
        velocity.add(acceleration.scaleVector(deltaTime));
        
        // Update position based on velocity
        currentPosition.add(velocity.scaleVector(deltaTime));
        
        // Reset acceleration for the next frame
        acceleration.setVector(0, 0);
    }

}
