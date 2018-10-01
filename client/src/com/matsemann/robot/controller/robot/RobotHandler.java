package com.matsemann.robot.controller.robot;

import com.matsemann.robot.controller.Logger;
import lejos.remote.ev3.RMIRegulatedMotor;
import lejos.remote.ev3.RemoteEV3;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class RobotHandler {

    private RemoteEV3 remoteEV3;
    private RMIRegulatedMotor a, b, c, d;

    public void connect(String ip) {
        if (remoteEV3 != null) {
            disconnect();
        }
        Logger.info("Connecting to robot with IP " + ip);

        try {
            remoteEV3 = new RemoteEV3(ip);
            a = remoteEV3.createRegulatedMotor("A", 'L');
            b = remoteEV3.createRegulatedMotor("B", 'L');
            c = remoteEV3.createRegulatedMotor("C", 'L');
            d = remoteEV3.createRegulatedMotor("D", 'L');
            Logger.ok("Successfully connected to robot " + ip);
        } catch (RemoteException | MalformedURLException | NotBoundException e) {
            Logger.error("Couldn't connect to robot " + ip + ", error: " + e.getMessage());
        }

    }

    public void disconnect() {
        Logger.info("Disconnecting from robot");
        if (remoteEV3 == null) {
            Logger.info("No active robot to disconnect from");
            return;
        }

        remoteEV3 = null;
        try {
            a.close();
            b.close();
            c.close();
            d.close();
            Logger.ok("Successfully disconnected from robot");
        } catch (RemoteException e) {
            Logger.error("Failed disconnecting: " + e.getMessage());
        }
    }

    public void rotateA() {
        try {
            a.forward();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


}
