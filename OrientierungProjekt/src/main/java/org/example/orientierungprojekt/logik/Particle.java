package org.example.orientierungprojekt.logik;

import org.example.orientierungprojekt.util.Vector;
import static org.example.orientierungprojekt.util.SimulationConfig.GLOBAL_FLOW;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Particle {

    private Vector originPosition; // die Ursprungsposition des Partikels für den Reset
    private Vector currentPosition;
    private Vector velocity; //Aenderungsrate der Position
    //private Vector velocityAngle; //Aenderungsrate der Position in Grad
    private Vector acceleration; //Aenderungsrate der Geschwindigkeit
    //private Vector accelerationAngle; //Aenderungsrate der Geschwindigkeit in Grad

    //private final float RADIUS = 5.0f;
    private final float MASS = 1.0f;
    private boolean isDead;
    private float lifespan;

    public Particle(){
        this.originPosition = new Vector(0, 0);
        this.currentPosition = new Vector(0, 0);
        this.velocity = new Vector(0, 0);
        //this.velocityAngle = new Vector(0, 0);
        this.acceleration = new Vector(0, 0);
        //this.accelerationAngle = new Vector(0, 0);
        this.lifespan = 1.0f; // Default lifespan of 1 second
        this.isDead = false;
    }

    public Particle(float x, float y){
        this.originPosition.setVector(x, y);
    }

    public Vector getCurrentPosition() {
        return currentPosition;
    }

    public Vector getVelocity() {
        return this.velocity;
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

    public void resetToOrigin() {
        this.currentPosition.setX(originPosition.getX());
        this.currentPosition.setY(originPosition.getY());
    }

    public boolean isDead() {
        return isDead;
    }

    public void updateLifespan(float deltaTime) {
        lifespan -= deltaTime;
        if (lifespan <= 0) {
            isDead = true;
        }
    }

    public void applyForce(Vector force) {
        // F = m * a => a = F / m
        this.acceleration.add(force.scaleVector(1/MASS));
    }

    public void updatePosition() {
        //newPosition = currentPosition + velocity * deltaTime
        velocity.add(this.acceleration);
        currentPosition.add(this.velocity);  
        // Gradually align velocity with the global flow
        Vector flowCorrection = GLOBAL_FLOW.subtractVector(velocity).scaleVector(0.5f); // Adjust 0.05f for smoothness
        velocity.add(flowCorrection);
        acceleration.scale(0.0f);
    }

    public void draw(GraphicsContext gc) {
        gc.setFill(Color.BLUE);
        gc.strokeLine(originPosition.getX(), originPosition.getY(), currentPosition.getX(), currentPosition.getY());
    }

}
