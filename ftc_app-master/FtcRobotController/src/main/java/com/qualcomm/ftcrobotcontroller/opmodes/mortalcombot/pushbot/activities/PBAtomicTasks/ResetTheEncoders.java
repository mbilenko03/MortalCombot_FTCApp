package com.qualcomm.ftcrobotcontroller.opmodes.mortalcombot.pushbot.activities.PBAtomicTasks;

import com.qualcomm.ftcrobotcontroller.opmodes.mortalcombot.pushbot.PBCommon;
import com.qualcomm.ftcrobotcontroller.opmodes.mortalcombot.pushbot.activities.PBTask;

public class ResetTheEncoders extends PBTask {

    public ResetTheEncoders(PBCommon pb, String taskComment) {
        super(pb, taskComment);
    }

    protected void resetDriverEncoders() {
        pushbot.motorLeft.setChannelMode(pushbot.RESET_ENCODERS);
        pushbot.motorRight.setChannelMode(pushbot.RESET_ENCODERS);
    }

    public boolean encodersZero() {
        return pushbot.motorLeft.getCurrentPosition() == 0 && pushbot.motorRight.getCurrentPosition() == 0;
     }

    public boolean activate() {
        if (!running) {
            resetDriverEncoders();
            running = true;
        }

        if (running)
            return encodersZero();
        else
            return false;
    }

}
