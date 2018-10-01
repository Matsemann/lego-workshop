package com.matsemann.robot.controller.command;

import com.matsemann.robot.controller.Logger;
import com.matsemann.robot.controller.robot.Robot;
import lejos.remote.ev3.RMIRegulatedMotor;

import java.util.List;

import static java.util.Collections.emptyList;

public class ResetControlCommand implements ControlCommand {

    @Override
    public String getName() {
        return "reset";
    }

    @Override
    public List<Option> getOptions() {
        return emptyList();
    }

    @Override
    public void setOption(String name, String value) {
    }

    @Override
    public String getOption(String name) {
        return null;
    }

    @Override
    public void execute(Robot robot) throws Exception {
        double max = 0;
        for (RMIRegulatedMotor motor : robot.getAllMotors()) {
            float maxSpeed = motor.getMaxSpeed();
            if (maxSpeed > max) {
                max = maxSpeed;
            }
        }
        int newSpeed = (int) (max * 0.5f);

        robot.execute("all", (motor, name) -> {
            motor.flt(true);
            motor.setSpeed(newSpeed);
            Logger.info("reset: motor " + name + " paused and set to speed " + newSpeed);
        });
    }
}
