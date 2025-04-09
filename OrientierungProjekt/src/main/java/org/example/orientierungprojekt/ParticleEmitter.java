package org.example.orientierungprojekt;

import org.example.orientierungprojekt.util.Vector;

import javafx.scene.canvas.GraphicsContext;

import java.util.List;
import java.util.ArrayList;

public class ParticleEmitter {

    private final Vector originVector;
    private List<Particle> particles;
    private int maxParticles;

    public ParticleEmitter(int maxParticles, float originX, float originY) {
        this.originVector = new Vector(originX, originY);
        this.maxParticles = maxParticles;
        this.particles = new ArrayList<>(maxParticles);
    }

    public void emit(float x, float y, float dx, float dy, float accX, float accY) {
        if (particles.size() < maxParticles) {
            Particle particle = new Particle(x, y, dx, dy, accX, accY);
            particles.add(particle);
        }
    }

    public void resetPosition() {
        for (Particle particle : particles) {
            particle.setPosition(originVector.getX(), originVector.getY());
        }
    }

    public void update(float deltaTime) {
        for (int i = 0; i < particles.size(); i++) {
            Particle particle = particles.get(i);
            particle.update(deltaTime);
            if (particle.isDead()) {
                particles.remove(i);
                i--;
            }
        }
    }

    public void render(GraphicsContext gc) {
        for (Particle particle : particles) {
            particle.draw(gc);
        }
    }

}
