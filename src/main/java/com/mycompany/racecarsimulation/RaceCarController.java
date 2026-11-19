/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.racecarsimulation;

import java.util.ArrayList;
import java.util.List;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.util.Duration;



/**
 * RaceCarController manages the simulation's logic.
 * It connects the RaceCarModel and RaceCarView classes.
 * It controls the slideshow, car movement, race buttons,
 * finishing order, and winner determination.
 */
public class RaceCarController {
    private RaceCarView view;

    private RaceCarModel[] models;
    private Car[] cars;

    private int currentIndex = 0;

    private Timeline slideshow;
    private Timeline raceTimeline;

    private boolean raceStarted = false;
    private boolean paused = false;
    private boolean raceFinished = false;

    private boolean[] finishedCars;
    private List<Integer> finishingOrder;

    private static final double FRAME_SECONDS = 0.02;
    
    /**
     * Constructor that creates the five race cars,
     * initializes their models, and connects the view.
     *
     * @param view the graphical interface of the simulation
     */
    public RaceCarController(RaceCarView view) {

        this.view = view;

        
        models = new RaceCarModel[5];

        models[0] = new RaceCarModel(
                "Ferrari", 45, Color.RED
        );

        models[1] = new RaceCarModel(
                "Lamborghini", 12, Color.BLUE
        );

        models[2] = new RaceCarModel(
                "Porsche", 27, Color.GREEN
        );

        models[3] = new RaceCarModel(
                "McLaren", 8, Color.YELLOW
        );

        models[4] = new RaceCarModel(
                "Bugatti", 99, Color.PURPLE
        );

        
        cars = new Car[models.length];

        for (int i = 0; i < models.length; i++) {

            RaceCarModel model = models[i];

            cars[i] = new Car(
                    0,
                    0,
                    model.getColor(),
                    model.getNumber(),
                    model.getType()
            );
        }

        finishedCars = new boolean[cars.length];

        finishingOrder = new ArrayList<>();

        view.setCar(cars[0]);
        view.updateCounter(1, cars.length);

        view.setOnEnterRace(() -> enterRace());
    }

    /**
     * Starts the slideshow and changes the displayed
     * car every three seconds using a Timeline.
     */
    public void startSlideshow() {

        cars[currentIndex].startTires();

        slideshow = new Timeline(
                new KeyFrame(
                        Duration.seconds(3),
                        event -> nextCar()
                )
        );

        slideshow.setCycleCount(Animation.INDEFINITE);
        slideshow.play();
    }

    /**
     * Displays the next car in the slideshow.
     * Returns to the first car after the last car.
     */
    private void nextCar() {

        cars[currentIndex].stopTires();

        currentIndex++;

        if (currentIndex >= cars.length) {
            currentIndex = 0;
        }

        view.setCar(cars[currentIndex]);

        view.updateCounter(
                currentIndex + 1,
                cars.length
        );

        cars[currentIndex].startTires();
    }

   /**
     * Stops the slideshow and prepares the race screen.
     * Connects the buttons and creates the race timeline.
     */
    private void enterRace() {

        slideshow.stop();

        for (Car car : cars) {
            car.stopTires();
        }

        view.showRaceScreen(cars);

        view.setOnStart(() -> startRace());
        view.setOnPause(() -> pauseRace());
        view.setOnReset(() -> resetRace());

        raceTimeline = new Timeline(
                new KeyFrame(
                        Duration.seconds(FRAME_SECONDS),
                        event -> updateRace()
                )
        );

        raceTimeline.setCycleCount(Animation.INDEFINITE);

        resetRace();
    }

    /**
     * Starts the race and begins moving the cars.
     * Prevents the race from starting more than once.
     */
    private void startRace() {

        if (raceFinished || raceStarted) {
            return;
        }

        raceStarted = true;
        paused = false;

        view.setRaceMessage("Race in progress!");

        view.setRaceControls(true, false, false);

        for (int i = 0; i < cars.length; i++) {

            if (!finishedCars[i]) {
                cars[i].startTires();
            }
        }

        raceTimeline.play();
    }

    /**
     * Pauses or resumes the race and tire animations.
     * Updates the race message and button states.
     */
    private void pauseRace() {

        if (!raceStarted || raceFinished) {
            return;
        }

        if (!paused) {

            raceTimeline.pause();

            for (Car car : cars) {
                car.pauseTires();
            }

            paused = true;

            view.setRaceMessage("Race paused!");

            view.setRaceControls(true, true, false);

        } else {

            raceTimeline.play();

            for (int i = 0; i < cars.length; i++) {

                if (!finishedCars[i]) {
                    cars[i].startTires();
                }
            }

            paused = false;

            view.setRaceMessage("Race in progress!");

            view.setRaceControls(true, false, false);
        }
    }

    /**
     * Resets the race by returning all cars to their
     * starting positions and generating new random speeds.
     * Also clears the previous results.
     */

    private void resetRace() {

        if (raceTimeline != null) {
            raceTimeline.stop();
        }

        raceStarted = false;
        paused = false;
        raceFinished = false;

        finishingOrder.clear();

        for (int i = 0; i < cars.length; i++) {

            finishedCars[i] = false;

            
            models[i].generateRandomSpeed();

            double startX = RaceCarView.START_X;
            double laneY = i * 75 + 15;

            models[i].setPosition(startX, laneY);

            cars[i].setLayoutX(startX);
            cars[i].setLayoutY(laneY);

            cars[i].resetTires();
        }

        view.setRaceMessage("Ready to race!");

        view.setResults(
                "Waiting for race results..."
        );

        view.setRaceControls(false, false, false);
    }

    /**
     * Updates the position of every car during the race.
     * Calculates movement using speed, multiplier, and time.
     * Checks for cars crossing the finish line and records
     * the finishing order.
     */
    private void updateRace() {

        double speedMultiplier =
                view.getSimulationSpeed();

        for (int i = 0; i < cars.length; i++) {

            if (finishedCars[i]) {
                continue;
            }

            RaceCarModel model = models[i];

           
            double distance =
                    model.getSpeed()
                    * speedMultiplier
                    * FRAME_SECONDS;

            double newX = model.getX() + distance;

            
            double finishPosition =
                    RaceCarView.FINISH_X
                    - RaceCarView.CAR_WIDTH;

            if (newX >= finishPosition) {

                newX = finishPosition;

                finishedCars[i] = true;

                finishingOrder.add(i);

                cars[i].stopTires();

               
                if (finishingOrder.size() == 1) {

                    view.setRaceMessage(
                            "Winner: "
                            + model.getType()
                            + " - Car #"
                            + model.getNumber()
                    );
                }
            }

            model.setPosition(newX, model.getY());

            cars[i].setLayoutX(newX);
        }

        updateResults();

        
        if (finishingOrder.size() == cars.length) {

            raceTimeline.stop();

            raceStarted = false;
            raceFinished = true;

            RaceCarModel winner =
                    models[finishingOrder.get(0)];

            view.setRaceMessage(
                    "Congratulations! Car #"
                    + winner.getNumber()
                    + " (" + getColorName(winner.getColor())
                    + ") wins!"
            );

            view.setRaceControls(false, false, true);
        }
    }

   /**
     * Creates a list of the cars that have finished
     * and displays their finishing positions.
     */
    private void updateResults() {

        StringBuilder results = new StringBuilder();

        for (int i = 0; i < finishingOrder.size(); i++) {

            int carIndex = finishingOrder.get(i);

            RaceCarModel model = models[carIndex];

            results.append(i + 1)
                    .append(". ")
                    .append(model.getType())
                    .append(" - Car #")
                    .append(model.getNumber())
                    .append(" (")
                    .append(getColorName(model.getColor()))
                    .append(")")
                    .append("\n");
        }

        if (finishingOrder.isEmpty()) {
            results.append("Race in progress...");
        }

        view.setResults(results.toString());
    }

  
    private String getColorName(Color color) {

        if (color.equals(Color.RED)) {
            return "Red";
        } else if (color.equals(Color.BLUE)) {
            return "Blue";
        } else if (color.equals(Color.GREEN)) {
            return "Green";
        } else if (color.equals(Color.YELLOW)) {
            return "Yellow";
        } else if (color.equals(Color.PURPLE)) {
            return "Purple";
        }

        return "Unknown";
    }

   /**
     * Stops all running timelines and tire animations.
     * Used to clean up the simulation when it is closed.
     */
    public void dispose() {

        if (slideshow != null) {
            slideshow.stop();
        }

        if (raceTimeline != null) {
            raceTimeline.stop();
        }

        for (Car car : cars) {
            car.stopTires();
        }
    }
}
