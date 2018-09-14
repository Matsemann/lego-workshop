package com.matsemann.robot.controller.ui.mainview;

import com.matsemann.robot.controller.command.*;
import com.matsemann.robot.controller.command.CommandHandler.CommandEvent;
import com.matsemann.robot.controller.command.CommandHandler.KeyEventCommands;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.util.Map;

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

        mainGrid.add(new Label("Knapp"), 0, 0);
        mainGrid.add(new Label("Når trykkes ned"), 1, 0);
        mainGrid.add(new Label("Når slippes"), 2, 0);

        int i = 1;
        for (KeyEventCommands commands : keyEventCommands.values()) {
            String additionalClass = i % 2 == 0 ? "even" : "odd";

            VBox downPane = createCommandsView(commands, false, additionalClass);
            VBox upPane = createCommandsView(commands, true, additionalClass);


            Pane namePane = new Pane(new Label(commands.key));
            namePane.getStyleClass().addAll("row", additionalClass);
            mainGrid.add(namePane, 0, i);
            mainGrid.add(downPane, 1, i);
            mainGrid.add(upPane, 2, i);
            i++;
        }
    }

    public VBox createCommandsView(KeyEventCommands commands, boolean keyUp, String additionalClass) {
        VBox vBox = new VBox();
        vBox.getStyleClass().addAll("row",additionalClass);
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(10, 30, 10, 0));

        int i = 0;
        for (ControlCommand command : (keyUp ? commands.upCommands : commands.downCommands)) {
            HBox singleCommandView = createSingleCommandView(command, commands.key, keyUp, i++);
            vBox.getChildren().add(singleCommandView);
        }

        Button plus = new Button("Legg til");
        plus.setOnAction(event -> {
            commandHandler.addCommandToButton(commands.key, keyUp, new EmptyCommand());
        });
        plus.getStyleClass().add("add");


        Region region = new Region();
        HBox.setHgrow(region, Priority.ALWAYS);
        HBox fjernBox = new HBox(region, plus);
        fjernBox.setPadding(new Insets(0, 10, 0, 10));

        vBox.getChildren().add(fjernBox);

        return vBox;
    }

    public HBox createSingleCommandView(ControlCommand command, String key, boolean keyUp, int index) {

        HBox pane = new HBox();
        pane.getStyleClass().addAll("command",command.getName());
        pane.setPadding(new Insets(0, 10, 0, 10));
        pane.setSpacing(20);

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

        Region region = new Region();
        HBox.setHgrow(region, Priority.ALWAYS);

        Button fjern = new Button("Fjern");
        fjern.setOnAction(event -> {
            commandHandler.removeCommandFromButton(key, keyUp, index);
        });
        fjern.getStyleClass().add("remove");
        pane.getChildren().addAll(region, fjern);

        return pane;
    }

    public void reset(ActionEvent actionEvent) {
        new DefaultKeybindings(commandHandler).resetKeybindings();
    }
}
