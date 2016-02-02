package com.qualcomm.ftcrobotcontroller.opmodes.mortalcombot.pushbot.activities.PBCompoundTasks;

import com.qualcomm.ftcrobotcontroller.opmodes.mortalcombot.pushbot.PBCommon;
import com.qualcomm.ftcrobotcontroller.opmodes.mortalcombot.pushbot.activities.PBAtomicTasks.HaltForTime;
import com.qualcomm.ftcrobotcontroller.opmodes.mortalcombot.pushbot.activities.PBAtomicTasks.SetArmPower;
import com.qualcomm.ftcrobotcontroller.opmodes.mortalcombot.pushbot.activities.PBAtomicTasks.SetClawPosition;
import com.qualcomm.ftcrobotcontroller.opmodes.mortalcombot.pushbot.activities.PBCompoundTask;
import com.qualcomm.robotcore.robocol.Telemetry;

public class PBAutonomousMasterTask extends PBCompoundTask {
    private final Telemetry telemetry;
    private int loopCount;

    public enum DriveMode {
        red,
        blue,
        speed
    }

    public PBAutonomousMasterTask(PBCommon pb, String taskComment, Telemetry t, DriveMode mode) {
        super(pb, taskComment);
        telemetry = t;

        pushbot.setClawPosition(1);

        if (mode == DriveMode.blue) {
            addTask(new SetArmPower(0.20f, 285, pb, "ArmUp"));
            addTask(new PBDriveTask(20.5, 0.25f, pb, "RobotOut"));
            addTask(new PBTurnTask(56.25f, 0.1f, -0.1f, pb, "Turn Diagonally"));
            addTask(new PBDriveTask(93.33, 0.25f, pb, "Drive Long Distance"));
            addTask(new PBTurnTask(56.25f, 0.1f, -0.1f, pb, "Turn to Rescue Beacon"));
            addTask(new PBDriveTask(11, 0.25f, pb, "Drive to Rescue Beacon"));
            addTask(new SetArmPower(-0.08f, 105, pb, "ArmDown"));
            addTask(new HaltForTime(pb, 1, "Wait for Time"));
            addTask(new SetClawPosition(0.2d, pb, "Release Climbers"));
        }

        else if (mode == DriveMode.red) {
            addTask(new SetArmPower(0.20f, 285, pb, "ArmUp"));
            addTask(new PBDriveTask(19.25, 0.25f, pb, "RobotOut"));
            addTask(new PBTurnTask(56.25f, -0.1f, 0.1f, pb, "Turn Diagonally"));
            addTask(new PBDriveTask(93.33, 0.25f, pb, "Drive Long Distance"));
            addTask(new PBTurnTask(45, -0.1f, 0.1f, pb, "Turn to Rescue Beacon"));
            addTask(new PBDriveTask(11, 0.25f, pb, "Drive to Rescue Beacon"));
            addTask(new SetArmPower(-0.08f, 105, pb, "ArmDown"));
            addTask(new HaltForTime(pb, 1, "Wait for Time"));
            addTask(new SetClawPosition(0.2d, pb, "Release Climbers"));
        }
        else if (mode == DriveMode.speed) {
            PBParallelTask driveAndLiftArm = new PBParallelTask(pb, "Drive and lift arm");
            driveAndLiftArm.addTask(new PBDriveTask(110, 1.0f, pb, "DriveTask 1"));
            driveAndLiftArm.addTask(new SetArmPower(0.25f, 150, pb, "Lift arm"));
            addTask(driveAndLiftArm);
            addTask(new SetClawPosition(0, pb, "Release Climbers"));
        }


       loopCount = 0;
    }

    @Override
    public boolean activate()
    {
        loopCount++;
        Boolean result = super.activate();

        if (loopCount % 17 == 0) {
            updateTelemetry();
            loopCount = 0;
        }

        return result;
    }

    private void updateTelemetry() {
        // Send telemetry data to the driver station
        telemetry.addData("Task", getComment());
        telemetry.addData("LeftMotorPosition", pushbot.motorLeft.getCurrentPosition());
        telemetry.addData("LeftMotorPower", -pushbot.motorLeft.getPower());
        telemetry.addData("RightMotorPosition", pushbot.motorRight.getCurrentPosition());
        telemetry.addData("RightMotorPower", pushbot.motorRight.getPower());
        telemetry.addData("ClawPositionLeft", pushbot.servoClawLeft.getPosition());
        telemetry.addData("ClawPositionRight", pushbot.servoClawRight.getPosition());
    }
}
