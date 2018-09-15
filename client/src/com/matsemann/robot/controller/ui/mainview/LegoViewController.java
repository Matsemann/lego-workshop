package com.matsemann.robot.controller.ui.mainview;

import com.matsemann.robot.controller.command.*;
import com.matsemann.robot.controller.command.CommandHandler.CommandEvent;
import com.matsemann.robot.controller.command.CommandHandler.KeyEventCommands;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polyline;

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

            Node downPane = createCommandsView(commands, false, additionalClass);
            Node upPane = createCommandsView(commands, true, additionalClass);


            Pane namePane = new Pane(new Label(commands.key));
            namePane.getStyleClass().addAll("row", additionalClass);
            mainGrid.add(namePane, 0, i);
            mainGrid.add(downPane, 1, i);
            mainGrid.add(upPane, 2, i);
            i++;
        }
    }

    private Node createCommandsView(KeyEventCommands commands, boolean keyUp, String additionalClass) {
        VBox vBox = new VBox();
        vBox.getStyleClass().addAll("row",additionalClass);
        vBox.setSpacing(0);
        vBox.setPadding(new Insets(10, 30, 10, 0));

        int i = 0;
        Node notch = createNotch("default");
        for (ControlCommand command : (keyUp ? commands.upCommands : commands.downCommands)) {
            Node singleCommandView = createSingleCommandView(command, commands.key, keyUp, i++, notch);
            vBox.getChildren().add(singleCommandView);
            notch = createNotch(command.getName());
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

    private Node createSingleCommandView(ControlCommand command, String key, boolean keyUp, int index, Node notch) {

        StackPane stackPane = new StackPane();
        stackPane.getStyleClass().add("stack");
        stackPane.setAlignment(Pos.TOP_LEFT);

        HBox commandPane = new HBox();

        commandPane.getStyleClass().addAll("command",command.getName());
        commandPane.setPadding(new Insets(0, 10, 0, 10));
        commandPane.setSpacing(20);

        ComboBox<String> combo = new ComboBox<>();
        combo.getItems().addAll(CommandCreator.NAMES);
        combo.getSelectionModel().select(command.getName());

        combo.setOnAction(event -> {
            commandHandler.removeCommandFromButton(key, keyUp, index);
            commandHandler.addCommandToButton(key, keyUp, CommandCreator.create(combo.getSelectionModel().getSelectedItem()), index);
        });

        commandPane.getChildren().add(combo);

        command.getOptions().forEach(option -> {
            ComboBox<String> optionBox = new ComboBox<>();
            optionBox.getItems().addAll(option.values);
            optionBox.getSelectionModel().select(command.getOption(option.name));

            optionBox.setOnAction(event -> {
                command.setOption(option.name, optionBox.getSelectionModel().getSelectedItem());
            });

            commandPane.getChildren().addAll(myLabel(option.name), optionBox);
        });

        Region region = new Region();
        HBox.setHgrow(region, Priority.ALWAYS);

        Button fjern = new Button("X");
        fjern.setOnAction(event -> {
            commandHandler.removeCommandFromButton(key, keyUp, index);
        });
        fjern.getStyleClass().add("remove");
        commandPane.getChildren().addAll(region, fjern);

        stackPane.getChildren().addAll(commandPane, notch);
        return stackPane;
    }

    private Node createNotch(String command) {
        Polyline polyline = new Polyline(0, 0, 8, 5, 30, 5, 38, 0);
        polyline.getStyleClass().addAll("notch", command);

        return polyline;
    }

    public void reset(ActionEvent actionEvent) {
        new DefaultKeybindings(commandHandler).resetKeybindings();
    }

    private Label myLabel(String text) {
        Label label = new Label(text);
        return label;
    }
}
