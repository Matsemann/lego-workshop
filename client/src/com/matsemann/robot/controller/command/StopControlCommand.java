package com.matsemann.robot.controller.command;

import com.matsemann.robot.controller.Logger;
import com.matsemann.robot.controller.robot.Robot;

import java.util.List;

import static java.util.Arrays.asList;

public class StopControlCommand implements ControlCommand {

    private String motor;

    public StopControlCommand() {
    }

    public StopControlCommand(String motor) {
        this.motor = motor;
    }

    @Override
    public String getName() {
        return "stop";
    }

    @Override
    public List<Option> getOptions() {
        return asList(
                new Option("motor", asList("A", "B", "C", "D", "all"))
        );
    }

    @Override
    public void setOption(String name, String value) {
        if (name.equals("motor")) {
            motor = value;
        }
    }

    @Override
    public String getOption(String name) {
        return motor;
    }



    @Override
    public void execute(Robot robot) throws Exception {
        robot.execute(motor, (motor, name) -> {
            motor.stop(true);
            Logger.info("stop: stopped motor " + name);
        });
    }
}
