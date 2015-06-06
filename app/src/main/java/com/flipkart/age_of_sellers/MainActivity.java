package com.flipkart.age_of_sellers;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.webkit.WebView;
import android.webkit.WebViewClient;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import android.content.Intent;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends Activity  {
    Button submit,reset;
    EditText username,password;

    Context cont;

    TextView tx1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        submit=(Button)findViewById(R.id.button);
        username=(EditText)findViewById(R.id.editText);
        password=(EditText)findViewById(R.id.editText2);
        cont = this;

        reset=(Button)findViewById(R.id.button2);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UserLoader user = new UserLoader();

                user.execute();
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nullMessage = "";
                username.setText(nullMessage);
                password.setText(nullMessage);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    private class UserLoader extends AsyncTask<String,Void,HashMap<String,String>> {

        String setServerString = "";

        protected HashMap<String,String> doInBackground(String... params) {

            try{
                HttpClient client = AndroidHttpClient.newInstance("Android");


                // Create Request to server and get response

                HttpGet httpget = new HttpGet("http://172.20.195.177:2445/sellers/show/"+username.getText());
                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                setServerString = client.execute(httpget, responseHandler);
                Log.e("serverdown",setServerString);

            }
            catch(Exception e){
                Log.e("exception",e.toString());
            }
            return jsonhelper(setServerString);
        }

        @Override
        protected void onPostExecute(HashMap<String,String> result) {
            if (result != null) {

                if(password.getText().toString().equals(result.get("password")))
                {
                    Seller me = Seller.getInstance();

                    me.init(result.get("name"), result.get("email"), result.get("geo_location"), result.get("phone"), result.get("password"), cont);

                    Toast.makeText(getApplicationContext(), "Redirecting...", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, BaseActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Wrong Credentials", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                Toast.makeText(getApplicationContext(), "Wrong User Name", Toast.LENGTH_SHORT).show();
            }
        }
        private HashMap<String,String> jsonhelper(String setServerString){
            HashMap<String,String> result = null;
            try {
                if(setServerString != null){
                    result = new HashMap<String,String>();
                    JSONObject jsonobject = new JSONObject(setServerString);

                    String var = jsonobject.getString("name");
                    result.put("name", var);
                    var = jsonobject.getString("email");
                    result.put("email", var);
                    var = jsonobject.getString("geo_location");
                    result.put("geo_location", var);
                    var = jsonobject.getString("password");
                    result.put("password", var);
                    var = jsonobject.getString("phone");
                    result.put("phone", var);
                }
            }
            catch (Exception e){
                Log.e("Error there",e.toString());
            }
            return result;
        }
    }
}
