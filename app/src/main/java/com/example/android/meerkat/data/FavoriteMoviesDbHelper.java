package com.example.android.meerkat.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FavoriteMoviesDbHelper extends SQLiteOpenHelper{
    private static final String DATABASE_NAME = "favoriteMovies.db";
    private static final int DATABASE_VERSION = 1;

    public FavoriteMoviesDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String CREATE_FAVORITES_MOVIES_TABLE = "CREATE TABLE " +
                FavoriteMoviesContract.MoviesEntry.TABLE_NAME + " (" +
                FavoriteMoviesContract.MoviesEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                FavoriteMoviesContract.MoviesEntry.COLUMN_POSTER_URL + " TEXT NOT NULL, " +
                FavoriteMoviesContract.MoviesEntry.COLUMN_MOVIE_TITLE + " TEXT NOT NULL, " +
                FavoriteMoviesContract.MoviesEntry.COLUMN_MOVIE_OVERVIEW + " TEXT NOT NULL, " +
                FavoriteMoviesContract.MoviesEntry.COLUMN_RELEASE_DATE + " TEXT NOT NULL, " +
                FavoriteMoviesContract.MoviesEntry.COLUMN_VOTE_AVERAGE + " TEXT NOT NULL" +
                "); ";

        sqLiteDatabase.execSQL(CREATE_FAVORITES_MOVIES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + FavoriteMoviesContract.MoviesEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
