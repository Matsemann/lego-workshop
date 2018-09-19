package com.matsemann.robot.controller.ui.mainview;

import com.matsemann.robot.controller.command.CommandCreator;
import com.matsemann.robot.controller.command.CommandHandler;
import com.matsemann.robot.controller.command.CommandHandler.KeyEventCommands;
import com.matsemann.robot.controller.command.ControlCommand;
import com.matsemann.robot.controller.command.EmptyCommand;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Polyline;

import java.util.List;

public class KeyBindPanel extends StackPane {

    private CommandHandler commandHandler;

    public KeyBindPanel(CommandHandler commandHandler, KeyEventCommands keyEventCommands, boolean keyUp) {
        this.commandHandler = commandHandler;
        List<ControlCommand> commands = keyUp ? keyEventCommands.upCommands : keyEventCommands.downCommands;

        VBox commandsPanel = new VBox();
        commandsPanel.setSpacing(0);
        commandsPanel.setPadding(new Insets(10, 30, 10, 0));

        int i = 0;
        Notch notch = new Notch("default");
        for (ControlCommand command : commands) {
            Node singleCommandView = createSingleCommandView(command, keyEventCommands.key, keyUp, i++, notch);
            commandsPanel.getChildren().add(singleCommandView);
            notch = new Notch(command.getName());
        }
        if (commands.size() == 0) {
            commandsPanel.getChildren().addAll(new Notch("default"));
            notch = new Notch("empty");
        }

        AddCommandButton addCommandButton = new AddCommandButton(() -> {
            commandHandler.addCommandToButton(keyEventCommands.key, keyUp, new EmptyCommand());
        });

        commandsPanel.getChildren().add(addCommandButton);


        int rowheights = commands.size() > 0 ? commands.size() * 40 : 20;

        Label keyLabel = new Label(keyEventCommands.key);
        keyLabel.setPrefHeight(rowheights + 2);

        StackPane keyName = new StackPane();
        keyName.setAlignment(Pos.TOP_LEFT);
        keyName.getStyleClass().addAll("keyname");
        keyName.setPickOnBounds(false);

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


        notch.setTranslateY(rowheights + 10);

        keyName.getChildren().addAll(topLine, tophighlight, topshadow, bottomLine, bottomhighlight, bottomshadow, keyLabel);
        keyName.getChildren().addAll(notch);


        setAlignment(Pos.TOP_LEFT);
        getChildren().addAll(commandsPanel, keyName);

    }

    private Node createSingleCommandView(ControlCommand command, String key, boolean keyUp, int index, Notch notch) {
        return new CommandPanel(command, notch, (commandName) -> {
            commandHandler.removeCommandFromButton(key, keyUp, index);
            commandHandler.addCommandToButton(key, keyUp, CommandCreator.create(commandName), index);
        }, () -> {
            commandHandler.removeCommandFromButton(key, keyUp, index);
        });
    }
}
