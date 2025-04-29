package org.example.orientierungprojekt.logik;

import org.example.orientierungprojekt.util.Vector;
import static org.example.orientierungprojekt.util.SimulationConfig.GLOBAL_FLOW;;

public class RepulsionHandler {

    //Hilfsklasse zur Berechnung der Kollisionen

    private float repelForce;

    public void applyRepulsion(Particle particle, Obstacle obstacle) {
        
            Vector particlePos = particle.getCurrentPosition();
            Vector obstaclePos = obstacle.getPosition();

            float distance = obstaclePos.distanceTo(particlePos);
            float distanceSquared = distance * distance;
            float radius = obstacle.getRadius();
        
            // Check if the particle is within the obstacle's influence radius
           // if (distanceSquared <= radius * radius) {
        
                // Avoid division by zero
                //if (distance > 0) {
                    // Calculate the direction of the repulsion force
                   // Vector direction = new Vector(dx / distance, dy / distance);
        
                    // Scale the force based on distance (inverse-square law)
                   // float forceMagnitude = repelForce / distanceSquared;
                   // Vector force = direction.scaleVector(forceMagnitude);
        
                    // Calculate the deflected direction
                   // Vector deflectedDirection = rotateVector(direction, angleOffset);
                    //Vector deflectedForce = deflectedDirection.scaleVector(forceMagnitude * deflectionFactor);
        
                    // Combine the forces
                    //Vector combinedForce = force.addVector(deflectedForce);
        
                    // Apply the combined force to the particle
                    //particle.applyForce(combinedForce);
    
                   // if (obstacle.isBehindObstacle(particle)) {
                        // If the particle is behind the obstacle, apply a stronger force to push it out
                      //  Vector backForce = direction.scaleVector(repelForce * 2); // Adjust the multiplier for strength
                      //  particle.applyForce(backForce);
                  //  }
               // }
            //} 
            // Add a reunification force to steer the particle back to the global flow
           // Vector currentVelocity = particle.getVelocity();
           // Vector flowCorrection = GLOBAL_FLOW.subtractVector(currentVelocity).scaleVector(0.1f); // Adjust 0.1f for smoothness
           // particle.applyForce(flowCorrection);
        }
}
