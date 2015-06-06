package com.flipkart.age_of_sellers;

import android.content.Intent;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amitpatil on 5/6/15.
 */
public class ProfileFragment extends Fragment {
    @Nullable
    ArrayAdapter<String> sellerInfoAdapter;
    String setServerString = "";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        List<String> sellerInfo = new ArrayList<String>();

        Seller me = Seller.getInstance();

        me.setContext(this.getActivity());

        sellerInfo.add("Name: " + me.getName());
        sellerInfo.add("EMail: "+ me.geteMail());
        sellerInfo.add("Geo Location: "+ me.getGeoLocation());
        sellerInfo.add("Phone: "+ me.getPhone());

        sellerInfoAdapter =
                new ArrayAdapter<String>(
                        getActivity(), // The current context (this activity)
                        R.layout.seller_item, // The name of the layout ID.
                        R.id.seller_item_textview, // The ID of the textview to populate.
                        sellerInfo);
        View rootView = inflater.inflate(R.layout.seller_info_board, container, false);
        ListView listView = (ListView) rootView.findViewById(R.id.list_selleritem);
        listView.setAdapter(sellerInfoAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String forecast = sellerInfoAdapter.getItem(position);
                Intent intent = new Intent(getActivity(), DetailGameBoardActivity.class)
                        .putExtra(Intent.EXTRA_TEXT, forecast);
                startActivity(intent);
            }
        });

        return rootView;
    }

    public ProfileFragment(){
    }
}
