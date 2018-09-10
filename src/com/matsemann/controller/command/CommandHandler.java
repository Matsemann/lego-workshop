package com.matsemann.controller.command;

import javafx.event.EventHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CommandHandler {


    private Map<String, List<ControlCommand>> keyBindings = new HashMap<>();
    private List<EventHandler> listeners = new ArrayList<>();

    public String getCommandStringForButtonPress(String keypress, boolean keyUp) {

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

    public void addCommandToButton(String keypress, boolean keyUp, ControlCommand command) {
        String key = makeKey(keypress, keyUp);
        if (!keyBindings.containsKey(key)) {
            keyBindings.put(key, new ArrayList<>());
        }
        keyBindings.get(key).add(command);
        fire();
    }

    public void removeCommandFromButton(String keypress, boolean keyUp, int index) {
        String key = makeKey(keypress, keyUp);
        if (!keyBindings.containsKey(key)) {
            return;
        }
        keyBindings.get(key).remove(index);
        fire();
    }

    public void removeAll() {
        keyBindings.clear();
        fire();
    }

    public void addListener(EventHandler handler) {
        listeners.add(handler);
    }

    private void fire() {
        listeners.forEach(h -> h.handle(null));
    }

    private String makeKey(String keypress, boolean keyUp) {
        return keypress + (keyUp ? "up" : "down");
    }

}
