package com.matsemann.robot.controller.command;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

public class CommandCreator {

    public final static List<Class<? extends ControlCommand>> COMMANDS = asList(
            EmptyCommand.class,
            MoveControlCommand.class,
            ResetControlCommand.class,
            StopControlCommand.class
    );


    public List<ControlCommand> getAvailableCommands() {
        return COMMANDS.stream()
                .map(this::create)
                .collect(toList());
    }

    public ControlCommand create(String name) {
        return COMMANDS.stream()
                .map(this::create)
                .filter(e -> name.equals(e.getName()))
                .findFirst().get();
    }

    private ControlCommand create(Class<? extends ControlCommand> clazz) {
        try {
            return clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException("Couldn't make class", e);
        }
    }

}
