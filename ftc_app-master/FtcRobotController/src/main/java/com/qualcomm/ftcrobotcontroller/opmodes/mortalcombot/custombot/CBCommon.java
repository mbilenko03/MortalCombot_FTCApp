package com.qualcomm.ftcrobotcontroller.opmodes.mortalcombot.custombot;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Servo;

public class CBCommon {
    public DcMotor motorLeft, motorRight, motorArm;
    public DcMotorController.DeviceMode READ_MODE, WRITE_MODE;
    private OpMode opModeSender;

    public CBCommon(OpMode sender) {
        // Define OpMode variable
        opModeSender = sender;

        // Define motor variables
        motorLeft = opModeSender.hardwareMap.dcMotor.get("motor_1");
        motorRight = opModeSender.hardwareMap.dcMotor.get("motor_2");

        // Reverse devices on left
        motorLeft.setDirection(DcMotor.Direction.REVERSE);

        // Define some helper variables
        READ_MODE = DcMotorController.DeviceMode.READ_ONLY;
        WRITE_MODE = DcMotorController.DeviceMode.WRITE_ONLY;
    }

    public void setChassisPower(float leftPower, float rightPower) {
        if (opModeSender.gamepad1.right_bumper) {
            leftPower = leftPower * 0.07f;
            rightPower = rightPower * 0.07f;
        }

        if (opModeSender.gamepad1.left_bumper) {
            leftPower = leftPower * 0.15f;
            rightPower = rightPower * 0.15f;
        }

        // Set power to both motors
        motorLeft.setPower(leftPower);
        motorRight.setPower(rightPower);
    }

    public void setArmPower(float armPower) {
        if (opModeSender.gamepad2.right_bumper) {
            armPower = armPower * 0.15f;
        }

        // Set power to motor
        motorArm.setPower(armPower);
    }
}
