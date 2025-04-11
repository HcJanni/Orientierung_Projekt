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
    private float angle = 0.0f;

    private List<Obstacle> obstacles;
    private Obstacle obstacle; // Optional: a single obstacle for simplicity

    public ParticleEmitter() {
        this.originVector = new Vector(0, 0);
        this.particles = new ArrayList<>(maxParticles);
        this.obstacles = new ArrayList<>(maxObstacles);
        this.obstacle = new Obstacle(250, 250);
    }

    public void addParticle(float x, float y) {
        if (particles.size() < maxParticles) {
            particles.add(new Particle(originVector.getX(), originVector.getY()));
        }
        else {
            System.out.println("Maximum der Partikel erreicht.");
        }
    }

    public void addObstacle(Obstacle obstacle, float x, float y) {
        obstacle = new Obstacle(obstacle.getPosition().getX(), obstacle.getPosition().getY());
        if (obstacles.size() < maxObstacles) {
            obstacles.add(obstacle);
        } else {
            System.out.println("Maximum der Hindernisse erreicht.");
        }
    }

    public void setObstacle(Obstacle obstacle) {
        obstacle = new Obstacle(obstacle.getPosition().getX(), obstacle.getPosition().getY());
    }

    public List<Particle> getParticles() {
        return particles;
    }
    
    private void initialize() {
        for(int i = 0; i < maxParticles; i++) {
            float angleInRadians = (float) Math.toRadians(angle);
            float speed = 1.0f; // Adjust speed as needed
            float dx = (float) (speed * Math.cos(angleInRadians));
            float dy = (float) (speed * Math.sin(angleInRadians));
            Particle particle = new Particle(originVector.getX(), originVector.getY() + 5*i, dx, dy, 0, 0);
            particles.add(particle);
        }
    }

   // Update particles
    private void update(float deltaTime) {

    particles.removeIf(Particle::isDead); // Remove dead particles
    
    for (Particle particle : particles) {

        if(obstacle != null) {
            obstacle.applyRepulsion(particle); // Apply repulsion from the obstacle
        }

        particle.updatePosition(deltaTime);

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
                update(0.016f * 100); // Update particles
                render(gc);    // Render particles
            }
        };
        timer.start();
    }
}
