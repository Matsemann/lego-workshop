package com.matsemann.controller.command;

import java.util.List;

import static java.util.Arrays.asList;

public class StopControlCommand implements ControlCommand {

    private String motor;

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
    public String getRobotCommand() {
        return getName() + ":" + motor;
    }
}
