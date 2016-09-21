package com.abdoofathy.movieapp;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DoAPICall extends AsyncTask<URL,Integer,String>{

    IOnDataReady data;

    public DoAPICall(IOnDataReady data){
        this.data = data;
    }
    @Override
    protected String doInBackground(URL... urls){
        BufferedReader reader;
        String jsonString;
        // Open Connection To API
        try {
            HttpURLConnection urlConnection = (HttpURLConnection) urls[0].openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            InputStream inputStream = urlConnection.getInputStream();
            StringBuilder buffer = new StringBuilder();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            jsonString = buffer.toString();
        }
        catch(IOException e){
            return "Exception: Please Check your internet connection";
        }
        return jsonString;
    }

    @Override
    protected void onPostExecute(String jsonString) {
        if(jsonString == null) {
            data.raiseError("No Internet Connection Found"); // thus jsonString sent is null
        }
        else if(jsonString.contains("Exception")){ // something went wrong with connection
            data.raiseError(jsonString);
        }else {
            data.getData(jsonString);
        }
    }
}
