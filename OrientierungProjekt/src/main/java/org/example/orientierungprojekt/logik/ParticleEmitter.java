package org.example.orientierungprojekt.logik;

import org.example.orientierungprojekt.util.Vector;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;

import java.util.List;
import java.util.ArrayList;

public class ParticleEmitter {

    private final Vector originVector;

    private List<Particle> particles;
    private List<Obstacle> obstacles;

    private final int maxParticles = 500;
    private final int maxObstacles = 10;

    private Obstacle obstacle;

    private float particleSpeed = 5.0f;

    private long lastSpawnTime = 0;
    private long spawnInterval = 20_000_000; // alle 20ms ≈ 50 Partikel pro Sekunde

    private float directionDegrees = 0.0f;

    private GraphicsContext gc;
    private Obstacle obstacle2;

    public ParticleEmitter() {
        this.originVector = new Vector(50, 0);
        this.particles = new ArrayList<>(maxParticles);
        this.obstacles = new ArrayList<>(maxObstacles);
        this.obstacle = new Obstacle(250, 250, 500, 1.0f); // Example obstacle with radius and repel force
        this.obstacle2 = new Obstacle(255, 490);
    }

    /*public void addParticle(float x, float y) {
         /*if (particles.size() >= maxParticles) {
                particles.remove(0); // ältesten Partikel löschen
            }
            Particle p = new Particle(x, y, particleSpeed, 0, 0, 0);
            p.setLifespan(particleLifetime);
            particles.add(p);


        if (particles.size() < maxParticles) {
            Particle p = new Particle(x, y, particleSpeed, 0, 0, 0);
            p.setLifespan(particleLifetime);
            particles.add(p);
        } else {
            System.out.println("Maximum der Partikel erreicht.");
        }
    }*/

    public void addParticle(float x, float y) {
        if (particles.size() >= maxParticles) {
            particles.remove(0); // Ältesten löschen, damit neue rein können
        }

        float radians = (float) Math.toRadians(directionDegrees);
        float dx = (float) (Math.cos(radians) * particleSpeed);
        float dy = (float) (Math.sin(radians) * particleSpeed);

        Particle p = new Particle(x, y, dx, dy, 0, 0);
        particles.add(p);
    }

    public void addObstacle(float x, float y) {
        obstacle = new Obstacle(x, y);
        if (obstacles.size() < maxObstacles) {
            obstacles.add(obstacle);
        } else {
            System.out.println("Maximum der Hindernisse erreicht.");
        }
    }

    public List<Particle> getParticles() {
        return particles;
    }
    
    private void initialize() {
        for(int i = 0; i < maxParticles; i++) {
            addParticle(this.originVector.getX(), this.originVector.getY() + 5 * i); // Initialize particles in a vertical line
        }
    }

    // Update particles
    private void update() {

        particles.removeIf(Particle::isDead); // Remove dead particles

        for (Particle particle : particles) {

        if(obstacle != null) {
            obstacle.applyRepulsion(particle); // Apply repulsion from the obstacle
            obstacle2.applyRepulsion(particle); // Apply repulsion from the second obstacle
        }

            particle.updatePosition();

        }
    }

    // Render particles
    public void render(GraphicsContext gc) {

        for (Particle particle : particles) {
            particle.draw(gc);
        }

        if(obstacle != null) {
            obstacle.draw(gc); // Draw the obstacle
            obstacle2.draw(gc); // Draw the second obstacle
        }
    }

    public void start(GraphicsContext gc) {

        this.gc = gc;

        initialize(); // initialize new particles

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update(); // Update particles
                render(gc);    // Render particles
            }
        };
        timer.start();
    }

    public void reset() {
        if (gc != null) {
            gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
        }
        particles.clear();
        initialize(); // neu starten
    }

    public void setParticleSpeed(float speed) {
        this.particleSpeed = speed;
    }

    public void setParticleDirection(float degrees) {
        this.directionDegrees = degrees;
    }

    private boolean isOutsideCanvas(Particle p) {
        float x = p.getCurrentPosition().getX();
        float y = p.getCurrentPosition().getY();
        return x < -10 || x > gc.getCanvas().getWidth() + 10
                || y < -10 || y > gc.getCanvas().getHeight() + 10;
    }

    public void updateParticleSpeeds(float speed) {
        for (Particle p : particles) {
            Vector dir = p.getVelocity().normalizeVector();
            p.setVelocity(dir.scaleVector(speed));
        }
    }



}
