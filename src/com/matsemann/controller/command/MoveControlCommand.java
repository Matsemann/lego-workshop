package com.matsemann.controller.command;

import java.util.List;

import static java.util.Arrays.asList;

public class MoveControlCommand implements ControlCommand {

    private String motor, angle;

    public MoveControlCommand() {

    }


    @Override
    public String getName() {
        return "move";
    }

    @Override
    public List<Option> getOptions() {
        return asList(
                new Option("motor", asList("A", "B", "C", "D")),
                new Option("angle", asList("forwards", "backwards", "15", "30", "45", "60", "90", "120", "180", "360", "-360", "-180", "-120", "-90", "-60", "-45", "-30", "-15"))
        );
    }

    @Override
    public void setOption(String name, String value) {
        if (name.equals("motor")) {
            motor = value;
        } else {
            angle = value;
        }
    }

    @Override
    public String getRobotCommand() {
        return getName() + ":" + motor + ":" + angle;
    }
}
