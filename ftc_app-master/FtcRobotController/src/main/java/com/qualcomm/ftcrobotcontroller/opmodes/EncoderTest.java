/*
 * Copyright (C) David Vick 2015
 * All rights reserved
 */
package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.hardware.ModernRoboticsUsbDcMotorController;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EncoderTest extends OpMode {
    private ElapsedTime buttonsET = new ElapsedTime(), buttons1ET = new ElapsedTime();

    private List<DcMotor> motors;
    private List<String> names;
    private int index;

    @Override
    public void init() {
        motors = new ArrayList<DcMotor>();
        names = new ArrayList<String>();
        index = 0;
        for (Map.Entry<String, DcMotor> entry : hardwareMap.dcMotor.entrySet()) {
            DcMotor motor = entry.getValue();
            if (motor.getController() instanceof ModernRoboticsUsbDcMotorController) {
                motors.add(motor);
                names.add(entry.getKey());
            }
        }
    }

    @Override
    public void loop() {
        if (motors.size() == 0) {
            stop();
            return;
        }

        if (buttonsET.time() >= 0.2) {
            if (gamepad1.dpad_left) {
                --index;
                buttonsET.reset();
            } else if (gamepad1.dpad_right) {
                ++index;
                buttonsET.reset();
            }

            if (index < 0) index += motors.size();
            else if (index >= motors.size()) index -= motors.size();
        }

        DcMotor motor = motors.get(index);

        if (motor.getMode() == DcMotorController.RunMode.RESET_ENCODERS
                && motor.getCurrentPosition() == 0)
            motor.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);

        if (buttons1ET.time() >= 0.2) {
            if (gamepad1.a) {
                switch (motor.getDirection()) {
                    case FORWARD:
                        motor.setDirection(DcMotor.Direction.REVERSE);
                        break;
                    case REVERSE:
                        motor.setDirection(DcMotor.Direction.FORWARD);
                        break;
                }
                buttons1ET.reset();
            }
            if (gamepad1.b) {
                motor.setMode(DcMotorController.RunMode.RESET_ENCODERS);
                buttons1ET.reset();
            }
        }

        motor.setPower(-gamepad1.right_stick_y);

        telemetry.addData("motor", names.get(index));
        telemetry.addData("reversed", motor.getDirection() == DcMotor.Direction.REVERSE);
        telemetry.addData("pos", motor.getCurrentPosition());
    }
}
