package ass.mad.arnhem.han.planninghelper;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import ass.mad.arnhem.han.planninghelper.Domain.Task;
import ass.mad.arnhem.han.planninghelper.Domain.Week;

public class DetailTaskActivity extends AppCompatActivity {

    private int dayNr, taskPos;

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


        getSupportActionBar().setTitle(week.getDays().get(dayNr).getTasks().get(taskPos).getTitel());


    }

}
