package ass.mad.arnhem.han.planninghelper.Domain;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Mees on 5/17/2016.
 */
public class Task implements Serializable {

    private int taskId;
    private String titel, description;
    private String startTime, endTime;
    private int icon;
    private boolean showGPS;

    public Task(int taskId, String titel, String description, String startTime, String endTime, int icon, boolean showGPS) {
        this.taskId = taskId;
        this.titel = titel;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.icon = icon;
        this.showGPS = showGPS;
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
}
