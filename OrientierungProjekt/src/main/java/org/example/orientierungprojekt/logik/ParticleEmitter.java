package org.example.orientierungprojekt.logik;

import javafx.scene.paint.Color;
import org.example.orientierungprojekt.util.Vector;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;

import java.util.List;
import java.util.ArrayList;

public class ParticleEmitter {

    private final Vector originVector;
    private RepulsionHandler repulsionHandler;

    private List<Particle> particles;
    private final List<Obstacle> obstacles;

    private int maxParticles = 300;
    private final int maxObstacles = 1;

    private float particleSpeed = 5.0f;

    private boolean clearBeforeRender = true; // Standard: löschen aktiv

    private AnimationTimer timer;
    private boolean isRunning = false;

    private float lifespanSeconds = 14.0f; // Standardlebensdauer

    //private int lastSpawnTime = 0;
    //private int spawnInterval = 20_000_000; // alle 20ms ≈ 50 Partikel pro Sekunde

    private float directionDegrees = 0.0f;

    private GraphicsContext gc;

    public ParticleEmitter() {
        this.originVector = new Vector(0, 300);
        this.particles = new ArrayList<>(maxParticles);
        this.obstacles = new ArrayList<>(maxObstacles);
        this.repulsionHandler = new RepulsionHandler();
    }

    public void addParticle(float x, float y) {
        if (particles.size() >= maxParticles) {
            particles.remove(0); // Ältesten löschen, damit neue rein können
        }

        Particle p = new Particle(x, y);
        particles.add(p);
    }

    public void addObstacle(float x, float y) {
        if (obstacles.size() >= maxObstacles) {
            obstacles.remove(0);
        } 
        
        CircleObstacle circle = new CircleObstacle(300, 300);
        SquareObstacle square = new SquareObstacle(x, y+200, 50.0f);
        TriangleObstacle triangle = new TriangleObstacle(x, y-200, 50.0f);
        obstacles.add(circle);
        obstacles.add(square);
        obstacles.add(triangle);
    }

    public void setObstacleType(String type) {
        obstacles.clear();
        switch (type) {
            case "Kreis":
                obstacles.add(new CircleObstacle(300, 300));
                break;
            case "Quadrat":
                obstacles.add(new SquareObstacle(300, 300, 50.0f));
                break;
            case "Dreieck":
                obstacles.add(new TriangleObstacle(300, 300, 50.0f));
                break;
        }
    }

    public void addObstacleAt(float x, float y, String type, float size) {
        switch (type) {
            case "Kreis" -> obstacles.add(new CircleObstacle(x, y, size));
            case "Quadrat" -> obstacles.add(new SquareObstacle(x, y, size));
            case "Dreieck" -> obstacles.add(new TriangleObstacle(x, y, size));
            case "Flügelprofil" -> obstacles.add(new AirfoilObstacle(x, y, size));
        }
    }

    public void removeNearestObstacle(float x, float y, float maxDistance) {
        Vector clickPos = new Vector(x, y);
        Obstacle toRemove = null;
        float minDist = Float.MAX_VALUE;

        for (Obstacle o : obstacles) {
            float dist = o.getPosition().distanceTo(clickPos);
            if (dist < minDist && dist <= maxDistance) {
                minDist = dist;
                toRemove = o;
            }
        }

        if (toRemove != null) {
            obstacles.remove(toRemove);
        }

        System.out.println("Clicked at: " + x + "," + y);
        System.out.println("Checking against " + obstacles.size() + " obstacles.");

        for (Obstacle o : obstacles) {
            float dist = o.getPosition().distanceTo(clickPos);
            System.out.println("Distance to obstacle: " + dist);
        }
    }

    public List<Particle> getParticles() {
        return particles;
    }

    public List<Obstacle> getObstacles() {
        return obstacles;
    }
    
    private void initialize() {
        gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
        initializeParticles();  // Statt direkter Code



        /*int half = maxParticles / 2;
        float spacing = 2.0f;

        float angleRad = (float) Math.toRadians(directionDegrees);
        float dx = (float) Math.cos(angleRad);
        float dy = (float) Math.sin(angleRad);

        for (int i = -half; i < half; i++) {
            float offsetX = i * 2.0f * -dy;  // Orthogonal zur Flugrichtung verteilen
            float offsetY = i * 2.0f * dx;
            addParticle(originVector.getX() + offsetX, originVector.getY() + offsetY);
        }


        for (int i = -half; i < half; i++) {
            addParticle(this.originVector.getX(), this.originVector.getY() + i * spacing);
        }
        setParticleLifespan(lifespanSeconds); // ← neue Zeile*/

        /*for(int i = 0; i < maxObstacles; i++){ //Overlap muss noch verhindert werden
            addObstacle( 300 + i, 300 + i);
        }*/
    }

    public void reset() {
        pause(); // Animation stoppen
        isRunning = false; // ← WICHTIG: internen Status zurücksetzen

        particles.clear();
        obstacles.clear();

        if (gc != null) {
            gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
        }

        initialize();
        setParticleLifespan(lifespanSeconds);
    }


    public void setClearBeforeRender(boolean value) {
        this.clearBeforeRender = value;
        System.out.println("Strömung anzeigen: " + value);
    }

    // Update particles
    private void update() {
        for (int i = 0; i < particles.size(); i++) {
            Particle p1 = particles.get(i);
    
            // Check if the particle is dead and reset it
            if (p1.isDead()) {
                gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
                p1.resetToOrigin();
                double radians = Math.toRadians(directionDegrees);
                float dx = (float) Math.cos(radians);
                float dy = (float) Math.sin(radians);
                p1.setVelocity(dx * particleSpeed, dy * particleSpeed);
                /*p1.resetToOrigin();
                p1.setVelocity(1, 0); // Reset velocity*/
                p1.setLifespan(lifespanSeconds); // Reset lifespan
                p1.setDead(false); // Mark as alive
            }
    
            // Check for collisions with other particles
            /*for (int j = i + 1; j < particles.size(); j++) {
                Particle p2 = particles.get(j);
                p1.particleBounce(p2); // Handle collision
            }*/

            // Apply repulsion from obstacles
            for (Obstacle obs : obstacles) {
                //repulsionHandler.applyCircleRepulsion(p1, obs);
                obs.applyRepulsion(p1);
            }
    
            // Update particle position and lifespan
            p1.updatePosition();
            p1.updateLifespan(1.0f / 60.0f); // Assuming ~60 FPS, deltaTime = 1/60
        }
    }

    // Render particles and obstacles
    public void render(GraphicsContext gc) {

        /*if (!clearBeforeRender) {
            gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
        }*/

        for (Particle particle : particles) {
            particle.draw(gc);
        }

        for(Obstacle obstacle : obstacles){
            obstacle.draw(gc);
        }
    }

    public void toggleClear() {
        clearBeforeRender = !clearBeforeRender;
        System.out.println("Clear-Modus: " + (clearBeforeRender ? "AN" : "AUS"));
    }

    public void start(GraphicsContext gc) {

        this.gc = gc;

        initialize(); // initialize particles and obstacles

        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update(); // Update particles
                render(gc); // Render particles
            }
        };

        /*AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update(); // Update particles
                render(gc);    // Render particles
            }
        };
        timer.start();*/
    }

    public void resume() {
        if (!isRunning && timer != null) {
            timer.start();
            isRunning = true;
        }
    }

    public void pause() {
        if (isRunning && timer != null) {
            timer.stop();
            isRunning = false;
        }
    }

    public void resetParticlesOnly() {
        particles.clear();
        initializeParticles();  // Neue Hilfsmethode, siehe unten
    }

    private void initializeParticles() {
        int half = maxParticles / 2;
        float spacing = 2.0f;

        for (int i = -half; i < half; i++) {
            addParticle(originVector.getX(), originVector.getY() + i * spacing);
        }

        setParticleLifespan(lifespanSeconds);
    }

    public boolean isRunning() { return isRunning; }

    public void setParticleSpeed(float speed) {
        this.particleSpeed = speed;

        // Wende neue Geschwindigkeit auf alle Partikel an
        for (Particle p : particles) {
            Vector dir = p.getVelocity().getNormalizedVector();
            p.setVelocity(dir.scaleVector(speed));
        }
    }

    public void setParticleLifespan(float seconds) {
        this.lifespanSeconds = seconds;
        for (Particle p : particles) {
            if (!p.isDead()) {
                p.setLifespan(seconds);
            }
        }
    }

    public void setWindDirection(float degrees) {
        this.directionDegrees = degrees;
    }

    public void setMaxParticles(int count) {
        this.maxParticles = count;

        // Partikelliste ggf. anpassen:
        if (particles.size() > maxParticles) {
            particles = particles.subList(0, maxParticles);
        }
    }

    public void setParticleDirection(float degrees) {
        this.directionDegrees = degrees;
    }

    public boolean isOutsideCanvas(Particle p) {
        float x = p.getPosition().getX();
        float y = p.getPosition().getY();
        return x < -10 || x > gc.getCanvas().getWidth() + 10
                || y < -10 || y > gc.getCanvas().getHeight() + 10;
    }

    public void updateParticleSpeeds(float speed) {
        for (Particle p : particles) {
            Vector dir = p.getVelocity().getNormalizedVector();
            p.setVelocity(dir.scaleVector(speed));
        }
    }
}