package com.abdoofathy.movieapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MovieDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        Intent intent = getIntent();
        MovieDetailFragment movieDetailFragment =  new MovieDetailFragment();
        movieDetailFragment.setArguments(intent.getExtras());
        getSupportFragmentManager().beginTransaction()
                .add(R.id.movieDetail, movieDetailFragment)
                .commit();
    }
}
