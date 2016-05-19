package ass.mad.arnhem.han.planninghelper;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.util.SparseArray;
import android.view.ViewGroup;

import java.text.DateFormatSymbols;
import java.util.ArrayList;

import ass.mad.arnhem.han.planninghelper.Domain.Week;

/**
 * Created by Mees on 5/17/2016.
 */
public class DayPagerAdapter extends FragmentPagerAdapter {

    SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();
    private ArrayList<String> tabs = new ArrayList<>();
    private Week week;

    public DayPagerAdapter(FragmentManager fm) {
        super(fm);

        week = PlanningApplication.getInstance().getWeek();

        for (int i = 0; i < week.getDays().size(); i++) {
            String weekday = new DateFormatSymbols().getShortWeekdays()[week.getDays().get(i).getDayOfTheWeek()];

            tabs.add(weekday);
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

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        registeredFragments.put(position, fragment);
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        registeredFragments.remove(position);
        super.destroyItem(container, position, object);
    }

    public Fragment getRegisteredFragment(int position) {
        return registeredFragments.get(position);
    }
}