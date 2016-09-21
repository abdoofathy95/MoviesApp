package com.abdoofathy.movieapp;

import org.json.JSONException;
import org.json.JSONObject;

public class Movie implements IJSONParser{
    private String movieId;
    private String movieTitle;
    private String posterImageURL;
    private String plot;
    private double voteAverage;
    private String releaseDate;
    private String runtime;
    private final String BASE_IMAGE_URL = "http://image.tmdb.org/t/p/w185/";

    public Movie(){

    }

    public Movie(String movieId, String movieTitle, String posterImageURL, String plot, double voteAverage, String releaseDate){
        this.movieId = movieId;
        this.movieTitle = movieTitle;
        this.posterImageURL = posterImageURL;
        this.plot = plot;
        this.voteAverage = voteAverage;
        this.releaseDate = releaseDate;
    }
    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getMovieTitle(){
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle){
        this.movieTitle = movieTitle;
    }

    public String getPosterImageURL(){
        return posterImageURL;
    }

    public void setPosterImageURL(String posterImageURL){
        this.posterImageURL = posterImageURL;
    }

    public String getPlot(){
        return plot;
    }

    public void setPlot(String plot){
        this.plot = plot;
    }

    public double getVoteAverage(){
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage){
        this.voteAverage = voteAverage;
    }

    public String getReleaseDate(){
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate){
        this.releaseDate = releaseDate;
    }


    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    @Override
    public void parse(JSONObject jsonObject) {
        try {
            movieId = jsonObject.getString("id");
            movieTitle = jsonObject.getString("title");
            posterImageURL = BASE_IMAGE_URL+jsonObject.getString("poster_path");
            plot = jsonObject.getString("overview");
            voteAverage = jsonObject.getDouble("vote_average");
            releaseDate = jsonObject.getString("release_date");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
