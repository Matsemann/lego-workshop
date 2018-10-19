package com.matsemann.robot.controller.command;

public class DefaultKeybindings {

    private CommandHandler commandHandler;

    public DefaultKeybindings(CommandHandler commandHandler) {
        this.commandHandler = commandHandler;
    }

    public void resetKeybindings() {
        commandHandler.removeAll();

        commandHandler.addCommandToButton("W", false, new MoveControlCommand("A", "forwards"));
        commandHandler.addCommandToButton("W", false, new MoveControlCommand("B", "forwards"));
        commandHandler.addCommandToButton("W", true, new StopControlCommand("A"));
        commandHandler.addCommandToButton("W", true, new StopControlCommand("B"));

        commandHandler.addCommandToButton("A", false, new MoveControlCommand("B", "forwards"));
        commandHandler.addCommandToButton("A", true, new StopControlCommand("B"));

        commandHandler.addCommandToButton("S", false, new MoveControlCommand("A", "backwards"));
        commandHandler.addCommandToButton("S", false, new MoveControlCommand("B", "backwards"));
        commandHandler.addCommandToButton("S", true, new StopControlCommand("A"));
        commandHandler.addCommandToButton("S", true, new StopControlCommand("B"));

        commandHandler.addCommandToButton("D", false, new MoveControlCommand("A", "forwards"));
        commandHandler.addCommandToButton("D", true, new StopControlCommand("A"));


        commandHandler.addCommandToButton("Q", false, new SpeedControlCommand("all", "50%"));
        commandHandler.addCommandToButton("E", false, new MoveControlCommand("D", "45"));

        commandHandler.addCommandToButton("Z", false, new StopControlCommand("all"));
        commandHandler.addCommandToButton("X", false, new ResetControlCommand());

    }
}
