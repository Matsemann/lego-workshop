package com.matsemann.robot.controller.command;

import com.matsemann.robot.controller.robot.Robot;

import java.util.List;

import static java.util.Arrays.asList;

public class MoveControlCommand implements ControlCommand {

    private String motor;
    private String angle;

    public MoveControlCommand() {

    }

    public MoveControlCommand(String motor, String angle) {
        this.motor = motor;
        this.angle = angle;
    }

    @Override
    public String getName() {
        return "move";
    }

    @Override
    public List<Option> getOptions() {
        return asList(
                new Option("motor", asList("A", "B", "C", "D")),
                new Option("angle", asList("forwards", "backwards", "15", "30", "45", "60", "90", "120", "180", "360", "-360", "-180", "-120", "-90", "-60", "-45", "-30", "-15"))
        );
    }

    @Override
    public void setOption(String name, String value) {
        if (name.equals("motor")) {
            motor = value;
        } else {
            angle = value;
        }
    }

    @Override
    public String getOption(String name) {
        if (name.equals("motor")) {
            return motor;
        } else {
            return angle;
        }
    }

    @Override
    public void execute(Robot robot) throws Exception {
        robot.execute(motor, (motor, name) -> {
            if (angle == null) {
                return;
            }
            if (angle.equals("forwards")) {
                motor.forward();
            } else if (angle.equals("backwards")) {
                motor.backward();
            } else if (!angle.isEmpty()) {
                int angleInt = Integer.parseInt(angle);
                motor.rotate(angleInt, true);
            }
        });
    }
}
