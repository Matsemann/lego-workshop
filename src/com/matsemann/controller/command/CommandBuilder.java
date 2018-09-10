package com.matsemann.controller.command;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

public class CommandBuilder {

    private List<Class> commands = asList(MoveControlCommand.class, ResetControlCommand.class, StopControlCommand.class);

    private Map<String, List<ControlCommand>> keyBindings = new HashMap<>();

    public String getCommandStringForButtonPress(char keypress, boolean keyUp) {

        String key = makeKey(keypress, keyUp);
        if (!keyBindings.containsKey(key)) {
            return null;
        }

        List<ControlCommand> controlCommands = keyBindings.get(key);

        String command = controlCommands.stream()
                .map(ControlCommand::getRobotCommand)
                .collect(Collectors.joining("|"));

        return command;
    }

    public void addCommandToButton(char keypress, boolean keyUp, ControlCommand command) {
        String key = makeKey(keypress, keyUp);
        if (!keyBindings.containsKey(key)) {
            keyBindings.put(key, new ArrayList<>());
        }
        keyBindings.get(key).add(command);
    }

    public void removeCommandFromButton(char keypress, boolean keyUp, int index) {
        String key = makeKey(keypress, keyUp);
        if (!keyBindings.containsKey(key)) {
            return;
        }
        keyBindings.get(key).remove(index);
    }

    private String makeKey(char keypress, boolean keyUp) {
        return keypress + (keyUp ? "up" : "down");
    }

}
