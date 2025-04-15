package org.example.orientierungprojekt;

import org.example.orientierungprojekt.util.Vector;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;

import java.util.List;
import java.util.ArrayList;

public class ParticleEmitter {

    private final Vector originVector;
    private List<Particle> particles;
    private final int maxParticles = 1000;
    private final int maxObstacles = 10;

    private List<Obstacle> obstacles;
    private Obstacle obstacle;

    public ParticleEmitter() {
        this.originVector = new Vector(0, 0);
        this.particles = new ArrayList<>(maxParticles);
        this.obstacles = new ArrayList<>(maxObstacles);
        this.obstacle = new Obstacle(250, 250);
    }

    public void addParticle(float x, float y) {
        if (particles.size() < maxParticles) {
            particles.add(new Particle(x, y, 1, 0, 0, 0)); // Initialize with zero velocity and acceleration
        }
        else {
            System.out.println("Maximum der Partikel erreicht.");
        }
    }

    public void addObstacle(float x, float y) {
        obstacle = new Obstacle(obstacle.getPosition().getX(), obstacle.getPosition().getY());
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
        }

        particle.updatePosition();

        }
    }   

    // Render particles
    public void render(GraphicsContext gc) {

        //gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight()); // Clear the canvas

        for (Particle particle : particles) {
            particle.draw(gc);
        }

        if(obstacle != null) {
            obstacle.draw(gc); // Draw the obstacle
        }
    }

    public void start(GraphicsContext gc) {

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
}
