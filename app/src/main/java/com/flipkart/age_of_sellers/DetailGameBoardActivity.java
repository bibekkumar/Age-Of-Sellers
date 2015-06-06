package com.flipkart.age_of_sellers;

/**
 * Created by amitpatil on 6/6/15.
 */
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

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
            View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
// The detail Activity called via intent. Inspect the intent for forecast data.
            Intent intent = getActivity().getIntent();
            if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT)) {
                forecastStr = intent.getStringExtra(Intent.EXTRA_TEXT);
                ((TextView) rootView.findViewById(R.id.detail_text))
                        .setText(forecastStr);

            }

            Button buttonOne = (Button) rootView.findViewById(R.id.startGame);
            buttonOne.setOnClickListener(new Button.OnClickListener() {
                public void onClick(View v) {
                    //Do stuff here
                    try {
                        String targetUrl = "http://172.20.195.177:2445/gameboards"; //TODO: Add the url here;
                        String urlParameters =
                                "&game_id=" + URLEncoder.encode(forecastStr, "UTF-8") +
                                        "&user_id=" + URLEncoder.encode("???", "UTF-8");
                        Helper.excutePost(targetUrl,urlParameters);

                    }
                    catch(Exception e){
                        e.printStackTrace();
                    }

                    Intent intent = new Intent(getActivity(),BaseActivity.class);
                    startActivity(intent);
                }
            });
            return rootView;
        }
    }
}

