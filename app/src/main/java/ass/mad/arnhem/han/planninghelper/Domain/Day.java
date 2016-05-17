package ass.mad.arnhem.han.planninghelper.Domain;

import java.util.ArrayList;

/**
 * Created by Mees on 5/17/2016.
 */
public class Day {

    private int dayOfTheWeek;
    private ArrayList<Task> tasks;

    public Day(int dayOfTheWeek, ArrayList<Task> tasks) {
        this.dayOfTheWeek = dayOfTheWeek;
        this.tasks = tasks;
    }

    public int getDayOfTheWeek() {
        return dayOfTheWeek;
    }
}
