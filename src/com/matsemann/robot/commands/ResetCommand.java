package com.matsemann.robot.commands;

import com.matsemann.robot.Robot;

public class ResetCommand extends Command {

    public ResetCommand(Robot robot) {
        super(robot);
    }

    @Override
    public String getName() {
        return "reset";
    }

    @Override
    public void handle(String[] command) {
        // robot
//        getMotors().add(null);
        // stop, set default speed

    }
}
