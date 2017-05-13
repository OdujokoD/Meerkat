package com.example.android.meerkat.loaders;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.util.Log;

import com.example.android.meerkat.adapters.ReviewAdapter;
import com.example.android.meerkat.model.Review;
import com.example.android.meerkat.utilities.JSONParser;
import com.example.android.meerkat.utilities.NetworkUtils;

import java.net.URL;
import java.util.List;

import static com.example.android.meerkat.utilities.Constants.FETCH_REVIEW_URL_EXTRA;

public class ReviewLoader implements LoaderManager.LoaderCallbacks<List<Review>>{

    private Context mContext;
    private ReviewAdapter mReviewAdapter;
    private List<Review> reviews;

    public ReviewLoader(Context context, ReviewAdapter reviewAdapter){
        mContext = context;
        mReviewAdapter = reviewAdapter;
    }

    @Override
    public Loader<List<Review>> onCreateLoader(int id, final Bundle args) {
        return new AsyncTaskLoader<List<Review>>(mContext) {
            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                if(args == null){
                    return;
                }
                //mLoadingIndicator.setVisibility(View.VISIBLE);
                forceLoad();
            }

            @Override
            public List<Review> loadInBackground() {
                String fetchReviewUrlString = args.getString(FETCH_REVIEW_URL_EXTRA);
                if(fetchReviewUrlString == null){
                    return null;
                }
                try {
                    Log.d("LoadInBackground", fetchReviewUrlString);
                    URL trailersRequestUrl = new URL(fetchReviewUrlString);
                    String jsonTrailerResponse = NetworkUtils
                            .getResponseFromHttpUrl(trailersRequestUrl);

                    JSONParser parser = new JSONParser();

                    return parser.parseReviewJSON(jsonTrailerResponse);

                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<List<Review>> loader, List<Review> data) {
        if (data != null) {
            Log.d("OnLoadFinished", "Im done");
            mReviewAdapter.setReviewData(data);
            reviews = data;
        } else {
            //showErrorMessage();
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Review>> loader) {

    }

    public List<Review> getReviews(){
        return reviews;
    }
}
