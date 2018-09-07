package com.matsemann.robot;

import com.matsemann.robot.commands.Command;
import com.matsemann.robot.commands.MoveCommand;
import com.matsemann.robot.commands.ResetCommand;
import com.matsemann.robot.commands.StopMotorCommand;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Commander {

    List<Command> commands = new ArrayList<>();

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

            Optional<Command> command = commands.stream()
                    .filter(c -> c.canHandle(commandInput[0]))
                    .findFirst();

            if (command.isPresent()) {
                try {
                    command.get().handle(commandInput);
                } catch (RuntimeException e) {
                    System.out.println("Command \"" + inputs[i] + "\" failed with: ");
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
