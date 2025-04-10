package org.example.orientierungprojekt;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        // FXML mit BorderPane, UI und Canvas laden
        FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
        Parent root = loader.load();

        // Szene erstellen
        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("2D Particle System mit UI");
        stage.setScene(scene);
        stage.show();

        // Controller holen und Simulation starten
        HelloController controller = loader.getController();
        controller.startSimulation();
    }

    public static void main(String[] args) {
        launch();
    }
}
