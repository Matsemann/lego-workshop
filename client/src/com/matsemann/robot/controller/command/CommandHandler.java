package com.matsemann.robot.controller.command;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CommandHandler {


    private Map<String, List<ControlCommand>> keyBindings = new LinkedHashMap<>();
    private List<EventHandler<CommandEvent>> listeners = new ArrayList<>();

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
        addCommandToButton(key, command, -1);
    }

    public void addCommandToButton(String key, ControlCommand command, int index) {
        if (!keyBindings.containsKey(key)) {
            keyBindings.put(key, new ArrayList<>());
        }
        if (index == -1) {
            keyBindings.get(key).add(command);
        } else {
            keyBindings.get(key).add(index, command);
        }
        fire();
    }

    public void removeCommandFromButton(String key, int index) {
        if (!keyBindings.containsKey(key)) {
            return;
        }
        keyBindings.get(key).remove(index);
        if (keyBindings.get(key).size() == 0) {
            keyBindings.remove(key);
        }
        fire();
    }

    public void removeAll() {
        keyBindings.clear();
        fire();
    }

    public Map<String, List<ControlCommand>> getKeyBindings() {
        return keyBindings;
    }

    public void addListener(EventHandler<CommandEvent> handler) {
        listeners.add(handler);
    }

    private void fire() {
        listeners.forEach(h -> h.handle(new CommandEvent()));
    }

    private String makeKey(String keypress, boolean keyUp) {
        return keypress + (keyUp ? "up" : "down");
    }


    public static class CommandEvent extends Event {
        public static final EventType<CommandEvent> CHANGED = new EventType<>(Event.ANY, "CHANGED");

        public CommandEvent() {
            super(CHANGED);
        }

    }

}
