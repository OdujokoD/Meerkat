package com.example.android.meerkat.loaders;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.util.Log;

import com.example.android.meerkat.adapters.FavoriteMovieReviewAdapter;
import com.example.android.meerkat.data.ReviewsContract;

public class ReviewsDbLoader implements LoaderManager.LoaderCallbacks<Cursor>{

    private Context mContext;
    private FavoriteMovieReviewAdapter mMovieReviewAdapter;

    public ReviewsDbLoader(Context context, FavoriteMovieReviewAdapter movieReviewAdapter){
        mContext = context;
        mMovieReviewAdapter = movieReviewAdapter;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<Cursor>(mContext) {

            Cursor mReviewData = null;

            @Override
            protected void onStartLoading() {
                if (mReviewData != null) {
                    deliverResult(mReviewData);
                } else {
                    forceLoad();
                }
            }

            @Override
            public Cursor loadInBackground() {
                try {
                    return getContext().getContentResolver()
                            .query(ReviewsContract.ReviewsEntry.CONTENT_URI,
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
                mReviewData = data;
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mMovieReviewAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mMovieReviewAdapter.swapCursor(null);
    }
}
