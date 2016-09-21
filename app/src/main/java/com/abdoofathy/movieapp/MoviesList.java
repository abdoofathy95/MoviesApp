package com.abdoofathy.movieapp;

import android.content.Context;
import android.util.Log;
import android.widget.GridView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class MoviesList implements IOnDataReady, IJSONParser, IPostAction {
    private List<Movie> moviesList;
    private Context context;
    private ImageAdapter imageAdapter;
    private GridView gridView;
    private final int DEFAULT_POSITION = 0;
    private boolean isTablet;

    public MoviesList(Context context, ImageAdapter imageAdapter, List<Movie> moviesList, GridView gridView, boolean isTablet){
        this.context = context;
        this.imageAdapter = imageAdapter;
        this.moviesList = moviesList;
        this.gridView = gridView;
        this.isTablet = isTablet;
    }

    @Override
    public void getData(String string) {
        try {
            JSONObject jsonObject = new JSONObject(string);
            parse(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace(); // to be handled differently
        }
    }

    @Override
    public void raiseError(String string) { // not complete
        Toast.makeText(context, string, Toast.LENGTH_SHORT).show();
//        ((Activity)context).finish();
    }

    @Override
    public void parse(JSONObject jsonObject) {
        JSONArray jsonArray;
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
            doAction();

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void doAction() {
        if(isTablet){
            gridView.setItemChecked(DEFAULT_POSITION, true);
            gridView.performItemClick(gridView.getSelectedView(), DEFAULT_POSITION, 0);
        }
    }
}
