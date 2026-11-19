package com.mycompany.racecarsimulation;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {
        RaceCarView view = new RaceCarView();

        RaceCarController controller =
                new RaceCarController(view);

        Scene scene = new Scene(view, 600, 400);

        stage.setTitle("Car Race Simulation");
        stage.setScene(scene);
        stage.show();

        controller.startSlideshow();
    }

    public static void main(String[] args) {
        launch();
    }

}