package com.skyrealm.brockyy.clickergame;

import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class Games_Screen extends ActionBarActivity {

    //declare variables
    GPSTracker gps;
    String country = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games__screen);
        setTitle("World War III");
        String firstCountry = null;
        TextView countryTextView = (TextView) findViewById(R.id.countryTextView);
        ImageView flagImage = (ImageView) findViewById(R.id.flagImageView);
        List<Address> firstAddresses = null;

        GPSTracker firstGPS = new GPSTracker(this);
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            firstAddresses = geocoder.getFromLocation(firstGPS.getLatitude(), firstGPS.getLongitude(), 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        firstCountry = firstAddresses.get(0).getCountryName();
        countryTextView.setText(firstCountry);

        flagImage.setImageDrawable(Drawable.createFromPath("@drawable/" + firstCountry));


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_games__screen, menu);
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

    public void click_button_Clicked(View view)
    {
        gps = new GPSTracker(Games_Screen.this);
        Geocoder geocoder;
        List<Address> addresses;
        String state = null;
        geocoder = new Geocoder(Games_Screen.this, Locale.getDefault());

        try {
            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
            country = addresses.get(0).getCountryName();
            state = addresses.get(0).getAdminArea();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Toast.makeText(this, "Country:" + country + " State:" + state, Toast.LENGTH_LONG).show();

        new update_click_to_server().execute();

        gps.stopUsingGps();
    }

    class update_click_to_server extends AsyncTask<Void, Void, String>
    {
        String responseStr;
        @Override
        protected String doInBackground(Void... params) {
            HttpResponse response;
            HttpClient httpClient = new DefaultHttpClient();

            HttpPost httpPost = new HttpPost("http://www.skyrealmstudio.com/cgi-bin/WW3/UpdateClick.py");

            List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
            nameValuePair.add(new BasicNameValuePair("country", country));


            try {
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            try {
                response = httpClient.execute(httpPost);
                responseStr = EntityUtils.toString(response.getEntity());

                // writing response to log
                Log.d("Http Response:", responseStr);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return responseStr;
        }

        protected void onPostExecute(String result)
        {
            if (result.equals("\nUpdated\n"))
            {
                Toast.makeText(Games_Screen.this, "Click Added", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(Games_Screen.this, "Could not add click", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

