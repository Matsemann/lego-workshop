package com.matsemann.robot.controller.ui.mainview;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

public class AddCommandButton extends HBox {

    public AddCommandButton(Runnable onAdd) {
        Button plus = new Button("➕");
        plus.setOnAction(event -> {
            onAdd.run();
        });
        plus.getStyleClass().add("add");
        plus.setTooltip(new Tooltip("Legg til kommando"));

        Region region = new Region();
        HBox.setHgrow(region, Priority.ALWAYS);

        getChildren().addAll(region, plus);
        setPadding(new Insets(0, 10, 0, 10));
    }
}
