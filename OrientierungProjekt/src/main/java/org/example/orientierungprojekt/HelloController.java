package org.example.orientierungprojekt;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Slider;

import java.util.ArrayList;
import java.util.List;

public class HelloController {

    @FXML
    private Canvas mainCanvas;

    @FXML
    private Slider speedSlider; // << Hinzugefügt

    private final List<Particle> particles = new ArrayList<>();

    /**
     * Wird von HelloApplication nach dem Laden des FXML aufgerufen.
     */
    public void startSimulation() {
        initializeParticles();
        drawParticles();

        // Wertänderung im Slider beobachten
        speedSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            System.out.println("Neue Geschwindigkeit im Slider: " + newVal.floatValue());
        });
    }

    private void initializeParticles() {
        for (int i = 0; i < 100; i++) {
            particles.add(new Particle(
                    (float) (Math.random() * mainCanvas.getWidth()),
                    (float) (Math.random() * mainCanvas.getHeight()),
                    (float) (Math.random() * 2 - 1),
                    (float) (Math.random() * 2 - 1),
                    0, 0
            ));
        }
    }

    private void drawParticles() {
        GraphicsContext gc = mainCanvas.getGraphicsContext2D();
        gc.clearRect(0, 0, mainCanvas.getWidth(), mainCanvas.getHeight());

        for (Particle particle : particles) {
            particle.draw(gc);
        }
    }
}
