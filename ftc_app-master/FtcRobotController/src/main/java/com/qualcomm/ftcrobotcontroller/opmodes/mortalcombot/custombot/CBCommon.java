package com.qualcomm.ftcrobotcontroller.opmodes.mortalcombot.custombot;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Servo;

public class CBCommon {
    public DcMotor motorLeftFront, motorRightFront, motorLeftBack, motorRightBack;
    public DcMotor motorTread;
    public DcMotor motorDeath;
    public DcMotorController.DeviceMode READ_MODE, WRITE_MODE;
    private OpMode opModeSender;

    public CBCommon(OpMode sender) {
        // Define OpMode variable
        opModeSender = sender;

        // Define motor variables
        motorLeftFront = opModeSender.hardwareMap.dcMotor.get("motor_lf");
        motorRightFront = opModeSender.hardwareMap.dcMotor.get("motor_rf");
        motorRightBack = opModeSender.hardwareMap.dcMotor.get("motor_rb");
        motorLeftBack = opModeSender.hardwareMap.dcMotor.get("motor_lb");
        motorTread = opModeSender.hardwareMap.dcMotor.get("motor_t");
        motorDeath = opModeSender.hardwareMap.dcMotor.get("motor_d");

        // Reverse devices on left
        motorLeftFront.setDirection(DcMotor.Direction.REVERSE);
        motorRightBack.setDirection(DcMotor.Direction.REVERSE);
        motorDeath.setDirection(DcMotor.Direction.REVERSE);

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

        // Set power to 4 motors
        motorLeftFront.setPower(leftPower);
        motorRightFront.setPower(rightPower);
        motorLeftBack.setPower(leftPower);
        motorRightBack.setPower(rightPower);
    }

    public void sendTreadMessage(boolean status) {
        if (status) {
            motorTread.setPower(1.0f);
            motorDeath.setPower(0.75f);
        }
        else {
            motorTread.setPower(0.0f);
            motorDeath.setPower(0.0f);
        }
    }
}
