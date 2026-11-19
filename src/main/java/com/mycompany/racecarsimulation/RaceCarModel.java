/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.racecarsimulation;
    
import java.util.Random;
import javafx.scene.paint.Color;

/**
 * RaceCarModel stores the information for each race car.
 * It keeps track of the car's type, number, color,
 * position, and speed.
 *
 * This class does not handle any graphical animations.
 */
public class RaceCarModel {
    private String type;
    private int number;
    private double x;
    private double y;
    private double speed;
    private Color color;
    private static final Random random = new Random();
  /**
     * Constructor that initializes the car's information.
     * The position starts at zero and a random speed is generated.
     *
     * @param type the name of the car
     * @param number the racing number
     * @param color the color of the car
     */
    public RaceCarModel(String type, int number, Color color) {
        this.type = type;
        this.number = number;
        this.color = color;

        x = 0;
        y = 0;
        
        
        generateRandomSpeed();
    }
    
    /**
     * Generates a random speed between 50 and 200.
     * A new speed is generated whenever this method is called.
     */
    public void generateRandomSpeed() {
    speed = random.nextInt(151) + 50 ;
}

    public String getType() {
        return type;
    }

    public int getNumber() {
        return number;
    }

    public Color getColor() {
        return color;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getSpeed() {
        return speed;
    }

    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

}
