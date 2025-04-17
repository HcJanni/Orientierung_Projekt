package org.example.orientierungprojekt.logik;

import org.example.orientierungprojekt.util.Vector;
import static org.example.orientierungprojekt.util.SimulationConfig.GLOBAL_FLOW;
import javafx.scene.canvas.GraphicsContext;

public class Obstacle {

    private Vector position;
    private float radius;
    private float repelForce;
    private float angleOffset = 45; // Default value
    private float deflectionFactor = 1.0f; // Default value for deflection factor


    public Obstacle(float x, float y) {
        this(x, y, 100.0f, 1.0f); // Default radius and repel force
    }

    public Obstacle(float x, float y, float radius, float repelForce) {
        this.radius = radius;
        this.position = new Vector(x, y); // Center the obstacle at the given position
        this.repelForce = repelForce;
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

    private Vector rotateVector(Vector vector, float angleInDegrees) {
        float angleInRadians = (float) Math.toRadians(angleInDegrees);
        float rotatedX = vector.getX() * (float) Math.cos(angleInRadians) - vector.getY() * (float) Math.sin(angleInRadians);
        float rotatedY = vector.getX() * (float) Math.sin(angleInRadians) + vector.getY() * (float) Math.cos(angleInRadians);
        return new Vector(rotatedX, rotatedY);
    }

    public boolean isInside(Vector point) {
        float dx = position.getX() - point.getX();
        float dy = position.getY() - point.getY();
        return (dx * dx + dy * dy) <= (radius * radius);
    }

    public void applyRepulsion(Particle particle) {
        Vector particlePos = particle.getCurrentPosition();
        float dx = particlePos.getX() - position.getX();
        float dy = particlePos.getY() - position.getY();
        float distanceSquared = dx * dx + dy * dy;
    
        // Check if the particle is within the obstacle's influence radius
        if (distanceSquared <= radius * radius) {
            float distance = (float) Math.sqrt(distanceSquared);
    
            // Avoid division by zero
            if (distance > 0) {
                // Calculate the direction of the repulsion force
                Vector direction = new Vector(dx / distance, dy / distance);
    
                // Scale the force based on distance (inverse-square law)
                float forceMagnitude = repelForce / distanceSquared;
                Vector force = direction.scaleVector(forceMagnitude);
    
                // Calculate the deflected direction
                Vector deflectedDirection = rotateVector(direction, angleOffset);
                Vector deflectedForce = deflectedDirection.scaleVector(forceMagnitude * deflectionFactor);
    
                // Combine the forces
                Vector combinedForce = force.addVector(deflectedForce);
    
                // Apply the combined force to the particle
                particle.applyForce(combinedForce);
            }
        }

        // Add a reunification force to steer the particle back to the global flow
        Vector currentVelocity = particle.getVelocity();
        Vector flowCorrection = GLOBAL_FLOW.subtractVector(currentVelocity).scaleVector(0.1f); // Adjust 0.1f for smoothness
        particle.applyForce(flowCorrection);
    }

    public void draw(GraphicsContext gc) {
        
        gc.setFill(javafx.scene.paint.Color.RED); // Set the color for the obstacle
        gc.fillOval(position.getX(), position.getY() - radius / 16, radius / 8, radius / 8); // Center the circle at the obstacle's position
    }
}