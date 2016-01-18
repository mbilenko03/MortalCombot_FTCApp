package com.qualcomm.ftcrobotcontroller.opmodes.mortalcombot.pushbot.activities.PBAtomicTasks;

import com.qualcomm.ftcrobotcontroller.opmodes.mortalcombot.pushbot.PBCommon;
import com.qualcomm.ftcrobotcontroller.opmodes.mortalcombot.pushbot.activities.PBTask;
import com.qualcomm.robotcore.util.Range;

    enum TurnType {
        INCREASING,
        DECREASING
    }

public class ClearDebris extends PBTask {

    private double sweeperPosition = 0.0d;
    private TurnType turnType = TurnType.DECREASING;

    public ClearDebris(PBCommon pb, String taskComment) {
        super(pb,taskComment);
    }

    public boolean activate () {

            if (sweeperPosition >= 1 && turnType == TurnType.INCREASING) {
                turnType = TurnType.DECREASING;
            }

            if (sweeperPosition <= 0 && turnType == TurnType.DECREASING) {
                turnType = TurnType.INCREASING;
            }

            if (turnType == TurnType.INCREASING) {
                sweeperPosition += 0.004;
            } else {
                sweeperPosition -= 0.004;
            }

        sweeperPosition = Range.clip(sweeperPosition, 0, 1);
        pushbot.setSweeperPosition(sweeperPosition);

        return sweeperPosition <= 0;
    }
}
