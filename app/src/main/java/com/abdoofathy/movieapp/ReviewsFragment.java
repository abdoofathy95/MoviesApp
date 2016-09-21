package com.abdoofathy.movieapp;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ReviewsFragment extends Fragment {
    public ReviewsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_reviews, container, false);
        ListView reviewsListView = (ListView) view.findViewById(R.id.reviewsListView);
        String movieId = getArguments().getString(Constants.MOVIE_ID);
        List reviewsList = new ArrayList();
        ReviewsArrayAdapter reviewsArrayAdapter = new ReviewsArrayAdapter(getContext(), R.layout.review_item, reviewsList);
        ReviewsParser reviewsParser = new ReviewsParser(getContext(), reviewsArrayAdapter, reviewsList);
        reviewsListView.setAdapter(reviewsArrayAdapter);


        DoAPICall doAPICall = new DoAPICall(reviewsParser);
        doAPICall.execute(generateURL(movieId));

        return view;
    }


    private URL generateURL(String movieId){
        Uri.Builder uriBuilder = new Uri.Builder();
        uriBuilder.scheme("https")
                .authority("api.themoviedb.org")
                .appendPath("3")
                .appendPath("movie")
                .appendPath(movieId)
                .appendPath("reviews")
                .appendQueryParameter("api_key",getString(R.string.movies_api_key));
        String urlString = uriBuilder.build().toString();
        try {
            return new URL(urlString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
