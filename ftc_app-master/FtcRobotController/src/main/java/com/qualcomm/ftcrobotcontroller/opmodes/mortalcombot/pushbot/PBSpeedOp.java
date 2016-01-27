package com.qualcomm.ftcrobotcontroller.opmodes.mortalcombot.pushbot;

import com.qualcomm.ftcrobotcontroller.opmodes.mortalcombot.pushbot.activities.PBCompoundTasks.PBAutonomousMasterTask;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

public class PBSpeedOp extends OpMode {
    private PBAutonomousMasterTask masterTask;

    @Override
    public void init() {
        //Makes Instances and Initializes
        PBCommon pushbot = new PBCommon(this);
        masterTask = new PBAutonomousMasterTask(pushbot, "Master Task", telemetry, PBAutonomousMasterTask.DriveMode.speed);
    }

    @Override
    public void loop() {
        if (masterTask.activate())
            masterTask.finalizeTask();
    }
}
