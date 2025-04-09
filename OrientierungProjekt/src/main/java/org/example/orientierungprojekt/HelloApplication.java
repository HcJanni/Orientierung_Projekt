package org.example.orientierungprojekt;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class HelloApplication extends Application {

    private List<Particle> particles = new ArrayList<>();

    @Override
    public void start(Stage stage) {
        // Create a Canvas
        Canvas canvas = new Canvas(800, 600);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Create a Pane to hold the Canvas
        Pane root = new Pane(canvas);
        Scene scene = new Scene(root);

        // Initialize particles
        initializeParticles();

        // Draw particles
        drawParticles(gc);

        // Set up the Stage
        stage.setTitle("2D Particle System");
        stage.setScene(scene);
        stage.show();
    }

    private void initializeParticles() {
        // Add some particles to the list
        for (int i = 0; i < 100; i++) {
            particles.add(new Particle(
                    (float) (Math.random() * 800), // Random x position
                    (float) (Math.random() * 600), // Random y position
                    (float) (Math.random() * 2 - 1), // Random x velocity
                    (float) (Math.random() * 2 - 1), // Random y velocity
                    0, 0 // No initial acceleration
            ));
        }
    }

    private void drawParticles(GraphicsContext gc) {
        // Clear the canvas
        gc.clearRect(0, 0, 800, 600);

        // Draw each particle
        for (Particle particle : particles) {
            particle.draw(gc);
        }
    }

    public static void main(String[] args) {
        launch();
    }
}