/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.racecarsimulation;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.transform.Scale;

public class RaceCarView extends BorderPane{
    
    public static final double START_X = 35;
    public static final double FINISH_X = 790;
    public static final double CAR_SCALE = 0.32;
    public static final double CAR_WIDTH = 300 * CAR_SCALE;
    
    private Label greeting;
    private StackPane carDisplay;
    private Label carCounter;
    private Label carType;    
    private Button enterRaceButton;
    private Button startButton;
    private Button pauseButton;
    private Button resetButton;
    private Slider speedSlider;
    private Label speedLabel;
    private Label raceMessage;
    private Label resultsLabel;
    private Pane raceTrack;
    public RaceCarView() {
       showSlideshowScreen();
    }
    
    public void updateCounter(int current, int total) {
    carCounter.setText(current + " of " + total);
}
    public void showSlideshowScreen() {

        setTop(null);
        setCenter(null);
        setBottom(null);

        Label greeting =
                new Label("Welcome to the Car Race Simulation!");

        greeting.setStyle(
                "-fx-font-size: 24px; -fx-font-weight: bold;"
        );

        carDisplay = new StackPane();
        carDisplay.setPrefSize(600, 250);

        carType = new Label();
        carType.setStyle(
                "-fx-font-size: 20px; -fx-font-weight: bold;"
        );

        carCounter = new Label("1 of 5");
        carCounter.setStyle("-fx-font-size: 16px;");

        enterRaceButton = new Button("Enter Race");

        enterRaceButton.setStyle(
                "-fx-background-color: #16a34a;" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 16px;"
        );

        VBox slideshow = new VBox(
                20,
                greeting,
                carDisplay,
                carType,
                carCounter,
                enterRaceButton
        );

        slideshow.setAlignment(Pos.CENTER);
        slideshow.setPadding(new Insets(25));

        setCenter(slideshow);
    }
    
    


    public void setCar(Car car) {

        carDisplay.getChildren().clear();
        
        car.getTransforms().clear();
        car.setLayoutX(0);
        car.setLayoutY(0);

        carDisplay.getChildren().add(car);
        carType.setText(car.getType());
    }
     public void setOnEnterRace(Runnable action) {
        enterRaceButton.setOnAction(event -> action.run());
    }
     
     
     public void showRaceScreen(Car[] cars) {

        // Remove the slideshow car first
        carDisplay.getChildren().clear();

        setTop(null);
        setCenter(null);
        setBottom(null);

        Label title = new Label("Car Race Simulation");

        title.setStyle(
                "-fx-font-size: 24px; -fx-font-weight: bold;"
        );

        raceTrack = new Pane();

        raceTrack.setPrefSize(900, 390);
        raceTrack.setMinSize(900, 390);
        raceTrack.setMaxSize(900, 390);

        raceTrack.setStyle(
                "-fx-background-color: #374151;"
        );
        for (int i = 1; i < 5; i++) {

            double laneY = i * 75;

            Line separator = new Line(
                    0, laneY, 900, laneY
            );

            separator.setStroke(Color.WHITE);
            separator.getStrokeDashArray().addAll(10.0, 8.0);

            raceTrack.getChildren().add(separator);
        }

        
        Line startLine = new Line(
                START_X, 0, START_X, 375
        );

        startLine.setStroke(Color.LIMEGREEN);
        startLine.setStrokeWidth(4);
        
        Line finishLine = new Line(
                FINISH_X, 0, FINISH_X, 375
        );

        finishLine.setStroke(Color.WHITE);
        finishLine.setStrokeWidth(5);

        Label startText = new Label("START");
        startText.setTextFill(Color.LIMEGREEN);
        startText.setLayoutX(5);
        startText.setLayoutY(375);

        Label finishText = new Label("FINISH");
        finishText.setTextFill(Color.WHITE);
        finishText.setLayoutX(FINISH_X - 20);
        finishText.setLayoutY(375);

        raceTrack.getChildren().addAll(
                startLine,
                finishLine,
                startText,
                finishText
        );
        
        for (int i = 0; i < cars.length; i++) {

            Car car = cars[i];

            car.getTransforms().setAll(
                    new Scale(CAR_SCALE, CAR_SCALE, 0, 0)
            );

            raceTrack.getChildren().add(car);
        }
        
        startButton = new Button("Start");
        pauseButton = new Button("Pause");
        resetButton = new Button("Reset");

        pauseButton.setDisable(true);
        
        speedSlider = new Slider(0.5, 3.0, 1.0);
        speedSlider.setPrefWidth(180);

        speedLabel = new Label("Speed: 1.0x");

        speedSlider.valueProperty().addListener(
                (observable, oldValue, newValue) -> {

                    speedLabel.setText(
                            String.format(
                                    "Speed: %.1fx",
                                    newValue.doubleValue()
                            )
                    );
                }
        );

        HBox buttons = new HBox(
                10,
                startButton,
                pauseButton,
                resetButton
        );

        buttons.setAlignment(Pos.CENTER_LEFT);

        VBox controls = new VBox(
                15,
                buttons,
                speedLabel,
                speedSlider
        );

        controls.setPadding(new Insets(15));
        controls.setPrefWidth(350);
        
        raceMessage = new Label("Ready to race!");

        raceMessage.setStyle(
                "-fx-font-size: 16px; -fx-font-weight: bold;"
        );

        resultsLabel = new Label(
                "Waiting for race results..."
        );

        resultsLabel.setWrapText(true);

        VBox results = new VBox(
                12,
                raceMessage,
                resultsLabel
        );

        results.setPadding(new Insets(15));
        results.setPrefWidth(400);

        HBox bottomArea = new HBox(
                30,
                controls,
                results
        );

        bottomArea.setAlignment(Pos.TOP_LEFT);

        VBox raceLayout = new VBox(
                15,
                title,
                raceTrack,
                bottomArea
        );

        raceLayout.setAlignment(Pos.TOP_CENTER);
        raceLayout.setPadding(new Insets(15));

        setCenter(raceLayout);
    }

    public void setOnStart(Runnable action) {
        startButton.setOnAction(event -> action.run());
    }

    public void setOnPause(Runnable action) {
        pauseButton.setOnAction(event -> action.run());
    }

    public void setOnReset(Runnable action) {
        resetButton.setOnAction(event -> action.run());
    }

    public double getSimulationSpeed() {
        return speedSlider.getValue();
    }

    public void setRaceMessage(String message) {
        raceMessage.setText(message);
    }

    public void setResults(String results) {
        resultsLabel.setText(results);
    }

    public void setRaceControls(boolean running,
                                boolean paused,
                                boolean finished) {

        startButton.setDisable(running || finished);

        pauseButton.setDisable(!running || finished);

        pauseButton.setText(
                paused ? "Resume" : "Pause"
        );
     }
}
