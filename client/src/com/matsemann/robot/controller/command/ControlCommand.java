package com.matsemann.robot.controller.command;

import java.util.List;

public interface ControlCommand {

    String getName();

    List<Option> getOptions();

    void setOption(String name, String value);

    String getRobotCommand();


    class Option {
        String name;
        List<String> values;

        public Option() {

        }

        public Option(String name, List<String> values) {
            this.name = name;
            this.values = values;
        }
    }

}
