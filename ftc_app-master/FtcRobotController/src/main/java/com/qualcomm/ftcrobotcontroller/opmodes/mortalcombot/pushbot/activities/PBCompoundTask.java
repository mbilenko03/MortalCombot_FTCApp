package com.qualcomm.ftcrobotcontroller.opmodes.mortalcombot.pushbot.activities;
import com.qualcomm.ftcrobotcontroller.opmodes.mortalcombot.pushbot.PBCommon;

import java.util.ArrayList;
import java.util.List;

public class PBCompoundTask extends PBTask {
    private int currentTask = 0;
    protected List<PBTask> tasks = new ArrayList<PBTask>();


    public PBCompoundTask(PBCommon pb, String taskComment) {
        super(pb, taskComment);
    }

    public boolean activate() {
        if (currentTask >= tasks.size()) {
            return true;
        }

        PBTask current = tasks.get(currentTask);
        if (current.activate()) {
            current.finalizeTask();
            currentTask++;
        }

        return false;
    }

    protected void addTask(PBTask task) {
        tasks.add(task);
    }

    @Override
    public String getComment(){
        String comment = super.getComment();
        comment = comment + "." + getCurrentTaskComment();

        return comment;
    }

    private String getCurrentTaskComment(){
        if (currentTask < tasks.size()) {
            return tasks.get(currentTask).getComment();
        }

        return "none";
    }

    final double WHEEL_DIAMETER = 4;
    final double ENCODERS_PER_REV = 1120;
    final double GEAR_RATIO = 1;

    protected double convertInchesToEncoders(double inchesToDrive) {
        double rev = inchesToDrive / (WHEEL_DIAMETER * Math.PI);
        return rev * ENCODERS_PER_REV / GEAR_RATIO;
    }
}
