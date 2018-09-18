package com.matsemann.robot.controller.ui.mainview;

import com.matsemann.robot.controller.command.CommandHandler;
import com.matsemann.robot.controller.command.CommandHandler.CommandEvent;
import com.matsemann.robot.controller.command.CommandHandler.KeyEventCommands;
import com.matsemann.robot.controller.command.DefaultKeybindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

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
            Node downPane = new KeyBindPanel(commandHandler, commands, false);
            Node upPane = new KeyBindPanel(commandHandler, commands, true);

            Pane namePane = new Pane(new Label(commands.key));
            mainGrid.add(namePane, 0, i);
            mainGrid.add(downPane, 1, i);
            mainGrid.add(upPane, 2, i);
            i++;
        }

        new Thread(() -> {
            try {
                Thread.sleep(20);
                scrollPane.setVvalue(scrollPos);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

    }

    public void reset(ActionEvent actionEvent) {
        new DefaultKeybindings(commandHandler).resetKeybindings();
    }

}
