package com.qualcomm.ftcrobotcontroller.opmodes.mortalcombot.pushbot.activities.PBAtomicTasks;

import com.qualcomm.ftcrobotcontroller.opmodes.mortalcombot.pushbot.PBCommon;
import com.qualcomm.ftcrobotcontroller.opmodes.mortalcombot.pushbot.activities.PBTask;

public class ReachedEncoderCount extends PBTask {
    double encoderCountLeft;
    double encoderCountRight;
    float speedLeft;
    float speedRight;

    int actualCountLeft;
    int actualCountRight;

    public ReachedEncoderCount(PBCommon pb, String taskComment, double counter, float driveSpeed) {
        super(pb, taskComment);
        encoderCountLeft = counter;
        encoderCountRight = counter;
        speedLeft = driveSpeed;
        speedRight = driveSpeed;
    }

    public ReachedEncoderCount(PBCommon pb, String taskComment, double counterLeft, double counterRight, float driveSpeedLeft, float driveSpeedRight) {
        super(pb, taskComment);
        encoderCountLeft = Math.abs(counterLeft);
        encoderCountRight = Math.abs(counterRight);
        speedLeft = driveSpeedLeft;
        speedRight = driveSpeedRight;
    }

    protected void updateEncoderCount() {
        actualCountLeft = Math.abs(pushbot.motorLeft.getCurrentPosition());
        actualCountRight = Math.abs(pushbot.motorRight.getCurrentPosition());
    }

    public boolean activate() {
        updateEncoderCount();


        if (actualCountLeft < encoderCountLeft)
            pushbot.motorLeft.setPower(speedLeft);
        else
            pushbot.motorLeft.setPower(0.0f);

        if (actualCountRight < encoderCountRight)
            pushbot.motorRight.setPower(speedRight);
        else
            pushbot.motorRight.setPower(0.0f);

        return actualCountLeft >= encoderCountLeft && actualCountRight >= encoderCountRight;

    }
}
