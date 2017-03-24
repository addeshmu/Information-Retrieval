package com.example.amit.congressapp;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public  class RequestTask extends AsyncTask<String, String, String> {

    Activity activity;
    Context context;
    public interface AsyncResponse {
        void processFinish(String output);
    }
    public AsyncResponse delegate = null;
    public RequestTask(AsyncResponse delegate,Context context ){
        this.delegate = delegate;
        this.context = context;
    }

    HttpURLConnection urlConnection;

    @Override
        protected String doInBackground(String... args) {

        StringBuilder result = new StringBuilder();

        try {
            URL url = new URL(args[0]);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());

            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

        }catch( Exception e) {
            e.printStackTrace();
        }
        finally {
            urlConnection.disconnect();
        }


        return result.toString();
    }



    @Override
    protected void onPostExecute(String result){

        delegate.processFinish(result);
        //Do something with the JSON string
    }

}