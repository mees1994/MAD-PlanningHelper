package ass.mad.arnhem.han.planninghelper;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import ass.mad.arnhem.han.planninghelper.Domain.Week;

/**
 * Created by Mees on 5/17/2016.
 */
public class DayFragment extends Fragment {

    int mNum;
    int mWeekNr;
    private RecyclerView recyclerView;
    private TaskAdapter taskAdapter;

    static DayFragment newInstance(int num, Week week) {
        DayFragment f = new DayFragment();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putInt("num", num + 1);
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
        View tv = v.findViewById(R.id.text);

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.WEEK_OF_YEAR, mWeekNr);
        cal.set(Calendar.DAY_OF_WEEK, mNum);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy", Locale.getDefault());
        String currentDate = sdf.format(cal.getTime());

        //int currentDate = mNum;

        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerview_tasks);
        taskAdapter = new TaskAdapter(getContext());
        taskAdapter.addItem(new RecyclerviewTask("Schoenen poetsen", "Dikke Airmaxjes wassen", "11:00", "12:00", null));

        recyclerView.setAdapter(taskAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        ((TextView) tv).setText(currentDate);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
