package com.example.android.meerkat.loaders;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.util.Log;

import com.example.android.meerkat.adapters.FavoriteMovieTrailerAdapter;
import com.example.android.meerkat.data.TrailersContract;

public class TrailersDbLoader implements LoaderManager.LoaderCallbacks<Cursor>{

    private Context mContext;
    private FavoriteMovieTrailerAdapter mMovieTrailerAdapter;

    public TrailersDbLoader(Context context, FavoriteMovieTrailerAdapter movieTrailerAdapter){
        mContext = context;
        mMovieTrailerAdapter = movieTrailerAdapter;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<Cursor>(mContext) {

            Cursor mTrailerData = null;

            @Override
            protected void onStartLoading() {
                if (mTrailerData != null) {
                    deliverResult(mTrailerData);
                } else {
                    forceLoad();
                }
            }

            @Override
            public Cursor loadInBackground() {
                try {
                    return getContext().getContentResolver()
                            .query(TrailersContract.TrailersEntry.CONTENT_URI,
                                    null,
                                    null,
                                    null,
                                    null);

                } catch (Exception e) {
                    Log.e("LOADER", "Failed to asynchronously load data.");
                    e.printStackTrace();
                    return null;
                }
            }

            public void deliverResult(Cursor data) {
                mTrailerData = data;
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mMovieTrailerAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mMovieTrailerAdapter.swapCursor(null);
    }
}
