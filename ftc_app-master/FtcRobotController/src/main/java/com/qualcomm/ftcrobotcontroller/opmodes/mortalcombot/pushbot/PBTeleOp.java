package com.qualcomm.ftcrobotcontroller.opmodes.mortalcombot.pushbot;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.Range;

public class PBTeleOp extends OpMode {
    // Global variables
    private PBCommon pushbot;
    private float motorLeftPower, motorRightPower, motorArmPower;
    private float motorLeftPowerLast, motorRightPowerLast, motorArmPowerLast;
    private double clawPosition, clawPositionLast = 0d;
    private double sweeperPosition = 0.0d;
    private boolean sweeperActivated = false;
    private TurnType turnType = TurnType.DECREASING;

    public PBTeleOp() {}

    enum TurnType {
        INCREASING,
        DECREASING
    }

    @Override
    public void init() {
        // Init PushBotCommon
        pushbot = new PBCommon(this);
    }

    @Override
    public void loop() {
        grabControllerInput();
        updateMotors();
        updateClaw();
        updateSweeper();
        updateTelemetry();
    }

    private void grabControllerInput(){
        // Update motor power
        motorLeftPower = -gamepad1.left_stick_y;
        motorRightPower = -gamepad1.right_stick_y;
        motorArmPower = -gamepad2.right_stick_y;

        // Check for loosen claw
        if (gamepad2.dpad_down)
            clawPosition += 0.004;

        // Check for tighten claw
        if (gamepad2.dpad_up)
            clawPosition -= 0.004;

        // Check sweeper activation
        if (gamepad1.y)
            sweeperActivated = !sweeperActivated;
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
    }

    private void updateClaw() {
        clawPosition = Range.clip(clawPosition, 0, 1);

        if (clawPosition != clawPositionLast) {
            pushbot.setClawPosition(clawPosition);
            clawPositionLast = clawPosition;
        }
    }

    private void updateSweeper() {
        if (sweeperActivated) {
            if (sweeperPosition >= 1 && turnType == TurnType.INCREASING) {
                turnType = TurnType.DECREASING;
            }

            if (sweeperPosition <= 0 && turnType == TurnType.DECREASING) {
                turnType = TurnType.INCREASING;
            }

            if (turnType == TurnType.INCREASING) {
                sweeperPosition += 0.004;
            } else {
                sweeperPosition -= 0.004;
            }
        }

        sweeperPosition = Range.clip(sweeperPosition, 0, 1);
        pushbot.setSweeperPosition(sweeperPosition);
    }

    private void updateTelemetry() {
        // Send telemetry data to the driver station.
        telemetry.addData("01", "Left power: " + motorLeftPower);
        telemetry.addData("02", "Right power: " + motorRightPower);
        telemetry.addData("03", "Arm power: " + motorArmPower);
        telemetry.addData("04", "Claw position: " + clawPosition);
        telemetry.addData("05", "Sweeper activated/position: " + sweeperActivated + ", " + sweeperPosition);
        telemetry.addData("06", "Chassis RB: " + gamepad1.right_bumper);
        telemetry.addData("07", "Arm RB: " + gamepad2.right_bumper);
        telemetry.addData("08", "Keep up the good work drivers.");
    }
}

/*
*  MICHAEL:
*  Try and run it :) I think it's all done, report back the success!
* */
