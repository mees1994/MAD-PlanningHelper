package ass.mad.arnhem.han.planninghelper;

import android.app.Application;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import ass.mad.arnhem.han.planninghelper.Domain.Day;
import ass.mad.arnhem.han.planninghelper.Domain.Task;
import ass.mad.arnhem.han.planninghelper.Domain.Week;

/**
 * Created by Mees on 5/17/2016.
 */
public class PlanningApplication extends Application {

    private static PlanningApplication singleton;
    private Week week;

    public static PlanningApplication getInstance() {
        return singleton;
    }

    public final void onCreate() {
        super.onCreate();
        singleton = this;

        Calendar calendar = Calendar.getInstance();

        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        String weekday = new DateFormatSymbols().getShortWeekdays()[dayOfWeek];

        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new Task(1, "Schoenen poetsen", "De nieuwe Air maxjes moeten weer shinen", calendar.getTime(), calendar.getTime(), null, false));

        ArrayList<Day> days = new ArrayList<>();
        days.add(new Day(calendar.SUNDAY, tasks));
        days.add(new Day(calendar.MONDAY, null));
        days.add(new Day(calendar.TUESDAY, null));
        days.add(new Day(calendar.WEDNESDAY, null));
        days.add(new Day(calendar.THURSDAY, null));
        days.add(new Day(calendar.FRIDAY, null));
        days.add(new Day(calendar.SATURDAY, null));


        week = new Week(1, 2017, days);
    }

    public Week getWeek() {
        return week;
    }
}
