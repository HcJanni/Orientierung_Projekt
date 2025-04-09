package org.example.orientierungprojekt;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HelloApplication extends Application {

    private List<Particle> particles = new ArrayList<>();

    @Override
    public void start(Stage stage) throws IOException {
        // Canvas vorbereiten
        Canvas canvas = new Canvas(800, 600);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // FXML laden (das UI-Overlay mit fester Höhe)
        FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
        Parent fxmlOverlay = loader.load(); // nicht casten zu Region!

        // BorderPane = oben UI, center Canvas
        BorderPane root = new BorderPane();
        root.setTop(fxmlOverlay);        // UI-Leiste oben
        root.setCenter(canvas);          // Simulation im Hauptbereich

        // Szene & Fenster
        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("2D Particle System mit UI-Leiste");
        stage.setScene(scene);
        stage.show();

        // Partikel vorbereiten
        initializeParticles();
        drawParticles(gc);
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