package com.example.levelindicator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;

public class MainActivity extends AppCompatActivity {

    AppCompatImageView metalicTank,plasticTank,organicTank,othersTank;
    TextView organic, metalic, plastic, others;

    String url = "http://192.168.43.107/smart_bin/general_reader.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        metalicTank = (AppCompatImageView) findViewById(R.id.metalic_tank);
        plasticTank = (AppCompatImageView) findViewById(R.id.plastic_tank);
        organicTank = (AppCompatImageView) findViewById(R.id.organic_tank);
        othersTank = (AppCompatImageView) findViewById(R.id.others_tank);

        organic = (TextView)findViewById(R.id.tv_organic);
        metalic = (TextView)findViewById(R.id.tv_metalic);
        plastic = (TextView)findViewById(R.id.tv_plastic);
        others = (TextView)findViewById(R.id.tv_others);

        metalicTank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,FetchDataActivity.class);
                startActivity(intent);
            }
        });

        plasticTank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,PlasticWasteActivity.class);
                startActivity(intent);
            }
        });

        organicTank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,OrganicWasteActivity.class);
                startActivity(intent);
            }
        });

        othersTank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,OtherWasteActivity.class);
                startActivity(intent);
            }
        });

        //request one

        // creating a new variable for our request queue
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

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
            try {
                // now we get our response from API in json object format.
                // in below line we are extracting a string with its key
                // value from our json object.
                // similarly we are extracting all the strings from our json object.

                String metalicLevel = response.getString("bin1");
                String plasticLevel = response.getString("bin4");
                String organicLevel = response.getString("bin2");
                String otherLevel = response.getString("bin3");

                // after extracting all the data we are
                // setting that data to all our views.
                metalic.setText(metalicLevel + "%");
                plastic.setText(plasticLevel +"%");
                organic.setText(organicLevel +"%");
                others.setText(otherLevel +"%");


                // we are using picasso to load the image from url.
                // Picasso.get().load(courseImageURL).into(courseIV);
            } catch (JSONException e) {
                // if we do not extract data from json object properly.
                // below line of code is use to handle json exception
                e.printStackTrace();
            }
        }, error -> {
            // below line is use to display a toast message along with our error.
            Toast.makeText(MainActivity.this, "Failed to get data..", Toast.LENGTH_SHORT).show();
        });
        // at last we are adding our json
        // object request to our request
        // queue to fetch all the json data.
        queue.add(jsonObjectRequest);

    }

}