package org.example.orientierungprojekt.logik;

import org.example.orientierungprojekt.util.Vector;
import static org.example.orientierungprojekt.util.SimulationConfig.GLOBAL_FLOW;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Particle {

    private final Vector originPosition;
    private Vector currentPosition;
    private Vector velocity;
    private Vector acceleration;

    private final float RADIUS = 8.0f;
    private final float MASS = 1.0f;
    private boolean isDead;
    private float lifespan;

    public Particle(float x, float y){
        this(x,y,0,0,0,0);
    }

    public Particle(float x, float y, float dx, float dy, float accX, float accY) {
        this.originPosition = new Vector(x - RADIUS / 2, y - RADIUS / 2);
        this.currentPosition = originPosition;
        this.velocity = new Vector(dx, dy);
        this.acceleration = new Vector(accX, accY);
        this.isDead = false;
        this.lifespan = 120;
    }

    public Vector getCurrentPosition() {
        return currentPosition;
    }

    public Vector getVelocity() {
        return this.velocity;
    }

    public Vector getAcceleration() {
        return acceleration;
    }

    public void setCurrentPosition(float x, float y) {
        this.currentPosition.setX(x);
        this.currentPosition.setY(y);
    }

    public void setVelocity(Vector velocity) {
        this.velocity.setX(velocity.getX());
        this.velocity.setY(velocity.getY());
    }

    public void setVelocity(float dx, float dy) {
        this.velocity.setX(dx);
        this.velocity.setY(dy);
    }

    public void setLifespan(float seconds) {
        this.lifespan = seconds;
    }

    public void setAcceleration(float accX, float accY) {
        this.acceleration.setX(accX);
        this.acceleration.setY(accY);
    }

    public void resetToOrigin() {
        this.currentPosition.setX(originPosition.getX());
        this.currentPosition.setY(originPosition.getY());
    }

    public boolean isDead() {
        return isDead;
    }

    public void applyForce(Vector force) {
        // F = m * a => a = F / m
        acceleration.add(force.scaleVector(1/MASS));
    }

    public void updateLifespan(float deltaTime) {
        lifespan -= deltaTime;
        if (lifespan <= 0) {
            isDead = true;
        }
    }

    public void updatePosition() {
        velocity.add(this.acceleration);
        currentPosition.add(velocity);  
        // Gradually align velocity with the global flow
        Vector flowCorrection = GLOBAL_FLOW.subtractVector(velocity).scaleVector(0.05f); // Adjust 0.05f for smoothness
        velocity.add(flowCorrection);
        acceleration.scale(0.0f);
    }

    public void draw(GraphicsContext gc) {
        
        gc.setFill(Color.BLUE);
        gc.strokeLine(originPosition.getX(), originPosition.getY(), currentPosition.getX(), currentPosition.getY());
    }

}
