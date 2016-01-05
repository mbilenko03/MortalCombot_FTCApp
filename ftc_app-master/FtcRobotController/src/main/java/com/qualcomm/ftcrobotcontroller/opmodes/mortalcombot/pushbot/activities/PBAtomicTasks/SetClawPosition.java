package com.qualcomm.ftcrobotcontroller.opmodes.mortalcombot.pushbot.activities.PBAtomicTasks;

import com.qualcomm.ftcrobotcontroller.opmodes.mortalcombot.pushbot.PBCommon;
import com.qualcomm.ftcrobotcontroller.opmodes.mortalcombot.pushbot.activities.PBTask;

public class SetClawPosition extends PBTask {
    double position;

    public SetClawPosition(double clawPosition, PBCommon pb, String taskComment) {
        super(pb, taskComment);
        position = clawPosition;

    }

    public boolean activate() {
        if (!running){
                pushbot.setClawPosition(position);
                running = true;
            }

            double leftPosition = 1 - position;
            double rightPosition = position;
            double currentLeftClawPosition = pushbot.servoClawLeft.getPosition();
            double currentRightClawPosition = pushbot.servoClawRight.getPosition();

        return currentLeftClawPosition == leftPosition && currentRightClawPosition == rightPosition;
    }
}