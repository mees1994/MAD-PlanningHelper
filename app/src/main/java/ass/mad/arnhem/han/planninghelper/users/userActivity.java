package ass.mad.arnhem.han.planninghelper.users;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ass.mad.arnhem.han.planninghelper.R;

public class userActivity extends AppCompatActivity {

    // Progress Dialog
    private ProgressDialog pDialog;

    // Creating JSON Parser object
    JSONParser jsonParser = new JSONParser();

    ArrayList<HashMap<String, String>> productsList;

    // url to get all products list
    private static String url_get_user_by_id = "http://www.peterotten.com/AndroidProject/get_user_by_id.php";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_USER = "gebruiker";
    private static final String TAG_android_id = "android_id";

    private static final String TAG_GEBRUIKERSNAAM = "Gebruikersnaam";
    private static final String TAG_VOORNAAM = "Voornaam";
    private static final String TAG_ACHTERNAAM = "Achternaam";

    // products JSONArray
    JSONArray products = null;

    Button btnCreateUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        String gebruikersnaam = sharedPref.getString("gebruikersnaam", "nousername");
        String voornaam = sharedPref.getString("voornaam","novoornaame");
        String achternaam = sharedPref.getString("achternaam","noachternaam");

        // Loading products in Background Thread
        if(gebruikersnaam == "nousername" || voornaam == "novoornaame" || achternaam == "noachternaam") {
            new loadUser().execute();
        } else{
            TextView txtGebruikersnaam = (TextView) findViewById(R.id.overviewGebruikersnaam);
            TextView textVoornaam = (TextView) findViewById(R.id.overviewVoornaam);
            TextView textAchternaam = (TextView) findViewById(R.id.overviewAchternaam);

            // display product data in EditText
            txtGebruikersnaam.setText(gebruikersnaam);
            textVoornaam.setText(voornaam);
            textAchternaam.setText(achternaam);
        }

        btnCreateUser = (Button) findViewById(R.id.buttonZoekVrienden);

        // view products click event
        btnCreateUser.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Launching All products Activity
                Intent i = new Intent(getApplicationContext(), zoekVriendenActivity.class);
                startActivity(i);

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user, menu);
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
    class loadUser extends AsyncTask<String, String, String[]> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(userActivity.this);
            pDialog.setMessage("Loading user details. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Getting user details in background thread
         * */
        protected String[] doInBackground(String... params) {
            String[] resultProduct = new String[3];
            int success;
            try {
                String android_id = Settings.Secure.getString(getContentResolver(),
                        Settings.Secure.ANDROID_ID);
                // Building Parameters
                List<NameValuePair> paramsGet = new ArrayList<NameValuePair>();
                paramsGet.add(new BasicNameValuePair(TAG_android_id, android_id));

                // getting product details by making HTTP request
                // Note that product details url will use GET request
                JSONObject json = jsonParser.makeHttpRequest(
                        url_get_user_by_id, "GET", paramsGet);

                // check your log for json response
                Log.d("Single user Details", json.toString());

                // json success tag
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    // successfully received product details
                    JSONArray productObj = json
                            .getJSONArray(TAG_USER); // JSON Array

                    // get first product object from JSON Array
                    JSONObject product = productObj.getJSONObject(0);

                    resultProduct[0] = product.getString(TAG_GEBRUIKERSNAAM);
                    resultProduct[1] = product.getString(TAG_VOORNAAM);
                    resultProduct[2] = product.getString(TAG_ACHTERNAAM);
                    Log.d("result",  product.getString(TAG_GEBRUIKERSNAAM));
                    return resultProduct;

                }else{
                    // no user found
                    // Launch Add New product Activity
                    Intent i = new Intent(getApplicationContext(),
                            createUserActivity.class);
                    // Closing all previous activities
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return resultProduct;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String[] result) {
            // product with this pid found
            // Edit Text
            TextView txtGebruikersnaam = (TextView) findViewById(R.id.overviewGebruikersnaam);
            TextView textVoornaam = (TextView) findViewById(R.id.overviewVoornaam);
            TextView textAchternaam = (TextView) findViewById(R.id.overviewAchternaam);

            // display product data in EditText
            txtGebruikersnaam.setText(result[0]);
            textVoornaam.setText(result[1]);
            textAchternaam.setText(result[2]);

            // dismiss the dialog once got all details
            pDialog.dismiss();
        }

    }
}
