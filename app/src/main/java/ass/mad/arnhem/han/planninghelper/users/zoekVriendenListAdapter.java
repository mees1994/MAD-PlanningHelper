package ass.mad.arnhem.han.planninghelper.users;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import ass.mad.arnhem.han.planninghelper.R;

/**
 * Created by MOBO on 19-5-2016.
 */
public class zoekVriendenListAdapter extends BaseAdapter {

    private ArrayList<HashMap<String, String>> personlist;
    private ArrayList<HashMap<String, String>> arraylist;
    // Declare Variables
    Context mContext;
    LayoutInflater inflater;
    ViewHolder holder;

    private static final String TAG_GEBUIKERSNAAM="Gebruikersnaam";
    private static final String TAG_ID ="ID";

    public zoekVriendenListAdapter(Context context, ArrayList<HashMap<String, String>> personlist) {
        mContext = context;
        inflater = (LayoutInflater)mContext.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        this.personlist = personlist;
        this.arraylist = new ArrayList<>();
        this.arraylist.addAll(personlist);
    }

    private static class ViewHolder {
        private TextView gebruikersnaam;
    }

    @Override
    public int getCount() {
        return personlist.size();
    }

    @Override
    public Object getItem(int position) {
        return personlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.listview_item_zoekvrienden, null);

            // Locate the TextViews in listview_item.xml
            holder.gebruikersnaam = (TextView) view.findViewById(R.id.vriendenZoekenGebruikersnaam);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        holder.gebruikersnaam.setText(personlist.get(position).get(TAG_GEBUIKERSNAAM));

        // Listen for ListView Item Click
        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Log.d("ID",personlist.get(position).get(TAG_ID));
//                // Send single item click data to SingleItemView Class
//                Intent intent = new Intent(mContext, SingleItemView.class);
//                // Pass all data rank
//                intent.putExtra("rank",(worldpopulationlist.get(position).getRank()));
//                // Pass all data country
//                intent.putExtra("country",(worldpopulationlist.get(position).getCountry()));
//                // Pass all data population
//                intent.putExtra("population",(worldpopulationlist.get(position).getPopulation()));
//                // Pass all data flag
//                // Start SingleItemView Class
//                mContext.startActivity(intent);
            }
        });

        return view;
    }

    // Filter Class
    public void filtertext(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        personlist.clear();
        if (charText.length() == 0) {
            personlist.addAll(arraylist);
        }
        else
        {
            for (HashMap<String, String> wp : arraylist)
            {
                if (wp.get(TAG_GEBUIKERSNAAM).toLowerCase(Locale.getDefault()).contains(charText))
                {
                    personlist.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}
