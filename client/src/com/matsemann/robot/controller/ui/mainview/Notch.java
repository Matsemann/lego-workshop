package com.matsemann.robot.controller.ui.mainview;

import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Polyline;

public class Notch extends StackPane {


    public Notch(String forCommand) {
        setAlignment(Pos.TOP_LEFT);
        setPickOnBounds(false);

        Polyline overNotch = new Polyline(0, 0, 8, 5, 30, 5, 38, 0);
        overNotch.getStyleClass().addAll("notch", forCommand);

        Polyline overNotchDarkline = new Polyline(0, 0, 38, 0);
        overNotchDarkline.getStyleClass().addAll("notchfill", forCommand);

        Polyline higlight = new Polyline(0, 0, 8, 5, 30, 5, 38, 0);
        higlight.getStyleClass().addAll("notch-border");

        getChildren().addAll(overNotchDarkline, overNotch, higlight);
    }
}
