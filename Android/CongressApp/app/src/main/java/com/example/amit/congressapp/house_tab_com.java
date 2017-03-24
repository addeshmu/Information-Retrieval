package com.example.amit.congressapp;


import android.content.Intent;
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
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class house_tab_com extends Fragment {
    ArrayList<com_item> arrayList;
    ListView stateList;
    String responseData;
    String[] cid = new String[50];
    RequestTask asyncTask;

    public house_tab_com() {
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


        asyncTask = (RequestTask) new RequestTask(new RequestTask.AsyncResponse(){

            @Override

            public void processFinish(String output){
                //Here you will receive the result fired from async class
                //of onPostExecute(result) method.

                try {
                    responseData = output;
                    JSONObject obj = new JSONObject(responseData);


                    JSONObject objj =(JSONObject) obj.get("page");
                    int limit = (int)objj.get("count");
                    JSONArray arr = obj.getJSONArray("results");

                    for(int i = 0; i<limit; i++){
                        JSONObject miniObj =arr.getJSONObject(i);
                        String id = miniObj.get("committee_id").toString();
                        String title = miniObj.get("name").toString();
                        String date =  miniObj.get("chamber").toString();
                        cid[i]=id;
                        com_item item = new com_item(id,title,date);
                        arrayList.add(item);
                    }

                } catch (Throwable t) {
                    Log.e("My App", "Could not parse malformed JSON: \"" + responseData + "\"");
                }
                CustomComAdapter adapter = new CustomComAdapter(getContext(), R.layout.com_list_item, arrayList);
                stateList.setAdapter(adapter);

            }

        },getContext()).execute("http://104.198.0.197:8080/committees?apikey=c113fa1d19e84e5e82663f29559830db&per_page=all&chamber=house");
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
    public void onPause()
    {
        super.onPause();
        asyncTask.cancel(true);



    }
}

