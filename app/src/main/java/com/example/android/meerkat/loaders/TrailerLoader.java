package com.example.android.meerkat.loaders;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;

import com.example.android.meerkat.adapters.TrailerAdapter;
import com.example.android.meerkat.model.Trailer;
import com.example.android.meerkat.utilities.JSONParser;
import com.example.android.meerkat.utilities.NetworkUtils;

import java.net.URL;
import java.util.List;

import static com.example.android.meerkat.utilities.Constants.FETCH_TRAILER_URL_EXTRA;

public class TrailerLoader implements LoaderManager.LoaderCallbacks<List<Trailer>>{

    private Context mContext;
    private TrailerAdapter mTrailerAdapter;
    private List<Trailer> trailers;

    public TrailerLoader(Context context, TrailerAdapter trailerAdapter){
        mContext = context;
        mTrailerAdapter = trailerAdapter;
    }

    @Override
    public Loader<List<Trailer>> onCreateLoader(int id, final Bundle args) {
        return new AsyncTaskLoader<List<Trailer>>(mContext) {
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
            public List<Trailer> loadInBackground() {
                String fetchTrailerUrlString = args.getString(FETCH_TRAILER_URL_EXTRA);
                if(fetchTrailerUrlString == null){
                    return null;
                }
                try {
                    URL trailersRequestUrl = new URL(fetchTrailerUrlString);
                    String jsonTrailerResponse = NetworkUtils
                            .getResponseFromHttpUrl(trailersRequestUrl);

                    JSONParser parser = new JSONParser();

                    return parser.parseTrailerJSON(jsonTrailerResponse);

                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<List<Trailer>> loader, List<Trailer> data) {
        if (data != null) {
            mTrailerAdapter.setTrailerData(data);
            trailers = data;
        } else {
            //showErrorMessage();
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Trailer>> loader) {

    }

    public List<Trailer> getTrailers(){
        return trailers;
    }
}
