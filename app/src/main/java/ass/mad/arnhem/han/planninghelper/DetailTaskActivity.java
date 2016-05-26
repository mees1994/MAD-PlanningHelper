package ass.mad.arnhem.han.planninghelper;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import ass.mad.arnhem.han.planninghelper.Domain.Task;
import ass.mad.arnhem.han.planninghelper.Domain.Week;
import ass.mad.arnhem.han.planninghelper.users.JSONParser;

public class DetailTaskActivity extends AppCompatActivity {

    // Progress Dialog
    private ProgressDialog pDialog;
    // url to add punten
    private static String url_create_product = "http://peterotten.com/AndroidProject/update_points.php";
    JSONParser jsonParser = new JSONParser();

    // JSON Node names
    private static final String TAG_SUCCESS = "success";

    private int dayNr, taskPos, weekNr, punten;
    private Button pictureBtn;
    private ImageView pictureIV;
    private String encodedImage;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private static final int SELECT_FILE = 1;
    private static final int OPEN_IMAGE_FULL_SCREEN = 2;
    private Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_task);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Week week = PlanningApplication.getInstance().getWeek();
        weekNr = week.getWeekNr();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            taskPos = extras.getInt("taskNr");
            dayNr = extras.getInt("dayNr");
        }

        task = week.getDays().get(dayNr).getTasks().get(taskPos);
        getSupportActionBar().setTitle(task.getTitel());
        TextView title = (TextView) findViewById(R.id.detailsTitle);
        TextView description = (TextView) findViewById(R.id.detailsDescription);
        TextView date = (TextView) findViewById(R.id.detailsDate);
        TextView startTime = (TextView) findViewById(R.id.detailsStartTime);
        TextView endTime = (TextView) findViewById(R.id.detailsEndTime);
        TextView duration = (TextView) findViewById(R.id.detailsDuration);
        ImageView icon = (ImageView) findViewById(R.id.detailsIcon);
        pictureBtn = (Button) findViewById(R.id.detailsPictureBtn);
        pictureIV = (ImageView) findViewById(R.id.detailsPicture);

        title.setText(task.getTitel());
        description.setText(task.getDescription());
        date.setText(calculateDate());
        startTime.setText(task.getStartTime());
        endTime.setText(task.getEndTime());
        icon.setImageDrawable(getDrawable(task.getIcon()));
        
        Date dStartTime = new Date();
        Date dEndTime = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm");
        Log.e("DetailTaskActivity", task.getStartTime());
        Log.e("DetailTaskActivity", task.getEndTime());
        try {
            dStartTime = dateFormat.parse(task.getStartTime());
            dEndTime = dateFormat.parse(task.getEndTime());
        } catch (ParseException e) {
            Log.e("DetailsTaskActivity", "Error while paring date" + e);
        }
        long durationAmount = (dEndTime.getTime() - dStartTime.getTime());
        Log.e("DetailTaskActivity", String.valueOf(durationAmount));
        duration.setText(String.valueOf(durationAmount));

        if (task.getPicture() != null) {
            pictureIV.setImageBitmap(task.getPicture());
        }
        pictureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPictureBtnClicked();
            }
        });

        if(task.isTaskCompleted()){
            CheckBox checkBoxCompleted = (CheckBox) findViewById(R.id.detailsTaskDoneCheckbox);
            checkBoxCompleted.setChecked(true);
            checkBoxCompleted.setEnabled(false);

        }

    }

    private String calculateDate() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.WEEK_OF_YEAR, weekNr);
        cal.set(Calendar.DAY_OF_WEEK, dayNr + 1);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy", Locale.getDefault());
        return sdf.format(cal.getTime());
    }

    private int calculatePoints() {
        String startTijd = task.getStartTime();
        String eindTijd = task.getEndTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        Date dateStart = null;
        try {
            dateStart = simpleDateFormat.parse(startTijd);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date dateEnd = null;
        try {
            dateEnd = simpleDateFormat.parse(eindTijd);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long difference = dateEnd.getTime() - dateStart.getTime();

        if(difference > 0) {
            int punten = Math.round((difference / 900000));
            return Math.round(punten);
        } else {
            return 0;
        }

    }

    public void onCameraBtnClicked() {
        if (checkCameraHardware(this)) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            File file = new File(Environment.getExternalStorageDirectory() + File.separator + "image.jpg");
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        } else {
            new MaterialDialog.Builder(this)
                    .title("No camera")
                    .content("The hardware doesn't have a camera.")
                    .positiveText("Close")
                    .show();
        }
    }

    public void onGalleryBtnClicked() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, SELECT_FILE);
    }

    public void onPictureBtnClicked() {
        new MaterialDialog.Builder(this)
                .title("How to add a picture")
                .items(R.array.paperclip_card_question)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        if (which == 0) {
                            onCameraBtnClicked();
                        } else if (which == 1) {
                            onGalleryBtnClicked();
                        }
                    }
                })
                .positiveText("Close")
                .show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        PlanningApplication app = PlanningApplication.getInstance();

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
                File file = new File(Environment.getExternalStorageDirectory() + File.separator + "image.jpg");
                Bitmap bitmap = decodeSampledBitmapFromFile(file.getAbsolutePath(), 1000, 700);

                task.setPicture(bitmap);
                pictureIV.setImageBitmap(bitmap);
            } else if (requestCode == SELECT_FILE) {
                // Get the Image from data
                String imgDecodableString;

                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                // Get the cursor
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgDecodableString = cursor.getString(columnIndex);
                cursor.close();
                // Set the Image in ImageView after decoding the String
                Bitmap bitmap = BitmapFactory.decodeFile(imgDecodableString);

                task.setPicture(bitmap);
                pictureIV.setImageBitmap(bitmap);
            }
        }
    }

    public static Bitmap decodeSampledBitmapFromFile(String path, int reqWidth, int reqHeight) { // BEST QUALITY MATCH

        //First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        // Calculate inSampleSize, Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        int inSampleSize = 1;

        if (height > reqHeight) {
            inSampleSize = Math.round((float) height / (float) reqHeight);
        }
        int expectedWidth = width / inSampleSize;

        if (expectedWidth > reqWidth) {
            //if(Math.round((float)width / (float)reqWidth) > inSampleSize) // If bigger SampSize..
            inSampleSize = Math.round((float) width / (float) reqWidth);
        }

        options.inSampleSize = inSampleSize;

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(path, options);
    }

    public static boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            return true;
        } else {
            return false;
        }
    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.detailsTaskDoneCheckbox:
                if (checked)
                    finishTask();
                else
                // Remove the meat
                break;
            // TODO: Veggie sandwich
        }
    }

    public void finishTask() {
        if(!task.isTaskCompleted()){
            task.setTaskCompleted(true);

            SharedPreferences sharedPref = getSharedPreferences("PlanningHelper", Context.MODE_PRIVATE);
            String gebruikersnaam = sharedPref.getString("gebruikersnaam", "nousername");
            String voornaam = sharedPref.getString("voornaam","novoornaame");
            String achternaam = sharedPref.getString("achternaam", "noachternaam");
            String android_id = Settings.Secure.getString(getContentResolver(),
                    Settings.Secure.ANDROID_ID);
            this.punten = sharedPref.getInt("punten", 0);

            // Loading products in Background Thread
            if(gebruikersnaam != "nousername" || voornaam != "novoornaame" || achternaam != "noachternaam") {

                int puntenErbij = calculatePoints();
                this.punten = this.punten + puntenErbij;
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putInt("punten", this.punten);
                editor.commit();
                new SavePoints().execute(gebruikersnaam,android_id);
            }
        }else{
            //is completed
        }
    }

    /**
     * Background Async Task to Create new product
     * */
    class SavePoints extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(DetailTaskActivity.this);
            pDialog.setMessage("Voegt punten toe..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Creating product
         * */
        protected String doInBackground(String... args) {
            String gebruikersnaam = args[0];
            String android_id = args[1];

            SharedPreferences sharedPref = getSharedPreferences("PlanningHelper", Context.MODE_PRIVATE);
            int punten = sharedPref.getInt("punten", 0);

            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("Gebruikersnaam", gebruikersnaam));
            params.add(new BasicNameValuePair("android_id", android_id));
            params.add(new BasicNameValuePair("Punten", Integer.toString(punten)));

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
                    Log.d("Details","punten Toegevoegd");

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
}
