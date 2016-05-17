package ass.mad.arnhem.han.planninghelper.Domain;

import java.util.ArrayList;

/**
 * Created by Mees on 5/17/2016.
 */
public class Week {

    private int weekNr;
    private int yearNr;
    private ArrayList<Day> days;

    public Week(int weekNr, int yearNr, ArrayList<Day> days) {
        this.weekNr = weekNr;
        this.yearNr = yearNr;
        this.days = days;
    }

    public ArrayList<Day> getDays() {
        return days;
    }
}
