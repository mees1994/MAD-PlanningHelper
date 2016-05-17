package ass.mad.arnhem.han.planninghelper;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.text.DateFormatSymbols;
import java.util.ArrayList;

import ass.mad.arnhem.han.planninghelper.Domain.Week;

/**
 * Created by Mees on 5/17/2016.
 */
public class DayPagerAdapter extends FragmentStatePagerAdapter {

    private ArrayList<String> tabs = new ArrayList<>();
    private Week week;

    public DayPagerAdapter(FragmentManager fm) {
        super(fm);

        week = PlanningApplication.getInstance().getWeek();

        for (int i = 0; i < week.getDays().size(); i++) {
            String weekday = new DateFormatSymbols().getShortWeekdays()[week.getDays().get(i).getDayOfTheWeek()];

            tabs.add(weekday);
            //tabs.
        }
    }

    @Override
    public Fragment getItem(int position) {
        return DayFragment.newInstance(position, week);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs.get(position);
    }

    @Override
    public int getCount() {
        return tabs.size();
    }
}