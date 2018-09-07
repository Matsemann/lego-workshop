package com.matsemann.robot.command;

import com.matsemann.robot.Robot;

public class ResetCommand extends RobotCommand {

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
