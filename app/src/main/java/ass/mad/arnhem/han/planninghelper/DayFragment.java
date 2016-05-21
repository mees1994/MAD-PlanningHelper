package ass.mad.arnhem.han.planninghelper;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
public class DayFragment extends Fragment {

    int mNum;
    int mWeekNr;

    private RecyclerView recyclerView;
    private TaskAdapter taskAdapter;
    private View dateTextView;
    private FloatingActionButton createTaskBtn;

    Week week;
    Day currentDay;
    ArrayList<Task> tasks;

    static DayFragment newInstance(int num, Week week) {
        DayFragment f = new DayFragment();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putInt("num", num);
        args.putInt("weeknr", week.getWeekNr());

        f.setArguments(args);

        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mNum = getArguments() != null ? getArguments().getInt("num") : 1;
        mWeekNr = getArguments() != null ? getArguments().getInt("weeknr") : 1;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_day_pager, container, false);
        initView(v);

        taskAdapter.dayNumber = mNum;
        for (Task task: tasks) {
            taskAdapter.addItem(new RecyclerviewTask(task.getTitel(), task.getDescription(), task.getStartTime().toString(), task.getEndTime().toString(), null));
        }
        recyclerView.setAdapter(taskAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        ((TextView) dateTextView).setText(calculateDate());
        createTaskBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CreateTaskActivity.class);
                intent.putExtra("weekNr", mWeekNr);
                intent.putExtra("dayNr", mNum);
                getActivity().startActivityForResult(intent, 1);
            }
        });

        return v;
    }

    private void initView(View v) {
        dateTextView = v.findViewById(R.id.text);
        createTaskBtn = (FloatingActionButton) v.findViewById(R.id.create_task_fab);// v.findViewById(R.id.create_task_fab);
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerview_tasks);
        taskAdapter = new TaskAdapter(getContext());

        week = PlanningApplication.getInstance().getWeek();
        currentDay = week.getDays().get(mNum);
        tasks = currentDay.getTasks();
    }

    private String calculateDate() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.WEEK_OF_YEAR, mWeekNr);
        cal.set(Calendar.DAY_OF_WEEK, mNum + 1);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy", Locale.getDefault());
        return sdf.format(cal.getTime());
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void refreshRecyclerview() {
        week = PlanningApplication.getInstance().getWeek();
        currentDay = week.getDays().get(mNum);
        tasks = currentDay.getTasks();

        taskAdapter.clearItems();

        for (Task task: tasks) {
            taskAdapter.addItem(new RecyclerviewTask(task.getTitel(), task.getDescription(), task.getStartTime().toString(), task.getEndTime().toString(), null));
        }
    }


}
