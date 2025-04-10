package org.example.orientierungprojekt;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Slider;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.List;

public class HelloController {

    @FXML
    private Canvas mainCanvas;

    private final List<Particle> particles = new ArrayList<>();

    @FXML
    private Slider speedSlider, particleSlider, lifeSlider, directionSlider;

    @FXML
    private Label speedLabel, particleLabel, lifeLabel, directionLabel;

    @FXML
    public void initialize() {
        // Geschw.
        speedSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            double value = Math.round(newVal.doubleValue() * 10.0) / 10.0;
            speedLabel.setText("Geschw.: " + value);
        });

        // Partikel (ganzzahlig)
        particleSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            int value = newVal.intValue();
            particleLabel.setText("Partikel: " + value);
        });

        // Lebensdauer
        lifeSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            double value = Math.round(newVal.doubleValue() * 10.0) / 10.0;
            lifeLabel.setText("Lebensdauer: " + value);
        });

        // Richtung
        directionSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            double value = Math.round(newVal.doubleValue() * 10.0) / 10.0;
            directionLabel.setText("Windrichtung: " + value + "°");
        });
    }

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
