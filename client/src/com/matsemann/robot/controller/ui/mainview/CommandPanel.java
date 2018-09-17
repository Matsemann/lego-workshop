package com.matsemann.robot.controller.ui.mainview;

import com.matsemann.robot.controller.command.CommandCreator;
import com.matsemann.robot.controller.command.ControlCommand;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

import java.util.function.Consumer;

public class CommandPanel extends StackPane {


    public CommandPanel(ControlCommand command, Notch notch, Consumer<String> typeChangeFn, Runnable removeFn) {

        getStyleClass().addAll("stack", command.getName());
        setAlignment(Pos.TOP_LEFT);

        HBox commandPane = new HBox();

        commandPane.getStyleClass().addAll("command",command.getName());
        commandPane.setPadding(new Insets(0, 10, 0, 10));


        ComboBox<String> combo = new ComboBox<>();
        combo.getItems().addAll(CommandCreator.NAMES);
        combo.getSelectionModel().select(command.getName());

        combo.setOnAction(event -> {
            typeChangeFn.accept(combo.getSelectionModel().getSelectedItem());
        });

        commandPane.getChildren().add(combo);

        command.getOptions().forEach(option -> {
            ComboBox<String> optionBox = new ComboBox<>();
            optionBox.getItems().addAll(option.values);
            optionBox.getSelectionModel().select(command.getOption(option.name));

            optionBox.setOnAction(event -> {
                command.setOption(option.name, optionBox.getSelectionModel().getSelectedItem());
            });

            commandPane.getChildren().addAll(new Label(option.name), optionBox);
        });

        Region region = new Region();
        HBox.setHgrow(region, Priority.ALWAYS);

        Button fjern = new Button("X");
        fjern.setTooltip(new Tooltip("Fjern kommando"));
        fjern.setOnAction(event -> {
            removeFn.run();
        });
        fjern.getStyleClass().add("remove");
        commandPane.getChildren().addAll(region, fjern);

        getChildren().addAll(commandPane);
        getChildren().addAll(notch.getElements());
    }
}
