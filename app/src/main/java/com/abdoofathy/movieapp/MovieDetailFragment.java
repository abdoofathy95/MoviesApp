package com.abdoofathy.movieapp;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MovieDetailFragment extends Fragment implements IUpdateData {

    private MoviesDbHelper moviesDbHelper;
    private boolean movieAlreadyAdded;
    private Button addToFavBtn;
    private TrailersFragment trailersFragment;
    private ReviewsFragment reviewsFragment;
    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        moviesDbHelper = new MoviesDbHelper(getContext());
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie_detail, container, false);

        Bundle data = getArguments();
        final String movieId = data.getString(Constants.MOVIE_ID);
        final String movieTitle = data.getString(Constants.MOVIE_TITLE);
        final String moviePostUrl = data.getString(Constants.MOVIE_POST_URL);
        final String moviePlot = data.getString(Constants.MOVIE_PLOT);
        final double voteAverage = data.getDouble(Constants.MOVIE_VOTE_AVERAGE);
        final String releaseDate = data.getString(Constants.MOVIE_RELEASE_DATE);

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

        addToFavBtn = (Button) view.findViewById(R.id.addToFavBtn);
        Button trailersBtn = (Button) view.findViewById(R.id.trailersBtn);
        Button reviewsBtn = (Button) view.findViewById(R.id.reviewsBtn);

        if (moviesDbHelper.isAddedToFavourites(movieId)){
            addToFavBtn.setSelected(true);
            addToFavBtn.setText(R.string.added_to_fav);
            movieAlreadyAdded = true;
        }else{
            addToFavBtn.setSelected(false);
            addToFavBtn.setText(R.string.add_to_fav);
            movieAlreadyAdded = false;
        }
        addToFavBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // check if the movie already exists in favs and add
                if ( movieAlreadyAdded ){
                    moviesDbHelper.removeFromFavourites(movieId);
                    addToFavBtn.setSelected(false);
                    addToFavBtn.setText(R.string.add_to_fav);
                    movieAlreadyAdded = false;
                    if(context != null)
                        ((IUpdateData) context).updateList(null);
                }else{
                    moviesDbHelper.addToFavourites(movieId, movieTitle, moviePostUrl, moviePlot, voteAverage, releaseDate);
                    addToFavBtn.setSelected(true);
                    addToFavBtn.setText(R.string.added_to_fav);
                    movieAlreadyAdded = true;
                    if(context != null)
                        ((IUpdateData) context).updateList(null);
                }
            }
        });

        trailersBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                trailersFragment = new TrailersFragment();
                Bundle bundle = new Bundle();
                bundle.putString(Constants.MOVIE_ID, movieId);
                trailersFragment.setArguments(bundle);
                getFragmentManager().beginTransaction().
                        replace(R.id.movieDetail, trailersFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        reviewsBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                reviewsFragment = new ReviewsFragment();
                Bundle bundle = new Bundle();
                bundle.putString(Constants.MOVIE_ID, movieId);
                reviewsFragment.setArguments(bundle);
                getFragmentManager().beginTransaction().
                        replace(R.id.movieDetail, reviewsFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        String movieId = getArguments().getString(Constants.MOVIE_ID);
        if (moviesDbHelper.isAddedToFavourites(movieId)){
            addToFavBtn.setSelected(true);
            addToFavBtn.setText(R.string.added_to_fav);
            movieAlreadyAdded = true;
        }else{
            addToFavBtn.setSelected(false);
            addToFavBtn.setText(R.string.add_to_fav);
            movieAlreadyAdded = false;
        }
    }

    @Override
    public void updateList(Context context) {
        this.context = context;
    }
}
