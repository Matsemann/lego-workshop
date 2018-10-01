package com.matsemann.robot.controller.robot;

import lejos.remote.ev3.RMIRegulatedMotor;

import java.util.*;
import java.util.function.Consumer;

import static java.util.Arrays.asList;

public class Robot {

    private final Map<String, RMIRegulatedMotor> motors;

    public Robot(RMIRegulatedMotor a, RMIRegulatedMotor b, RMIRegulatedMotor c, RMIRegulatedMotor d) {
        motors = new HashMap<>();

        motors.put("A", a);
        motors.put("B", b);
        motors.put("C", c);
        motors.put("D", d);
    }

    public RMIRegulatedMotor getMotor(String motor) {
        return motors.get(motor);
    }

    public List<RMIRegulatedMotor> getAllMotors() {
        return new ArrayList<>(motors.values());
    }


    public void execute(String motor, MotorFunction func) throws Exception {
        if (motor == null) {
            return;
        }

        if (motor.equals("all")) {
            for (Map.Entry<String, RMIRegulatedMotor> motorEntry : motors.entrySet()) {
                func.accept(motorEntry.getValue(), motorEntry.getKey());
            }
        } else {
            RMIRegulatedMotor rmiMotor = getMotor(motor);
            if (rmiMotor != null) {
                func.accept(rmiMotor, motor);
            }
        }
    }

    public interface MotorFunction {
        void accept(RMIRegulatedMotor rmiMotor, String name) throws Exception;
    }
}
