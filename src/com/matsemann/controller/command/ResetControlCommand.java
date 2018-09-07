package com.matsemann.controller.command;

import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

public class ResetControlCommand implements ControlCommand {

    public ResetControlCommand() {

    }


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
