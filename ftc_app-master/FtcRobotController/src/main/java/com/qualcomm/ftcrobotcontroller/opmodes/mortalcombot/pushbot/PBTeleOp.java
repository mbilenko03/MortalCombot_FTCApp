package com.qualcomm.ftcrobotcontroller.opmodes.mortalcombot.pushbot;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.Range;

import java.util.Random;

public class PBTeleOp extends OpMode {
    // Global variables
    private PBCommon pushbot;
    private float motorLeftPower, motorRightPower, motorArmPower;
    private float motorLeftPowerLast, motorRightPowerLast, motorArmPowerLast;
    //private float motorTapePowerIn, motorTapePowerOut;
    private double clawPosition, clawPositionLast = 0d;
    //private double tiltPosition, lastTiltPosition = 1.0d;
    private String driverMessage;

    public PBTeleOp() {}

    enum TurnType {
        INCREASING,
        DECREASING
    }

    @Override
    public void init() {
        // Init PushBotCommon
        pushbot = new PBCommon(this);
        driverMessage = getRandomDriverMessage();
    }

    @Override
    public void loop() {
        grabControllerInput();
        updateMotors();
        updateClaw();
        //updateTapeMeasureTilt();
        updateTelemetry();
    }

    private void grabControllerInput(){
        // Update motor power
        motorLeftPower = -gamepad1.left_stick_y;
        motorRightPower = -gamepad1.right_stick_y;
        motorArmPower = -gamepad2.right_stick_y;
        //motorTapePowerOut = gamepad1.right_trigger;
        //motorTapePowerIn = -gamepad1.left_trigger;

        // Check for loosen claw
        if (gamepad2.dpad_down)
            clawPosition += 0.004;

        // Check for tighten claw
        if (gamepad2.dpad_up)
            clawPosition -= 0.004;

        /*
        if (gamepad2.right_trigger > 0)
            tiltPosition += 0.0009;

        if (gamepad2.left_trigger > 0)
            tiltPosition -= 0.0009;
            */
    }

    private void updateMotors() {
        if (motorLeftPower != motorLeftPowerLast || motorRightPower != motorRightPowerLast) {
            pushbot.setChassisPower(motorLeftPower, motorRightPower);
            motorLeftPowerLast = motorLeftPower;
            motorRightPowerLast = motorRightPower;
        }

        if (motorArmPower != motorArmPowerLast) {
            pushbot.setArmPower(motorArmPower);
            motorArmPowerLast = motorArmPower;
        }
/*
        if (motorTapePowerOut > 0) {
            pushbot.dispenseTape(motorTapePowerOut);
        }

        if (motorTapePowerIn < 0) {
            pushbot.dispenseTape(motorTapePowerIn);
        }
        */
    }

    private void updateClaw() {
        clawPosition = Range.clip(clawPosition, 0, 0.7);

        if (clawPosition != clawPositionLast) {
            pushbot.setClawPosition(clawPosition);
            clawPositionLast = clawPosition;
        }
    }

    /*
    private void updateTapeMeasureTilt() {
        tiltPosition = Range.clip(tiltPosition, 0.3798, 0.7056);

        if (tiltPosition != lastTiltPosition) {
            pushbot.setTiltPosition(tiltPosition);
            lastTiltPosition = tiltPosition;
        }
    }
    */

    private void updateTelemetry() {
        // Send telemetry data to the driver station.
        telemetry.addData("Left power", motorLeftPower);
        telemetry.addData("Right power", motorRightPower);
        telemetry.addData("Arm power", motorArmPower);
        telemetry.addData("Claw position", clawPosition);
        //telemetry.addData("TiltPosition", tiltPosition);
        //telemetry.addData("TapePower", "In: " + motorTapePowerIn + ", Out: " + motorTapePowerOut);
        telemetry.addData("Message", driverMessage);
    }

    private String getRandomDriverMessage() {
        String[] driverMessages = {
                "Keep up the good work, drivers!",
                "Hang in there :)",
                "Don't Frick up.",
                "Don't try to See More",
                "HUE!"
        };
        return driverMessages[new Random().nextInt(driverMessages.length)];
    }
}