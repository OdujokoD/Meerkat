package com.example.android.meerkat.utilities;

import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import com.example.android.meerkat.adapters.MovieAdapter;
import com.example.android.meerkat.model.Movie;

import java.net.URL;
import java.util.List;

public class MovieDBTask extends AsyncTask<URL, Void, List<Movie>>{

    private ProgressBar mLoadingIndicator;
    private MovieAdapter mMovieAdapter;
    private movieDBTaskCallback callback;

    public MovieDBTask(ProgressBar progressBar, MovieAdapter movieAdapter){
        mLoadingIndicator = progressBar;
        mMovieAdapter = movieAdapter;
    }

    public interface movieDBTaskCallback{
        void requestError();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mLoadingIndicator.setVisibility(View.VISIBLE);
    }

    @Override
    protected List<Movie> doInBackground(URL... urls) {
        if (urls.length == 0) {
            return null;
        }

        URL moviesRequestUrl = urls[0];

        try {
            String jsonWeatherResponse = NetworkUtils
                    .getResponseFromHttpUrl(moviesRequestUrl);

            JSONParser parser = new JSONParser(jsonWeatherResponse);

            return parser.parseMovieJSON();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<Movie> movieList) {
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        if (movieList != null) {
            mMovieAdapter.setMovieData(movieList);
        } else {
            //req
        }
    }
}
