package com.matsemann.robot.robotserver;

import com.matsemann.robot.robotserver.command.MoveCommand;
import com.matsemann.robot.robotserver.command.ResetCommand;
import com.matsemann.robot.robotserver.command.RobotCommand;
import com.matsemann.robot.robotserver.command.StopMotorCommand;

import java.util.ArrayList;
import java.util.List;

public class Commander {

    List<RobotCommand> commands = new ArrayList<>();

    public Commander(Robot robot) {
        commands.add(new ResetCommand(robot));
        commands.add(new StopMotorCommand(robot));
        commands.add(new MoveCommand(robot));
        // What more? Adjust speed?
    }


    public void handleCommand(String input) {
        String[] inputs = input.split("\\|");
        for (int i = 0; i < inputs.length; i++) {
            String[] commandInput = inputs[i].split(":");

            RobotCommand handler = null;

            for (RobotCommand command : commands) {
                if (command.canHandle(commandInput[0])) {
                    handler = command;
                    break;
                }
            }

            if (handler != null) {
                try {
                    handler.handle(commandInput);
                } catch (RuntimeException e) {
                    System.out.println("RobotCommand \"" + inputs[i] + "\" failed with: ");
                    e.printStackTrace();
                }
            }  else {
                System.out.println("No command found for: \"" + inputs[i] + "\"");
            }


        }
    }

    public static void main(String[] args) {
        Commander c = new Commander(new Robot() {
        });
        c.handleCommand("move:A:90|move:B:-45");
    }

}
