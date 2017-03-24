package com.example.amit.congressapp;


import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import static java.util.Collections.sort;


/**
 * A simple {@link Fragment} subclass.
 */
public class fav_tab_leg extends Fragment {
    public ArrayList<leg_item> arrayList1;
    ListView stateList1;
    String responseData;
    Map<String, Integer> mapIndex;
    View viewS;
    public CustomListAdapter adapter1;
    final String[] bio_guide1 = new String[1000];
    public fav_tab_leg() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewS = inflater.inflate(R.layout.fragment_fav_tab_leg, container, false);
        arrayList1 = new ArrayList<>();
        stateList1 = (ListView) viewS.findViewById(R.id.stateListView2);
        final String[] bio_guide = new String[1000];

        ArrayList<String> ar = new ArrayList<String>();
        final String[] url = {""};

        SharedPreferences sharedPref = getActivity().getSharedPreferences("NAME", Context.MODE_PRIVATE);
        //ArrayAdapter ad = (ArrayAdapter<String>)stateList.getAdapter();
        Map<String, ?> keys = sharedPref.getAll();
        int i = 0;
        findStateFull ft = new findStateFull();
        for (Map.Entry<String, ?> entry : keys.entrySet()) {
            Log.d("map values", entry.getKey() + ": " + entry.getValue().toString());
            String key = entry.getKey();
            if (key.split(">")[1].equals("bio")) {
                bio_guide[i] = key.split(">")[0];

                Object oo = entry.getValue();
                JSONObject miniObj = null;
                try {
                    miniObj = new JSONObject(oo.toString());
                    JSONArray arrr = miniObj.getJSONArray("results");
                    miniObj = arrr.getJSONObject(0);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    url[0] = "https://theunitedstates.io/images/congress/225x275/" + miniObj.get("bioguide_id").toString() + ".jpg";
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String bio = null;
                try {
                    bio = miniObj.get("last_name").toString() + "," + miniObj.get("first_name").toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    bio_guide[i] = miniObj.get("bioguide_id").toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String AllInfo = null;
                try {
                    if (miniObj.get("district").toString() != "null") {
                        AllInfo = "(" + miniObj.get("party").toString() + ")" + ft.getStateName(miniObj.get("state").toString()) + " - " + "District " + miniObj.get("district").toString();
                    } else {
                        AllInfo = "(" + miniObj.get("party").toString() + ")" + ft.getStateName(miniObj.get("state").toString()) + " - " + "District 0";
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String name = null;
                try {
                    name = miniObj.get("last_name").toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                leg_item item = new leg_item(url[0], bio, AllInfo,bio_guide[i]);
                arrayList1.add(item);
                ar.add(name);
                i++;
            }


        }

        adapter1 = new CustomListAdapter(getContext(), R.layout.state_list_item, arrayList1);
        stateList1.setAdapter(adapter1);
        // Arrays.sort(ar);
        getIndexList(ar);
        displayIndex();


        stateList1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getContext(), legDetailActivity.class);
                intent.putExtra("bioguide", bio_guide[i]);
                startActivity(intent);
            }
        });
        return viewS;
    }

    private void getIndexList(ArrayList<String> fruits) {
        mapIndex = new LinkedHashMap<String, Integer>();
        for (int i = 0; i < fruits.size(); i++) {
            if (fruits.get(i) != null) {
                String fruit = fruits.get(i);
                String index = fruit.substring(0, 1);

                if (mapIndex.get(index) == null)
                    mapIndex.put(index, i);
            } else {
                break;
            }
        }
    }

    private void displayIndex() {

        LinearLayout indexLayout = (LinearLayout) viewS.findViewById(R.id.side_layout7);
        indexLayout.removeAllViews();
        TextView textView;
        List<String> indexList = new ArrayList<String>(mapIndex.keySet());
        for (String index : indexList) {
            textView = (TextView) getActivity().getLayoutInflater().inflate(
                    R.layout.side_index_fav, null);
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

    public void onResume() {

        ArrayList<leg_item> arr2 = new ArrayList<leg_item>(arrayList1) ;

        SharedPreferences sharedPref = getActivity().getSharedPreferences("NAME", Context.MODE_PRIVATE);

        int i,j=0;
        arrayList1.clear();
        /*final String[] ar2 = new String[500];*/
        ArrayList<String> ar2 = new ArrayList<String>();
        for (i = 0; i < arr2.size(); i++) {

            leg_item lo = arr2.get(i);
            if (sharedPref.contains(lo.getBioguide() + ">bio")) {
                arrayList1.add(lo);
                bio_guide1[j]=lo.getBioguide();
                String am = lo.getBio();
                String last = am.split(",")[0];
                ar2.add(last);
                j++;
                stateList1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int j, long l) {
                        final Intent intent = new Intent(getContext(), legDetailActivity.class);
                        intent.putExtra("bioguide",bio_guide1[j] );

                        startActivity(intent);
                    }
                });
            }
        }


        Collections.sort(ar2);

        Collections.sort(arrayList1, new Comparator<leg_item>() {
            @Override
            public int compare(leg_item leg, leg_item t1) {
                return leg.getBio().compareTo(t1.getBio());

            }
        });
        adapter1.notifyDataSetChanged();

        getIndexList(ar2);
        displayIndex();
        super.onResume();


    }

}