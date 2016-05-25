package ass.mad.arnhem.han.planninghelper.users;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import ass.mad.arnhem.han.planninghelper.R;

/**
 * Created by MOBO on 23-5-2016.
 */
public class vriendenOverzichtAdapter extends BaseAdapter {

    // Progress Dialog
    private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();
    private static String url_create_vriendlink = "http://www.peterotten.com/AndroidProject/delete_friends.php";

    private ArrayList<HashMap<String, String>> personlist;
    private ArrayList<HashMap<String, String>> arraylist;
    // Declare Variables
    Context mContext;
    LayoutInflater inflater;
    ViewHolder holder;

    String UsernameID = "0";

    private static final String TAG_GEBUIKERSNAAM="Gebruikersnaam";
    private static final String TAG_PUNTEN="Punten";
    private static final String TAG_ID ="ID";
    private static final String TAG_SUCCESS = "success";

    public vriendenOverzichtAdapter(Context context, ArrayList<HashMap<String, String>> personlist) {
        mContext = context;
        inflater = (LayoutInflater)mContext.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        this.personlist = personlist;
        this.arraylist = new ArrayList<>();
        this.arraylist.addAll(personlist);
    }

    private static class ViewHolder {
        private TextView gebruikersnaam;
        private TextView punten;
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
            view = inflater.inflate(R.layout.listview_item_listvrienden, null);

            // Locate the TextViews in listview_item.xml
            holder.gebruikersnaam = (TextView) view.findViewById(R.id.listVriendenUsername);
            holder.punten = (TextView) view.findViewById(R.id.listVriendenPunten);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        holder.gebruikersnaam.setText(personlist.get(position).get(TAG_GEBUIKERSNAAM));
        // Set the results into TextViews
        holder.punten.setText(personlist.get(position).get(TAG_PUNTEN));

        // Listen for ListView Item Click
        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                AlertDialog.Builder builder = new AlertDialog.Builder(arg0.getContext());
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                String ID = personlist.get(position).get(TAG_ID);
                                new DeleteFriendLink().execute(ID);
                                personlist.remove(position);
                                notifyDataSetChanged();
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };
                builder.setMessage("Weet je zeker dat je "+ personlist.get(position).get(TAG_GEBUIKERSNAAM) +" wilt verwijderen?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
            }
        });

        return view;
    }

    /**
     * Background Async Task to Create new product
     * */
    class DeleteFriendLink extends AsyncTask<String, String, String> {

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
                    Log.d("vriendenAdapter","Removed friend");
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

        }

    }
}
