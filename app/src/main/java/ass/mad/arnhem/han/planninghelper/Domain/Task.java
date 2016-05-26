package ass.mad.arnhem.han.planninghelper.Domain;

import android.graphics.Bitmap;

/**
 * Created by Mees on 5/17/2016.
 */
public class Task {

    private int taskId;
    private String titel, description;
    private String startTime, endTime;
    private int icon;
    private boolean showGPS;
    private Bitmap picture;
    private boolean taskCompleted;

    public Task(int taskId, String titel, String description, String startTime, String endTime, int icon, boolean showGPS, Bitmap picture) {
        this.taskId = taskId;
        this.titel = titel;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.icon = icon;
        this.showGPS = showGPS;
        this.picture = picture;
        this.taskCompleted = false;
    }

    public int getTaskId() {
        return taskId;
    }

    public String getTitel() {
        return titel;
    }

    public String getDescription() {
        return description;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public int getIcon() {
        return icon;
    }

    public boolean isShowGPS() {
        return showGPS;
    }

    public Bitmap getPicture() {
        return picture;
    }

    public void setPicture(Bitmap picture) {
        this.picture = picture;
    }

    public boolean isTaskCompleted() {
        return taskCompleted;
    }

    public void setTaskCompleted(boolean taskCompleted) {
        this.taskCompleted = taskCompleted;
    }
}
