package com.matsemann.robot;

import com.matsemann.robot.command.RobotCommand;
import com.matsemann.robot.command.MoveCommand;
import com.matsemann.robot.command.ResetCommand;
import com.matsemann.robot.command.StopMotorCommand;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

            Optional<RobotCommand> command = commands.stream()
                    .filter(c -> c.canHandle(commandInput[0]))
                    .findFirst();

            if (command.isPresent()) {
                try {
                    command.get().handle(commandInput);
                } catch (RuntimeException e) {
                    System.out.println("RobotCommand \"" + inputs[i] + "\" failed with: ");
                    e.printStackTrace();
                }
            } else {
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
