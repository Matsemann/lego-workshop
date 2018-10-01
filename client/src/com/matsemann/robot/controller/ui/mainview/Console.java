package com.matsemann.robot.controller.ui.mainview;

import com.matsemann.robot.controller.Logger;
import javafx.scene.control.ScrollPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class Console extends TextFlow implements Logger.LogView {

    private static final int NUM_LINES = 500;
    private ScrollPane consolePane;

    public Console(ScrollPane consolePane) {
        this.consolePane = consolePane;
        consolePane.setVvalue(1);
        Logger.attachLogView(this);
        getStyleClass().add("console");
    }

    @Override
    public void handleMessage(String msg, String level) {
        System.out.println(consolePane.getVvalue());

        Text text = new Text(msg + "\n") ;
        text.getStyleClass().addAll(level.toLowerCase());

        getChildren().add(text);
        if (getChildren().size() > NUM_LINES) {
            getChildren().remove(0);
        }

        if (consolePane.getVvalue() >= 0.95) {
            consolePane.setVvalue(1);
        }
    }
}
