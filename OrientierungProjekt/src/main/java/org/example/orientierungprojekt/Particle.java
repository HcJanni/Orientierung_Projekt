package org.example.orientierungprojekt;

import org.example.orientierungprojekt.util.Vector;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Particle {
    private Vector position;
    private Vector velocity;
    private Vector acceleration;

    private final float radius = 7.5f;
    private final float mass = 1.0f;
    private boolean isDead;
    private float lifespan;

    public Particle(float x, float y, float dx, float dy, float accX, float accY) {
        this.position = new Vector(x, y);
        this.velocity = new Vector(dx, dy);
        this.acceleration = new Vector(accX, accY);
        this.isDead = false;
        this.lifespan = 10; // Lebensdauer in Sekunden
    }

    public Vector getPosition() {
        return position;
    }

    public Vector getVelocity() {
        return velocity;
    }

    public Vector getAcceleration() {
        return acceleration;
    }

    public void setPosition(float x, float y) {
        this.position.setX(x);
        this.position.setY(y);
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
       if(lifespan <= 0) {
            isDead = true;
        } 
        return isDead;
    }

    public void applyForce(Vector force) {
        // F = m * a => a = F / m
        Vector accelerationChange = new Vector(force.getX() / mass, force.getY() / mass);
        acceleration.add(accelerationChange);
    }

    public void draw(GraphicsContext gc) {
        gc.setFill(Color.BLUE);
        gc.fillOval(position.getX(), position.getY(), radius, radius);
    }

    public void update(float deltaTime) {
        // Update velocity based on acceleration
        velocity.add(acceleration.scaleVector(deltaTime));
        
        // Update position based on velocity
        position.add(velocity.scaleVector(deltaTime));
        
        // Reset acceleration for the next frame
        acceleration.setVector(0, 0);

        // Decrease lifespan
        lifespan -= deltaTime / 100; // Assuming deltaTime is in milliseconds
    }

}
