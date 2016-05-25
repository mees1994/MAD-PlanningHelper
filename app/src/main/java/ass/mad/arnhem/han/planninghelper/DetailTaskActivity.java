package ass.mad.arnhem.han.planninghelper;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import ass.mad.arnhem.han.planninghelper.Domain.Task;
import ass.mad.arnhem.han.planninghelper.Domain.Week;

public class DetailTaskActivity extends AppCompatActivity {

    private int dayNr, taskPos;
    private TextView taskTitle, taskDescription, taskStartTime, taskEndTime;
    private ImageView taskIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_task);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Week week = PlanningApplication.getInstance().getWeek();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            taskPos = extras.getInt("taskNr");
            dayNr = extras.getInt("dayNr");
        }

        taskTitle = (TextView) findViewById(R.id.detailsTitle);
        taskDescription = (TextView) findViewById(R.id.detailsDescription);
        taskStartTime = (TextView) findViewById(R.id.detailsStartTime);
        taskEndTime = (TextView) findViewById(R.id.detailsEndTime);
        taskIcon = (ImageView) findViewById(R.id.detailIcon);

        Task task = week.getDays().get(dayNr).getTasks().get(taskPos);

        taskTitle.setText(task.getTitel());
        taskDescription.setText(task.getDescription());
        taskStartTime.setText(task.getStartTime());
        taskEndTime.setText(task.getEndTime());
        taskIcon.setImageDrawable(getDrawable(task.getIcon()));

    }

}
