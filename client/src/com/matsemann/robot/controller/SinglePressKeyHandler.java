package com.matsemann.robot.controller;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

import java.util.HashSet;
import java.util.Set;

class SinglePressKeyHandler implements EventHandler<KeyEvent> {

    private EventHandler<KeyEvent> eventEventHandler;
    private Set<String> keyDowns = new HashSet<>();

    public SinglePressKeyHandler(EventHandler<KeyEvent> eventEventHandler) {
        this.eventEventHandler = eventEventHandler;
    }

    /**
     * Filter so we don't get many keydown events..
     */
    @Override
    public void handle(KeyEvent event) {
        String key = event.getCode().getName();

        if (event.getEventType().getName().equals("KEY_PRESSED")) {
            if (!keyDowns.contains(key)) {
                keyDowns.add(key);
                eventEventHandler.handle(event);
            }
        } else {
            keyDowns.remove(key);
            eventEventHandler.handle(event);
        }
    }
}
