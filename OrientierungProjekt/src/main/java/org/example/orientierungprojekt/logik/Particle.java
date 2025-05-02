package org.example.orientierungprojekt.logik;

import org.example.orientierungprojekt.util.Vector;
import static org.example.orientierungprojekt.util.SimulationConfig.GLOBAL_FLOW;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Particle {

    /*
     * Quelle: thenatureofcode.com / Für das Modellieren von Kräften mit Hilfe von Vektoren
     */

    private Vector originPosition; // die Ursprungsposition des Partikels für den Reset
    private Vector position;
    private Vector velocity; //Aenderungsrate der Position
    private Vector acceleration; //Aenderungsrate der Geschwindigkeit
    
    private float angle;
    private float angleVelocity; //Aenderungsrate der Position mit Hilfe eines Winkels
    private float angleAcceleration; //Aenderungsrate der Geschwindigkeit mit Hilfe eines Winkels

    //private final float RADIUS = 5.0f;
    private final float MASS = 1.0f;
    private boolean isDead;
    private float lifespan;

    public Particle(){
        this.originPosition = new Vector(0.5f, 0.5f);
        this.position = new Vector(0.5f, 0.5f);
        this.velocity = new Vector(1, 0);
        this.acceleration = new Vector(0, 0);
        this.angle = 0.0f;
        this.angleVelocity = 0.0f;
        this.angleAcceleration = 0.0f;
        this.lifespan = 1.0f; // Default lifespan of 1 second
        this.isDead = false;
    }

    public Particle(float x, float y){
        this.originPosition = new Vector(x, y);
        this.position = new Vector(x,y);
        this.velocity = new Vector(1, 0);
        this.acceleration = new Vector(0, 0);
        this.angle = 0.0f;
        this.angleVelocity = 0.0f;
        this.angleAcceleration = 0.0f;
        this.lifespan = 1.0f; // Default lifespan of 1 second
        this.isDead = false;
    }

    public Vector getPosition() {
        return this.position;
    }

    public Vector getVelocity() {
        return this.velocity;
    }

    public void setPosition(float x, float y) {
        this.position.setX(x);
        this.position.setY(y);
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

    public void setDead(boolean state){
        this.isDead = state;
    }

    public void resetToOrigin() {
        this.position.setVector(this.originPosition.getX(), this.originPosition.getY());
    }
        
    public void updateLifespan(float deltaTime) {
        lifespan -= deltaTime;
        if (lifespan <= 0) {
            isDead = true;
        }
    }

    public boolean isDead(){
        return this.isDead;
    }

    public void applyForce(Vector force) {
        // F = m * a => a = F / m
        this.acceleration.add(force.scaleVector(1/MASS));
    }

    public void updatePosition() {
        //newPosition = currentPosition + velocity * deltaTime
        this.velocity.add(this.acceleration);
        this.position.add(this.velocity);
        // Gradually align velocity with the global flow
        //Vector flowCorrection = GLOBAL_FLOW.subtractVector(velocity).scaleVector(0.5f); // Adjust 0.05f for smoothness
       // velocity.add(flowCorrection);
        this.angleVelocity += this.angleAcceleration;
        this.angle += this.angleVelocity;

        this.acceleration.scale(0.0f);
     }

     public void updateDirection(float angleInRadians){
        
     }

    public void draw(GraphicsContext gc) {
        gc.setFill(Color.BLUE);
        gc.strokeOval(position.getX(), position.getY(), 0.05f, 0.05f); // (x,y,hoehe,breite) Einfach ein Punkt
    }

}
