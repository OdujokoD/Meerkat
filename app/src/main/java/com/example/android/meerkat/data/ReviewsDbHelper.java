package com.example.android.meerkat.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ReviewsDbHelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "movieReviews.db";
    private static final int DATABASE_VERSION = 1;

    public ReviewsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String CREATE_MOVIES_REVIEWS_TABLE = "CREATE TABLE " +
                ReviewsContract.ReviewsEntry.TABLE_NAME + " (" +
                ReviewsContract.ReviewsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                ReviewsContract.ReviewsEntry.COLUMN_AUTHOR + " TEXT NOT NULL, " +
                ReviewsContract.ReviewsEntry.COLUMN_CONTENT + " TEXT NOT NULL, " +
                ReviewsContract.ReviewsEntry.COLUMN_MOVIE_ID + " TEXT NOT NULL" +
                "); ";

        sqLiteDatabase.execSQL(CREATE_MOVIES_REVIEWS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ReviewsContract.ReviewsEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
