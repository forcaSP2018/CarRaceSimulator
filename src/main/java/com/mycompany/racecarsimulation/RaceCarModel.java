/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.racecarsimulation;
    
import java.util.Random;
import javafx.scene.paint.Color;

/**
 *
 * @author julia
 */
public class RaceCarModel {
    private String type;
    private int number;
    private double x;
    private double y;
    private double speed;
    private Color color;
    private static final Random random = new Random();

    public RaceCarModel(String type, int number, Color color) {
        this.type = type;
        this.number = number;
        this.color = color;

        x = 0;
        y = 0;
        
        
        generateRandomSpeed();
    }
    
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
