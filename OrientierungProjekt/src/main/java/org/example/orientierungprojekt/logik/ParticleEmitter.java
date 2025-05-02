package org.example.orientierungprojekt.logik;

import org.example.orientierungprojekt.util.Vector;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;

import java.util.List;
import java.util.ArrayList;

public class ParticleEmitter {

    private final Vector originVector;
    //private RepulsionHandler repulsionHandler;

    private List<Particle> particles;
    private List<Obstacle> obstacles;

    private final int maxParticles = 1000;
    private final int maxObstacles = 10;

    private float particleSpeed = 5.0f;

    //private int lastSpawnTime = 0;
    //private int spawnInterval = 20_000_000; // alle 20ms ≈ 50 Partikel pro Sekunde

    private float directionDegrees = 0.0f;

    private GraphicsContext gc;

    public ParticleEmitter() {
        this.originVector = new Vector(0, 0);
        this.particles = new ArrayList<>(maxParticles);
        this.obstacles = new ArrayList<>(maxObstacles);
     //   repulsionHandler = new RepulsionHandler();
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
        
        Obstacle obs = new Obstacle(x, y);
        obstacles.add(obs);
    }

    public List<Particle> getParticles() {
        return particles;
    }

    public List<Obstacle> getObstacles() {
        return obstacles;
    }
    
    private void initialize() {
        gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());

        for(int i = 0; i < maxParticles; i++) {
            addParticle(this.originVector.getX(), this.originVector.getY() + 3 * i); // Partikel werden in vertikaler Linie initialisieren
        }

        for(int i = 0; i < maxObstacles; i++){ //Overlap muss noch verhindert werden
            addObstacle( (float) Math.random() * 500 + i, (float) Math.random() * 500 + i );
        }
    }

    // Update particles
    private void update() {
        for (Particle particle : particles) {
            if(particle.isDead()){
                gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
                particle.resetToOrigin();
                particle.setVelocity(1,0);
                particle.setLifespan(1.0f);
                particle.setDead(false);
            }
            particle.updatePosition();
            particle.updateLifespan(0.0016f);
        }
    }

    // Render particles and obstacles
    public void render(GraphicsContext gc) {

        for (Particle particle : particles) {
            particle.draw(gc);
        }

        for(Obstacle obstacle : obstacles){
            obstacle.draw(gc);
        }
        
    }

    public void start(GraphicsContext gc) {

        this.gc = gc;

        initialize(); // initialize particles and obstacles

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update(); // Update particles
                render(gc);    // Render particles
            }
        };

        timer.start();

    }

    public void setParticleSpeed(float speed) {
        this.particleSpeed = speed;
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