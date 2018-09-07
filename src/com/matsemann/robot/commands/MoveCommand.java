package com.matsemann.robot.commands;

import com.matsemann.robot.Robot;

public class MoveCommand extends Command {

    private Robot robot;

    public MoveCommand(Robot robot) {
        super(robot);
    }


    @Override
    public String getName() {
        return "move";
    }

    @Override
    public void handle(String[] command) {
        // move:A:forwards
        // move:C:backwards
        // move:A:90
        // move:B:-90

        Robot.Motor motor = getMotor(command[1]);
        String amount = command[2];
        if (amount.equals("forwards")) {
            // motor.something
        } else if (amount.equals("backwards")) {

        } else {
            int i = Integer.parseInt(amount);
        }
    }
}
