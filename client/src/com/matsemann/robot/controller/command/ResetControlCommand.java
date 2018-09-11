package com.matsemann.robot.controller.command;

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
    public String getRobotCommand() {
        return getName();
    }
}
