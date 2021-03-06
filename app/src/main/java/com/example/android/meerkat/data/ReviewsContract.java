package com.example.android.meerkat.data;

import android.net.Uri;
import android.provider.BaseColumns;

public class ReviewsContract {
    public static final String AUTHORITY = "com.example.android.meerkat.ReviewsContentProvider";
    private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String PATH_REVIEWS = "movieReviews";

    public static final class ReviewsEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_REVIEWS).build();
        public static final String TABLE_NAME = "movieReviews";
        public static final String COLUMN_AUTHOR = "author";
        public static final String COLUMN_CONTENT = "content";
        public static final String COLUMN_MOVIE_ID = "movieId";
    }
}
