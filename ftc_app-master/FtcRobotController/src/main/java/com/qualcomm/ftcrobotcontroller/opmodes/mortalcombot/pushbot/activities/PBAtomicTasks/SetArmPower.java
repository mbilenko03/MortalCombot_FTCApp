package com.qualcomm.ftcrobotcontroller.opmodes.mortalcombot.pushbot.activities.PBAtomicTasks;

import com.qualcomm.ftcrobotcontroller.opmodes.mortalcombot.pushbot.PBCommon;
import com.qualcomm.ftcrobotcontroller.opmodes.mortalcombot.pushbot.activities.PBTask;

public class SetArmPower extends PBTask {
    float power;
    int cycleDuration;
    int cycleCount;

    public SetArmPower(float armPower, int cycleDuration, PBCommon pb, String taskComment) {
        super(pb, taskComment);
        power = armPower;
        this.cycleDuration = cycleDuration;
    }

    public boolean activate() {
        if (!running) {
            cycleCount = 0;
            running = true;
        }

        pushbot.motorArm.setPower(power);
        cycleCount++;

        if (cycleCount >= cycleDuration) {
            pushbot.motorArm.setPower(0);
            return true;
        }

        return false;
    }
}
