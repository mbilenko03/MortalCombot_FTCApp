package com.qualcomm.ftcrobotcontroller.opmodes.mortalcombot.pushbot.activities.PBAtomicTasks.archived;

import com.qualcomm.ftcrobotcontroller.opmodes.mortalcombot.pushbot.PBCommon;
import com.qualcomm.ftcrobotcontroller.opmodes.mortalcombot.pushbot.activities.PBAtomicTasks.SetChassisPower;

/**
 * Created by konemac on 12/11/15.
 */
public class HaltTask extends SetChassisPower {
    public HaltTask(PBCommon pb) {
        super(pb, 0.0f, "Stop Motors");
    }
}
