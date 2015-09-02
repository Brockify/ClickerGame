package com.skyrealm.brockyy.clickergame;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
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

public class Add_Friend extends ActionBarActivity {
    String username;
    EditText OtherUser;
    String UserReceiving;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend_screen);
        OtherUser = (EditText) findViewById(R.id.OtherUser);
        username = getIntent().getExtras().getString("username");
        UserReceiving = OtherUser.getText().toString();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_friend_screen, menu);
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


    public void friendrequestButtonClicked(View view) {
        username = getIntent().getExtras().getString("username");
        UserReceiving = OtherUser.getText().toString();

        new addfriend().execute();
    }
    class addfriend extends AsyncTask<Void, Void, Void>
    {
        private ProgressDialog pDialog;
        String responseStr;

        @Override
        protected void onPreExecute()
        {
            pDialog = new ProgressDialog(Add_Friend.this);
            pDialog.setMessage("Adding Friend...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            HttpResponse response;
            HttpClient httpClient = new DefaultHttpClient();

            HttpPost httpPost = new HttpPost("http://www.skyrealmstudio.com/cgi-bin/PokeWars/Add_Friend.py");

            List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
            nameValuePair.add(new BasicNameValuePair("UserSending", username));
            nameValuePair.add(new BasicNameValuePair("UserReceiving", UserReceiving));


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
            return null;
        }
        @Override
        protected void onPostExecute(Void result)
        {
            if(responseStr.equals("Success"))
            {
                Toast.makeText(Add_Friend.this, "Friend Request Sent", Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(Add_Friend.this, responseStr, Toast.LENGTH_LONG).show();
            }
            pDialog.dismiss();
        }
    }
}