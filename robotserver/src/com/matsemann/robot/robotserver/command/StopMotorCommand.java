package com.matsemann.robot.robotserver.command;

import com.matsemann.robot.robotserver.Robot;

public class StopMotorCommand extends RobotCommand {

    public StopMotorCommand(Robot robot) {
        super(robot);
    }

    @Override
    public String getName() {
        return "stop";
    }

    @Override
    public void handle(String[] command) {
        // stop:A
        // stop:B
        // stop:all

        if (command[1].equals("all")) {
//            getMotors().forEach(Robot.Motor::stop);
        } else {
            getMotor(command[1]).stop();
        }
    }
}
