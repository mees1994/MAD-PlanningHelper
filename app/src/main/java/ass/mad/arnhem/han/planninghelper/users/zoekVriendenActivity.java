package ass.mad.arnhem.han.planninghelper.users;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import ass.mad.arnhem.han.planninghelper.R;

public class zoekVriendenActivity extends AppCompatActivity {

    // Progress Dialog
    private ProgressDialog pDialog;

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_USER = "gebruiker";
    private static final String TAG_android_id = "android_id";

    private static String url_get_all_but_user = "http://www.peterotten.com/AndroidProject/get_all_but_user.php";

    private static final String TAG_GEBRUIKERSNAAM ="Gebruikersnaam";
    private static final String TAG_VOORNAAM = "Voornaam";
    private static final String TAG_ACHTERNAAM = "Achternaam";
    private static final String TAG_PUNTEN ="Punten";
    private static final String TAG_ID ="ID";

    JSONArray peoples = null;
    ArrayList<HashMap<String, String>> personList;

    // Creating JSON Parser object
    JSONParser jsonParser = new JSONParser();
    EditText editsearch;
    zoekVriendenListAdapter zoekVriendenListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoek_vrienden);


        personList = new ArrayList<HashMap<String,String>>();
        new searchUsers().execute();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_zoek_vrienden, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Background Async Task to Load the user by making HTTP Request
     * */
    class searchUsers extends AsyncTask<String, String, JSONArray> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(zoekVriendenActivity.this);
            pDialog.setMessage("Loading user details. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Getting user details in background thread
         * */
        protected JSONArray doInBackground(String... params) {

            int success;
            try {
                String android_id = Settings.Secure.getString(getContentResolver(),
                        Settings.Secure.ANDROID_ID);

                SharedPreferences sharedPref = getSharedPreferences("PlanningHelper", Context.MODE_PRIVATE);
                String UsernameID = sharedPref.getString("usernameid", "555");

                Log.d("zoekvriendjes",android_id);
                Log.d("zoekvriendjes",UsernameID);

                // Building Parameters
                List<NameValuePair> paramsGet = new ArrayList<NameValuePair>();
                paramsGet.add(new BasicNameValuePair(TAG_android_id, android_id));
                paramsGet.add(new BasicNameValuePair("UsernameID", UsernameID));

                // getting product details by making HTTP request
                // Note that product details url will use GET request
                JSONObject json = jsonParser.makeHttpRequest(
                        url_get_all_but_user, "GET", paramsGet);

                // check your log for json response
                Log.d("All user Details", json.toString());
                // json success tag
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    // successfully received product details
                    JSONArray productObj = json
                            .getJSONArray(TAG_USER); // JSON Array

                    // get first product object from JSON Array
                    peoples = productObj.getJSONArray(0);

                    //HashMap<String,String> persons = new HashMap<String,String>();
                    //resultProduct[0] = product.(TAG_USERSARRAY);

                    return peoples;

                }else{
                    //nothing found
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return peoples;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(JSONArray peoples) {
            // dismiss the dialog once got all details
            pDialog.dismiss();
            if(peoples != null) {
                for (int i = 0; i < peoples.length(); i++) {
                    JSONObject c = peoples.optJSONObject(i);
                    String userID = c.optString(TAG_ID);
                    String gebruikersnaam = c.optString(TAG_GEBRUIKERSNAAM);
                    String voornaam = c.optString(TAG_VOORNAAM);
                    String achternaam = c.optString(TAG_ACHTERNAAM);
                    String punten = c.optString(TAG_PUNTEN);

                    HashMap<String, String> persons = new HashMap<String, String>();

                    persons.put(TAG_ID, userID);
                    persons.put(TAG_GEBRUIKERSNAAM, gebruikersnaam);
                    persons.put(TAG_VOORNAAM, voornaam);
                    persons.put(TAG_ACHTERNAAM, achternaam);
                    persons.put(TAG_PUNTEN, punten);

                    personList.add(persons);
                }

                zoekVriendenListAdapter = new zoekVriendenListAdapter(getBaseContext(), personList);
                ListView list = (ListView) findViewById(R.id.listviewZoekVrienden);
                list.setAdapter(zoekVriendenListAdapter);

                // Locate the EditText in listview_main.xml
                editsearch = (EditText) findViewById(R.id.search);

                // Capture Text in EditText
                editsearch.addTextChangedListener(new TextWatcher() {

                    @Override
                    public void afterTextChanged(Editable arg0) {
                        // TODO Auto-generated method stub
                        String text = editsearch.getText().toString().toLowerCase(Locale.getDefault());
                        zoekVriendenListAdapter.filtertext(text);
                    }

                    @Override
                    public void beforeTextChanged(CharSequence arg0, int arg1,
                                                  int arg2, int arg3) {
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                              int arg3) {
                        // TODO Auto-generated method stub
                    }
                });
            }

        }

    }

}
