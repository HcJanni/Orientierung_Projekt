package org.example.orientierungprojekt.logik;

import org.example.orientierungprojekt.util.Vector;
import static org.example.orientierungprojekt.util.SimulationConfig.*;

public class RepulsionHandler {

    //Hilfsklasse zur Berechnung der Kollisionen

    private static float repelForce = -2.0f;
    private static float deflectionFactor = 1.0f;
    private static float angleOffset = 2.0f;
    private static final float FLOW_CORRECTION_SCALE = 2.0f;
    private static final float BACK_FORCE_MULTIPLIER = 1.0f;

    /*public static void applyCircleRepulsion(Particle particle, Obstacle obstacle) {
        
        float obstacleOffset = obstacle.getRadius() / 16; //Durch 16 teilen für Offset beim Zeichnen

        Vector particlePos = particle.getPosition();
        Vector obstaclePos = obstacle.getPosition().subtractVector(obstacleOffset, 0);

        //float distance = obstaclePos.distanceTo(particlePos);
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
                /*if (obstacle.isBehindObstacle(particle)) {
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

    }*/

    public static void applyCircleRepulsion(Particle particle, Obstacle obstacle) {
        Vector particlePos = particle.getPosition();
        Vector obstaclePos = obstacle.getPosition();
        float distance = particlePos.distanceTo(obstaclePos);
        float radius = obstacle.getRadius();
        float influenceRadius = radius;
        Vector direction = particlePos.subtractVector(obstaclePos).getNormalizedVector();

        if (distance < influenceRadius && distance > 0.01f) {
            float strength = 1.0f - (distance / influenceRadius);
            float forceMagnitude = 25.0f * strength;
            Vector force = direction.scaleVector(forceMagnitude);
            particle.applyForce(force);
        }

        // Rückführung auf ursprüngliche y-Koordinate
        float yDeviation = particlePos.getY() - particle.getInitialY();
        float strength = 0.01f * Math.abs(yDeviation);
        Vector verticalCorrection = new Vector(0, -yDeviation * strength); // sanfte Rückführung
        particle.applyForce(verticalCorrection);

    }

    public static void applySquareRepulsion(Particle particle, SquareObstacle obstacle) {
        Vector pos = particle.getPosition();
        Vector oPos = obstacle.getPosition();
        float halfSize = obstacle.getRadius();

        if (pos.getX() > oPos.getX() - halfSize && pos.getX() < oPos.getX() + halfSize &&
                pos.getY() > oPos.getY() - halfSize && pos.getY() < oPos.getY() + halfSize) {
            Vector away = pos.subtractVector(oPos).getNormalizedVector();
            particle.applyForce(away.scaleVector(15.0f));
        }
    }

    public static void applyTriangleRepulsion(Particle particle, TriangleObstacle obstacle) {
        float r = obstacle.getRadius();
        float cx = obstacle.getPosition().getX();
        float cy = obstacle.getPosition().getY();

        Vector a = new Vector(cx, cy - r);
        Vector b = new Vector(cx - r, cy + r);
        Vector c = new Vector(cx + r, cy + r);
        Vector p = particle.getPosition();

        if (isInsideTriangle(p, a, b, c)) {
            Vector away = p.subtractVector(obstacle.getPosition()).getNormalizedVector();
            particle.applyForce(away.scaleVector(20.0f));
        }
    }

    private static boolean isInsideTriangle(Vector p, Vector a, Vector b, Vector c) {
        float denominator = ((b.getY() - c.getY()) * (a.getX() - c.getX()) +
                (c.getX() - b.getX()) * (a.getY() - c.getY()));

        float alpha = ((b.getY() - c.getY()) * (p.getX() - c.getX()) +
                (c.getX() - b.getX()) * (p.getY() - c.getY())) / denominator;
        float beta = ((c.getY() - a.getY()) * (p.getX() - c.getX()) +
                (a.getX() - c.getX()) * (p.getY() - c.getY())) / denominator;
        float gamma = 1.0f - alpha - beta;

        return alpha >= 0 && beta >= 0 && gamma >= 0;
    }
}
