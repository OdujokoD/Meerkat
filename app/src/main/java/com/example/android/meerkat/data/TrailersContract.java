package com.example.android.meerkat.data;

import android.net.Uri;
import android.provider.BaseColumns;

public class TrailersContract {

    public static final String AUTHORITY = "com.example.android.meerkat";
    private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String PATH_TRAILERS = "trailers";

    public static final class TrailersEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_TRAILERS).build();
        public static final String TABLE_NAME = "movieTrailers";
        public static final String COLUMN_MOVIE_ID = "movieId";
        public static final String COLUMN_NAME = "trailerName";
        public static final String COLUMN_KEY = "trailerKey";
    }
}
