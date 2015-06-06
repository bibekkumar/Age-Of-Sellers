package com.flipkart.age_of_sellers;

import android.content.Intent;
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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        List<String> weekForecast = new ArrayList<String>();
        HttpClient client = new DefaultHttpClient();
        String url = "http://172.20.195.177:2445/gameboards";
        Log.e("test","test");
        try
        {
            String setServerString = "";

            // Create Request to server and get response

            HttpGet httpget = new HttpGet(url);
            Log.e("test","test");
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            Log.e("test","test test test ");
            setServerString = client.execute(httpget, responseHandler);
            Log.e("test","test test test test ");

            // Show response on activity
            Log.e("json",setServerString);
            //content.setText(SetServerString);
            JSONArray jsonArray = new JSONArray(setServerString);
            for (int i = 0; i < jsonArray.length(); i++) {
                Log.e("fkl","Here");

                JSONObject jsonobject = jsonArray.getJSONObject(i);
                String title = jsonobject.getString("title");
                String id = jsonobject.getString("id");
                weekForecast.add(title);
            }

        }
        catch(Exception ex)
        {
            //content.setText("Fail!");
            Log.e("BC BC","BC");
            Log.e("Chutiyapa", ex.getMessage());
            //ex.printStackTrace();
        }


// Now that we have some dummy forecast data, create an ArrayAdapter.
// The ArrayAdapter will take data from a source (like our dummy forecast) and
// use it to populate the ListView it's attached to.
        final ArrayAdapter<String> forecastAdapter =
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
        return rootView;
    }
    public GameBoardFragment(){


    }
}
