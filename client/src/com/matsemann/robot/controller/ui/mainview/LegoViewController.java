package com.matsemann.robot.controller.ui.mainview;

import com.matsemann.robot.controller.command.*;
import com.matsemann.robot.controller.command.CommandHandler.CommandEvent;
import com.matsemann.robot.controller.command.CommandHandler.KeyEventCommands;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

public class LegoViewController implements EventHandler<CommandEvent> {


    public ToggleButton consoleToggle;
    public SplitPane mainSplitPane;
    public SplitPane consolePane;
    public Pane leftConsole;
    public Pane rightConsole;
    public GridPane mainGrid;

    private boolean hidden = true;
    private CommandHandler commandHandler;

    public void initialize() {
        mainSplitPane.getItems().remove(consolePane);
    }

    public void setCommandHandler(CommandHandler commandHandler) {
        this.commandHandler = commandHandler;
        commandHandler.addListener(this);
        reset(null);
    }


    public void handleToggleButton(ActionEvent actionEvent) {
        if (hidden) {
            hidden = false;
            mainSplitPane.getItems().addAll(consolePane);
        } else {
            hidden = true;
            mainSplitPane.getItems().remove(1);
        }
        consoleToggle.setSelected(!hidden);
    }

    @Override
    public void handle(CommandEvent notused) {
        System.out.println("changes detected, redrawing");
        Map<String, KeyEventCommands> keyEventCommands = commandHandler.getKeyEventCommands();

        mainGrid.getChildren().clear();

        int i = 0;
        for (KeyEventCommands commands : keyEventCommands.values()) {
            VBox downPane = createCommandsView(commands, false);
            VBox upPane = createCommandsView(commands, true);

            mainGrid.add(new Label(commands.key), 0, i);
            mainGrid.add(downPane, 1, i);
            mainGrid.add(upPane, 2, i);
            i++;
        }
    }

    public VBox createCommandsView(KeyEventCommands commands,  boolean keyUp) {
        VBox vBox = new VBox();

        int i = 0;
        for (ControlCommand command : (keyUp ? commands.upCommands : commands.downCommands)) {
            HBox singleCommandView = createSingleCommandView(command, commands.key, keyUp, i++);
            vBox.getChildren().add(singleCommandView);
        }

        Button plus = new Button("+");
        plus.setOnAction(event -> {
            commandHandler.addCommandToButton(commands.key, keyUp, new EmptyCommand());
        });

        vBox.getChildren().add(plus);

        return vBox;
    }

    public HBox createSingleCommandView(ControlCommand command, String key, boolean keyUp, int index) {

        HBox pane = new HBox();

        ComboBox<String> combo = new ComboBox<>();
        combo.getItems().addAll(CommandCreator.NAMES);
        combo.getSelectionModel().select(command.getName());

        combo.setOnAction(event -> {
            commandHandler.removeCommandFromButton(key, keyUp, index);
            commandHandler.addCommandToButton(key, keyUp, CommandCreator.create(combo.getSelectionModel().getSelectedItem()), index);
        });

        pane.getChildren().add(combo);

        command.getOptions().forEach(option -> {
            ComboBox<String> optionBox = new ComboBox<>();
            optionBox.getItems().addAll(option.values);
            optionBox.getSelectionModel().select(command.getOption(option.name));

            optionBox.setOnAction(event -> {
                command.setOption(option.name, optionBox.getSelectionModel().getSelectedItem());
            });

            pane.getChildren().addAll(new Label(option.name), optionBox);
        });

        Button fjern = new Button("Fjern");
        fjern.setOnAction(event -> {
            commandHandler.removeCommandFromButton(key, keyUp, index);
        });
        pane.getChildren().add(fjern);

        return pane;
    }

    public void reset(ActionEvent actionEvent) {
        new DefaultKeybindings(commandHandler).resetKeybindings();
    }
}
