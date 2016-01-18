package com.qualcomm.ftcrobotcontroller.opmodes.mortalcombot.pushbot.activities.PBCompoundTasks;

import com.qualcomm.ftcrobotcontroller.opmodes.mortalcombot.pushbot.PBCommon;
import com.qualcomm.ftcrobotcontroller.opmodes.mortalcombot.pushbot.activities.PBTask;

import java.util.ArrayList;
import java.util.List;

public class PBParallelTask extends PBTask {
    protected List<PBTask> tasks = new ArrayList<PBTask>();

    public PBParallelTask(PBCommon pb, String taskComment) {
        super(pb, taskComment);
    }

    public boolean activate() {
        boolean allFinished = true;

        for (int i = 0; i < tasks.size(); i++) {
            PBTask task = tasks.get(i);
            if (!task.completed) {
                if (task.activate())
                    task.finalizeTask();
                else
                    allFinished = false;
            }
        }
        return allFinished;
    }

    public void addTask(PBTask task) {
        tasks.add(task);
    }

    @Override
    public String getComment(){
        String comment = super.getComment();
        comment = comment + "." + getCurrentTaskComment();

        return comment;
    }

    private String getCurrentTaskComment() {

        String result = "(";

        for (int i = 0; i < tasks.size(); i++) {
            PBTask task = tasks.get(i);
            if (!task.completed) {
                if (result.length() > 1)
                    result += ",";
                result += task.getComment();
            }
        }

        result += ")";

        return result;
    }

}