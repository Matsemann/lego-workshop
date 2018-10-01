package com.matsemann.robot.controller;

import static com.matsemann.robot.controller.Logger.Level.*;

public class Logger {

    private static final Logger logger = new Logger();
    private LogView viewer;


    private void log(String msg, Level level) {
        if (level == Level.ERROR) {
            System.err.println(level.name() + ": " + msg);
        } else {
            System.out.println(level.name() + ": " + msg);
        }

        if (viewer != null) {
            viewer.handleMessage(msg, level.name());
        }
    }

    public static void attachLogView(LogView viewer) {
        logger.viewer = viewer;
    }

    public static void ok(String msg) {
        logger.log(msg, OK);
    }

    public static void info(String msg) {
        logger.log(msg, INFO);
    }

    public static void error(String msg) {
        logger.log(msg, ERROR);
    }

    public static void error(String msg, Exception e) {
        logger.log(msg + e.getMessage(), ERROR);
        e.printStackTrace();
    }

    public interface LogView {
        void handleMessage(String msg, String level);
    }

    public enum Level {
        OK, INFO, ERROR
    }

}
