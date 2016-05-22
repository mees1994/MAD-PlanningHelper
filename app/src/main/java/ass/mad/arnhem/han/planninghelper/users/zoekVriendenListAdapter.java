package ass.mad.arnhem.han.planninghelper.users;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import ass.mad.arnhem.han.planninghelper.R;

/**
 * Created by MOBO on 19-5-2016.
 */
public class zoekVriendenListAdapter extends BaseAdapter {

    // Progress Dialog
    private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();
    private static String url_create_vriendlink = "http://www.peterotten.com/AndroidProject/create_vriendlink.php";

    private ArrayList<HashMap<String, String>> personlist;
    private ArrayList<HashMap<String, String>> arraylist;
    // Declare Variables
    Context mContext;
    LayoutInflater inflater;
    ViewHolder holder;

    String UsernameID = "0";

    private static final String TAG_GEBUIKERSNAAM="Gebruikersnaam";
    private static final String TAG_ID ="ID";
    private static final String TAG_SUCCESS = "success";

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
                String ID = personlist.get(position).get(TAG_ID);
                Log.d("ID",personlist.get(position).get(TAG_ID));
                new CreateNewFriendLink().execute(ID);
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

    /**
     * Background Async Task to Create new product
     * */
    class CreateNewFriendLink extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        /**
         * Creating product
         * */
        protected String doInBackground(String... args) {
            String VriendID = args[0];
            SharedPreferences sharedPref = mContext.getSharedPreferences("PlanningHelper", Context.MODE_PRIVATE);
            UsernameID = sharedPref.getString("usernameid", "555");
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("VriendID", VriendID));
            params.add(new BasicNameValuePair("UsernameID", UsernameID));
            Log.d("ID",UsernameID);

            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(url_create_vriendlink,
                    "POST", params);

            // check log cat for response
            Log.d("Create Response", json.toString());

            // check for success tag
            try {
                int success = json.getInt(TAG_SUCCESS);
                String message = json.getString("message");
                Log.d("test",message);

                if (success == 1) {

                    // successfully created product
                    Intent i = new Intent(mContext, userActivity.class);
                    mContext.startActivity(i);
                } else {
                    // failed to create product
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
        }

    }
}
