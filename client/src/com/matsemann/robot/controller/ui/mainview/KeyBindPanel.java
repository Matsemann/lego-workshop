package com.matsemann.robot.controller.ui.mainview;

import com.matsemann.robot.controller.command.CommandCreator;
import com.matsemann.robot.controller.command.CommandHandler;
import com.matsemann.robot.controller.command.CommandHandler.KeyEventCommands;
import com.matsemann.robot.controller.command.ControlCommand;
import com.matsemann.robot.controller.command.EmptyCommand;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class KeyBindPanel extends VBox {

    private CommandHandler commandHandler;

    public KeyBindPanel(CommandHandler commandHandler, KeyEventCommands commands, boolean keyUp) {
        this.commandHandler = commandHandler;
        setSpacing(0);
        setPadding(new Insets(10, 30, 10, 0));

        int i = 0;
        Notch notch = new Notch("default");
        for (ControlCommand command : (keyUp ? commands.upCommands : commands.downCommands)) {
            Node singleCommandView = createSingleCommandView(command, commands.key, keyUp, i++, notch);
            getChildren().add(singleCommandView);
            notch = new Notch(command.getName());
        }

        Button plus = new Button("➕");
        plus.setOnAction(event -> {
            commandHandler.addCommandToButton(commands.key, keyUp, new EmptyCommand());
        });
        plus.getStyleClass().add("add");


        Region region = new Region();
        HBox.setHgrow(region, Priority.ALWAYS);
        HBox leggTilBoks = new HBox(region, plus);
        leggTilBoks.setPadding(new Insets(0, 10, 0, 10));

        getChildren().add(leggTilBoks);

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
