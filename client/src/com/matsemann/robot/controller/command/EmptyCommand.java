package com.matsemann.robot.controller.command;

import com.matsemann.robot.controller.Logger;
import com.matsemann.robot.controller.robot.Robot;

import java.util.List;

import static java.util.Collections.emptyList;

public class EmptyCommand implements ControlCommand {

    @Override
    public String getName() {
        return "nothing";
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
        Logger.info("nothing: nothing");
    }
}
