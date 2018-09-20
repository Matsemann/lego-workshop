package com.matsemann.robot.controller.ui.mainview;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;

public class MotorStatus extends StackPane {

    Line line;
    Rectangle power;

    public MotorStatus(String motorName) {
        StackPane circle = new StackPane();
        circle.setAlignment(Pos.TOP_LEFT);

        Circle circleShape = new Circle(30);
        circleShape.getStyleClass().addAll("circle");
        line = new Line(0, 0, 0, 10);
        line.getStyleClass().addAll("line");
        line.setTranslateX(30);

        circle.getChildren().addAll(circleShape, line);

        Label label = new Label(motorName);
        label.setTranslateY(-7);

        Rectangle powerBg = new Rectangle(60, 15);
        powerBg.getStyleClass().addAll("powerbg");
        power = new Rectangle(0, 13);
        power.getStyleClass().addAll("power");
        setSpeed(50);
        setAngle(0);

        getChildren().addAll(circle, label, powerBg, power);
        getStyleClass().addAll("motorstatus");

        // test rotation
        /*
        new Thread(() -> {
            int angle = 0;
            int speed = 50;
            while (true) {
                angle = (angle + 20) % 360;
                speed = (speed + 5) % 100;
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                setAngle(angle);
                setSpeed(speed);
            }
        }).start(); */

    }

    public void setAngle(float degrees) {
        Platform.runLater(() -> {
            Rotate rotate2 = new Rotate(degrees, 0, 30);
            line.getTransforms().clear();
            line.getTransforms().add(rotate2);
        });
    }

    public void setSpeed(float speed) {
        power.setWidth(58 * (speed / 100.0));
        power.setTranslateX(-29 + 29 * (speed / 100.0));
    }
}
