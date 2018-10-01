package com.matsemann.robot.controller.robot;

import com.matsemann.robot.controller.Logger;
import lejos.remote.ev3.RMIRegulatedMotor;
import lejos.remote.ev3.RemoteEV3;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class RobotHandler {

    private RemoteEV3 remoteEV3;
    private Robot robot;

    public void connect(String ip) {
        if (robot != null) {
            disconnect();
        }
        Logger.info("Connecting to robot with IP " + ip);

        try {
            remoteEV3 = new RemoteEV3(ip);
            robot = new Robot(
                    remoteEV3.createRegulatedMotor("A", 'L'),
                    remoteEV3.createRegulatedMotor("B", 'L'),
                    remoteEV3.createRegulatedMotor("C", 'L'),
                    remoteEV3.createRegulatedMotor("D", 'L')
            );
            Logger.ok("Successfully connected to robot " + ip);
        } catch (RemoteException | MalformedURLException | NotBoundException e) {
            Logger.error("Couldn't connect to robot " + ip + ", error: ", e);
        }

    }

    public Robot getRobot() {
        return robot;
    }

    public void disconnect() {
        Logger.info("Disconnecting from robot");
        if (robot == null) {
            Logger.info("No active robot to disconnect from");
            return;
        }

        try {
            for (RMIRegulatedMotor motor : robot.getAllMotors()) {
                motor.close();
            }
            Logger.ok("Successfully disconnected from robot");
        } catch (RemoteException e) {
            Logger.error("Failed disconnecting: ", e);
        } finally {
            robot = null;
            remoteEV3 = null;
        }
    }

}
