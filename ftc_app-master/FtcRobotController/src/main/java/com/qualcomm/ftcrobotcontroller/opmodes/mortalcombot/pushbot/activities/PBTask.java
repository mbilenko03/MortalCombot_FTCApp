package com.qualcomm.ftcrobotcontroller.opmodes.mortalcombot.pushbot.activities;

import com.qualcomm.ftccommon.DbgLog;
import com.qualcomm.ftcrobotcontroller.opmodes.mortalcombot.pushbot.PBCommon;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;

public abstract class PBTask {
    protected PBCommon pushbot;
    private String comment;
    protected boolean running = false;
    public boolean completed = false;

    protected DcMotorController.DeviceMode READ_MODE, WRITE_MODE, SWITCHING_TO_WRITE_MODE, SWITCHING_TO_READ_MODE;

    public PBTask(PBCommon pb, String taskComment) {
        pushbot = pb;
        comment = taskComment;

        READ_MODE = DcMotorController.DeviceMode.READ_ONLY;
        WRITE_MODE = DcMotorController.DeviceMode.WRITE_ONLY;
        SWITCHING_TO_WRITE_MODE = DcMotorController.DeviceMode.SWITCHING_TO_WRITE_MODE;
        SWITCHING_TO_READ_MODE = DcMotorController.DeviceMode.SWITCHING_TO_READ_MODE;
    }

    public abstract boolean activate();

    public void finalizeTask()
    {
        completed = true;
        DbgLog.msg("Finished executing task " + getComment());
    }

    public String getComment() {
        if (comment != null)
            return comment;
        else
            return "No comment.";
    }

    public DcMotorController.DeviceMode getMotorDeviceMode(DcMotor motor) {
        return motor.getController().getMotorControllerDeviceMode();
    }

    public boolean encodersZero() {
        return pushbot.motorLeft.getCurrentPosition() == 0 && pushbot.motorRight.getCurrentPosition() == 0;
    }
    // </editor-fold>

}
