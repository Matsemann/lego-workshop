package com.matsemann.robot.controller.command;

import com.matsemann.robot.controller.Logger;
import com.matsemann.robot.controller.robot.Robot;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CommandHandler {


    private Map<String, KeyEventCommands> keyEventCommands = new LinkedHashMap<>();

    private List<EventHandler<CommandEvent>> listeners = new ArrayList<>();

    public void executeCommandsForKeyPress(String key, boolean keyUp, Robot robot) {
        if (!keyEventCommands.containsKey(key)) {
            Logger.error("No keybindings found for " + key);
            return;
        }

        KeyEventCommands keyCommands = this.keyEventCommands.get(key);
        List<ControlCommand> commands = keyUp ? keyCommands.upCommands : keyCommands.downCommands;

        try {
            for (ControlCommand command : commands) {
                command.execute(robot);
            }
        } catch (Exception e) {
            Logger.error("Some commands failed: ", e);
        }
    }

    public void addCommandToButton(String keypress, boolean keyUp, ControlCommand command) {
        addCommandToButton(keypress, keyUp, command, -1);
    }

    public void addCommandToButton(String keypress, boolean keyUp, ControlCommand command, int index) {
        if (!keyEventCommands.containsKey(keypress)) {
            keyEventCommands.put(keypress, new KeyEventCommands(keypress));
        }
        if (command == null) {
            return;
        }

        KeyEventCommands commands = this.keyEventCommands.get(keypress);
        List<ControlCommand> list = keyUp ? commands.upCommands : commands.downCommands;
        if (index == -1) {
            list.add(command);
        } else {
            list.add(index, command);
        }
        fire();
    }

    public void removeCommandFromButton(String keypress, boolean keyUp, int index) {
        if (!keyEventCommands.containsKey(keypress)) {
            return;
        }

        KeyEventCommands commands = keyEventCommands.get(keypress);

        (keyUp ? commands.upCommands : commands.downCommands).remove(index);

//        if (commands.upCommands.size() == 0 && commands.downCommands.size() == 0) {
//            keyEventCommands.remove(keypress);
//        }

        fire();
    }

    public void removeAll() {
        keyEventCommands.clear();
        fire();
    }


    public void addListener(EventHandler<CommandEvent> handler) {
        listeners.add(handler);
    }

    private void fire() {
        listeners.forEach(h -> h.handle(new CommandEvent()));
    }

    public Map<String, KeyEventCommands> getKeyEventCommands() {
        return keyEventCommands;
    }

    public static class CommandEvent extends Event {
        public static final EventType<CommandEvent> CHANGED = new EventType<>(Event.ANY, "CHANGED");

        public CommandEvent() {
            super(CHANGED);
        }

    }

    public static class KeyEventCommands {
        public String key;
        public List<ControlCommand> downCommands = new ArrayList<>();
        public List<ControlCommand> upCommands = new ArrayList<>();

        public KeyEventCommands(String key) {
            this.key = key;
        }
    }

}
