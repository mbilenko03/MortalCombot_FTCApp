package com.qualcomm.ftcrobotcontroller.opmodes.mortalcombot.pushbot.activities.PBCompoundTasks;

import com.qualcomm.ftcrobotcontroller.opmodes.mortalcombot.pushbot.PBCommon;
import com.qualcomm.ftcrobotcontroller.opmodes.mortalcombot.pushbot.activities.PBAtomicTasks.ReachedEncoderCount;
import com.qualcomm.ftcrobotcontroller.opmodes.mortalcombot.pushbot.activities.PBAtomicTasks.ResetTheEncoders;
import com.qualcomm.ftcrobotcontroller.opmodes.mortalcombot.pushbot.activities.PBAtomicTasks.RunUsingTheEncoders;
import com.qualcomm.ftcrobotcontroller.opmodes.mortalcombot.pushbot.activities.PBCompoundTask;

// task to turn robot; use 0 speed on left/right to turn sharply, a value > 0 but less than the other side to turn slowly
public class PBTurnTask extends PBCompoundTask {

    final float WHEEL_BASE = 12.375f; // wheel base in inches

    public PBTurnTask(float turnAngle, float driveSpeedLeft, float driveSpeedRight, PBCommon pb, String taskComment) {
        super(pb, taskComment);

        float turnRadius = WHEEL_BASE * driveSpeedLeft / (driveSpeedLeft - driveSpeedRight);
        double encodersLeftInches = 2 * Math.PI * turnRadius * turnAngle / 360.0;
        double encodersRightInches = 2 * Math.PI * (turnRadius - WHEEL_BASE) * turnAngle / 360.0;

        double encodersLeft = convertInchesToEncoders(encodersLeftInches);
        double encodersRight = convertInchesToEncoders(encodersRightInches);

        addTask(new RunUsingTheEncoders(pb, "Run with encoders"));
        addTask(new ResetTheEncoders(pb, "Reset encoders"));
        addTask(new ReachedEncoderCount(pb, "DriveDistance", encodersLeft, encodersRight, driveSpeedLeft, driveSpeedRight));
    }
}