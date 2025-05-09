package org.example.orientierungprojekt.util;

import javafx.scene.control.*;

import org.example.orientierungprojekt.logik.ParticleEmitter;

public class UIControl {

    private ParticleEmitter emitter;

    private Slider speedSlider, particleSlider, lifeSlider;
    private Button resetButton;
    private float currentSpeed = 5.0f; // Standardwert
    private ComboBox<String> obstacleDropdown;

    public UIControl(ParticleEmitter emitter,
                     Slider speedSlider, Slider lifeSlider,
                     Button resetButton, ComboBox<String> obstacleDropdown) {
        this.emitter = emitter;
        this.speedSlider = speedSlider;
        this.lifeSlider = lifeSlider;
        this.resetButton = resetButton;
        this.obstacleDropdown = obstacleDropdown;

        setupListeners();
    }


    // Baut die UI Elemente auf
    private void setupListeners() {
        speedSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            currentSpeed = newVal.floatValue();
            System.out.println("Neue Geschwindigkeit: " + currentSpeed);
            SimulationConfig.GLOBAL_PARTICLE_SPEED = currentSpeed;
            emitter.setParticleSpeed(currentSpeed);

            // GLOBAL_FLOW anpassen
            SimulationConfig.GLOBAL_FLOW.setX(currentSpeed);
            SimulationConfig.GLOBAL_FLOW.setY(0);
        });

        obstacleDropdown.setOnAction(e -> {
            String selected = obstacleDropdown.getValue();
        });

        lifeSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            float seconds = newVal.floatValue();
            emitter.setParticleLifespan(seconds);
        });
    }
}
