package com.example.amit.congressapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;


/**
 * A simple {@link Fragment} subclass.
 */
public class BillsFrag extends Fragment  {


    public BillsFrag() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentTabHost bTabHost;
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_bills, container, false);
        bTabHost = (FragmentTabHost) view.findViewById(android.R.id.tabhost);
        bTabHost.setup(getActivity(), getChildFragmentManager(), R.id.realtabcontent);
        bTabHost.addTab(bTabHost.newTabSpec("tab4").setIndicator("Active Bills"),active_tab_bill.class,null);
        bTabHost.addTab(bTabHost.newTabSpec("tab5").setIndicator("New Bills"),new_tab_bill.class, null);
        return view;
        //return inflater.inflate(R.layout.fragment_bills, container, false);
    }


}
