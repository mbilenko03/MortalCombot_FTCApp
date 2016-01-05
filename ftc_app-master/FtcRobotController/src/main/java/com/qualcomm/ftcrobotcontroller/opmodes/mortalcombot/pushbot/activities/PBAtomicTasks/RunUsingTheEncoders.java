package com.qualcomm.ftcrobotcontroller.opmodes.mortalcombot.pushbot.activities.PBAtomicTasks;

import com.qualcomm.ftcrobotcontroller.opmodes.mortalcombot.pushbot.PBCommon;
import com.qualcomm.ftcrobotcontroller.opmodes.mortalcombot.pushbot.activities.PBTask;

public class RunUsingTheEncoders extends PBTask {

    public RunUsingTheEncoders(PBCommon pb, String taskComment) {
        super(pb, taskComment);
    }

    protected boolean runUsingEncoders() {
        if (!running) {
            pushbot.motorLeft.setMode(pushbot.RUN_USING_ENCODERS);
            pushbot.motorRight.setMode(pushbot.RUN_USING_ENCODERS);

            running = true;
        }

        return pushbot.motorLeft.getMode() == pushbot.RUN_USING_ENCODERS &&
                pushbot.motorRight.getMode() == pushbot.RUN_USING_ENCODERS;
    }

    public boolean activate() {
        return runUsingEncoders();
    }

}
