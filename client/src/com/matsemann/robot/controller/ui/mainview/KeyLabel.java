package com.matsemann.robot.controller.ui.mainview;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Polyline;

public class KeyLabel extends StackPane {

    public KeyLabel(int rows, String key, boolean keyUp, Notch bottomNotch) {
        int rowheights = rows > 0 ? rows * 40 : 20;


        Label keyLabel = new Label(key);
        keyLabel.getStyleClass().addAll("keylabel", rows == 0 ? "empty" : "");
        keyLabel.setPrefHeight(rowheights + 2);

        Label directionLabel = new Label(keyUp ? "opp" : "ned");
        directionLabel.getStyleClass().addAll("directionlabel", rows == 0 ? "empty" : "");
        directionLabel.setPrefHeight(rowheights + 2);


        Polyline topLine = new Polyline(0, 0, 160, 0, 168, 10, 0, 10);
        topLine.getStyleClass().addAll("topline");
        Polyline tophighlight = new Polyline(0, 10, 0, 0, 160, 0);
        tophighlight.getStyleClass().addAll("tophighlight");
        Polyline topshadow = new Polyline(160, 0, 168, 11, 117, 11, 109, 16, 87, 16, 79, 11, 0, 11);
        topshadow.getStyleClass().addAll("topshadow");

        Polyline bottomLine = new Polyline(0, 0, 168, 0, 160, 10, 0, 10);
        bottomLine.getStyleClass().addAll("bottomline");
        bottomLine.setTranslateY(rowheights + 10);
        Polyline bottomhighlight = new Polyline(0, 10, 0, 0, 168, 0);
        bottomhighlight.getStyleClass().addAll("bottomhighlight");
        bottomhighlight.setTranslateY(rowheights + 9.5);
        Polyline bottomshadow = new Polyline(0, 10, 160, 10, 168, 0);
        bottomshadow.getStyleClass().addAll("bottomshadow");
        bottomshadow.setTranslateY(rowheights + 10);


        bottomNotch.setTranslateY(rowheights + 10);

        setAlignment(Pos.TOP_LEFT);
        getStyleClass().addAll("keyname");
        setPickOnBounds(false);
        getChildren().addAll(topLine, tophighlight, topshadow, bottomLine, bottomhighlight, bottomshadow, keyLabel, directionLabel);
        getChildren().addAll(bottomNotch);
    }
}
