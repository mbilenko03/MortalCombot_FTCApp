package com.qualcomm.ftcrobotcontroller.opmodes.mortalcombot.custombot;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;

public class CBTeleOp extends OpMode {
    private CBCommon custombot;
    private float motorLeftPower, motorRightPower;
    private float motorLeftPowerLast, motorRightPowerLast;
    private boolean threadActivated = false;

    public CBTeleOp() {

    }

    @Override
    public void init() {
        custombot = new CBCommon(this);
    }

    @Override
    public void loop() {
        updateMotorPower();
        updateTelemetry();
    }

    private void updateMotorPower() {
        motorLeftPower = -gamepad1.left_stick_y;
        motorRightPower = -gamepad1.right_stick_y;

        if (motorLeftPower != motorLeftPowerLast || motorRightPower != motorRightPowerLast) {
            custombot.setChassisPower(motorLeftPower, motorRightPower);
            motorLeftPowerLast = motorLeftPower;
            motorRightPowerLast = motorRightPower;
        }

        if (gamepad1.x) {
            threadActivated = !threadActivated;
            custombot.sendTreadMessage(threadActivated);
        }
    }

    private void updateTelemetry() {
        telemetry.addData("01", "Left Drive: " + motorLeftPower);
        telemetry.addData("02", "Right Drive: " + motorRightPower);
        telemetry.addData("03", "Slow down on chassis/arm: " + gamepad1.right_bumper);
        telemetry.addData("04", "Slow down on claw: " + gamepad2.right_bumper);
    }
}
