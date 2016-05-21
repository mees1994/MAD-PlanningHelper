package ass.mad.arnhem.han.planninghelper;

import android.graphics.Bitmap;

/**
 * Created by Mees on 5/17/2016.
 */
public class RecyclerviewTask {

    private int icon;
    private String taskTitle, taskDescription, startTime, endTime;

    public RecyclerviewTask(String taskTitle, String taskDescription, String startTime, String endTime, int icon) {
        this.taskTitle = taskTitle;
        this.taskDescription = taskDescription;
        this.startTime = startTime;
        this.endTime = endTime;
        this.icon = icon;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public int getIcon() {
        return icon;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }
}
