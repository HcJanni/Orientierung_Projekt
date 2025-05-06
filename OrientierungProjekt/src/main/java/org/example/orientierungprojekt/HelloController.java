package org.example.orientierungprojekt;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import org.example.orientierungprojekt.logik.ParticleEmitter;
import org.example.orientierungprojekt.util.UIControl;

public class HelloController {

    @FXML
    private Canvas mainCanvas;

    @FXML
    private Button resetButton;

    @FXML private Button startButton;
    @FXML private Button pauseButton;


    private ParticleEmitter particleEmitter;
    private UIControl uiControl;

    @FXML
    private Slider speedSlider, particleSlider, lifeSlider, directionSlider;

    @FXML
    private Label speedLabel, particleLabel, lifeLabel;

    @FXML private ComboBox<String> obstacleDropdown;

    @FXML private CheckBox clearToggleBox;

    @FXML
    private Canvas legendCanvas;

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

            // Fläche & Partikel zurücksetzen
            if (particleEmitter != null) {
                resetAndUnlock();
            }
        });

        // Lebensdauer
        lifeSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            double value = Math.round(newVal.doubleValue() * 10.0) / 10.0;
            lifeLabel.setText("Lebensdauer: " + value);
        });

        obstacleDropdown.setValue("Bitte auswählen"); // Default

        clearToggleBox.setSelected(true); // Standardwert: an
        clearToggleBox.setOnAction(e -> {
            boolean clear = clearToggleBox.isSelected();
            particleEmitter.setClearBeforeRender(clear);
            resetAndUnlock(); // sofort zurücksetzen
        });

        startButton.setOnAction(e -> {
            particleEmitter.resume();
            startButton.setDisable(true);
            pauseButton.setDisable(false);
        });

        pauseButton.setOnAction(e -> {
            particleEmitter.pause();
            startButton.setDisable(false);
            pauseButton.setDisable(true);
        });

        drawLegendGradient();

        mainCanvas.setOnMousePressed(event -> {
            if (particleEmitter == null || particleEmitter.isRunning()) {
                return;
            }

            double x = event.getX();
            double y = event.getY();

            if (event.isPrimaryButtonDown()) {
                particleEmitter.addObstacleAt((float) x, (float) y, obstacleDropdown.getValue());
                particleEmitter.resetParticlesOnly(); // ⬅️ Partikel zurücksetzen
            }

            if (event.isSecondaryButtonDown()) {
                particleEmitter.removeNearestObstacle((float) x, (float) y, 30.0f);
                particleEmitter.resetParticlesOnly(); // ⬅️ ebenfalls zurücksetzen
            }

            GraphicsContext gc = mainCanvas.getGraphicsContext2D();
            gc.clearRect(0, 0, mainCanvas.getWidth(), mainCanvas.getHeight());
            particleEmitter.render(gc);
        });

        resetButton.setOnAction(e -> resetAndUnlock());







    }

    /**
     * Wird von HelloApplication nach dem Laden des FXML aufgerufen.
     */
    public void startSimulation() {
        GraphicsContext gc = mainCanvas.getGraphicsContext2D();
        // NICHT sofort starten!
        particleEmitter = new ParticleEmitter();
        particleEmitter.start(gc); // ⬅️ Das initialisiert nur – Animation startet erst mit resume()

        speedSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            System.out.println("Neue Geschwindigkeit im Slider: " + newVal.floatValue());
        });
        // UIControl verbinden
        uiControl = new UIControl(
                particleEmitter,
                speedSlider, particleSlider, lifeSlider, directionSlider,
                resetButton, obstacleDropdown
        );
    }

    private void drawLegendGradient() {
        GraphicsContext gc = legendCanvas.getGraphicsContext2D();
        for (int x = 0; x < legendCanvas.getWidth(); x++) {
            double t = x / legendCanvas.getWidth();
            Color color = new Color(t, 0, 1 - t, 1.0);
            gc.setFill(color);
            gc.fillRect(x, 0, 1, legendCanvas.getHeight());
        }
    }

    private void resetAndUnlock() {
        particleEmitter.reset();
        startButton.setDisable(false);
        pauseButton.setDisable(true);
    }

}
