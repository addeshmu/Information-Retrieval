package com.example.amit.congressapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class CommFrag extends Fragment {


    public CommFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentTabHost bTabHost;
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_comm, container, false);
        bTabHost = (FragmentTabHost) view.findViewById(android.R.id.tabhost);
        bTabHost.setup(getActivity(), getChildFragmentManager(), R.id.realtabcontent);
        bTabHost.addTab(bTabHost.newTabSpec("tab6").setIndicator("House"),house_tab_com.class,null);
        bTabHost.addTab(bTabHost.newTabSpec("tab7").setIndicator("Senate"),senate_tab_com.class, null);
        bTabHost.addTab(bTabHost.newTabSpec("tab8").setIndicator("Joint"),joint_tab_com.class, null);
        return view;
    }

}
