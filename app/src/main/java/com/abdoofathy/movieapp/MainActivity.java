package com.abdoofathy.movieapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private URL apiUrl;
    private SharedPreferences prefs;
    private ImageAdapter imageAdapter;
    private List<Movie> movies;
    private Context context;
    private boolean isTablet;
    private GridView moviesPostersGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // check database for movies
        isTablet = getResources().getBoolean(R.bool.isTablet);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String sortCriteria = prefs.getString(getString(R.string.pref_sort_criteria_key),getString(R.string.pref_sort_criteria_default));

        movies = new ArrayList<>(); // data model
        moviesPostersGridView = (GridView) findViewById(R.id.postersGridView);
        imageAdapter = new ImageAdapter(this, movies);
        moviesPostersGridView.setAdapter(imageAdapter);
        context = this;

        moviesPostersGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Movie movie = (Movie) adapterView.getItemAtPosition(i);

                if(isTablet){ // check if two pane is to be applied
                    moviesPostersGridView.smoothScrollToPositionFromTop(i,0);
                    openMovieInFragment(movie);
                }else {

                    // open an explicit intent (Movie Detail Activity) and send data with it'
                    openMovieInActivity(movie);
                }
            }
        });

        IOnDataReady moviesList = new MoviesList(this, imageAdapter, movies, moviesPostersGridView, isTablet);
        // build URI (get by popularity first) as it's the default
        apiUrl = getUrl(sortCriteria);

        // fetch from API call
        DoAPICall apiCall = new DoAPICall(moviesList);
        executeApiCall(apiCall);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sort_options_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if(itemId == R.id.action_sort_popular){
            // sort according to popularity
            // save as a pref
            prefs = PreferenceManager.getDefaultSharedPreferences(this);
            prefs.edit().putString(getString(R.string.pref_sort_criteria_key),getString(R.string.pref_sort_popular))
                    .apply();
            apiUrl = getUrl(getString(R.string.pref_sort_popular));
            IOnDataReady moviesList = new MoviesList(this, imageAdapter, movies, moviesPostersGridView, isTablet);
            DoAPICall apiCall = new DoAPICall(moviesList);
            executeApiCall(apiCall);
            return true;
        }
        if (itemId == R.id.action_sort_rate){
            // sort according to highest rate

            // save as a pref
            prefs = PreferenceManager.getDefaultSharedPreferences(this);
            prefs.edit().putString(getString(R.string.pref_sort_criteria_key),getString(R.string.pref_sort_rate))
                    .apply();
            apiUrl = getUrl(getString(R.string.pref_sort_rate));
            IOnDataReady moviesList = new MoviesList(this, imageAdapter, movies, moviesPostersGridView, isTablet);
            DoAPICall apiCall = new DoAPICall(moviesList);
            executeApiCall(apiCall);
            return true;
        }

        if (itemId == R.id.action_show_favourites){
            // show favourite movies
            // start new intent that runs the favs activity
            Intent favouriteMoviesIntent = new Intent(this, FavouriteMovies.class);
            startActivity(favouriteMoviesIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public URL getUrl(String sortCriteria){
        Uri.Builder uriBuilder = new  Uri.Builder();
        uriBuilder.scheme("https")
                .authority("api.themoviedb.org")
                .appendPath("3")
                .appendPath("discover")
                .appendPath("movie")
                .appendQueryParameter("sort_by",sortCriteria)
                .appendQueryParameter("api_key",getString(R.string.movies_api_key));

        URL apiUrl = null;
        try {
            apiUrl = new URL(uriBuilder.build().toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return apiUrl;
    }

    public void executeApiCall(DoAPICall apiCall){
         if(isNetworkAvailable(this)){
            apiCall.execute(apiUrl);
        }else{
            Toast.makeText(this,"No Internet Connection Found, Please Connect to Proceed", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    public boolean isNetworkAvailable(Context context)
    {
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()&& cm.getActiveNetworkInfo().isAvailable()&&
                cm.getActiveNetworkInfo().isConnected())
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    private void openMovieInFragment(Movie movie){
        MovieDetailFragment movieDetailFragment =  new MovieDetailFragment();
        Bundle arguements = new Bundle();

        arguements.putString(Constants.MOVIE_ID, movie.getMovieId());
        arguements.putString(Constants.MOVIE_TITLE, movie.getMovieTitle());
        arguements.putString(Constants.MOVIE_POST_URL, movie.getPosterImageURL());
        arguements.putString(Constants.MOVIE_PLOT, movie.getPlot());
        arguements.putDouble(Constants.MOVIE_VOTE_AVERAGE, movie.getVoteAverage());
        arguements.putString(Constants.MOVIE_RELEASE_DATE, movie.getReleaseDate());

        movieDetailFragment.setArguments(arguements);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.movieDetail, movieDetailFragment)
                .commit();
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
}
