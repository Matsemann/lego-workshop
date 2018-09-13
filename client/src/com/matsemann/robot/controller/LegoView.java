package com.matsemann.robot.controller;

import com.matsemann.robot.controller.command.CommandCreator;
import com.matsemann.robot.controller.command.CommandHandler;
import com.matsemann.robot.controller.command.DefaultKeybindings;
import com.matsemann.robot.controller.ui.mainview.LegoViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LegoView extends Application {

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ui/mainview/MainView.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setTitle("Matsemann's LEGO Controller");
        primaryStage.setScene(scene);
        primaryStage.show();


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

        LegoViewController controller = loader.getController();
        controller.setCommandHandler(commandHandler);


    }

}
