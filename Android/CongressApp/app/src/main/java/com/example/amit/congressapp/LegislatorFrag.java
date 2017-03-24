package com.example.amit.congressapp;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;


/**
 * A simple {@link Fragment} subclass.
 */
public class LegislatorFrag extends Fragment {

    FragmentTabHost mTabHost;
    public LegislatorFrag() {
        // Required empty public constructor
    }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {

            View view =  inflater.inflate(R.layout.fragment_legislator, container, false);
            mTabHost = (FragmentTabHost)view.findViewById(android.R.id.tabhost);
            mTabHost.setup(getActivity(), getChildFragmentManager(), R.id.realtabcontent);
        mTabHost.addTab(mTabHost.newTabSpec("tab1").setIndicator("State"),
                state_tab_leg.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("tab2").setIndicator("House"),
                house_tab_leg.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("tab3").setIndicator("Senate"),
                senate_tab_leg.class, null);

        return view;
        // Inflate the layout for this fragment
       // return inflater.inflate(R.layout.fragment_legislator, container, false);
    }

}
