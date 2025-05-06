package org.example.orientierungprojekt.logik;

import org.example.orientierungprojekt.util.Vector;
import static org.example.orientierungprojekt.util.SimulationConfig.GLOBAL_FLOW;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.example.orientierungprojekt.util.SimulationConfig;

public class Particle {

    /*
     * Quelle: thenatureofcode.com / Für das Modellieren von Kräften mit Hilfe von Vektoren
     */

    private Vector originPosition; // die Ursprungsposition des Partikels für den Reset
    private Vector position;
    private Vector velocity; //Aenderungsrate der Position
    private Vector acceleration; //Aenderungsrate der Geschwindigkeit

    private final float initialY;

    private float angle;
    private float angleVelocity; //Aenderungsrate der Position mit Hilfe eines Winkels
    private float angleAcceleration; //Aenderungsrate der Geschwindigkeit mit Hilfe eines Winkels

    private final float DURCHMESSER = 10.0f;
    private final float RADIUS = DURCHMESSER / 2.0f;
    private final float MASS = 1.0f;

    private boolean isDead;
    private float lifespan;

    private float radius = 1.5f; // Standardgröße

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
        this.initialY = position.getY();
    }

    public Particle(float x, float y){
        x -= RADIUS;
        y -= RADIUS;
        this.originPosition = new Vector(x, y);
        this.position = new Vector(x, y);
        this.velocity = new Vector(1, 0);
        this.acceleration = new Vector(0, 0);
        this.angle = 0.0f;
        this.angleVelocity = 0.0f;
        this.angleAcceleration = 0.0f;
        this.lifespan = 1.0f; // Default lifespan of 1 second
        this.isDead = false;
        this.initialY = position.getY();
    }

    public float getInitialY() {
        return initialY;
    }

    public Vector getPosition() {
        return this.position;
    }

    public Vector getVelocity() {
        return this.velocity;
    }

    public float getRadius(){
        return this.RADIUS;
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
        this.lifespan -= deltaTime;
        if (lifespan <= 0) {
            this.isDead = true;
        }
    }

    public boolean isDead(){
        return this.isDead;
    }

    public boolean isOverlapping(Particle other) {
        float distance = this.position.distanceTo(other.getPosition());
        return distance < (this.getRadius() + other.getRadius());
    }

    public void particleBounce(Particle other){

        if (isOverlapping(other)) {
            // Calculate the direction of the collision
            Vector collisionNormal = this.position.getDistanceVector(other.getPosition()).getNormalizedVector();
    
            // Relative velocity
            Vector relativeVelocity = this.velocity.subtractVector(other.getVelocity());
    
            // Calculate the velocity along the collision normal
            float velocityAlongNormal = relativeVelocity.dot(collisionNormal);
    
            // If the particles are moving away from each other, no collision response
            if (velocityAlongNormal > 0) {
                return;
            }
    
            // Calculate restitution (elasticity of the collision)
            float restitution = 1.0f; // Perfectly elastic collision
    
            // Impulse scalar
            float impulseScalar = -(1 + restitution) * velocityAlongNormal / (1 / this.MASS + 1 / other.MASS);
    
            // Apply impulse to both particles
            Vector impulse = collisionNormal.scaleVector(impulseScalar);
            this.velocity.add(impulse.scaleVector(1 / this.MASS));
            other.velocity.subtract(impulse.scaleVector(1 / other.MASS));
        }
    }

    public void applyForce(Vector force) {
        // F = m * a => a = F / m
        this.acceleration.add(force.scaleVector(1/MASS));
    }

    public void updatePosition() {
        //newPosition = currentPosition + velocity * deltaTime
        this.velocity.add(this.acceleration);
        // Wenn Geschwindigkeit extrem klein ist, Boost geben
        if (this.velocity.getLength() < 0.05f) {
            Vector nudge = GLOBAL_FLOW.getNormalizedVector().scaleVector(0.3f);
            this.velocity.add(nudge);
        }
        this.position.add(this.velocity);
        // Gradually align velocity with the global flow
        Vector flowCorrection = GLOBAL_FLOW.subtractVector(velocity).scaleVector(0.5f); // Adjust 0.05f for smoothness
        velocity.add(flowCorrection);
        this.angleVelocity += this.angleAcceleration;
        this.angle += this.angleVelocity;

        this.acceleration.scale(0.25f);

    }

     public void updateDirection(float angleInRadians){
        
     }

    public void draw(GraphicsContext gc) {
        /*float maxSpeed = SimulationConfig.getSpeed(); // oder fester Wert z. B. 5.0f
        float actualSpeed = velocity.getLength();
        double intensity = Math.min(actualSpeed / maxSpeed, 1.0);

        // Geschwindigkeit in eine Farbe umwandeln (z. B. Blau = schnell, Rot = langsam)
        Color c = Color.color(1.0 - intensity, 0.0, intensity); // Rot → Blau

        gc.setFill(c);
        gc.fillOval(position.getX(), position.getY(), radius, radius);*/

        float speed = velocity.getLength();  // tatsächliche Geschwindigkeit des Partikels
        float maxSpeed = 3.0f;              // ggf. anpassen

        float intensity = Math.min(speed / maxSpeed, 1.0f); // Skaliert auf 0–1
        Color color = new Color(intensity, 0, 1.0f - intensity, 1.0); // von Blau (langsam) zu Rot (schnell)

        gc.setFill(color);
        gc.fillOval(position.getX(), position.getY(), radius, radius);


        //gc.setFill(Color.BLUE);
        //gc.fillOval(position.getX() - RADIUS, position.getY() - RADIUS, DURCHMESSER, DURCHMESSER); // (x,y,hoehe,breite) Einfach ein Punkt
        //gc.strokeLine(position.getX(), position.getY(), position.getX(), position.getY());
       // gc.strokeLine(0, 0, position.getX(), position.getY() + RADIUS); // ZUM DEBUGGEN
    }

}
