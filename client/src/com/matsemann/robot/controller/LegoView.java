package com.matsemann.robot.controller;

import com.matsemann.robot.controller.command.CommandHandler;
import com.matsemann.robot.controller.command.DefaultKeybindings;
import com.matsemann.robot.controller.robot.Robot;
import com.matsemann.robot.controller.robot.RobotHandler;
import com.matsemann.robot.controller.ui.mainview.LegoViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LegoView extends Application {

    private RobotHandler robotHandler;

    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName());
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        robotHandler = new RobotHandler();
//        robotHandler.connect("192.168.0.220");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("ui/mainview/MainView.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(LegoView.class.getResource("/com/matsemann/robot/controller/ui/mainview/legostyle.css").toExternalForm());

        primaryStage.setTitle("Matsemann's LEGO Controller");
        primaryStage.setScene(scene);
        primaryStage.show();


        CommandHandler commandHandler = new CommandHandler();
        DefaultKeybindings defaultKeybindings = new DefaultKeybindings(commandHandler);
        defaultKeybindings.resetKeybindings();

        SinglePressKeyHandler keyHandler = new SinglePressKeyHandler(event -> {
            boolean keyUp = event.getEventType().getName().equals("KEY_RELEASED");
            String key = event.getCode().getName();

            Logger.ok("Got key event: " + key + " " + (keyUp ? "up" : "down"));

            Robot robot = robotHandler.getRobot();

            if (robot != null) {
                commandHandler.executeCommandsForKeyPress(key, keyUp, robot);
            } else {
                Logger.info("Not executing, not connected");
            }
        });

        scene.setOnKeyPressed(keyHandler);
        scene.setOnKeyReleased(keyHandler);

        LegoViewController controller = loader.getController();
        controller.setCommandHandler(commandHandler);


    }

    @Override
    public void stop() throws Exception {
        Logger.ok("Closing down");
        robotHandler.disconnect();
    }

}
