package com.qualcomm.ftcrobotcontroller.opmodes.mortalcombot.pushbot;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.Range;

public class PBTeleOp extends OpMode {
    // Global variables
    private PBCommon pushbot;
    private float motorLeftPower, motorRightPower, motorArmPower;
    private float motorLeftPowerLast, motorRightPowerLast, motorArmPowerLast;
    private double clawPosition, clawPositionLast = 0.0d;

    public PBTeleOp() {}

    @Override
    public void init() {
        // Init PushBotCommon
        pushbot = new PBCommon(this);
    }

    @Override
    public void loop() {
        updateMotorPower();
        updateClawPosition();
        updateTelemetry();
    }

    private void updateMotorPower() {
        motorLeftPower = -gamepad1.left_stick_y;
        motorRightPower = -gamepad1.right_stick_y;
        motorArmPower = -gamepad2.right_stick_y;

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

    private void updateClawPosition() {
        // Loosen claw
        if (gamepad2.dpad_down) {
            clawPosition += 0.004;
        }

        // Tighten claw
        if (gamepad2.dpad_up) {
            clawPosition -= 0.004;
        }

        clawPosition = Range.clip(clawPosition, 0, 1);

        if (clawPosition != clawPositionLast) {
            pushbot.setClawPosition(clawPosition);
            clawPositionLast = clawPosition;
        }
    }

    private void updateTelemetry() {
        // Send telemetry data to the driver station.
        telemetry.addData("01", "Left power: " + motorLeftPower);
        telemetry.addData("02", "Right power: " + motorRightPower);
        telemetry.addData("03", "Arm power: " + motorArmPower);
        telemetry.addData("04", "Claw position: " + clawPosition);
        telemetry.addData("06", "Slow down on chassis: " + gamepad1.right_bumper);
        telemetry.addData("07", "Slow down on claw: " + gamepad2.right_bumper);
        telemetry.addData("08", "Keep up the good work drivers.");
    }
}
