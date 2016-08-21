package com.abdoofathy.movieapp;

/**
 * Created by Awesome on 8/12/2016.
 */
public class MovieTrailerUrl {
    private String trailerName;
    private String trailerUrl;
    private final String BASE_URL = "https://www.youtube.com/watch?v=";

    public String getTrailerUrl() {
        return trailerUrl;
    }

    public void setTrailerUrl(String trailerUrl) {
        this.trailerUrl = BASE_URL + trailerUrl;
    }

    public String getTrailerName() {
        return trailerName;
    }

    public void setTrailerName(String trailerName) {
        this.trailerName = trailerName;
    }
}
