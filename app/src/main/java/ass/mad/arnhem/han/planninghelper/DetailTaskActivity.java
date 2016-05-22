package ass.mad.arnhem.han.planninghelper;

import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import ass.mad.arnhem.han.planninghelper.Domain.Task;
import ass.mad.arnhem.han.planninghelper.Domain.Week;

public class DetailTaskActivity extends AppCompatActivity {

    private int dayNr, taskPos, weekNr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_task);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Week week = PlanningApplication.getInstance().getWeek();
        weekNr = week.getWeekNr();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            taskPos = extras.getInt("taskNr");
            dayNr = extras.getInt("dayNr");
        }

        Task task = week.getDays().get(dayNr).getTasks().get(taskPos);
        getSupportActionBar().setTitle(task.getTitel());
        TextView title = (TextView) findViewById(R.id.detailsTitle);
        TextView description = (TextView) findViewById(R.id.detailsDescription);
        TextView date = (TextView) findViewById(R.id.detailsDate);
        TextView startTime = (TextView) findViewById(R.id.detailsStartTime);
        TextView endTime = (TextView) findViewById(R.id.detailsEndTime);
        TextView duration = (TextView) findViewById(R.id.detailsDuration);
        ImageView icon = (ImageView) findViewById(R.id.detailsIcon);

        title.setText(task.getTitel());
        description.setText(task.getDescription());
        date.setText(calculateDate());
        startTime.setText(task.getStartTime());
        endTime.setText(task.getEndTime());
        icon.setImageDrawable(getDrawable(task.getIcon()));

        Date dStartTime = new Date();
        Date dEndTime = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm");
        try {
            dStartTime = dateFormat.parse(task.getStartTime());
            dEndTime = dateFormat.parse(task.getEndTime());
        } catch (ParseException e) {
            Log.e("DetailsTaskActivity", "Error while paring date" + e);
        }
        long durationAmount = dEndTime.getTime() - dStartTime.getTime();
        duration.setText(durationAmount + "");

    }

    private String calculateDate() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.WEEK_OF_YEAR, weekNr);
        cal.set(Calendar.DAY_OF_WEEK, dayNr + 1);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy", Locale.getDefault());
        return sdf.format(cal.getTime());
    }
}
