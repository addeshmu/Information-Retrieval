package com.example.amit.congressapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavouritesFrag extends Fragment  {


    public FavouritesFrag() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentTabHost bTabHost;
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_favourites, container, false);
        bTabHost = (FragmentTabHost) view.findViewById(android.R.id.tabhost);
        bTabHost.setup(getActivity(), getChildFragmentManager(), R.id.realtabcontent);
        bTabHost.addTab(bTabHost.newTabSpec("tab9").setIndicator("Legislators"),fav_tab_leg.class,null);
        bTabHost.addTab(bTabHost.newTabSpec("tab10").setIndicator("Bills"),fav_tab_bill.class, null);
        bTabHost.addTab(bTabHost.newTabSpec("tab11").setIndicator("Committees"),fav_tab_com.class, null);
        return view;
        //return inflater.inflate(R.layout.fragment_bills, container, false);
    }


}
