package ass.mad.arnhem.han.planninghelper;

import android.graphics.Bitmap;

/**
 * Created by Mees on 5/17/2016.
 */
public class RecyclerviewTask {

    private Bitmap icon;
    private String taskTitle, taskDescription, startTime, endTime;

    public RecyclerviewTask(String taskTitle, String taskDescription, String startTime, String endTime, Bitmap icon) {
        this.taskTitle = taskTitle;
        this.taskDescription = taskDescription;
        this.startTime = startTime;
        this.endTime = endTime;
        this.icon = icon;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public Bitmap getIcon() {
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
