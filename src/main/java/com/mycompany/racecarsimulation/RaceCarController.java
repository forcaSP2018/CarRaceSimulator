/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.racecarsimulation;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.util.Duration;




public class RaceCarController {
    private RaceCarView view;
    

    private Car[] cars;

    private int currentIndex = 0;

    private Timeline slideshow;

    public RaceCarController(RaceCarView view) {

        this.view = view;

        cars = new Car[5];

        cars[0] = new Car(0, 0, Color.RED, 45, "Ferrari");
        cars[1] = new Car(0, 0, Color.BLUE, 12, "Lamborghini");
        cars[2] = new Car(0, 0, Color.GREEN, 27, "Porsche");
        cars[3] = new Car(0, 0, Color.YELLOW, 8, "McLaren");
        cars[4] = new Car(0, 0, Color.PURPLE, 99, "Mercedes");

        view.setCar(cars[0]);
        view.updateCounter(1, cars.length);
    }

    public void startSlideshow() {

        cars[currentIndex].startTires();

        slideshow = new Timeline(new KeyFrame(
        Duration.seconds(3),
        event -> nextCar())
        );

        slideshow.setCycleCount(Timeline.INDEFINITE);
        slideshow.play();
    }

    private void nextCar() {       
    cars[currentIndex].stopTires();

    currentIndex++;

    if (currentIndex >= cars.length) {
        currentIndex = 0;
    }

    view.setCar(cars[currentIndex]);

    view.updateCounter(currentIndex + 1, cars.length);

    cars[currentIndex].startTires();
    }
}
