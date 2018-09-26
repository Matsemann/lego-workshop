package com.matsemann.robot.controller.robot;

import lejos.remote.ev3.RMIRegulatedMotor;
import lejos.remote.ev3.RemoteEV3;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class RobotHandler {

    private RemoteEV3 remoteEV3;
    private RMIRegulatedMotor a, b, c, d;

    public RobotHandler() {

    }

    public void connect(String ip) {
        if (remoteEV3 != null) {
            disconnect();
        }

        try {
            remoteEV3 = new RemoteEV3(ip);
            a = remoteEV3.createRegulatedMotor("A", 'L');
            b = remoteEV3.createRegulatedMotor("B", 'L');
            c = remoteEV3.createRegulatedMotor("C", 'L');
            d = remoteEV3.createRegulatedMotor("D", 'L');
        } catch (RemoteException | MalformedURLException | NotBoundException e) {
            throw new RuntimeException(e);
        }

    }

    public void disconnect() {
        remoteEV3 = null;
        try {
            a.close();
            b.close();
            c.close();
            d.close();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
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
