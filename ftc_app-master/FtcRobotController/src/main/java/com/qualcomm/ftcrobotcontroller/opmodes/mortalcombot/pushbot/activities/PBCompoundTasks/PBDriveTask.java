package com.qualcomm.ftcrobotcontroller.opmodes.mortalcombot.pushbot.activities.PBCompoundTasks;

import com.qualcomm.ftcrobotcontroller.opmodes.mortalcombot.pushbot.PBCommon;
import com.qualcomm.ftcrobotcontroller.opmodes.mortalcombot.pushbot.activities.PBAtomicTasks.ReachedEncoderCount;
import com.qualcomm.ftcrobotcontroller.opmodes.mortalcombot.pushbot.activities.PBAtomicTasks.ResetTheEncoders;
import com.qualcomm.ftcrobotcontroller.opmodes.mortalcombot.pushbot.activities.PBAtomicTasks.RunUsingTheEncoders;
import com.qualcomm.ftcrobotcontroller.opmodes.mortalcombot.pushbot.activities.PBCompoundTask;

public class PBDriveTask extends PBCompoundTask {
    public PBDriveTask(double inchesToDrive, float driveSpeed, PBCommon pb, String taskComment) {
        super(pb, taskComment);

        double encoders = convertInchesToEncoders(inchesToDrive);

        addTask(new RunUsingTheEncoders(pb, "Run with encoders"));
        addTask(new ResetTheEncoders(pb, "Reset encoders"));
        addTask(new ReachedEncoderCount(pb, "Check if encoders reached", encoders, driveSpeed));
    }
}
