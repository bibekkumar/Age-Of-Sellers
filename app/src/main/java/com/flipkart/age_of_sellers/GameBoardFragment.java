package com.flipkart.age_of_sellers;

import android.content.Intent;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.flipkart.age_of_sellers.DetailGameBoardActivity;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by amitpatil on 5/6/15.
 */
public class GameBoardFragment extends Fragment {
    @Nullable
    ArrayAdapter<String> forecastAdapter;
    String setServerString = "";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        List<String> weekForecast = new ArrayList<String>();
        weekForecast.add("HEllo");
        weekForecast.add("HEllo");
        weekForecast.add("HEllo");


        String url = "http://172.20.195.177:2445/gameboards";
        Log.e("test","test");
        try
        {

            Log.e("test", "test test test test ");

            // Show response on activity


            //Log.e("serverString",setServerString);
            //content.setText(SetServerString);


        }
        catch(  Exception ex)
        {
            //content.setText("Fail!");
            //ex.printStackTrace();
        }


// Now that we have some dummy forecast data, create an ArrayAdapter.
// The ArrayAdapter will take data from a source (like our dummy forecast) and
// use it to populate the ListView it's attached to.
        forecastAdapter =
                new ArrayAdapter<String>(
                        getActivity(), // The current context (this activity)
                        R.layout.game_item, // The name of the layout ID.
                        R.id.game_item_textview, // The ID of the textview to populate.
                        weekForecast);
        View rootView = inflater.inflate(R.layout.fragment_game_board, container, false);
        ListView listView = (ListView) rootView.findViewById(R.id.list_gameboards);
        listView.setAdapter(forecastAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String forecast = forecastAdapter.getItem(position);
                Intent intent = new Intent(getActivity(), DetailGameBoardActivity.class)
                        .putExtra(Intent.EXTRA_TEXT, forecast);
                startActivity(intent);
            }
        });
        new GameBoardLoader().execute("");
        return rootView;
    }
    private class GameBoardLoader extends AsyncTask<String,Void,ArrayList<String>>{

        protected ArrayList<String> doInBackground(String... params) {

            try{
                HttpClient client = AndroidHttpClient.newInstance("Android");


            // Create Request to server and get response

                HttpGet httpget = new HttpGet("http://172.20.195.177:2445/gameboards");
                Log.e("test","test");
                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                Log.e("test","test test test ");
                setServerString = client.execute(httpget, responseHandler);
                Log.e("serverdown",setServerString);

            }
            catch(Exception e){
                Log.e("exception",e.toString());
            }
            return jsonhelper(setServerString);
         }

        @Override
        protected void onPostExecute(ArrayList<String> result) {
            Log.e("Clear","Clear");
            if (result != null) {
                forecastAdapter.clear();
                ;
                for (String dayForecastStr : result) {
                    forecastAdapter.add(dayForecastStr);
                }
            }
        }
        private ArrayList<String> jsonhelper(String setServerString){
            ArrayList<String> result = null;
            try {
                result = new ArrayList<String>();
                JSONArray jsonArray = new JSONArray(setServerString);
                for (int i = 0; i < jsonArray.length(); i++) {


                    JSONObject jsonobject = jsonArray.getJSONObject(i);
                    String title = jsonobject.getString("title");
                    result.add(title);
                    String id = jsonobject.getString("id");
                    Log.e("fkl", "Here");

                }

            }
            catch (Exception e){
                Log.e("Error there",e.toString());
            }
            return result;
        }
    }
    public GameBoardFragment(){


    }
}
