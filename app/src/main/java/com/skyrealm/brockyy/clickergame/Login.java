package com.skyrealm.brockyy.clickergame;

import android.app.ProgressDialog;
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

public class Login extends ActionBarActivity {
    String username;
    String password;

    EditText passwordEditText;
    EditText usernameEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        usernameEditText = (EditText) findViewById(R.id.usernameEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);

        username = usernameEditText.getText().toString();
        password = passwordEditText.getText().toString();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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
}
    public void loginButtonClicked(View view) {
        username = usernameEditText.getText().toString();
        password = passwordEditText.getText().toString();

        if (username.equals("") || password.equals(""))
        {
            Toast.makeText(this, "None of the fields can be empty", Toast.LENGTH_LONG).show();
        } else {
            new loginUser().execute();
        }
    }
class loginUser extends AsyncTask<Void, Void, Void>
{
    private ProgressDialog pDialog;
    String responseStr;

    @Override
    protected void onPreExecute()
    {
        pDialog = new ProgressDialog(Login.this);
        pDialog.setMessage("Logging in...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    @Override
    protected Void doInBackground(Void... params) {

        HttpResponse response;
        HttpClient httpClient = new DefaultHttpClient();

        HttpPost httpPost = new HttpPost("http://www.skyrealmstudio.com/cgi-bin/PokeWars/Login.py");

        List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
        nameValuePair.add(new BasicNameValuePair("username", username));
        nameValuePair.add(new BasicNameValuePair("password", password));


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
            Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(Login.this, responseStr, Toast.LENGTH_LONG).show();
        }
        pDialog.dismiss();
    }
}
}
