package org.example.orientierungprojekt.logik;

import org.example.orientierungprojekt.util.SimulationConfig;
import org.example.orientierungprojekt.util.Vector;

public class RepulsionHandler {

    public static void applyCircleRepulsion(Particle particle, Obstacle obstacle) {
        Vector particlePos = particle.getPosition();
        Vector obstaclePos = obstacle.getPosition();
        float distance = particlePos.distanceTo(obstaclePos);
        float radius = obstacle.getRadius();
        float influenceRadius = radius;
        Vector direction = particlePos.subtractVector(obstaclePos).getNormalizedVector();

        if (distance < influenceRadius && distance > 0.0001f) {
            float strength = 1.0f - (distance / influenceRadius);
            float forceMagnitude = 25.0f * strength; //Warum 25?
            Vector force = direction.scaleVector(forceMagnitude);
            particle.applyForce(force);
        }

        // Rückführung auf ursprüngliche y-Koordinate
        applyVerticalCorrection(particle);

        // Formwiderstand (drag force)
        float rho = 1.225f; // Luftdichte
        float speed = particle.getVelocity().getLength();
        float area = (float) Math.PI * particle.getRadius() * particle.getRadius(); // projizierte Fläche
        float Cd = obstacle.getDragCoefficient();

        float dragMagnitude = 0.5f * Cd * rho * area * speed * speed;
        dragMagnitude = Math.min(dragMagnitude, 0f); // 🔧 Sicherer Grenzwert

        if (speed < 0.001f) return; // zu langsam für sinnvollen Drag

        Vector dragDirection = particle.getVelocity().getNormalizedVector().scaleVector(-1); // entgegen der Bewegung
        Vector dragForce = dragDirection.scaleVector(dragMagnitude);

        particle.applyForce(dragForce);
    }

    public static void applySquareRepulsion(Particle particle, SquareObstacle obstacle) {
        Vector pos = particle.getPosition();
        Vector oPos = obstacle.getPosition();
        float halfSize = obstacle.getRadius();

        float dx = pos.getX() - oPos.getX();
        float dy = pos.getY() - oPos.getY();

        // Check if inside square
        if (Math.abs(dx) < halfSize && Math.abs(dy) < halfSize) {

            // Bestimme naheste Seite: horizontal oder vertikal
            if (Math.abs(dx) > Math.abs(dy)) {
                // Links oder rechts → vertikale Wand → Kraft horizontal
                float pushX = dx > 0 ? 1 : -1;
                float pushY = dy > 0 ? 1 : -1;
                particle.applyForce(new Vector(pushX, pushY));
            } else {
                // Oben oder unten → horizontale Wand → Kraft vertikal
                float pushY = dy > 0 ? 1 : -1;
                particle.applyForce(new Vector(0, pushY));
            }

        }

        applyVerticalCorrection(particle);

        // Formwiderstand (drag force)
        float rho = 1.225f; // Luftdichte
        float speed = particle.getVelocity().getLength();
        float area = (float) Math.PI * particle.getRadius() * particle.getRadius(); // projizierte Fläche
        float Cd = obstacle.getDragCoefficient();

        float dragMagnitude = 0.5f * Cd * rho * area * speed * speed;
        dragMagnitude = Math.min(dragMagnitude, 0f); // 🔧 Sicherer Grenzwert

        if (speed < 0.001f) return; // zu langsam für sinnvollen Drag

        Vector dragDirection = particle.getVelocity().getNormalizedVector().scaleVector(-1); // entgegen der Bewegung
        Vector dragForce = dragDirection.scaleVector(dragMagnitude);

        particle.applyForce(dragForce);
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
            particle.applyForce(away.scaleVector(1.0f));
        }

        applyVerticalCorrection(particle);

        // Formwiderstand (drag force)
        float rho = 1.225f; // Luftdichte
        float speed = particle.getVelocity().getLength();
        float area = (float) Math.PI * particle.getRadius() * particle.getRadius(); // projizierte Fläche
        float Cd = obstacle.getDragCoefficient();

        float dragMagnitude = 0.5f * Cd * rho * area * speed * speed;
        dragMagnitude = Math.min(dragMagnitude, 0f); // 🔧 Sicherer Grenzwert

        if (speed < 0.001f) return; // zu langsam für sinnvollen Drag

        Vector dragDirection = particle.getVelocity().getNormalizedVector().scaleVector(-1); // entgegen der Bewegung
        Vector dragForce = dragDirection.scaleVector(dragMagnitude);

        particle.applyForce(dragForce);
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

    public static void applyDiamondRepulsion(Particle particle, DiamondObstacle obstacle) {
        Vector p = particle.getPosition();
        Vector center = obstacle.getPosition();

        float r = obstacle.getRadius();
        float expandedRadius = r * 1.4f; // ⬅️ Vergrößerter Radius für Wirkung

        // Die vier Eckpunkte einer größeren Raute (für weichere, frühere Abstoßung)
        Vector top = new Vector(center.getX(), center.getY() - expandedRadius);
        Vector right = new Vector(center.getX() + expandedRadius, center.getY());
        Vector bottom = new Vector(center.getX(), center.getY() + expandedRadius);
        Vector left = new Vector(center.getX() - expandedRadius, center.getY());

        if (isInsideDiamond(p, top, right, bottom, left)) {
            Vector away = p.subtractVector(center).getNormalizedVector();
            particle.applyForce(away.scaleVector(1.5f));  // Repulsion leicht erhöht
        }

        // Rückführung
        applyVerticalCorrection(particle);

        // Luftwiderstand
        float rho = 1.225f;
        float speed = particle.getVelocity().getLength();
        float area = (float) Math.PI * particle.getRadius() * particle.getRadius();
        float Cd = obstacle.getDragCoefficient();

        float dragMagnitude = 0.5f * Cd * rho * area * speed * speed;
        dragMagnitude = Math.min(dragMagnitude, 0f);

        if (speed < 0.001f) return;

        Vector dragDirection = particle.getVelocity().getNormalizedVector().scaleVector(-1);
        Vector dragForce = dragDirection.scaleVector(dragMagnitude);

        particle.applyForce(dragForce);
    }

    private static boolean isInsideDiamond(Vector p, Vector top, Vector right, Vector bottom, Vector left) {
        // Einfache Methode: zerlege Raute in zwei Dreiecke oben/unten
        return isInsideTriangle(p, top, right, left) || isInsideTriangle(p, bottom, right, left);
    }

    public static void applyLeftTriangleRepulsion(Particle particle, LeftTriangleObstacle obstacle) {
        Vector p = particle.getPosition();
        Vector center = obstacle.getPosition();
        float r = obstacle.getRadius();

        // Eckpunkte für ein nach links gerichtetes, gestrecktes Dreieck
        Vector a = new Vector(center.getX() - r, center.getY());         // Spitze links
        Vector b = new Vector(center.getX() + r, center.getY() - r);     // rechts oben
        Vector c = new Vector(center.getX() + r, center.getY() + r);     // rechts unten

        if (isInsideTriangle(p, a, b, c)) {
            Vector away = p.subtractVector(center).getNormalizedVector();
            particle.applyForce(away.scaleVector(1.5f));
        }

        // Rückführung
        applyVerticalCorrection(particle);

        // Luftwiderstand
        float rho = 1.225f;
        float speed = particle.getVelocity().getLength();
        float area = (float) Math.PI * particle.getRadius() * particle.getRadius();
        float Cd = obstacle.getDragCoefficient();

        float dragMagnitude = 0.5f * Cd * rho * area * speed * speed;
        dragMagnitude = Math.min(dragMagnitude, 0f);

        if (speed < 0.001f) return;

        Vector dragDirection = particle.getVelocity().getNormalizedVector().scaleVector(-1);
        Vector dragForce = dragDirection.scaleVector(dragMagnitude);

        particle.applyForce(dragForce);
    }

    private static void applyVerticalCorrection(Particle particle){
        float yDeviation = particle.getPosition().getY() - particle.getInitialY();
        float verticalStrength = 0.0001f * Math.abs(yDeviation);
        Vector correction = new Vector(0, -yDeviation * verticalStrength);
        particle.applyForce(correction);
    }

}