package org.example.orientierungprojekt;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Slider;
import javafx.scene.control.Label;


public class HelloController {

    @FXML
    private Canvas mainCanvas;

    private ParticleEmitter particleEmitter;

    private Obstacle obstacle;

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
        GraphicsContext gc = mainCanvas.getGraphicsContext2D();
        particleEmitter = new ParticleEmitter(100, 0 , 0);
        particleEmitter.start(gc);
        obstacle = new Obstacle(50, 50);
        obstacle.draw(gc);


        speedSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            System.out.println("Neue Geschwindigkeit im Slider: " + newVal.floatValue());
        });
    }

}
