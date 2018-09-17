package com.matsemann.robot.controller.ui.mainview;

import com.matsemann.robot.controller.command.*;
import com.matsemann.robot.controller.command.CommandHandler.CommandEvent;
import com.matsemann.robot.controller.command.CommandHandler.KeyEventCommands;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
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
    public ScrollPane scrollPane;

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

        double scrollPos = scrollPane.getVvalue();

        mainGrid.getChildren().clear();

        mainGrid.add(new Label("Knapp"), 0, 0);
        mainGrid.add(new Label("Når trykkes ned"), 1, 0);
        mainGrid.add(new Label("Når slippes"), 2, 0);

        int i = 1;
        for (KeyEventCommands commands : keyEventCommands.values()) {
            String additionalClass = i % 2 == 0 ? "even" : "odd";

            Node downPane = createCommandsView(commands, false, additionalClass);
            Node upPane = createCommandsView(commands, true, additionalClass);


            Pane namePane = new Pane(new Label(commands.key));
            namePane.getStyleClass().addAll("row", additionalClass);
            mainGrid.add(namePane, 0, i);
            mainGrid.add(downPane, 1, i);
            mainGrid.add(upPane, 2, i);
            i++;
        }

        Platform.runLater(() -> {
            scrollPane.setVvalue(scrollPos);
        });
    }

    private Node createCommandsView(KeyEventCommands commands, boolean keyUp, String additionalClass) {
        VBox vBox = new VBox();
        vBox.getStyleClass().addAll("row",additionalClass);
        vBox.setSpacing(0);
        vBox.setPadding(new Insets(10, 30, 10, 0));

        int i = 0;
        Notch notch = new Notch("default");
        for (ControlCommand command : (keyUp ? commands.upCommands : commands.downCommands)) {
            Node singleCommandView = createSingleCommandView(command, commands.key, keyUp, i++, notch);
            vBox.getChildren().add(singleCommandView);
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

        vBox.getChildren().add(leggTilBoks);

        return vBox;
    }

    private Node createSingleCommandView(ControlCommand command, String key, boolean keyUp, int index, Notch notch) {
        return new CommandPanel(command, notch, (commandName) -> {
            commandHandler.removeCommandFromButton(key, keyUp, index);
            commandHandler.addCommandToButton(key, keyUp, CommandCreator.create(commandName), index);
        }, () -> {
            commandHandler.removeCommandFromButton(key, keyUp, index);
        });
    }

    public void reset(ActionEvent actionEvent) {
        new DefaultKeybindings(commandHandler).resetKeybindings();
    }

}
