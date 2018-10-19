package com.matsemann.robot.controller.command;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

public class CommandCreator {

    public static final List<Class<? extends ControlCommand>> COMMANDS = asList(
            EmptyCommand.class,
            MoveControlCommand.class,
            SpeedControlCommand.class,
            ResetControlCommand.class,
            StopControlCommand.class
    );

    public static final List<String> NAMES = COMMANDS.stream()
            .map(CommandCreator::create)
            .map(ControlCommand::getName)
            .collect(toList());

    public static ControlCommand create(String name) {
        return COMMANDS.stream()
                .map(CommandCreator::create)
                .filter(e -> name.equals(e.getName()))
                .findFirst().get();
    }

    private static ControlCommand create(Class<? extends ControlCommand> clazz) {
        try {
            return clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException("Couldn't make class", e);
        }
    }

}
