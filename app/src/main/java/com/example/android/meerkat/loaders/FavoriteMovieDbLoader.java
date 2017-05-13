package com.example.android.meerkat.loaders;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.util.Log;

import com.example.android.meerkat.adapters.FavoriteMovieAdapter;
import com.example.android.meerkat.data.FavoriteMoviesContract;

public class FavoriteMovieDbLoader implements LoaderManager.LoaderCallbacks<Cursor>{

    private Context mContext;
    private FavoriteMovieAdapter mMovieAdapter;

    public FavoriteMovieDbLoader(Context context, FavoriteMovieAdapter movieAdapter){
        mContext = context;
        mMovieAdapter = movieAdapter;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<Cursor>(mContext) {

            Cursor mMovieData = null;

            @Override
            protected void onStartLoading() {
                if (mMovieData != null) {
                    deliverResult(mMovieData);
                } else {
                    forceLoad();
                }
            }

            @Override
            public Cursor loadInBackground() {
                Log.d("DBLOADER", "Im loading!!!");
                try {
                    return getContext().getContentResolver()
                            .query(FavoriteMoviesContract.MoviesEntry.CONTENT_URI,
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
                mMovieData = data;
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mMovieAdapter.swapCursor(data);
        Log.d("DATA SIZE", data.getCount() + "");
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mMovieAdapter.swapCursor(null);
    }
}
