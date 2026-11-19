/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.racecarsimulation;

import javafx.scene.Group;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class Car extends Group{
private Group frontTire;
private Group backTire;

private RotateTransition frontRotation;
private RotateTransition backRotation;
private String type;
    public Car(double x, double y, Color color, int number, String type) {

        setLayoutX(x);
        setLayoutY(y);

        this.type = type;
        Polygon body = new Polygon(
                20.0, 80.0,
                35.0, 50.0,
                80.0, 45.0,
                110.0, 15.0,
                210.0, 15.0,
                250.0, 50.0,
                290.0, 60.0,
                300.0, 80.0,
                300.0, 100.0,
                20.0, 100.0
        );

        body.setFill(color);
        body.setStroke(Color.BLACK);

        
        Polygon leftWindow = new Polygon(
                115.0, 20.0,
                155.0, 20.0,
                155.0, 50.0,
                90.0, 50.0
        );

        leftWindow.setFill(Color.LIGHTBLUE);

        
        Polygon rightWindow = new Polygon(
                165.0, 20.0,
                205.0, 20.0,
                240.0, 50.0,
                165.0, 50.0
        );

        rightWindow.setFill(Color.LIGHTBLUE);

        
        Rectangle numberBackground =
                new Rectangle(145, 60, 50, 30);

        numberBackground.setFill(Color.WHITE);

        
        Text carNumber = new Text(
                155, 83, String.valueOf(number)
        );

        carNumber.setFont(Font.font(22));
        carNumber.setFill(Color.BLACK);

        
        backTire = createTire(80, 100);
        frontTire = createTire(240, 100);

       
        getChildren().addAll(
                body,
                leftWindow,
                rightWindow,
                numberBackground,
                carNumber,
                backTire,
                frontTire
        );

        
        frontRotation = createRotation(frontTire);
        backRotation = createRotation(backTire);
    }

    
    private Group createTire(double x, double y) {

        Group tire = new Group();

        Circle outerTire = new Circle(0, 0, 25);
        outerTire.setFill(Color.BLACK);

        Circle innerTire = new Circle(0, 0, 17);
        innerTire.setFill(Color.LIGHTGRAY);

        Line spoke1 = new Line(-12, 0, 12, 0);
        Line spoke2 = new Line(0, -12, 0, 12);

        spoke1.setStrokeWidth(3);
        spoke2.setStrokeWidth(3);

        Circle center = new Circle(0, 0, 5);
        center.setFill(Color.DARKGRAY);

        tire.getChildren().addAll(
                outerTire,
                innerTire,
                spoke1,
                spoke2,
                center
        );

        tire.setLayoutX(x);
        tire.setLayoutY(y);

        return tire;
    }

    public String getType() {
    return type;
    }
    
    private RotateTransition createRotation(Group tire) {

        RotateTransition rotation =
                new RotateTransition(Duration.seconds(1), tire);

        rotation.setByAngle(360);
        rotation.setCycleCount(Animation.INDEFINITE);
        rotation.setInterpolator(Interpolator.LINEAR);

        return rotation;
    }

    
    public void startTires() {
        frontRotation.play();
        backRotation.play();
    }

   
    public void stopTires() {
        frontRotation.stop();
        backRotation.stop();
    }    
}
