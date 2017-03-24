package com.example.amit.congressapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import static java.util.Collections.sort;


/**
 * A simple {@link Fragment} subclass.
 */
public class house_tab_leg extends Fragment {
    ArrayList<leg_item> arrayList1;
    ListView stateList1;
    String responseData;
    Map<String, Integer> mapIndex;
    public house_tab_leg() {
        // Required empty public constructor
    }
    RequestTask asyncTask;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View viewS =  inflater.inflate(R.layout.fragment_house_tab_leg, container, false);
        arrayList1 = new ArrayList<>();
        stateList1 = (ListView)viewS.findViewById(R.id.stateListView);
        final String [] bio_guide = new String[1000];
        final String[] ar = new String [500];
        final String[] url = {""};


        asyncTask = (RequestTask) new RequestTask(new RequestTask.AsyncResponse(){

            @Override

            public void processFinish(String output){
                //Here you will receive the result fired from async class
                //of onPostExecute(result) method.

                try {
                    responseData = output;
                    JSONObject obj = new JSONObject(responseData);

                    //ArrayAdapter ad = (ArrayAdapter<String>)stateList.getAdapter();

                    int limit = (int)obj.get("count");
                    JSONArray arr = obj.getJSONArray("results");
                    findStateFull ft = new findStateFull();
                    for(int i = 0; i<limit; i++){

                        JSONObject miniObj =arr.getJSONObject(i);
                        url[0] = "https://theunitedstates.io/images/congress/225x275/"+miniObj.get("bioguide_id").toString()+".jpg";
                        String bio = miniObj.get("last_name").toString()+","+miniObj.get("first_name").toString();
                        bio_guide[i] = miniObj.get("bioguide_id").toString();
                        String AllInfo;
                        if(miniObj.get("district").toString()!="null") {
                            AllInfo = "(" + miniObj.get("party").toString() + ")" + ft.getStateName(miniObj.get("state").toString()) + " - " + "District " + miniObj.get("district").toString();
                        }else{
                            AllInfo = "(" + miniObj.get("party").toString() + ")" + ft.getStateName(miniObj.get("state").toString()) + " - " + "District 0";
                        }
                        String name = miniObj.get("last_name").toString();
                        leg_item item = new leg_item(url[0],bio,AllInfo,bio_guide[i]);
                        arrayList1.add(item);
                        ar[i]=name;
                        //ad.add(row);
                        //     ad.add(miniObj.get("bioguide_id"));
                    }

                } catch (Throwable t) {
                    Log.e("My App", "Could not parse malformed JSON: \"" + responseData + "\"");
                }
                CustomListAdapter adapter1 = new CustomListAdapter(getContext(), R.layout.state_list_item, arrayList1
                );
                stateList1.setAdapter(adapter1);
                // Arrays.sort(ar);
                getIndexList(ar);
                displayIndex();

            }

        },getContext()).execute("http://www.transparent-gov-add.appspot.com/hw8.php?operation=legislatorsHouse");
        stateList1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getContext(),legDetailActivity.class);
                intent.putExtra("bioguide",bio_guide[i]);
                startActivity(intent);
            }
        });
        return viewS;
    }
    private void getIndexList(String[] fruits) {
        mapIndex = new LinkedHashMap<String, Integer>();
        for (int i = 0; i < fruits.length; i++) {
            if (fruits[i] != null) {
                String fruit = fruits[i];
                String index = fruit.substring(0, 1);

                if (mapIndex.get(index) == null)
                    mapIndex.put(index, i);
            }else{
                break;
            }
        }
    }
    private void displayIndex() {
        LinearLayout indexLayout = (LinearLayout) getView().findViewById(R.id.side_layout);

        TextView textView;
        List<String> indexList = new ArrayList<String>(mapIndex.keySet());
        for (String index : indexList) {
            textView = (TextView) getActivity().getLayoutInflater().inflate(
                    R.layout.side_index, null);
            textView.setText(index);
            //  textView.setOnClickListener();
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView selectedIndex = (TextView) v;
                    stateList1.setSelection(mapIndex.get(selectedIndex.getText()));
                }
            });
            indexLayout.addView(textView);
        }
    }

    public void onPause()
    {
        super.onPause();
        asyncTask.cancel(true);

    }

}

