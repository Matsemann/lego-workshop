package com.matsemann.robot.controller.ui.mainview;

import com.matsemann.robot.controller.command.CommandCreator;
import com.matsemann.robot.controller.command.CommandHandler;
import com.matsemann.robot.controller.command.CommandHandler.CommandEvent;
import com.matsemann.robot.controller.command.ControlCommand;
import com.matsemann.robot.controller.command.DefaultKeybindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        Map<String, List<ControlCommand>> bindings = commandHandler.getKeyBindings();

        mainGrid.getChildren().clear();
        CommandCreator commandCreator = new CommandCreator();
        List<String> names = commandCreator.getAvailableCommands().stream().map(ControlCommand::getName).collect(Collectors.toList());

        int i = 0;
        for (Map.Entry<String, List<ControlCommand>> keyBinding : bindings.entrySet()) {

            VBox vBox = new VBox();

            int j = 0;
            for (ControlCommand controlCommand : keyBinding.getValue()) {

                HBox pane = new HBox();
                ComboBox<String> combo = new ComboBox<>();
                combo.getItems().addAll(names);
                combo.getSelectionModel().select(controlCommand.getName());

                int index = j;
                combo.setOnAction(event -> {
                    commandHandler.removeCommandFromButton(keyBinding.getKey(), index);
                    commandHandler.addCommandToButton(keyBinding.getKey(), commandCreator.create(combo.getSelectionModel().getSelectedItem()), index);
                });

                pane.getChildren().add(combo);

                controlCommand.getOptions().forEach(option -> {
                    ComboBox<String> optionBox = new ComboBox<>();
                    optionBox.getItems().addAll(option.values);
                    optionBox.getSelectionModel().select(controlCommand.getOption(option.name));

                    optionBox.setOnAction(event -> {
                        controlCommand.setOption(option.name, optionBox.getSelectionModel().getSelectedItem());
                    });

                    pane.getChildren().addAll(new Label(option.name), optionBox);
                });

                vBox.getChildren().add(pane);
                j++;

            }


            mainGrid.add(new Label(keyBinding.getKey()), 0, i);
            mainGrid.add(vBox, 1, i++);
        }
    }

    public void reset(ActionEvent actionEvent) {
        new DefaultKeybindings(commandHandler).resetKeybindings();
    }
}
