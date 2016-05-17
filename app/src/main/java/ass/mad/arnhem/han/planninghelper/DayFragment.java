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

import java.io.Console;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Mees on 5/17/2016.
 */
public class DayFragment extends Fragment {

    private TaskAdapter taskAdapter;
    private RecyclerView recyclerView;
    int mNum;

    /**
     * Create a new instance of CountingFragment, providing "num"
     * as an argument.
     */
    static DayFragment newInstance(int num) {
        DayFragment f = new DayFragment();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putInt("num", num + 1);
        f.setArguments(args);

        return f;
    }

    /**
     * When creating, retrieve this instance's number from its arguments.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNum = getArguments() != null ? getArguments().getInt("num") : 1;
    }

    /**
     * The Fragment's UI is just a simple text view showing its
     * instance number.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_day_pager, container, false);
        View tv = v.findViewById(R.id.text);

        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerview_tasks);
        taskAdapter = new TaskAdapter(getContext());
        taskAdapter.addItem(new RecyclerviewTask("Schoenen poetsen", "Dikke Airmaxjes wassen", "11:00", "12:00", null));

        recyclerView.setAdapter(taskAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        int currentDate = mNum;

        //((TextView) tv).setText(currentDate.format());
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //setListAdapter(new ArrayAdapter<String>(getActivity(),
        //        android.R.layout.simple_list_item_1, arrayTest));
    }
}
