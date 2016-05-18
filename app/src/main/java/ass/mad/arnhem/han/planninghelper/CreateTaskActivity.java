package ass.mad.arnhem.han.planninghelper;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;

import ass.mad.arnhem.han.planninghelper.Domain.Task;
import ass.mad.arnhem.han.planninghelper.Domain.Week;

public class CreateTaskActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnStartTimePicker, btnEndTimePicker, btnSave;
    EditText txtStartTime, txtEndTime, txtTaskTitle, txtTaskDescription;
    private int mHour, mMinute, weekNr, dayNr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Create Task");

        btnStartTimePicker=(Button)findViewById(R.id.btn_start_time);
        btnEndTimePicker=(Button)findViewById(R.id.btn_end_time);
        txtStartTime=(EditText)findViewById(R.id.create_task_start_time_text);
        txtEndTime=(EditText)findViewById(R.id.create_task_end_time_text);
        txtTaskTitle=(EditText)findViewById(R.id.create_task_title_text);
        txtTaskDescription=(EditText)findViewById(R.id.create_task_description_text);
        btnSave = (Button) findViewById(R.id.create_task_save_btn);

        btnStartTimePicker.setOnClickListener(this);
        btnEndTimePicker.setOnClickListener(this);
        btnSave.setOnClickListener(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            weekNr = extras.getInt("weekNr");
            dayNr = extras.getInt("dayNr");
        }
    }

    @Override
    public void onClick(View v) {

        if (v == btnStartTimePicker) {

            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            txtStartTime.setText(hourOfDay + ":" + minute);
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }
        if (v == btnEndTimePicker) {

            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            txtEndTime.setText(hourOfDay + ":" + minute);
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }
        if (v == btnSave) {
            Week week = PlanningApplication.getInstance().getWeek();
            Log.d("dayNr", "" + dayNr);
            week.getDays().get(dayNr+1).addTask(new Task(1,
                    txtTaskTitle.getText().toString(),
                    txtTaskDescription.getText().toString(),
                    txtStartTime.getText().toString(),
                    txtEndTime.getText().toString(),
                    null, false));

            finish();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == 16908332) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
