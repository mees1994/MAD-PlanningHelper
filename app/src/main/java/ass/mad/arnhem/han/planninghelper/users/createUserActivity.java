package ass.mad.arnhem.han.planninghelper.users;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ass.mad.arnhem.han.planninghelper.R;

public class createUserActivity extends AppCompatActivity {

    // Progress Dialog
    private ProgressDialog pDialog;

    JSONParser jsonParser = new JSONParser();
    EditText inputGebruikersnaam;
    EditText inputVoornaam;
    EditText inputAchternaam;

    // url to create new product
    private static String url_create_product = "http://peterotten.com/AndroidProject/create_user.php";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_create_user);

        // Edit Text
        inputGebruikersnaam = (EditText) findViewById(R.id.inputGebruikersnaam);
        inputVoornaam = (EditText) findViewById(R.id.inputVoornaam);
        inputAchternaam = (EditText) findViewById(R.id.inputAchternaam);

        inputGebruikersnaam.addTextChangedListener(new TextValidator(inputGebruikersnaam) {
            @Override
            public void validate(TextView textView, String text) {
                if( text.length() == 0 )
                    textView.setError( "Gebruikersnaam benodigd!" );
            }
        });
        inputVoornaam.addTextChangedListener(new TextValidator(inputVoornaam) {
            @Override
            public void validate(TextView textView, String text) {
                if( text.length() == 0 )
                    textView.setError( "Voornaam benodigd!" );
                if (text.matches(".*\\d+.*")) {
                    textView.setError("Alleen letters!");
                }
            }
        });
        inputAchternaam.addTextChangedListener(new TextValidator(inputAchternaam) {
            @Override
            public void validate(TextView textView, String text) {
                if( text.length() == 0 ) {
                    textView.setError("Achternaam benodigd!");
                }
                if (text.matches(".*\\d+.*")) {
                    textView.setError("Alleen letters!");
                }
            }
        });

        // Create button
        Button btnCreateProduct = (Button) findViewById(R.id.btnCreateProduct);

        // button click event
        btnCreateProduct.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // creating new product in background thread
                String gebruikersnaam = inputGebruikersnaam.getText().toString();
                String voornaam = inputVoornaam.getText().toString();
                String achternaam = inputAchternaam.getText().toString();
                String android_id = Settings.Secure.getString(getContentResolver(),
                        Settings.Secure.ANDROID_ID);
                new CreateNewProduct().execute(gebruikersnaam,voornaam,achternaam,android_id);
            }
        });
    }

    /**
     * Background Async Task to Create new product
     * */
    class CreateNewProduct extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(createUserActivity.this);
            pDialog.setMessage("Maakt gebruiker aan..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Creating product
         * */
        protected String doInBackground(String... args) {
            String gebruikersnaam = args[0];
            String voornaam = args[1];
            String achternaam = args[2];
            String android_id = args[3];

            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("Gebruikersnaam", gebruikersnaam));
            params.add(new BasicNameValuePair("Voornaam", voornaam));
            params.add(new BasicNameValuePair("Achternaam", achternaam));
            params.add(new BasicNameValuePair("android_id", android_id));

            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(url_create_product,
                    "POST", params);

            // check log cat for response
            Log.d("Create Response", json.toString());

            // check for success tag
            try {
                int success = json.getInt(TAG_SUCCESS);
                String message = json.getString("message");
                Log.d("createUserActivity",message);

                if (success == 1) {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    // successfully created product
                    Intent i = new Intent(getApplicationContext(), userActivity.class);
                    startActivity(i);

                    // closing this screen
                    finish();
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
            pDialog.dismiss();
        }

    }

    public abstract class TextValidator implements TextWatcher {
        private final TextView textView;

        public TextValidator(TextView textView) {
            this.textView = textView;
        }

        public abstract void validate(TextView textView, String text);

        @Override
        final public void afterTextChanged(Editable s) {
            Log.d("aftertext","test");
            String text = textView.getText().toString();
            validate(textView, text);
        }

        @Override
        final public void beforeTextChanged(CharSequence s, int start, int count, int after) { /* Don't care */ }

        @Override
        final public void onTextChanged(CharSequence s, int start, int before, int count) { /* Don't care */ }
    }
}
