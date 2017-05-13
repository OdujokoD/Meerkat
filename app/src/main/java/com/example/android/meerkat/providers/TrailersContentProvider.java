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
import android.support.annotation.Nullable;

import com.example.android.meerkat.data.TrailersContract;
import com.example.android.meerkat.data.TrailersDbHelper;

import static com.example.android.meerkat.utilities.Constants.TRAILER;
import static com.example.android.meerkat.utilities.Constants.TRAILER_WITH_ID;

public class TrailersContentProvider extends ContentProvider{

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private TrailersDbHelper mDbHelper;

    public static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(TrailersContract.AUTHORITY,
                TrailersContract.PATH_TRAILERS, TRAILER);
        uriMatcher.addURI(TrailersContract.AUTHORITY,
                TrailersContract.PATH_TRAILERS + "/#", TRAILER_WITH_ID);

        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        Context context = getContext();
        mDbHelper = new TrailersDbHelper(context);
        return true;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        final SQLiteDatabase db = mDbHelper.getReadableDatabase();

        int match = sUriMatcher.match(uri);
        Cursor retCursor;

        switch (match) {
            case TRAILER:
                retCursor =  db.query(TrailersContract.TrailersEntry.TABLE_NAME,
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

    @Nullable
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
            case TRAILER:
                long id = db.insert(TrailersContract.TrailersEntry.TABLE_NAME,
                        null, contentValues);
                if ( id > 0 ) {
                    returnUri = ContentUris.withAppendedId(TrailersContract.TrailersEntry.CONTENT_URI, id);
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
            case TRAILER_WITH_ID:
                String id = uri.getPathSegments().get(1);
                mDbHelper = db.delete(TrailersContract.TrailersEntry.TABLE_NAME,
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
