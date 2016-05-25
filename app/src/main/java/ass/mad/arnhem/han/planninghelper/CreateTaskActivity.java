package ass.mad.arnhem.han.planninghelper;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.Calendar;

import ass.mad.arnhem.han.planninghelper.Domain.Task;
import ass.mad.arnhem.han.planninghelper.Domain.Week;

public class CreateTaskActivity extends AppCompatActivity implements View.OnClickListener, ItemSelectedListener {

    private Button btnStartTimePicker, btnEndTimePicker, btnSave, btnSelectIcon;
    private EditText txtStartTime, txtEndTime, txtTaskTitle, txtTaskDescription;
    private ImageView iconImage;
    private MaterialDialog iconDialog;
    private int mHour, mMinute, weekNr, dayNr, currentIcon;
    final int[] icon = {R.drawable.list, R.drawable.book, R.drawable.bowl, R.drawable.dog, R.drawable.football, R.drawable.hospital, R.drawable.tools, R.drawable.vacuumcleaner, R.drawable.washingmachine, R.drawable.weight};

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
        iconImage = (ImageView) findViewById(R.id.create_task_icon);
        btnSave = (Button) findViewById(R.id.create_task_save_btn);
        btnSelectIcon = (Button) findViewById(R.id.btn_select_icon);

        btnStartTimePicker.setOnClickListener(this);
        btnEndTimePicker.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        btnSelectIcon.setOnClickListener(this);

        currentIcon = R.drawable.list;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            weekNr = extras.getInt("weekNr");
            dayNr = extras.getInt("dayNr");
        }
    }

    @Override
    public void onClick(View v) {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

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
                            txtStartTime.setText(needsLeadingZero(hourOfDay) + hourOfDay + ":" + needsLeadingZero(minute) + minute);
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

                            txtEndTime.setText(needsLeadingZero(hourOfDay) + hourOfDay + ":" + needsLeadingZero(minute) + minute);
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }

        if (v == btnSave) {
            if (isCorrectlyFilledIn()) {
                Week week = PlanningApplication.getInstance().getWeek();
                week.getDays().get(dayNr).addTask(new Task(1,
                        txtTaskTitle.getText().toString(),
                        txtTaskDescription.getText().toString(),
                        txtStartTime.getText().toString(),
                        txtEndTime.getText().toString(),
                        currentIcon, false, null));

                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("dayNr", dayNr);

                setResult(RESULT_OK, intent);
                finish();
            } else {
                TextView errorText = (TextView) findViewById(R.id.create_task_error_text);
                errorText.setVisibility(View.VISIBLE);
            }
        }

        if (v == btnSelectIcon) {
            iconDialog = new MaterialDialog.Builder(this)
                    .title("Select icon")
                    .adapter(new CreateTaskIconAdapter(getResources().getStringArray(R.array.icon_list), icon, this, this),
                            null)
                            .show();
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

    @Override
    public void onIconSelected(int pos) {
        currentIcon = icon[pos];
        iconDialog.dismiss();
        iconImage.setImageDrawable(getDrawable(icon[pos]));
    }

    private boolean isCorrectlyFilledIn() {
        if (!TextUtils.isEmpty(txtTaskTitle.getText()) && !TextUtils.isEmpty(txtTaskDescription.getText()) &&
                !TextUtils.isEmpty(txtStartTime.getText()) && !TextUtils.isEmpty(txtEndTime.getText())) {
            return true;
        }
        return false;
    }

    private String needsLeadingZero(int value){
        String leadingZero = "";
        if (value <= 9){
            leadingZero = "0";
        }
        return leadingZero;
    }
}
