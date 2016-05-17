package ass.mad.arnhem.han.planninghelper.Domain;

import android.graphics.Bitmap;

import java.util.Date;

/**
 * Created by Mees on 5/17/2016.
 */
public class Task {

    private int taskId;
    private String titel, description;
    private Date startTime, endTime;
    private Bitmap icon;
    private boolean showGPS;

    public Task(int taskId, String titel, String description, Date startTime, Date endTime, Bitmap icon, boolean showGPS) {
        this.taskId = taskId;
        this.titel = titel;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.icon = icon;
        this.showGPS = showGPS;
    }

}
