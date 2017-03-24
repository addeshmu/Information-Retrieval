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
public class CustomListAdapter extends ArrayAdapter<leg_item> {

    ArrayList<leg_item> items;
    Context context;
    int resource;

    public CustomListAdapter(Context context, int resource, ArrayList<leg_item> items) {
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
            convertView = layoutInflater.inflate(R.layout.state_list_item, null, true);

        }
        leg_item item = getItem(position);

        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageViewProduct);
      //  Log.v("ADD",item.getImage());
        Picasso.with(context).load(item.getImage()).into(imageView);

//        try {
//            imageView.setImageBitmap(BitmapFactory.decodeFile(item.getImage());
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }

        //Picasso.with(context).load(item.getImage()).into(imageView);
        TextView txtName = (TextView) convertView.findViewById(R.id.txtName);
        txtName.setText(item.getBio());
        TextView txtInfo1 = (TextView) convertView.findViewById(R.id.txtInfo);
        txtInfo1.setText(item.getInfo());


        return convertView;
    }
}