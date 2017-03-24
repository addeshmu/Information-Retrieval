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
public class active_tab_bill extends Fragment {
    ArrayList<bill_item> arrayList;
    ListView stateList;
    String responseData;
    String[] bill_id = new String[50];

    RequestTask asyncTask;
    public active_tab_bill() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View viewS =  inflater.inflate(R.layout.fragment_active_tab_bill, container, false);
        arrayList = new ArrayList<>();
        stateList = (ListView)viewS.findViewById(R.id.BillListView);
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
                        String id = miniObj.get("bill_id").toString();
                        String title = miniObj.get("official_title").toString();
                        String date =  miniObj.get("introduced_on").toString();
                        SimpleDateFormat fromUser = new SimpleDateFormat("MMM dd,yyyy");
                        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
                        String reformattedStr = fromUser.format(myFormat.parse(date));
                        bill_id[i]=id;
                        bill_item item = new bill_item(id,title,reformattedStr);
                        arrayList.add(item);
                    }

                } catch (Throwable t) {
                    Log.e("My App", "Could not parse malformed JSON: \"" + responseData + "\"");
                }
                CustomBillAdapter adapter = new CustomBillAdapter(getContext(), R.layout.bill_list_item, arrayList);
                stateList.setAdapter(adapter);

            }

        },getContext()).execute("http://www.transparent-gov-add.appspot.com/hw8.php?operation=billsActive");
        stateList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getContext(),billDetailActivity.class);
                intent.putExtra("bill_id",bill_id[i]);
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

