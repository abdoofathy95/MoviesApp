package com.abdoofathy.movieapp;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieDetailFragment extends Fragment {
    private TrailerArrayAdapter trailersAdapter;
    private ListView trailersListView;
    private List<MovieTrailerUrl> trailersUrls ;
    private MovieTrailerUrlList movieTrailerUrlList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        trailersUrls = new ArrayList<MovieTrailerUrl>();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        trailersAdapter = new TrailerArrayAdapter(getContext(), R.layout.trailer_item, trailersUrls);
        movieTrailerUrlList = new MovieTrailerUrlList(getContext(), trailersAdapter, trailersUrls);

        trailersListView = (ListView) view.findViewById(R.id.trailersListView);
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


        Bundle data = getArguments();
        String movieId = data.getString(Constants.MOVIE_ID);
        String movieTitle = data.getString(Constants.MOVIE_TITLE);
        String moviePostUrl = data.getString(Constants.MOVIE_POST_URL);
        String moviePlot = data.getString(Constants.MOVIE_PLOT);
        double voteAverage = data.getDouble(Constants.MOVIE_VOTE_AVERAGE);
        String releaseDate = data.getString(Constants.MOVIE_RELEASE_DATE);

        DoAPICall doAPICall = new DoAPICall(movieTrailerUrlList);
        URL url = generateURL(movieId);
        if(url != null){
            doAPICall.execute(url);
        }

        TextView movieTitleTv = (TextView) view.findViewById(R.id.movieTitle);
        movieTitleTv.setText(movieTitle);

        ImageView moviePoster = (ImageView) view.findViewById(R.id.moviePoster);
        Picasso.with(getContext()).load(moviePostUrl).into(moviePoster);

        TextView moviePlotTv = (TextView) view.findViewById(R.id.plot);
        moviePlotTv.setText(moviePlot);

        TextView movieVoteAverageTv = (TextView) view.findViewById(R.id.averageRate);
        movieVoteAverageTv.setText(voteAverage+"/10");

        TextView movieReleaseDateTv = (TextView) view.findViewById(R.id.releaseDate);
        movieReleaseDateTv.setText(releaseDate+"");

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
                .appendQueryParameter("api_key",getString(R.string.movies_api_key));
        String urlString = uriBuilder.build().toString();
        try {
            URL url = new URL(urlString);
            return url;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
