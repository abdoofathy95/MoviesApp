package com.abdoofathy.movieapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class MoviesDbHelper extends SQLiteOpenHelper {
        // If you change the database schema, you must increment the database version.
        public static final int DATABASE_VERSION = 1;
        public static final String DATABASE_NAME = "MoviesApp.db";

        public MoviesDbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(MoviesContract.SQL_CREATE_FAVOURITE_MOVIES);
        }
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // first move the user favourite movies then remove
        }
        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            onUpgrade(db, oldVersion, newVersion); // do same
        }

        public void addToFavourites(String movieId, String movieTitle, String moviePostUrl, String moviePlot,
                                    double voteAverage, String releaseDate){
            ContentValues contentValues = new ContentValues();
            contentValues.put(MoviesContract.MovieEntry.COLUMN_NAME_MOVIE_ID, movieId);
            contentValues.put(MoviesContract.MovieEntry.COLUMN_NAME_MOVIE_TITLE, movieTitle);
            contentValues.put(MoviesContract.MovieEntry.COLUMN_NAME_POSTER_URL, moviePostUrl);
            contentValues.put(MoviesContract.MovieEntry.COLUMN_NAME_PLOT, moviePlot);
            contentValues.put(MoviesContract.MovieEntry.COLUMN_NAME_VOTE_AVERAGE, voteAverage);
            contentValues.put(MoviesContract.MovieEntry.COLUMN_NAME_RELEASE_DATE, releaseDate);
            getWritableDatabase().insert(MoviesContract.MovieEntry.TABLE_NAME, null, contentValues);
            close();
        }

        public List<Movie> getAllFavourites(){
            List<Movie> movies = new ArrayList<>();
            Cursor cur = getReadableDatabase().query(MoviesContract.MovieEntry.TABLE_NAME,
                    new String [] {MoviesContract.MovieEntry.COLUMN_NAME_MOVIE_ID, MoviesContract.MovieEntry.COLUMN_NAME_MOVIE_TITLE,
                            MoviesContract.MovieEntry.COLUMN_NAME_POSTER_URL, MoviesContract.MovieEntry.COLUMN_NAME_PLOT,
                            MoviesContract.MovieEntry.COLUMN_NAME_VOTE_AVERAGE, MoviesContract.MovieEntry.COLUMN_NAME_RELEASE_DATE}
                    , null, null, null, null, null);
            while(cur.moveToNext()){
                movies.add(new Movie(cur.getString(0), cur.getString(1), cur.getString(2), cur.getString(3), cur.getDouble(4),
                        cur.getString(5)));
            }
            cur.close();
            close();
            return movies;
        }

        public boolean isAddedToFavourites(String movieId){
            int count = getReadableDatabase().query(true, MoviesContract.MovieEntry.TABLE_NAME,
                    new String [] {MoviesContract.MovieEntry.COLUMN_NAME_MOVIE_ID},
                    MoviesContract.MovieEntry.COLUMN_NAME_MOVIE_ID + "=" + movieId,
                    null, null,null,null,"1").getCount();
            close();
            return count > 0;
        }

        public void removeFromFavourites(String movieId){
            getWritableDatabase().delete(MoviesContract.MovieEntry.TABLE_NAME,
                    MoviesContract.MovieEntry.COLUMN_NAME_MOVIE_ID + "=" + movieId,
                    null);
            close();
        }
    }