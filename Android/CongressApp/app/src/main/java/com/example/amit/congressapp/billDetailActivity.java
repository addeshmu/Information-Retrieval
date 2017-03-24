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

import java.net.URI;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import static java.lang.Math.abs;
import static java.security.AccessController.getContext;

public class billDetailActivity extends AppCompatActivity {
    String responseData;
    JSONObject miniObj;
    TextView pN;
    String url="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_detail);
        Bundle extras = getIntent().getExtras();
        setTitle("Bill Info");

        if(extras!=null){
            String newString = extras.getString("bill_id");
            String newnew = newString;
             url = "https://congress.api.sunlightfoundation.com/bills?apikey=c113fa1d19e84e5e82663f29559830db&bill_id="+newString;
        }
        RequestTask asyncTask = (RequestTask) new RequestTask(new RequestTask.AsyncResponse(){

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

                    miniObj =arr.getJSONObject(0);

                    ImageView img00 = (ImageView)findViewById(R.id.Bstar);
                    SharedPreferences sharedPref = getSharedPreferences("NAME", Context.MODE_PRIVATE);
                    final String abc = miniObj.get("bill_id").toString();
                    if (sharedPref.contains(abc+">bill")){
                        img00.setBackgroundResource(0);
                        img00.setBackgroundResource(R.drawable.ic_action_name);
                        img00.setTag(2);
                    }else{
                        img00.setBackgroundResource(0);
                        img00.setBackgroundResource(R.drawable.ic_star_nofill);
                        img00.setTag(1);
                    }
                    img00.setOnClickListener(new View.OnClickListener(){
                        public void onClick(View v){
                            ImageView imageview = (ImageView)findViewById(R.id.Bstar);

                            if (Integer.parseInt(imageview.getTag().toString()) == 1) {
                                imageview.setBackgroundResource(0);
                                imageview.setBackgroundResource(R.drawable.ic_action_name);
                                imageview.setTag(2);
                                SharedPreferences sharedPref = getSharedPreferences("NAME",Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPref.edit();
                                editor.putString(abc+">bill", responseData);
                                editor.commit();

                            } else {
                                imageview.setBackgroundResource(0);
                                imageview.setBackgroundResource(R.drawable.ic_star_nofill);
                                SharedPreferences sharedPref = getSharedPreferences("NAME",Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPref.edit();
                                editor.remove(abc+">bill");
                                editor.commit();
                                imageview.setTag(1);

                            }



                        }
                    });




                    pN = (TextView) findViewById(R.id.viewDetailId);
                    pN.setText(miniObj.get("bill_id").toString());

                    pN = (TextView) findViewById(R.id.viewDetailBTitle);
                    pN.setText(miniObj.get("official_title").toString().equals("null")?"N.A":miniObj.get("official_title").toString());

                    pN = (TextView) findViewById(R.id.viewDetailBtype);
                    pN.setText(miniObj.get("bill_type").toString().equals("null")?"N.A":miniObj.get("bill_type").toString());

                    pN = (TextView) findViewById(R.id.viewDetailSponsor);
                    JSONObject temp =(JSONObject) miniObj.get("sponsor");
                    pN.setText(temp.get("title").toString()+'.'+temp.get("last_name").toString()+','+temp.get("first_name").toString());

                    pN = (TextView) findViewById(R.id.viewDetailBChamber);
                    pN.setText(miniObj.get("chamber").toString().substring(0, 1).toUpperCase() + miniObj.get("chamber").toString().substring(1));

                    pN = (TextView) findViewById(R.id.viewDetailStatus);
                    temp =(JSONObject) miniObj.get("history");
                    String bool="New";
                    if(temp.get("active").toString().equals("true")) {
                        bool = "Active";
                    }
                    pN.setText(bool);


                    pN = (TextView) findViewById(R.id.viewDetailIntro);
                    SimpleDateFormat fromUser = new SimpleDateFormat("MMM dd,yyyy");
                    SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String reformattedStrS = fromUser.format(myFormat.parse(miniObj.get("introduced_on").toString()));
                    pN.setText(reformattedStrS);

                    pN = (TextView) findViewById(R.id.viewDetailCUrl);
                    temp =(JSONObject) miniObj.get("urls");
                    pN.setText(temp.get("congress").toString().equals("null")?"N.A":temp.get("congress").toString());
                    final Intent intent1 = new Intent();
                    intent1.setAction(Intent.ACTION_VIEW);
                    intent1.addCategory(Intent.CATEGORY_BROWSABLE);
                    intent1.setData(Uri.parse(temp.get("congress").toString()));
                    pN.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            startActivity(intent1);
                        }
                    });

                    pN = (TextView) findViewById(R.id.viewDetailVStatus);
                    temp =(JSONObject) miniObj.get("last_version");
                    pN.setText(temp.get("version_name").toString().equals("null")?"N.A":temp.get("version_name").toString());

                    pN = (TextView) findViewById(R.id.viewDetailBUrl);
                    temp =(JSONObject) miniObj.get("last_version");
                    JSONObject temp2 =(JSONObject)temp.get("urls");
                    pN.setText(temp2.get("pdf").toString());
                    final Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                    intent.setData(Uri.parse(temp2.get("pdf").toString()));
                    pN.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            startActivity(intent);
                        }
                    });



                    ImageView imageView = (ImageView) findViewById(R.id.chamberImageBill);
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
