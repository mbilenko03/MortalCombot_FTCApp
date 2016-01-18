package com.qualcomm.ftcrobotcontroller.opmodes.mortalcombot.pushbot.activities.PBCompoundTasks;

import com.qualcomm.ftcrobotcontroller.opmodes.mortalcombot.pushbot.PBCommon;
import com.qualcomm.ftcrobotcontroller.opmodes.mortalcombot.pushbot.activities.PBAtomicTasks.ClearDebris;
import com.qualcomm.ftcrobotcontroller.opmodes.mortalcombot.pushbot.activities.PBAtomicTasks.HaltForTime;
import com.qualcomm.ftcrobotcontroller.opmodes.mortalcombot.pushbot.activities.PBAtomicTasks.SetArmPower;
import com.qualcomm.ftcrobotcontroller.opmodes.mortalcombot.pushbot.activities.PBAtomicTasks.SetChassisPower;
import com.qualcomm.ftcrobotcontroller.opmodes.mortalcombot.pushbot.activities.PBAtomicTasks.SetClawPosition;
import com.qualcomm.ftcrobotcontroller.opmodes.mortalcombot.pushbot.activities.PBCompoundTask;
import com.qualcomm.robotcore.robocol.Telemetry;

public class PBAutonomousMasterTask extends PBCompoundTask {
    private final Telemetry telemetry;
    private int loopCount;

    public enum DriveMode {
        red,
        blue
    }

    public PBAutonomousMasterTask(PBCommon pb, String taskComment, Telemetry t, DriveMode mode) {
        super(pb, taskComment);
        telemetry = t;

 /*Testing Task
        addTask(new PBDriveTask(24, 0.25f, pb, "Drive Test"));
        addTask(new PBTurnTask (56.25f, 0.5f, -0.5f, pb, "Turn Test"));
        addTask(new SetChassisPower(pb, 1.0f, "Set Chassis Power"));
        addTask(new SetArmPower(0.5f, 100, pb, "Arm Test"));
        addTask(new SetClawPosition(0.5f, pb, "Claw Test")); */


/* Parallel Task
        PBParallelTask driveAndLiftArm = new PBParallelTask(pb, "Drive and lift arm");
        driveAndLiftArm.addTask(new PBDriveTask(12, 0.5f, pb, "DriveTask 1"));
        driveAndLiftArm.addTask(new SetArmPower(0.5f, 20, pb, "Lift arm"));
        addTask(driveAndLiftArm); */


        if (mode == DriveMode.blue) {
            addTask(new SetArmPower(0.20f, 285, pb, "ArmUp"));
            addTask(new PBDriveTask(20.5, 0.25f, pb, "RobotOut"));
            addTask(new PBTurnTask(56.25f, 0.1f, -0.1f, pb, "Turn Diagonally"));
            addTask(new PBDriveTask(77.33, 0.25f, pb, "Turn Diagonally"));
            addTask(new PBDriveTask(16.5, 0.25f, pb, "Finish Long Distance"));
            addTask(new PBTurnTask(45, 0.1f, -0.1f, pb, "Turn to Rescue Beacon"));
            addTask(new ClearDebris(pb, "Clear Debris"));
            addTask(new PBDriveTask(11, 0.25f, pb, "Drive to Rescue Beacon"));
            addTask(new SetArmPower(-0.08f, 105, pb, "ArmDown"));
            addTask(new HaltForTime(pb, 2, "Wait for Time"));
            addTask(new SetClawPosition(0.0d, pb, "Release Climbers"));
        } else {
            addTask(new SetArmPower(0.20f, 285, pb, "ArmUp"));
            addTask(new PBDriveTask(18, 0.25f, pb, "RobotOut"));
            addTask(new PBTurnTask(56.25f, -0.1f, 0.1f, pb, "Turn Diagonally"));
            addTask(new PBDriveTask(76.33, 0.25f, pb, "Turn Diagonally"));
            addTask(new PBDriveTask(16.5, 0.25f, pb, "Finish Long Distance"));
            addTask(new PBTurnTask(56.25f, -0.1f, 0.1f, pb, "Turn to Rescue Beacon"));
            addTask(new ClearDebris(pb, "Clear Debris"));
            addTask(new PBDriveTask(11, 0.25f, pb, "Drive to Rescue Beacon"));
            addTask(new SetArmPower(-0.08f, 105, pb, "ArmDown"));
            addTask(new HaltForTime(pb, 2, "Wait for Time"));
            addTask(new SetClawPosition(0.0d, pb, "Release Climbers"));
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
