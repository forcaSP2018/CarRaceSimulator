/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.racecarsimulation;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class RaceCarView extends BorderPane{
    private Label greeting;
    private StackPane carDisplay;
    private Label carCounter;
    private Label carType;
    public RaceCarView() {
        carType = new Label();
        
        greeting = new Label("Welcome to the Car Race Simulation!");

        carDisplay = new StackPane();
        carDisplay.setPrefSize(500, 250);
        
        carCounter = new Label("1 of 5");
        VBox bottomBox = new VBox(10);

        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.getChildren().addAll(carType, carCounter);
        setBottom(bottomBox);

        setTop(greeting);
        setCenter(carDisplay);

        BorderPane.setAlignment(greeting, Pos.CENTER);
        BorderPane.setAlignment(carDisplay, Pos.CENTER);
    }
    
    public void updateCounter(int current, int total) {
    carCounter.setText(current + " of " + total);
}

    public void setCar(Car car) {

        carDisplay.getChildren().clear();

        carDisplay.getChildren().add(car);
        carType.setText(car.getType());
    }
}
