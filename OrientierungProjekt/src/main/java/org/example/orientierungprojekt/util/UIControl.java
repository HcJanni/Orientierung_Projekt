package org.example.orientierungprojekt.util;

import javafx.scene.control.*;
import javafx.scene.canvas.GraphicsContext;

import org.example.orientierungprojekt.logik.ParticleEmitter;

public class UIControl {

    private ParticleEmitter emitter;

    private Slider speedSlider, particleSlider, lifeSlider, directionSlider;
    private Button resetButton;
    private float currentSpeed = 5.0f; // Standardwert
    private float currentDirection = 0.0f;
    private ComboBox<String> obstacleDropdown;



    public UIControl(ParticleEmitter emitter,
                     Slider speedSlider, Slider particleSlider, Slider lifeSlider, Slider directionSlider,
                     Button resetButton, ComboBox<String> obstacleDropdown) {
        this.emitter = emitter;
        this.speedSlider = speedSlider;
        this.particleSlider = particleSlider;
        this.lifeSlider = lifeSlider;
        this.directionSlider = directionSlider;
        this.resetButton = resetButton;
        this.obstacleDropdown = obstacleDropdown;

        setupListeners();
    }


    private void setupListeners() {
        resetButton.setOnAction(e -> {
            System.out.println("Simulation wurde zurückgesetzt.");
            emitter.reset(); // Diese Methode musst du noch in ParticleEmitter implementieren
        });

        speedSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            currentSpeed = newVal.floatValue();
            System.out.println("Neue Geschwindigkeit: " + currentSpeed);
            emitter.setParticleSpeed(currentSpeed);
        });

        directionSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            currentDirection = newVal.floatValue();
            System.out.println("Neue Windrichtung: " + currentDirection + "°");
            emitter.setParticleDirection(currentDirection);
        });

        obstacleDropdown.setOnAction(e -> {
            String selected = obstacleDropdown.getValue();
            emitter.setObstacleType(selected);
        });





        // TODO: Weitere Slider-Events folgen später hier
    }
}
