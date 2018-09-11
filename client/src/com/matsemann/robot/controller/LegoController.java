package com.matsemann.robot.controller;

import com.matsemann.robot.controller.command.CommandHandler;
import com.matsemann.robot.controller.command.DefaultKeybindings;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.util.HashSet;
import java.util.Set;

public class LegoController extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Matsemann's LEGO Controller");


        Button button = new Button("laal");
        Scene scene = new Scene(button, 300, 250);


        primaryStage.setScene(scene);

        CommandHandler commandHandler = new CommandHandler();
        DefaultKeybindings defaultKeybindings = new DefaultKeybindings(commandHandler);
        defaultKeybindings.resetKeybindings();


        SinglePressKeyHandler keyHandler = new SinglePressKeyHandler(event -> {
            boolean keyUp = event.getEventType().getName().equals("KEY_RELEASED");
            String key = event.getCode().getName();

            String c = commandHandler.getCommandStringForButtonPress(key, keyUp);
            if (c != null) {
                System.out.println(c); // do something
            }
        });

        scene.setOnKeyPressed(keyHandler);
        scene.setOnKeyReleased(keyHandler);

        primaryStage.show();

    }

    class SinglePressKeyHandler implements EventHandler<KeyEvent> {

        private EventHandler<KeyEvent> eventEventHandler;
        private Set<String> keyDowns = new HashSet<>();

        public SinglePressKeyHandler(EventHandler<KeyEvent> eventEventHandler) {
            this.eventEventHandler = eventEventHandler;
        }

        /**
         * Filter so we don't get many keydown events..
         */
        @Override
        public void handle(KeyEvent event) {
            String key = event.getCode().getName();

            if (event.getEventType().getName().equals("KEY_PRESSED")) {
                if (!keyDowns.contains(key)) {
                    keyDowns.add(key);
                    eventEventHandler.handle(event);
                }
            } else {
                keyDowns.remove(key);
                eventEventHandler.handle(event);
            }
        }
    }

}
