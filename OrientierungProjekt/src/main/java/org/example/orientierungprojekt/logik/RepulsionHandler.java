package org.example.orientierungprojekt.logik;

import org.example.orientierungprojekt.util.Vector;
import static org.example.orientierungprojekt.util.SimulationConfig.*;

public class RepulsionHandler {

    //Hilfsklasse zur Berechnung der Kollisionen

    private static float repelForce = -1.0f;
    private static float deflectionFactor = 1.0f;
    private static float angleOffset = 2.0f;
    private static final float FLOW_CORRECTION_SCALE = 1.0f;
    private static final float BACK_FORCE_MULTIPLIER = 1.0f;

    public void applyRepulsion(Particle particle, Obstacle obstacle) {
        
        float obstacleOffset = obstacle.getRadius() / 16; //Durch 16 teilen für Offset beim Zeichnen

        Vector particlePos = particle.getPosition();
        Vector obstaclePos = obstacle.getPosition().subtractVector(obstacleOffset, 0);
    
        float distance = obstaclePos.distanceTo(particlePos);
        float distanceSquared = distance * distance;
        float radius = obstacle.getRadius();
    
        if (distanceSquared <= radius * radius) {
            if (distance > 0.0001f) {
                Vector direction = obstaclePos.getDistanceVector(particlePos).getNormalizedVector();
                // Repulsion force
                float forceMagnitude = repelForce / distanceSquared;
                Vector force = direction.scaleVector(forceMagnitude);
    
                // Deflection force
                Vector deflectedDirectionVector = new Vector(direction).rotateVector(angleOffset);

                Vector deflectedForce = deflectedDirectionVector.scaleVector(forceMagnitude * deflectionFactor);
    
                // Combine forces
                Vector combinedForce = force.addVector(deflectedForce);
                particle.applyForce(combinedForce);
                
                // Stronger force if behind the obstacle
                if (obstacle.isBehindObstacle(particle)) {
                    Vector backForce = direction.scaleVector(repelForce * BACK_FORCE_MULTIPLIER);
                    particle.applyForce(backForce);
                }
            }
    
            // Reunification force
            Vector currentVelocity = particle.getVelocity();
            float correctionStrength = Math.min(1.0f, distance / (radius * 2));
            Vector flowCorrection = GLOBAL_FLOW.subtractVector(currentVelocity).scaleVector(correctionStrength * 2 * FLOW_CORRECTION_SCALE);
            particle.applyForce(flowCorrection);
        }

    }

}
