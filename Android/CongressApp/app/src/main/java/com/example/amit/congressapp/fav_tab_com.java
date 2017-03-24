package com.example.amit.congressapp;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class fav_tab_com extends Fragment {
    ArrayList<com_item> arrayList;
    ListView stateList;
    String responseData;
    String[] cid = new String[50];
    final String[] ccid = new String[1000];
    CustomComAdapter adapter;
    public fav_tab_com() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View viewS =  inflater.inflate(R.layout.fragment_house_tab_com, container, false);
        arrayList = new ArrayList<>();
        stateList = (ListView)viewS.findViewById(R.id.comListView);
        final String [] bio_guide = new String[1000];
        final String[] ar = new String [1000];
        final String[] url = {""};

        SharedPreferences sharedPref = getActivity().getSharedPreferences("NAME", Context.MODE_PRIVATE);
        //ArrayAdapter ad = (ArrayAdapter<String>)stateList.getAdapter();
        Map<String, ?> keys = sharedPref.getAll();
        int i = 0;
        findStateFull ft = new findStateFull();
        for (Map.Entry<String, ?> entry : keys.entrySet()) {
            Log.d("map values", entry.getKey() + ": " + entry.getValue().toString());
            String key = entry.getKey();
            if (key.split(">")[1].equals("com")) {
                bio_guide[i] = key.split(">")[0];
                Object oo = entry.getValue();
                JSONObject miniObj = null;
                try {
                    miniObj = new JSONObject(oo.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JSONArray arrr = null;
                try {
                    arrr = miniObj.getJSONArray("results");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    miniObj = arrr.getJSONObject(0);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                String id = null;
                try {
                    id = miniObj.get("committee_id").toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String title = null;
                try {
                    title = miniObj.get("name").toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String date = null;
                try {
                    date = miniObj.get("chamber").toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                cid[i] = id;
                i++;
                com_item item = new com_item(id, title, date);
                arrayList.add(item);
            }

        }
        adapter = new CustomComAdapter(getContext(), R.layout.com_list_item, arrayList);
        stateList.setAdapter(adapter);


        stateList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getContext(),comDetailActivity.class);
                intent.putExtra("committee_id",cid[i]);
                startActivity(intent);
            }
        });
        return viewS;
    }
    public void onResume() {

        ArrayList<com_item> arr2 = new ArrayList<com_item>(arrayList) ;

        SharedPreferences sharedPref = getActivity().getSharedPreferences("NAME", Context.MODE_PRIVATE);

        int i,j=0;
        arrayList.clear();
        for (i = 0; i < arr2.size(); i++) {

            com_item lo = arr2.get(i);
            if (sharedPref.contains(lo.getCId() + ">com")) {
                arrayList.add(lo);
                ccid[j]=lo.getCId();
                j++;
                stateList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int j, long l) {
                        final Intent intent = new Intent(getContext(), comDetailActivity.class);
                        intent.putExtra("committee_id",ccid[j] );

                        startActivity(intent);
                    }
                });
            }
        }



        adapter.notifyDataSetChanged();
        super.onResume();


    }
}

