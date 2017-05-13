package com.example.android.meerkat.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TrailersDbHelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "movieTrailers.db";
    private static final int DATABASE_VERSION = 1;

    public TrailersDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String CREATE_MOVIES_TRAILERS_TABLE = "CREATE TABLE " +
                TrailersContract.TrailersEntry.TABLE_NAME + " (" +
                TrailersContract.TrailersEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                TrailersContract.TrailersEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                TrailersContract.TrailersEntry.COLUMN_KEY + " TEXT NOT NULL, " +
                TrailersContract.TrailersEntry.COLUMN_MOVIE_ID + " TEXT NOT NULL" +
                "); ";

        sqLiteDatabase.execSQL(CREATE_MOVIES_TRAILERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TrailersContract.TrailersEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
