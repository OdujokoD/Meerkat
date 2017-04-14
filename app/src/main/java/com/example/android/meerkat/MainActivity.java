package com.example.android.meerkat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.meerkat.model.Movie;
import com.example.android.meerkat.utilities.JSONParser;
import com.example.android.meerkat.utilities.NetworkUtils;

import java.net.URL;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickListener{

    private MovieAdapter mMovieAdapter;
    private ProgressBar mLoadingIndicator;
    private RecyclerView mRecyclerView;
    private LinearLayout mNetworkErrorContainer;
    private TextView mNetworkMessage;
    private Button mRetryNetworkConnection;

    private static final String PREFS_NAME = "MyPrefsFile";
    private static final String PREFS_PARAM = "currentCategory";
    private final static String MOVIE_TITLE_EXTRA_TEXT = "movie title";
    private final static String MOVIE_IMAGE_EXTRA_TEXT = "movie poster url";
    private final static String MOVIE_RELEASE_DATE_EXTRA_TEXT = "movie release date";
    private final static String MOVIE_RATING_EXTRA_TEXT = "movie rating";
    private final static String MOVIE_OVERVIEW_EXTRA_TEXT = "movie overview";
    final static String[] movie_categories = {"popular", "top_rated"};
    private String currentCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLoadingIndicator = (ProgressBar)findViewById(R.id.pb_loading_indicator);
        mNetworkErrorContainer = (LinearLayout)findViewById(R.id.ll_network_error);
        mNetworkMessage = (TextView)findViewById(R.id.tv_network_error_message);
        mRetryNetworkConnection = (Button)findViewById(R.id.btn_retry);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_movie_list);
        //LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        GridLayoutManager layoutManager = new GridLayoutManager(this,getSpanCount());

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        mMovieAdapter = new MovieAdapter(this);
        mRecyclerView.setAdapter(mMovieAdapter);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        currentCategory = settings.getString(PREFS_PARAM, movie_categories[0]);
        fetchMovies(currentCategory);
    }

    @Override
    protected void onStop() {
        super.onStop();

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(PREFS_PARAM, currentCategory);

        editor.apply();
    }

    private int getSpanCount() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int columnPartition = 400;
        int width = displayMetrics.widthPixels;
        int spanCount = width / columnPartition;
        if (spanCount < 2) return 2;

        return spanCount;
    }

    private void fetchMovies(String category){
        URL movieListUrl = null;
        if(category.equals(movie_categories[0])){
            movieListUrl = NetworkUtils.buildPopularMoviesURL();
        }
        if(category.equals(movie_categories[1])){
            movieListUrl = NetworkUtils.buildTopRatedMoviesURL();
        }

        ConnectivityManager connectivityManager =
                (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        boolean isConnected = activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();

        if(isConnected) {
            hideErrorMessage();
            new MovieDBTask().execute(movieListUrl);
        }
        else{
            showErrorMessage();
        }
    }

    @Override
    public void onClick(Movie currentMovie) {
        Context context = this;
        Class destinationClass = MovieDetailActivity.class;

        Intent intentToDestinationClass = new Intent(context, destinationClass);
        intentToDestinationClass.putExtra(MOVIE_IMAGE_EXTRA_TEXT, currentMovie.getMoviePosterURL());
        intentToDestinationClass.putExtra(MOVIE_TITLE_EXTRA_TEXT, currentMovie.getOriginalTitle());
        intentToDestinationClass.putExtra(MOVIE_RELEASE_DATE_EXTRA_TEXT, currentMovie.getReleaseDate());
        intentToDestinationClass.putExtra(MOVIE_RATING_EXTRA_TEXT, currentMovie.getVoteAverage());
        intentToDestinationClass.putExtra(MOVIE_OVERVIEW_EXTRA_TEXT, currentMovie.getOverview());

        startActivity(intentToDestinationClass);
    }

    private class MovieDBTask extends AsyncTask<URL, Void, List<Movie>>{

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

                return parser.parseJSON();

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
                showErrorMessage();
            }
        }
    }

    private void hideErrorMessage(){
        mNetworkErrorContainer.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        String errorMessage = "Oop!..\nWe can't seem to connect to the network.";
        mNetworkErrorContainer.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.INVISIBLE);
        mNetworkMessage.setText(errorMessage);
        mRetryNetworkConnection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchMovies(currentCategory);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int selectedItemId = item.getItemId();

        if(selectedItemId == R.id.action_popular_movies){
            currentCategory = movie_categories[0];
            fetchMovies(currentCategory);
            return true;
        }

        if(selectedItemId == R.id.action_top_rated){
            currentCategory = movie_categories[1];
            fetchMovies(currentCategory);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
