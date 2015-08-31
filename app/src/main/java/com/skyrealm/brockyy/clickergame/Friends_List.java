package com.skyrealm.brockyy.clickergame;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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
import java.util.ArrayList;
import java.util.List;


public class Friends_List extends ActionBarActivity {
    ArrayList<String> friendsusername = new ArrayList<String>();
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends__list);
        username = getIntent().getExtras().getString("username");
        new friendlist().execute();

    }
    public void pendingfriendsButtonClicked(View view)
    {
        String username = getIntent().getExtras().getString("username");
        Intent intent = new Intent(Friends_List.this, Pending_Friends.class);
        intent.putExtra("username", username);
        startActivity(intent);
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

    class friendlist extends AsyncTask<Void, Void, Void> {
        private ProgressDialog pDialog;
        String responseStr;

        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(Friends_List.this);
            pDialog.setMessage("Logging in...");
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
            HttpPost httppost = new HttpPost("http://www.skyrealmstudio.com/cgi-bin/PokeWars/Friends_List.py");


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
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            return null;

        }

        public void onPostExecute(Void result)
        {
            ArrayAdapter adapter = new ArrayAdapter(Friends_List.this, R.layout.friend_list_layout, R.id.friendusername, friendsusername);
            ListView listView = (ListView) findViewById(R.id.listView);
            listView.setAdapter(adapter);
            pDialog.dismiss();
        }
    }
}