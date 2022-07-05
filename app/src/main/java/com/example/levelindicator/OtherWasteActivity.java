package com.example.levelindicator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;

public class OtherWasteActivity extends AppCompatActivity {

    // creating variables for our textview,
    // imageview,cardview and progressbar.
    private TextView levelIndicator, timeStamp, binName;
    private ImageView imageViewContainer;
    private ProgressBar loadingPB;
    private CardView courseCV;

    // below line is the variable for url from
    // where we will be querying our data.
    String url = "http://192.168.43.107/smart_bin/others_sensor.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_waste);

        // in below line we are initializing our all views.
        loadingPB = findViewById(R.id.idLoadingPB);
        courseCV = findViewById(R.id.idCVCourse);
        levelIndicator = findViewById(R.id.idTVLevel);
        binName = findViewById(R.id.idTVWasteName);
        timeStamp = findViewById(R.id.idTVTime);
        imageViewContainer = findViewById(R.id.idIVContainer);

        // creating a new variable for our request queue
        RequestQueue queue = Volley.newRequestQueue(OtherWasteActivity.this);

        // as our data is in json object format so we are using
        // json object request to make data request from our url.
        // in below line we are making a json object
        // request and creating a new json object request.
        // inside our json object request we are calling a
        // method to get the data, "url" from where we are
        // calling our data,"null" as we are not passing any data.
        // later on we are calling response listener method
        // to get the response from our API.
        // this is the error listener method which
// we will call if we get any error from API.
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            // inside the on response method.
            // we are hiding our progress bar.
            loadingPB.setVisibility(View.GONE);

            // in below line we are making our card
            // view visible after we get all the data.
            courseCV.setVisibility(View.VISIBLE);
            try {
                // now we get our response from API in json object format.
                // in below line we are extracting a string with its key
                // value from our json object.
                // similarly we are extracting all the strings from our json object.

                String courseName = response.getString("bin3");
                String courseMode = response.getString("time_in");

                // after extracting all the data we are
                // setting that data to all our views.
                levelIndicator.setText(courseName + "%");
                timeStamp.setText(courseMode);



                // we are using picasso to load the image from url.
                // Picasso.get().load(courseImageURL).into(courseIV);
            } catch (JSONException e) {
                // if we do not extract data from json object properly.
                // below line of code is use to handle json exception
                e.printStackTrace();
            }
        }, error -> {
            // below line is use to display a toast message along with our error.
            Toast.makeText(OtherWasteActivity.this, "Failed to get data..", Toast.LENGTH_SHORT).show();
        });
        // at last we are adding our json
        // object request to our request
        // queue to fetch all the json data.
        queue.add(jsonObjectRequest);

    }
}