package com.abdoofathy.movieapp;

import android.provider.BaseColumns;

public final class MovieContract {

    public abstract class MovieEntry implements BaseColumns{
        public static final String TABLE_NAME = "movie";
        public static final String COLUMN_NAME_MOVIE_ID = "movieId";
        public static final String COLUMN_NAME_MOVIE_TITLE = "movieTitle";
        public static final String COLUMN_NAME_POSTER_URL = "posterImageURL";
        public static final String COLUMN_NAME_PLOT = "plot";
        public static final String COLUMN_NAME_VOTE_AVERAGE = "voteAverage";
        public static final String COLUMN_NAME_RELEASE_DATE = "releaseDate";
    }

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    public static final String SQL_CREATE_MOVIES =
            "CREATE TABLE " + MovieEntry.TABLE_NAME + " (" +
                    MovieEntry._ID + " INTEGER PRIMARY KEY," +
                    MovieEntry.COLUMN_NAME_MOVIE_ID + "INTEGER UNIQUE" + COMMA_SEP +
                    MovieEntry.COLUMN_NAME_MOVIE_TITLE + TEXT_TYPE + " NOT NULL" + COMMA_SEP +
                    MovieEntry.COLUMN_NAME_POSTER_URL + TEXT_TYPE + " NOT NULL" + COMMA_SEP +
                    MovieEntry.COLUMN_NAME_PLOT + TEXT_TYPE + " NOT NULL" + COMMA_SEP +
                    MovieEntry.COLUMN_NAME_VOTE_AVERAGE + "REAL NOT NULL" + COMMA_SEP +
                    MovieEntry.COLUMN_NAME_RELEASE_DATE + TEXT_TYPE + " NOT NULL" + COMMA_SEP +
            " )";

    public static final String SQL_DELETE_MOVIES =
            "DROP TABLE IF EXISTS " + MovieEntry.TABLE_NAME;
}
