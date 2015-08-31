package com.skyrealm.brockyy.clickergame;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


public class Pending_Friends extends ActionBarActivity {
    ArrayList<String> friendsusername = new ArrayList<String>();
    String username;
    TextView OtherUser;
    String UserSending;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pending_friends);
        username = getIntent().getExtras().getString("username");
        new pendingfriendlist().execute();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_friends__list, menu);

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

    class pendingfriendlist extends AsyncTask<Void, Void, Void> {
        private ProgressDialog pDialog;
        String responseStr;

        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(Pending_Friends.this);
            pDialog.setMessage("trying...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            HttpResponse response;
            String responseStr = null;
            String friend;
            String jsonStr = null;
            JSONArray json = null;
            // Create a new HttpClient and Post Header
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://www.skyrealmstudio.com/cgi-bin/PokeWars/Pending_Friend_List.py");


            try {
                // Add your data
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("username", username));
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                // Execute HTTP Post Request
                response = httpclient.execute(httppost);
                responseStr = EntityUtils.toString(response.getEntity());
                jsonStr = responseStr;


            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
            } catch (IOException e) {
                // TODO Auto-generated catch block
            }
            System.out.println(responseStr);
            try {
                json = new JSONArray(jsonStr);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            for (int counter = 0; counter < json.length(); counter++)
                try {
                    friend = json.getJSONObject(counter).getString("username");
                    friendsusername.add(friend);
                    Log.d("Message:", friendsusername.get(counter));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            return null;

        }

        public void onPostExecute(Void result)
        {
            ArrayAdapter adapter = new ArrayAdapter(Pending_Friends.this, R.layout.pending_friends_layout, R.id.pendingfriend, friendsusername);
            ListView listView = (ListView) findViewById(R.id.listView2);
            listView.setAdapter(adapter);
            pDialog.dismiss();
        }
    }
    public void acceptfriendButtonClicked(View view){

        LinearLayout OtherUserRow = (LinearLayout) view.getParent();
        OtherUser = (TextView) OtherUserRow.findViewById(R.id.pendingfriend);
        UserSending = OtherUser.getText().toString();
        String username = getIntent().getExtras().getString("username");

        new addFriend().execute();
    }
    class addFriend extends AsyncTask<Void, Void, Void>
    {

        private ProgressDialog pDialog;
        String responseStr;
        String username = getIntent().getExtras().getString("username");
        String UserReceiving = username;
        String UserSending = OtherUser.getText().toString();
        @Override
        protected void onPreExecute()
        {
            pDialog = new ProgressDialog(Pending_Friends.this);
            pDialog.setMessage("trying...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            HttpResponse response;
            HttpClient httpClient = new DefaultHttpClient();

            HttpPost httpPost = new HttpPost("http://www.skyrealmstudio.com/cgi-bin/PokeWars/Accept_Friend_Request.py");

            List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
            nameValuePair.add(new BasicNameValuePair("UserSending", UserSending));
            nameValuePair.add(new BasicNameValuePair("UserReceiving", username));



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
            if(responseStr.equals("Friend Added"))
            {
                Toast.makeText(Pending_Friends.this, "Friend Added", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(Pending_Friends.this, responseStr, Toast.LENGTH_LONG).show();
            }
            pDialog.dismiss();
        }
    }

}