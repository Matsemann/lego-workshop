package com.matsemann.robot.controller.ui.mainview;

import javafx.scene.Node;
import javafx.scene.shape.Polyline;

import java.util.List;

import static java.util.Arrays.asList;

public class Notch {

    private String forCommand;

    public Notch(String forCommand) {
        this.forCommand = forCommand;
    }

    List<Node> getElements() {
        Polyline overNotch = new Polyline(0, 0, 8, 5, 30, 5, 38, 0);
        overNotch.getStyleClass().addAll("notch", forCommand);

        Polyline overNotchDarkline = new Polyline(0, 0, 38, 0);
        overNotchDarkline.getStyleClass().addAll("notchfill", forCommand);

        Polyline higlight = new Polyline(0, 0, 8, 5, 30, 5, 38, 0);
        higlight.getStyleClass().addAll("notch-border");

        return asList(overNotchDarkline, overNotch, higlight);
    }
}
