package com.abdoofathy.movieapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.List;

public class FavouriteMovies extends AppCompatActivity implements IUpdateData{
    private MoviesDbHelper moviesDbHelper;
    private Context context;
    private boolean isTablet;
    private GridView gridView;
    private ImageAdapter imageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_movies);
        context = this;
        isTablet = getResources().getBoolean(R.bool.isTablet);
        gridView = (GridView) findViewById(R.id.favPostersGridView);
        moviesDbHelper = new MoviesDbHelper(this);
        List<Movie> moviesList = moviesDbHelper.getAllFavourites();

        imageAdapter = new ImageAdapter(this, moviesList);
        gridView.setAdapter(imageAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie movie = (Movie) parent.getItemAtPosition(position);

                if(isTablet){ // check if two pane is to be applied
                    gridView.smoothScrollToPositionFromTop(position, 0);
                    openMovieInFragment(movie);
                }else {

                    // open an explicit intent (Movie Detail Activity) and send data with it'
                    openMovieInActivity(movie);
                }
            }
        });

        gridView.setEmptyView(findViewById(R.id.emptyFav));
    }


    private void openMovieInFragment(Movie movie){
        MovieDetailFragment movieDetailFragment =  new MovieDetailFragment();
        Bundle arguments = new Bundle();

        arguments.putString(Constants.MOVIE_ID, movie.getMovieId());
        arguments.putString(Constants.MOVIE_TITLE, movie.getMovieTitle());
        arguments.putString(Constants.MOVIE_POST_URL, movie.getPosterImageURL());
        arguments.putString(Constants.MOVIE_PLOT, movie.getPlot());
        arguments.putDouble(Constants.MOVIE_VOTE_AVERAGE, movie.getVoteAverage());
        arguments.putString(Constants.MOVIE_RELEASE_DATE, movie.getReleaseDate());

        movieDetailFragment.setArguments(arguments);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.movieDetail, movieDetailFragment)
                .commit();
        movieDetailFragment.updateList(this);
    }

    private void openMovieInActivity(Movie movie){
        Intent detailIntent = new Intent(context, MovieDetailActivity.class);

        detailIntent.putExtra(Constants.MOVIE_ID, movie.getMovieId());
        detailIntent.putExtra(Constants.MOVIE_TITLE, movie.getMovieTitle());
        detailIntent.putExtra(Constants.MOVIE_POST_URL, movie.getPosterImageURL());
        detailIntent.putExtra(Constants.MOVIE_PLOT, movie.getPlot());
        detailIntent.putExtra(Constants.MOVIE_VOTE_AVERAGE, movie.getVoteAverage());
        detailIntent.putExtra(Constants.MOVIE_RELEASE_DATE, movie.getReleaseDate());

        startActivity(detailIntent);
    }

    @Override
    public void updateList(Context context) {
        imageAdapter.updateData(moviesDbHelper.getAllFavourites());
        imageAdapter.notifyDataSetChanged();
        if(imageAdapter.getCount() == 0){
            finish();
        }
    }
}
