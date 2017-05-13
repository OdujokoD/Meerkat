package com.example.android.meerkat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.meerkat.adapters.MovieAdapter;
import com.example.android.meerkat.model.Movie;
import com.example.android.meerkat.utilities.JSONParser;
import com.example.android.meerkat.utilities.NetworkUtils;

import java.net.URL;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.android.meerkat.utilities.Constants.FETCH_MOVIE_URL_EXTRA;
import static com.example.android.meerkat.utilities.Constants.MOVIE_DB_LOADER_ID;
import static com.example.android.meerkat.utilities.Constants.POPULAR_MOVIE_PATH;
import static com.example.android.meerkat.utilities.Constants.PREFS_NAME;
import static com.example.android.meerkat.utilities.Constants.PREFS_PARAM;
import static com.example.android.meerkat.utilities.Constants.TOP_RATED_MOVIE_PATH;

public class MainActivity extends AppCompatActivity implements
        MovieAdapter.MovieAdapterOnClickListener, LoaderManager.LoaderCallbacks<List<Movie>>{

    @BindView(R.id.pb_loading_indicator)
    ProgressBar mLoadingIndicator;
    @BindView(R.id.recyclerview_movie_list)
    RecyclerView mRecyclerView;
    @BindView(R.id.ll_network_error)
    LinearLayout mNetworkErrorContainer;
    @BindView(R.id.tv_network_error_message)
    TextView mNetworkMessage;
    @BindView(R.id.btn_retry)
    Button mRetryNetworkConnection;

    private MovieAdapter mMovieAdapter;
//    final static String[] movie_categories = {"popular", "top_rated"};
    private String currentCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        GridLayoutManager layoutManager = new GridLayoutManager(this,getSpanCount());

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        mMovieAdapter = new MovieAdapter(this);
        mRecyclerView.setAdapter(mMovieAdapter);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        currentCategory = settings.getString(PREFS_PARAM, POPULAR_MOVIE_PATH);
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
        URL movieListUrl = NetworkUtils.buildMovieURL(category);
        /*if(category.equals(movie_categories[0])){
            movieListUrl = NetworkUtils.buildPopularMoviesURL(category);
        }
        if(category.equals(movie_categories[1])){
            movieListUrl = NetworkUtils.buildTopRatedMoviesURL();
        }*/

        ConnectivityManager connectivityManager =
                (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        boolean isConnected = activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();

        if(isConnected) {
            hideErrorMessage();
            /*MovieDBTask movieDBTask = new MovieDBTask(mLoadingIndicator, mMovieAdapter);
            movieDBTask.execute(movieListUrl);*/

            Bundle fetchBundle = new Bundle();
            if (movieListUrl != null) {
                fetchBundle.putString(FETCH_MOVIE_URL_EXTRA, movieListUrl.toString());
            }

            LoaderManager loaderManager = getSupportLoaderManager();
            Loader<List<Movie>> fetchMovieLoader = loaderManager.getLoader(MOVIE_DB_LOADER_ID);

            if(fetchMovieLoader == null){
                loaderManager.initLoader(MOVIE_DB_LOADER_ID, fetchBundle, this);
            }
            else {
                loaderManager.restartLoader(MOVIE_DB_LOADER_ID, fetchBundle, this);
            }
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
        intentToDestinationClass.putExtra("Movie", currentMovie);
        startActivity(intentToDestinationClass);
    }


    private void hideErrorMessage(){
        mNetworkErrorContainer.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    public void showErrorMessage() {
        String errorMessage = "Oops!..\nWe can't seem to connect to the network.";
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

        switch (selectedItemId){
            case R.id.action_popular_movies:
                currentCategory = POPULAR_MOVIE_PATH;
                fetchMovies(currentCategory);
                return true;
            case R.id.action_top_rated:
                currentCategory = TOP_RATED_MOVIE_PATH;
                fetchMovies(currentCategory);
                return true;
            case R.id.action_favorite:
                Intent intent = new Intent(getApplicationContext(), FavoriteMovies.class);
                startActivity(intent);
                break;
            default:
                Log.d("Error", "ID does not exist");
                break;
        }

        /*if(selectedItemId == R.id.action_popular_movies){
            currentCategory = POPULAR_MOVIE_PATH;
            fetchMovies(currentCategory);
            return true;
        }

        if(selectedItemId == R.id.action_top_rated){
            currentCategory = TOP_RATED_MOVIE_PATH;
            fetchMovies(currentCategory);
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<List<Movie>> onCreateLoader(int id, final Bundle args) {
        return new AsyncTaskLoader<List<Movie>>(this) {
            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                if(args == null){
                    return;
                }
                mLoadingIndicator.setVisibility(View.VISIBLE);
                forceLoad();
            }

            @Override
            public List<Movie> loadInBackground() {
                String fetchMovieUrlString = args.getString(FETCH_MOVIE_URL_EXTRA);
                if(fetchMovieUrlString == null){
                    return null;
                }
                try {
                    URL moviesRequestUrl = new URL(fetchMovieUrlString);
                    String jsonMovieResponse = NetworkUtils
                            .getResponseFromHttpUrl(moviesRequestUrl);

                    JSONParser parser = new JSONParser(jsonMovieResponse);

                    return parser.parseMovieJSON();

                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<List<Movie>> loader, List<Movie> data) {
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        if (data != null) {
            mMovieAdapter.setMovieData(data);
        } else {
            showErrorMessage();
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Movie>> loader) {

    }
}
