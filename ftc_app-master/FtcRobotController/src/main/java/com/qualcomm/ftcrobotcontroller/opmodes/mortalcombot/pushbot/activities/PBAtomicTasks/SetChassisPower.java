package com.qualcomm.ftcrobotcontroller.opmodes.mortalcombot.pushbot.activities.PBAtomicTasks;

import com.qualcomm.ftcrobotcontroller.opmodes.mortalcombot.pushbot.PBCommon;
import com.qualcomm.ftcrobotcontroller.opmodes.mortalcombot.pushbot.activities.PBTask;

public class SetChassisPower extends PBTask {
    float speed;

    public SetChassisPower(PBCommon pb, float driveSpeed, String taskComment) {
        super(pb, taskComment);
        speed = driveSpeed;
    }

    public boolean activate() {
        pushbot.motorLeft.setPower(speed);
        pushbot.motorRight.setPower(speed);
        return true;
    }
}
