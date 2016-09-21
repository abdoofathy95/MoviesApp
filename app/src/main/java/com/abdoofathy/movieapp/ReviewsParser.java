package com.abdoofathy.movieapp;

import android.content.Context;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class ReviewsParser implements IOnDataReady, IJSONParser {
    private Context context;
    private ReviewsArrayAdapter reviewsArrayAdapter;
    private List<Review> reviewsList;

    public ReviewsParser(Context context, ReviewsArrayAdapter reviewsArrayAdapter, List reviewsList){
        this.context = context;
        this.reviewsArrayAdapter = reviewsArrayAdapter;
        this.reviewsList = reviewsList;
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
    }

    @Override
    public void parse(JSONObject jsonObject) {
        try {
            JSONArray jsonArray = jsonObject.getJSONArray("results");
            JSONObject reviewJsonObject;
            Review review;
            for (int i=0; i<jsonArray.length(); i++){
                reviewJsonObject = (JSONObject) jsonArray.get(i);
                review = new Review(reviewJsonObject.getString("author"), reviewJsonObject.getString("content"));
                reviewsList.add(i, review);
            }
            reviewsArrayAdapter.notifyDataSetChanged();
        } catch (JSONException e) { // format problem
            e.printStackTrace();
        }
    }
}
