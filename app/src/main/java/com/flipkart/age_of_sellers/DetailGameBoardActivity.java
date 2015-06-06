package com.flipkart.age_of_sellers;

/**
 * Created by amitpatil on 6/6/15.
 */
import android.content.Intent;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class DetailGameBoardActivity extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new DetailFragment())
                    .commit();
        }
        new CostSheetLoader().execute("1");
    }

   /*( public boolean onCreateOptionsMenu(Menu menu) {
// Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.detail, menu);
        return true;
    }*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
// Handle action bar item clicks here. The action bar will
// automatically handle clicks on the Home/Up button, so long
// as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    /**
     * A placeholder fragment containing a simple view.
     */
    public static class DetailFragment extends Fragment {
        public DetailFragment() {
        }

        String forecastStr;
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            String str[];
            View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
// The detail Activity called via intent. Inspect the intent for forecast data.
            Intent intent = getActivity().getIntent();
            if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT)) {
                forecastStr = intent.getStringExtra(Intent.EXTRA_TEXT);
               str = forecastStr.split("|");
                ((TextView) rootView.findViewById(R.id.detail_text))
                        .setText(str[0]);

            }

            Button buttonOne = (Button) rootView.findViewById(R.id.startGame);
            buttonOne.setOnClickListener(new Button.OnClickListener() {
                public void onClick(View v) {
                    //Do stuff here
                    try {
                        String targetUrl = "http://172.20.195.177:2445/gameboards"; //TODO: Add the url here;
                        String urlParameters =
                                "&game_id=" + URLEncoder.encode(forecastStr.split("|")[1], "UTF-8") +
                                        "&user_id=" + URLEncoder.encode("???", "UTF-8");
                        Helper.excutePost(targetUrl, urlParameters);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    Intent intent = new Intent(getActivity(), BaseActivity.class);
                    startActivity(intent);
                }
            });

            return rootView;

        }
    }
    private class CostSheetLoader extends AsyncTask<String,Void,JSONObject> {

        String setServerString;
        protected JSONObject doInBackground(String... params) {

            JSONObject jsonObject = null;
            try{
                HttpClient client = AndroidHttpClient.newInstance("Android");


                // Create Request to server and get response

                HttpGet httpget = new HttpGet("http://172.20.195.177:2445/cost_sheet/show/"+params[0]);
                //Log.e("test","test");
                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                //Log.e("test","test test test ");
                setServerString = client.execute(httpget, responseHandler);
                //Log.e("serverdown",setServerString);
                jsonObject = new JSONObject(setServerString);
            }
            catch(Exception e){
                //Log.e("exception",e.toString());
            }
            return jsonObject;
        }

        @Override
        protected void onPostExecute(JSONObject result) {

            CostSheet cs = CostSheet.getInstance();
            cs.init(result);
            //if (result != null) {
              //  forecastAdapter.clear();
                ;
                //for (String dayForecastStr : result) {
                  //  forecastAdapter.add(dayForecastStr);

            //}
        }

        private ArrayList<String> jsonhelper(String setServerString){
            ArrayList<String> result = null;
            try {
                result = new ArrayList<String>();
                JSONArray jsonArray = new JSONArray(setServerString);
                for (int i = 0; i < jsonArray.length(); i++) {


                    JSONObject jsonobject = jsonArray.getJSONObject(i);
                    String title = jsonobject.getString("title");
                    String id = jsonobject.getString("id");
                    result.add(title+"|"+id);

                    // Log.e("fkl", "Here");

                }

            }
            catch (Exception e){
                //   Log.e("Error there",e.toString());
            }
            return result;
        }
        public  Map<String, Object> jsonToMap(JSONObject json) {
            Map<String, Object> retMap = new HashMap<String, Object>();

            if(json != JSONObject.NULL) {
                retMap = toMap(json);
            }
            return retMap;
        }

        public  Map<String, Object> toMap(JSONObject object) {
            Map<String, Object> map = new HashMap<String, Object>();

            Iterator<String> keysItr = object.keys();
            while(keysItr.hasNext()) {
                String key = keysItr.next();
                Object value = null;
                try {
                    value = object.get(key);
                }
                catch (Exception e){

                }
                if(value instanceof JSONArray) {
                    value = toList((JSONArray) value);
                }

                else if(value instanceof JSONObject) {
                    value = toMap((JSONObject) value);
                }
                map.put(key, value);
            }
            return map;
        }

        public  List<Object> toList(JSONArray array) {
            List<Object> list = new ArrayList<Object>();
            for(int i = 0; i < array.length(); i++) {
                Object value = null;
                try {
                    value = array.get(i);
                }
                catch (Exception e){

                }
                if(value instanceof JSONArray) {
                    value = toList((JSONArray) value);
                }

                else if(value instanceof JSONObject) {
                    value = toMap((JSONObject) value);
                }
                list.add(value);
            }
            return list;
        }
    }
}

