package com.abdoofathy.movieapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class TrailersFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trailers_list, container, false);
        List<MovieTrailerUrl> trailersUrls = new ArrayList<>();
        TrailerArrayAdapter trailersAdapter = new TrailerArrayAdapter(getContext(), R.layout.trailer_item, trailersUrls);
        MovieTrailerUrlList movieTrailerUrlList = new MovieTrailerUrlList(getContext(), trailersAdapter, trailersUrls);

        ListView trailersListView = (ListView) view.findViewById(R.id.trailersListView);
        trailersListView.setAdapter(trailersAdapter);
        trailersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String url = ((MovieTrailerUrl)adapterView.getItemAtPosition(i)).getTrailerUrl();
                Intent playVideoIntent = new Intent();
                playVideoIntent.setAction(Intent.ACTION_VIEW);
                playVideoIntent.setData(Uri.parse(url));
                startActivity(playVideoIntent);
            }
        });
        String movieId = getArguments().getString(Constants.MOVIE_ID);
        DoAPICall doAPICall = new DoAPICall(movieTrailerUrlList);
        URL url = generateURL(movieId);
        if(url != null){
            doAPICall.execute(url);
        }

        return view;
    }

    private URL generateURL(String movieId){
        Uri.Builder uriBuilder = new Uri.Builder();
        uriBuilder.scheme("https")
                .authority("api.themoviedb.org")
                .appendPath("3")
                .appendPath("movie")
                .appendPath(movieId)
                .appendPath("videos")
                .appendQueryParameter("api_key",BuildConfig.MOVIES_API_KEY);
        String urlString = uriBuilder.build().toString();
        try {
            return new URL(urlString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
