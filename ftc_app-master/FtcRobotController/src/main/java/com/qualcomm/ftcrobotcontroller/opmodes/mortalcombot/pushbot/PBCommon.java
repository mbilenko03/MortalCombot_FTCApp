package com.qualcomm.ftcrobotcontroller.opmodes.mortalcombot.pushbot;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.Range;

public class PBCommon {
    // Global variables
    public DcMotor motorLeft, motorRight, motorArm;
    public Servo servoClawLeft, servoClawRight, servoSweeper;
    public DcMotorController.DeviceMode READ_MODE, WRITE_MODE;
    public DcMotorController.RunMode RUN_USING_ENCODERS, RESET_ENCODERS, RUN_TO_POSITION;
    public OpMode opModeSender;

    public PBCommon(OpMode sender) {
        // Define OpMode variable
        opModeSender = sender;

        // Define motor variables
        motorLeft = opModeSender.hardwareMap.dcMotor.get("motor_1");
        motorRight = opModeSender.hardwareMap.dcMotor.get("motor_2");
        motorArm = opModeSender.hardwareMap.dcMotor.get("motor_3");

        // Define servo variables
        servoClawLeft = opModeSender.hardwareMap.servo.get("servo_1");
        servoClawRight = opModeSender.hardwareMap.servo.get("servo_2");
        servoSweeper = opModeSender.hardwareMap.servo.get("servo_3");

        // Reverse devices on left
        motorLeft.setDirection(DcMotor.Direction.REVERSE);
        servoClawLeft.setDirection(Servo.Direction.REVERSE);
        servoSweeper.setDirection(Servo.Direction.REVERSE);

        // Define some helper variables
        READ_MODE = DcMotorController.DeviceMode.READ_ONLY;
        WRITE_MODE = DcMotorController.DeviceMode.WRITE_ONLY;

        RUN_USING_ENCODERS = DcMotorController.RunMode.RUN_USING_ENCODERS;
        RESET_ENCODERS = DcMotorController.RunMode.RESET_ENCODERS;
        RUN_TO_POSITION = DcMotorController.RunMode.RUN_TO_POSITION;
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

    // Method to set the PushBot claw position.
    public void setClawPosition(double clawPosition) {
        clawPosition = Range.clip(clawPosition, 0, 1);

        servoClawLeft.setPosition(clawPosition);
        servoClawRight.setPosition(clawPosition);
    }

    // Method to set the PushBot sweeper position
    public void setSweeperPosition(double pos) {
        servoSweeper.setPosition(pos);
    }
}
