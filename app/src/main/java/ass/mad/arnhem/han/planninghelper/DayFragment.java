package ass.mad.arnhem.han.planninghelper;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
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
public class DayFragment extends ListFragment {
    int mNum;
    int mWeekNr;

    /**
     * Create a new instance of CountingFragment, providing "num"
     * as an argument.
     */
    static DayFragment newInstance(int num, Week week) {
        DayFragment f = new DayFragment();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putInt("num", num + 1);
        args.putInt("weeknr", week.getWeekNr());

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
        mWeekNr = getArguments() != null ? getArguments().getInt("weeknr") : 1;
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

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.WEEK_OF_YEAR, mWeekNr);
        cal.set(Calendar.DAY_OF_WEEK, mNum);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy", Locale.getDefault());
        String currentDate = sdf.format(cal.getTime());

        //int currentDate = mNum;


        ((TextView) tv).setText(currentDate);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String[] arrayTest = {"Peter", "is", "Homo"};

        setListAdapter(new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, arrayTest));
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Log.i("FragmentList", "Item clicked: " + id);
    }
}
