package com.example.amit.congressapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import static java.lang.Math.abs;
import static java.lang.Math.min;

public class comDetailActivity extends AppCompatActivity {
    String responseData;
    JSONObject miniObj;
    TextView pN;
    String url="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_com_detail);
        Bundle extras = getIntent().getExtras();
        setTitle("Committee Info");

        if(extras!=null){
            String newString = extras.getString("committee_id");
            String newnew = newString;
            url = "http://104.198.0.197:8080/committees?apikey=c113fa1d19e84e5e82663f29559830db&committee_id="+newString;
        }
        RequestTask asyncTask = (RequestTask) new RequestTask(new RequestTask.AsyncResponse(){

            @Override

            public void processFinish(String output){
                //Here you will receive the result fired from async class
                //of onPostExecute(result) method.

                try {
                    responseData = output;
                    String NA = "N.A.";
                    JSONObject obj = new JSONObject(responseData);

                    //ArrayAdapter ad = (ArrayAdapter<String>)stateList.getAdapter();

                    int limit = (int)obj.get("count");
                    JSONArray arr = obj.getJSONArray("results");
                    findStateFull ft = new findStateFull();

                    miniObj =arr.getJSONObject(0);

                    ImageView img00 = (ImageView)findViewById(R.id.Cstar);
                    SharedPreferences sharedPref = getSharedPreferences("NAME", Context.MODE_PRIVATE);
                    final String abc = miniObj.get("committee_id").toString();
                    if (sharedPref.contains(abc+">com")){
                        img00.setBackgroundResource(R.drawable.ic_action_name);
                        img00.setTag(2);
                    }else{

                        img00.setBackgroundResource(0);
                        img00.setBackgroundResource(R.drawable.ic_star_nofill);
                        img00.setTag(1);
                    }
                    img00.setOnClickListener(new View.OnClickListener(){
                        public void onClick(View v){
                            ImageView imageview = (ImageView)findViewById(R.id.Cstar);

                            if (Integer.parseInt(imageview.getTag().toString()) == 1) {
                                imageview.setBackgroundResource(R.drawable.ic_action_name);
                                imageview.setTag(2);
                                SharedPreferences sharedPref = getSharedPreferences("NAME",Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPref.edit();
                                editor.putString(abc+">com", responseData);
                                editor.commit();

                            } else {
                                imageview.setBackgroundResource(R.drawable.ic_star_nofill);
                                SharedPreferences sharedPref = getSharedPreferences("NAME",Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPref.edit();
                                editor.remove(abc+">com");
                                editor.commit();
                                imageview.setTag(1);

                            }



                        }
                    });


                    pN = (TextView) findViewById(R.id.viewDetailCId);
                    pN.setText(miniObj.get("committee_id").toString());

                    pN = (TextView) findViewById(R.id.viewDetailCName);
                    pN.setText(miniObj.get("name").toString());

                    pN = (TextView) findViewById(R.id.viewDetailCChamber);
                    pN.setText(miniObj.get("chamber").toString().substring(0, 1).toUpperCase() + miniObj.get("chamber").toString().substring(1));

                    pN = (TextView) findViewById(R.id.viewDetailPC);
                    if(miniObj.has("parent_committee_id")) {
                        pN.setText(miniObj.get("parent_committee_id").toString());
                    }else{
                        pN.setText(NA);
                    }

                    pN = (TextView) findViewById(R.id.viewDetailCContact);
                    if(miniObj.has("phone")) {
                        pN.setText(miniObj.get("phone").toString());
                    }else{
                        pN.setText(NA);
                    }
                    pN = (TextView) findViewById(R.id.viewDetailCOffice);
                    if(miniObj.has("office")) {
                        pN.setText(miniObj.get("office").toString());
                    }else{

                        pN.setText(NA);
                    }

                    ImageView imageView = (ImageView) findViewById(R.id.chamberImageCom);
                    String profileUrl =null;
                    if(miniObj.get("chamber").toString().equals("house")){
                        profileUrl = "http://cs-server.usc.edu:45678/hw/hw8/images/h.png";
                        Picasso.with(getBaseContext()).load(profileUrl).into(imageView);
                    }else{
                        profileUrl = "http://cs-server.usc.edu:45678/hw/hw8/images/s.svg";
                        imageView.setImageResource(R.drawable.ic_senate);
                    }


                } catch (Throwable t) {
                    Log.e("My App", "Could not parse malformed JSON: \"" + responseData + "\"");
                }

            }

        },this).execute(url);
    }
}
