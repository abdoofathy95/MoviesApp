package com.abdoofathy.movieapp;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class MoviesList implements IOnDataReady, IJSONParser {
    private List<Movie> moviesList;
    private Context context;
    private ImageAdapter imageAdapter;

    public MoviesList(Context context, ImageAdapter imageAdapter, List<Movie> moviesList){
        this.context = context;
        this.imageAdapter = imageAdapter;
        this.moviesList = moviesList;
    }

    @Override
    public void getData(String string) {
        try {
            JSONObject jsonObject = new JSONObject(string);
            parse(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void raiseError(String string) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).show();
        ((Activity)context).finish();
    }

    @Override
    public void parse(JSONObject jsonObject) {
        JSONArray jsonArray = null;
        moviesList.clear();
        try {
            jsonArray = jsonObject.getJSONArray("results");
            Movie movie;
            JSONObject movieJsonObject;
            for (int i=0; i<jsonArray.length(); i++){
                movieJsonObject = (JSONObject) jsonArray.get(i);
                movie = new Movie();
                movie.parse(movieJsonObject);
                moviesList.add(i,movie);
            }
            // data changed
            imageAdapter.notifyDataSetChanged();

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
