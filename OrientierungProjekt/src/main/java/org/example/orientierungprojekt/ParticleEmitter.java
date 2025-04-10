package org.example.orientierungprojekt;

import org.example.orientierungprojekt.util.Vector;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;

import java.util.List;
import java.util.ArrayList;

public class ParticleEmitter {

    private final Vector originVector;
    private List<Particle> particles;
    private int maxParticles;

    private float angle = 0; // Angle in degrees


    public ParticleEmitter(int maxParticles, float originX, float originY) {
        this.originVector = new Vector(originX, originY);
        this.maxParticles = maxParticles;
        this.particles = new ArrayList<>(maxParticles);
    }

    private void emit() {
        for(int i = 0; i < maxParticles; i++) {
            float angleInRadians = (float) Math.toRadians(angle);
            float speed = 1.0f; // Adjust speed as needed
            float dx = (float) (speed * Math.cos(angleInRadians));
            float dy = (float) (speed * Math.sin(angleInRadians));
            Particle particle = new Particle(originVector.getX(), originVector.getY() + 25*i, dx, dy, 0, 0);
            particles.add(particle);
        }
    }

   // Update particles
    private void update(float deltaTime) {
    particles.removeIf(Particle::isDead); // Remove dead particles
    for (Particle particle : particles) {
        particle.update(deltaTime);
    }
}   

    // Render particles
    public void render(GraphicsContext gc) {
        for (Particle particle : particles) {
            particle.draw(gc);
        }
    }
    public void start(GraphicsContext gc) {
        gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight()); // Clear the canvas

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                emit(); // Emit new particles
                update(10); // Update particles
                render(gc);    // Render particles
            }
        };
        timer.start();
    }
}
