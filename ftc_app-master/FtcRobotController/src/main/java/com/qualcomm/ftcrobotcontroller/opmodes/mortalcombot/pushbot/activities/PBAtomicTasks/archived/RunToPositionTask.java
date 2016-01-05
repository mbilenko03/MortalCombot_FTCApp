package com.qualcomm.ftcrobotcontroller.opmodes.mortalcombot.pushbot.activities.PBAtomicTasks.archived;

import com.qualcomm.ftcrobotcontroller.opmodes.mortalcombot.pushbot.PBCommon;
import com.qualcomm.ftcrobotcontroller.opmodes.mortalcombot.pushbot.activities.PBTask;

/**
 * Created by Michael on 11/14/2015.
 */
public class RunToPositionTask extends PBTask
{
    private final double motorPower;
    private final int encodersCount;

    public RunToPositionTask(PBCommon pb, String taskComment, int count, double power) {
        super(pb, taskComment);

        motorPower = power;
        encodersCount = count;
    }

    @Override
    public boolean activate() {
        if (!running) {
            pushbot.motorLeft.setTargetPosition(encodersCount);
            pushbot.motorRight.setTargetPosition(encodersCount);

            pushbot.motorLeft.setMode(pushbot.RUN_TO_POSITION);
            pushbot.motorRight.setMode(pushbot.RUN_TO_POSITION);

            pushbot.motorLeft.setPower(motorPower);
            pushbot.motorRight.setPower(motorPower);

            running = true;

            return false;
        }

        return pushbot.motorLeft.getMode() != pushbot.RUN_TO_POSITION &&
                pushbot.motorRight.getMode() != pushbot.RUN_TO_POSITION;
    }
}
