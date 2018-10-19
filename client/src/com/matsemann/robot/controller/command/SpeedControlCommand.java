package com.matsemann.robot.controller.command;

import com.matsemann.robot.controller.Logger;
import com.matsemann.robot.controller.robot.Robot;
import lejos.remote.ev3.RMIRegulatedMotor;

import java.util.List;

import static java.util.Arrays.asList;

public class SpeedControlCommand implements ControlCommand {
    private String motor;
    private String speed;

    public SpeedControlCommand() {

    }

    public SpeedControlCommand(String motor, String speed) {
        this.motor = motor;
        this.speed = speed;
    }

    @Override
    public String getName() {
        return "speed";
    }

    @Override
    public List<Option> getOptions() {
        return asList(
                new Option("motor", asList("A", "B", "C", "D", "all")),
                new Option("speed", asList("10%", "20%", "30%", "40%", "50%", "60%", "70%", "80%", "90%", "100%"))
        );
    }

    @Override
    public void setOption(String name, String value) {
        if (name.equals("motor")) {
            motor = value;
        } else {
            speed = value;
        }
    }

    @Override
    public String getOption(String name) {
        if (name.equals("motor")) {
            return motor;
        } else {
            return speed;
        }
    }

    @Override
    public void execute(Robot robot) throws Exception {
        if (motor == null || motor.isEmpty() || speed == null || speed.isEmpty()) {
            return;
        }
        double max = 0;
        for (RMIRegulatedMotor motor : robot.getAllMotors()) {
            float maxSpeed = motor.getMaxSpeed();
            if (maxSpeed > max) {
                max = maxSpeed;
            }
        }

        float percent = (Integer.parseInt(speed.substring(0, speed.length() - 1)) / 100.f);

        int newSpeed = (int) (max * percent);

        robot.execute(motor, (motor, name) -> {
            motor.setSpeed(newSpeed);
            Logger.info("reset: motor " + name + " paused and set to speed " + newSpeed);
        });
    }
}
