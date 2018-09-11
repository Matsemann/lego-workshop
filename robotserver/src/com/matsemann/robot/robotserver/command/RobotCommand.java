package com.matsemann.robot.robotserver.command;

import com.matsemann.robot.robotserver.Robot;

import java.util.List;

public abstract class RobotCommand {

    protected Robot robot;

    public RobotCommand(Robot robot) {
        this.robot = robot;
    }


    abstract String getName();

    public abstract void handle(String[] command);

    public boolean canHandle(String command) {
        return getName().equals(command);
    }

    protected List<Robot.Motor> getMotors() {
        return null;
    }

    protected Robot.Motor getMotor(String s) {
        switch (s) {
            case "A": return null;
            default: return null;
        }
    }

}
