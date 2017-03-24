package com.example.amit.congressapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Currency;
import java.util.Date;


import static java.lang.Math.abs;
import static java.lang.Math.negateExact;

public class legDetailActivity extends AppCompatActivity {
    String responseData="";
    JSONObject miniObj = null;

    private Handler mHandler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leg_detail);
        Bundle extras = getIntent().getExtras();
        setTitle("Legislator Info");

        // experiment with the ActionBar
        //ActionBar actionBar = getSupportActionBar();
        //actionBar.setDisplayHomeAsUpEnabled(true);
        String url="";
        final String profileUrl="";

        if(extras!=null){
            String newString = extras.getString("bioguide");
            String newnew = newString;
            url  = "http://www.transparent-gov-add.appspot.com/hw8.php?operation=legSubOpBioGuideIndividual&bioguide_id="+newnew;
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

                    final JSONObject miniObj =arr.getJSONObject(0);

                    ImageView img00 = (ImageView)findViewById(R.id.link4);
                    SharedPreferences sharedPref = getSharedPreferences("NAME",Context.MODE_PRIVATE);
                    final String abc = miniObj.get("bioguide_id").toString();
                    if (sharedPref.contains(abc+">bio")){
                        img00.setBackgroundResource(R.drawable.ic_action_name);
                        img00.setTag(2);
                    }else{
                    img00.setBackgroundResource(0);
                    img00.setBackgroundResource(R.drawable.ic_star_nofill);
                    img00.setTag(1);
                    }
                    img00.setOnClickListener(new View.OnClickListener(){
                        public void onClick(View v){
                            ImageView imageview = (ImageView)findViewById(R.id.link4);

                            if (Integer.parseInt(imageview.getTag().toString()) == 1) {
                                imageview.setBackgroundResource(R.drawable.ic_action_name);
                                imageview.setTag(2);
                                SharedPreferences sharedPref = getSharedPreferences("NAME",Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPref.edit();
                                editor.putString(abc+">bio", responseData);
                                editor.commit();

                            } else {
                                imageview.setBackgroundResource(R.drawable.ic_star_nofill);
                                SharedPreferences sharedPref = getSharedPreferences("NAME",Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPref.edit();
                                editor.remove(abc+">bio");
                                editor.commit();
                                imageview.setTag(1);

                            }



                        }
                    });
                    // Declare the animations and initialize them




                    ImageView img0 = (ImageView)findViewById(R.id.link1);
                    try {
                        if (!miniObj.has("website") || miniObj.get("website").toString().equals("null")) {
                            img0.setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {

                                    Context context = getApplicationContext();
                                    CharSequence text = "WEBSITE link not available for this person.";
                                    int duration = Toast.LENGTH_SHORT;

                                    Toast toast = Toast.makeText(context, text, duration);
                                    toast.show();
                                }
                            });
                        } else {

                            img0.setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    Intent intent = new Intent();
                                    intent.setAction(Intent.ACTION_VIEW);
                                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                                    try {
                                        intent.setData(Uri.parse((String) miniObj.get("website")));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    startActivity(intent);
                                }
                            });
                        }
                    }
                    catch (JSONException e){e.printStackTrace();}
                    ImageView img1 = (ImageView)findViewById(R.id.link2);
                    try{
                    if(!miniObj.has("facebook_id")||miniObj.get("facebook_id").toString().equals("null"))
                    {
                        img1.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {

                                Context context = getApplicationContext();
                                CharSequence text = "Facebook link not available for this person.";
                                int duration = Toast.LENGTH_SHORT;

                                Toast toast = Toast.makeText(context, text, duration);
                                toast.show();
                            }
                        });
                    }
                    else{

                    img1.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            Intent intent = new Intent();
                            intent.setAction(Intent.ACTION_VIEW);
                            intent.addCategory(Intent.CATEGORY_BROWSABLE);
                            try {
                                intent.setData(Uri.parse("http://www.facebook.com/" + miniObj.get("facebook_id").toString()));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            startActivity(intent);
                        }
                    });
                    }
                    }catch (JSONException e){e.printStackTrace();}



                    ImageView img2 = (ImageView)findViewById(R.id.link3);
                    try {
                        if (!miniObj.has("twitter_id") || miniObj.get("twitter_id").toString().equals("null")) {
                            img2.setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {

                                    Context context = getApplicationContext();
                                    CharSequence text = "TWITTER link not available for this person.";
                                    int duration = Toast.LENGTH_SHORT;

                                    Toast toast = Toast.makeText(context, text, duration);
                                    toast.show();
                                }
                            });
                        } else {

                            img2.setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    Intent intent = new Intent();
                                    intent.setAction(Intent.ACTION_VIEW);
                                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                                    try {
                                        intent.setData(Uri.parse("http://www.twitter.com/" + miniObj.get("twitter_id").toString()));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    startActivity(intent);
                                }
                            });
                        }
                    }
                    catch (JSONException e){e.printStackTrace();}
                    /*Context context = getApplicationContext();
                    CharSequence text = "Hello toast!";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();*/

                    ImageView imageView = (ImageView) findViewById(R.id.profileImage);
                    //  Log.v("ADD",item.getImage());
                    String profileUrl = "https://theunitedstates.io/images/congress/225x275/"+miniObj.get("bioguide_id").toString()+".jpg";
                    Picasso.with(getBaseContext()).load(profileUrl).into(imageView);

                    ImageView imageView2 = (ImageView) findViewById(R.id.partyImage);
                    //  Log.v("ADD",item.getImage());
                    String partyName = "";
                    if((miniObj.get("party").toString()).equals("D")) {
                        profileUrl = "http://cs-server.usc.edu:45678/hw/hw8/images/d.png";
                        partyName = "Democrat";
                    }else if(miniObj.get("party").toString().equals("R")){
                        profileUrl ="http://cs-server.usc.edu:45678/hw/hw8/images/r.png";
                        partyName="Republican";
                    }else{
                        profileUrl = "http://independentamericanparty.org/wp-content/themes/v/images/logo-american-heritage-academy.png";
                        partyName = "Independent";
                    }
                    Picasso.with(getBaseContext()).load(profileUrl).into(imageView2);
                    TextView pN = (TextView) findViewById(R.id.partyName);
                    pN.setText(partyName);

                    pN = (TextView) findViewById(R.id.viewDetailName);
                    pN.setText(miniObj.get("title").toString().equals("null")?"":miniObj.get("title").toString()+"."+miniObj.get("last_name").toString()+","+miniObj.get("first_name").toString());

                    pN = (TextView) findViewById(R.id.viewDetailEmail);
                    pN.setText(miniObj.get("oc_email").toString().equals("null")?"N.A":miniObj.get("oc_email").toString());

                    pN = (TextView) findViewById(R.id.viewDetailChamber);
                    pN.setText(miniObj.get("chamber").toString().substring(0, 1).toUpperCase() + miniObj.get("chamber").toString().substring(1));

                    pN = (TextView) findViewById(R.id.viewDetailContact);
                    pN.setText(miniObj.get("phone").toString().equals("null")?"N.A":miniObj.get("phone").toString());

                    pN = (TextView) findViewById(R.id.viewDetailSterm);
                    SimpleDateFormat fromUser = new SimpleDateFormat("MMM dd,yyyy");
                    SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String reformattedStrS = fromUser.format(myFormat.parse(miniObj.get("term_start").toString()));
                    pN.setText(reformattedStrS);

                    pN = (TextView) findViewById(R.id.viewDetailEterm);
                    fromUser = new SimpleDateFormat("MMM dd,yyyy");
                    myFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String reformattedStrE = fromUser.format(myFormat.parse(miniObj.get("term_end").toString()));
                    pN.setText(reformattedStrE);

                    pN = (TextView) findViewById(R.id.viewDetailOffice);
                    pN.setText(miniObj.get("office").toString().equals("null")?"N.A":miniObj.get("office").toString());

                    pN = (TextView) findViewById(R.id.viewDetailState);
                    pN.setText(ft.getStateName(miniObj.get("state").toString()));

                    pN = (TextView) findViewById(R.id.viewDetailFax);
                    pN.setText(miniObj.get("fax").toString().equals("null")?"N.A.":miniObj.get("fax").toString());

                    pN = (TextView) findViewById(R.id.viewDetailBday);
                    fromUser = new SimpleDateFormat("MMM dd,yyyy");
                    myFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String reformattedStr = fromUser.format(myFormat.parse(miniObj.get("birthday").toString()));
                    pN.setText(reformattedStr);

                    ProgressBar  mProgress = (ProgressBar) findViewById(R.id.viewDetailsProgBar);
                    mProgress.setMax(100);
                    mProgress.setProgress(0);
                    final DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd");
                    final LocalDate st = dtf.parseLocalDate(miniObj.get("term_start").toString());
                    final LocalDate et = dtf.parseLocalDate(miniObj.get("term_end").toString());
                    Date date = new Date();
                    LocalDate td = new LocalDate(date);
                    int tdays = abs(Days.daysBetween(et,st).getDays());
                    int cdays = abs(Days.daysBetween(td,st).getDays());
                    int prog = (int)(((float)((float)(cdays)/(float)(tdays)))*100);
                    mProgress.setProgress(prog);
                    pN = (TextView) findViewById(R.id.viewDetailsProgBarPercentage);
                    pN.setText(String.valueOf(prog)+"%");


                } catch (Throwable t) {
                    Log.e("My App", "Could not parse malformed JSON: \"" + responseData + "\"");
                }

            }

        },this).execute(url);




    }



}
