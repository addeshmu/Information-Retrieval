package com.example.amit.congressapp;

/**
 * Created by Amit on 11/17/2016.
 */

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by quocnguyen on 03/08/2016.
 */
public class CustomBillAdapter extends ArrayAdapter<bill_item> {

    ArrayList<bill_item> items;
    Context context;
    int resource;

    public CustomBillAdapter(Context context, int resource, ArrayList<bill_item> items) {
        super(context, resource, items);
        this.items =items;
        this.context = context;
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) getContext()
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.bill_list_item, null, true);

        }
        bill_item item = getItem(position);
        TextView txtBillId = (TextView) convertView.findViewById(R.id.txtId);
        txtBillId.setText(item.getId());
        TextView txtOfficialTitle = (TextView) convertView.findViewById(R.id.txtTitle);
        txtOfficialTitle.setText(item.getTitle());
        TextView textIntroOn =  (TextView) convertView.findViewById(R.id.txtIntro);
        textIntroOn.setText(item.getIntro());
        return convertView;
    }
}