package com.example.android.meerkat.providers;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.example.android.meerkat.data.FavoriteMoviesContract;
import com.example.android.meerkat.data.FavoriteMoviesDbHelper;

import static com.example.android.meerkat.data.FavoriteMoviesContract.MoviesEntry.TABLE_NAME;
import static com.example.android.meerkat.utilities.Constants.MOVIES;
import static com.example.android.meerkat.utilities.Constants.MOVIE_WITH_ID;

public class FavoriteMovieContentProvider extends ContentProvider{
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private FavoriteMoviesDbHelper mDbHelper;

    public static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(FavoriteMoviesContract.AUTHORITY,
                FavoriteMoviesContract.PATH_FAVORITE_MOVIES, MOVIES);
        uriMatcher.addURI(FavoriteMoviesContract.AUTHORITY,
                FavoriteMoviesContract.PATH_FAVORITE_MOVIES + "/#", MOVIE_WITH_ID);

        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        Context context = getContext();
        mDbHelper = new FavoriteMoviesDbHelper(context);
        return true;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        final SQLiteDatabase db = mDbHelper.getReadableDatabase();

        int match = sUriMatcher.match(uri);
        Cursor retCursor;

        switch (match) {
            case MOVIES:
                retCursor =  db.query(TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        retCursor.setNotificationUri(getContext().getContentResolver(), uri);

        return retCursor;
    }

    @Override
    public String getType(@NonNull Uri uri) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues contentValues) {
        final SQLiteDatabase db = mDbHelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case MOVIES:
                long id = db.insert(FavoriteMoviesContract.MoviesEntry.TABLE_NAME,
                        null, contentValues);
                if ( id > 0 ) {
                    returnUri = ContentUris.withAppendedId(FavoriteMoviesContract.MoviesEntry.CONTENT_URI, id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, String s, String[] selectionArgs) {
        final SQLiteDatabase db = mDbHelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);
        int mDbHelper;

        switch (match) {
            case MOVIE_WITH_ID:
                String id = uri.getPathSegments().get(1);
                mDbHelper = db.delete(FavoriteMoviesContract.MoviesEntry.TABLE_NAME,
                        "_id=?", new String[]{id});
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (mDbHelper != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return mDbHelper;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues contentValues,
                      String selection, String[] selectionArgs) {

        throw new UnsupportedOperationException("Not yet implemented");
    }
}
