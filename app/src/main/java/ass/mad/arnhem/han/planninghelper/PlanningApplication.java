package ass.mad.arnhem.han.planninghelper;

import android.app.Application;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

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

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        String currentTime = sdf.format(calendar.getTime());
        ArrayList<Task> tasks = new ArrayList<>();

        tasks.add(new Task(1, "Kamer opruimen", "De kamer moet spik en span zijn", currentTime, currentTime, R.drawable.list, false));

        ArrayList<Day> days = new ArrayList<>();
        days.add(new Day(calendar.SUNDAY, new ArrayList<Task>()));
        days.add(new Day(calendar.MONDAY, new ArrayList<Task>()));
        days.add(new Day(calendar.TUESDAY, new ArrayList<Task>()));
        days.add(new Day(calendar.WEDNESDAY, new ArrayList<Task>()));
        days.add(new Day(calendar.THURSDAY, tasks));
        days.add(new Day(calendar.FRIDAY, new ArrayList<Task>()));
        days.add(new Day(calendar.SATURDAY, new ArrayList<Task>()));

        week = new Week(Calendar.WEEK_OF_YEAR, Calendar.YEAR, days);
    }

    public Week getWeek() {
        return week;
    }
}
