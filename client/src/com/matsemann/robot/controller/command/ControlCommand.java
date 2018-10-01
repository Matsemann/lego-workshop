package com.matsemann.robot.controller.command;

import com.matsemann.robot.controller.robot.Robot;

import java.util.List;

public interface ControlCommand {

    String getName();

    List<Option> getOptions();

    void setOption(String name, String value);

    String getOption(String name);

    void execute(Robot robot) throws Exception;


    class Option {
        public String name;
        public List<String> values;

        public Option() {

        }

        public Option(String name, List<String> values) {
            this.name = name;
            this.values = values;
        }
    }

}
