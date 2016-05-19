package ass.mad.arnhem.han.planninghelper.users;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ass.mad.arnhem.han.planninghelper.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class userActivityFragment extends Fragment {

    public userActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_overview, container, false);
    }
}
