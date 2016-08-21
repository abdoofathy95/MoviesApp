package com.abdoofathy.movieapp;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Awesome on 8/12/2016.
 */
public class MovieTrailerUrlList implements IOnDataReady, IJSONParser {
    private Context context;
    private List<MovieTrailerUrl> trailersUrls ;
    private TrailerArrayAdapter trailerArrayAdapter;

    public MovieTrailerUrlList(Context context, TrailerArrayAdapter trailerArrayAdapter, List<MovieTrailerUrl> trailersUrls){
        this.context = context;
        this.trailerArrayAdapter = trailerArrayAdapter;
        this.trailersUrls = trailersUrls;
    }

    @Override
    public void getData(String string) { // parse data
        try {
            JSONObject jsonObject = new JSONObject(string);
            parse(jsonObject);
        } catch (JSONException e) {
            raiseError(e.toString());
        }
    }

    @Override
    public void raiseError(String string) {
        ((Activity)context).finish();
        Toast.makeText(context, string, Toast.LENGTH_LONG).show();
    }

    @Override
    public void parse(JSONObject jsonObject) {
        try {
            JSONArray jsonArray = jsonObject.getJSONArray("results");
            JSONObject trailerJsonObject;
            MovieTrailerUrl movieTrailerUrl;
            for (int i=0; i<jsonArray.length(); i++){
                movieTrailerUrl = new MovieTrailerUrl();
                trailerJsonObject = (JSONObject) jsonArray.get(i);
                movieTrailerUrl.setTrailerName(trailerJsonObject.getString("name"));
                movieTrailerUrl.setTrailerUrl(trailerJsonObject.getString("key"));
                trailersUrls.add(i,movieTrailerUrl);
            }
            trailerArrayAdapter.notifyDataSetChanged();

        } catch (JSONException e) {
            raiseError(e.toString());
        }
    }
}
