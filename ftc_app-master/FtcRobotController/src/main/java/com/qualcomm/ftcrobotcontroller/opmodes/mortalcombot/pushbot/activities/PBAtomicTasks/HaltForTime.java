package com.qualcomm.ftcrobotcontroller.opmodes.mortalcombot.pushbot.activities.PBAtomicTasks;

import com.qualcomm.ftcrobotcontroller.opmodes.mortalcombot.pushbot.PBCommon;
import com.qualcomm.ftcrobotcontroller.opmodes.mortalcombot.pushbot.activities.PBTask;
import com.qualcomm.robotcore.util.ElapsedTime;

public class HaltForTime extends PBTask {
    private ElapsedTime elapsedTime;
    private double secondsToHalt;
    private boolean firstRun = true;

    public HaltForTime(PBCommon pb, double secondsToHalt, String taskComment) {
        super(pb, taskComment);
        this.secondsToHalt = secondsToHalt;
    }

    @Override
    public boolean activate() {
        if (firstRun) {
            elapsedTime = new ElapsedTime(System.nanoTime());
            pushbot.setChassisPower(0.0f, 0.0f);
            firstRun = false;
        }

        return elapsedTime.time() >= secondsToHalt;
    }
}
