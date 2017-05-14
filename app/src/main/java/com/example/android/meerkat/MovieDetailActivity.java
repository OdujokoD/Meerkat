package com.example.android.meerkat;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.meerkat.adapters.ReviewAdapter;
import com.example.android.meerkat.adapters.TrailerAdapter;
import com.example.android.meerkat.data.FavoriteMoviesContract;
import com.example.android.meerkat.data.ReviewsContract;
import com.example.android.meerkat.data.TrailersContract;
import com.example.android.meerkat.loaders.ReviewLoader;
import com.example.android.meerkat.loaders.TrailerLoader;
import com.example.android.meerkat.model.Movie;
import com.example.android.meerkat.model.Review;
import com.example.android.meerkat.model.Trailer;
import com.example.android.meerkat.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.android.meerkat.utilities.Constants.FETCH_REVIEW_URL_EXTRA;
import static com.example.android.meerkat.utilities.Constants.FETCH_TRAILER_URL_EXTRA;
import static com.example.android.meerkat.utilities.Constants.REVIEW_LOADER_ID;
import static com.example.android.meerkat.utilities.Constants.TRAILER_LOADER_ID;

public class MovieDetailActivity extends AppCompatActivity implements
        TrailerAdapter.TrailerAdapterOnClickListener{

    @BindView(R.id.movie_detail_poster)
    ImageView mMoviePoster;
    @BindView(R.id.tv_movie_title)
    TextView mMovieTitle;
    @BindView(R.id.tv_movie_release_date)
    TextView mMovieReleaseDate;
    @BindView(R.id.tv_movie_rating)
    TextView mMovieRating;
    @BindView(R.id.tv_movie_overview)
    TextView mMovieOverview;
    @BindView(R.id.rv_trailers_list)
    RecyclerView mTrailerRecyclerView;
    @BindView(R.id.rv_reviews_list)
    RecyclerView mReviewsRecyclerView;

    private ReviewLoader reviewLoader;
    private TrailerLoader trailerLoader;

    private String movieId;
    private String movieTitle;
    private String imageUrl;
    private String movieReleaseDate;
    private String movieRating;
    private String movieOverview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);

        mTrailerRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mTrailerRecyclerView.setHasFixedSize(true);

        TrailerAdapter mTrailerAdapter = new TrailerAdapter(this);
        mTrailerRecyclerView.setAdapter(mTrailerAdapter);

        mReviewsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mReviewsRecyclerView.setHasFixedSize(true);

        ReviewAdapter mReviewAdapter = new ReviewAdapter();
        mReviewsRecyclerView.setAdapter(mReviewAdapter);


        Intent parentIntent = getIntent();
        Movie movies = parentIntent.getExtras().getParcelable("Movie");

        if (movies != null) {
            imageUrl = movies.getMoviePosterURL();
            Picasso.with(getApplicationContext())
                    .load(imageUrl)
                    .placeholder(R.drawable.image_placeholder_detail)
                    .error(R.drawable.image_placeholder_detail_error)
                    .into(mMoviePoster);

            movieId = movies.getMovieId();

            movieTitle = movies.getOriginalTitle();
            mMovieTitle.setText(movieTitle);

            movieReleaseDate = movies.getReleaseDate();
            mMovieReleaseDate.setText(movieReleaseDate);

            movieRating = movies.getVoteAverage();
            mMovieRating.setText(movieRating);

            movieOverview = movies.getOverview();
            mMovieOverview.setText(movieOverview);

            trailerLoader = new TrailerLoader(this, mTrailerAdapter);
            fetchTrailers(movieId);

            reviewLoader = new ReviewLoader(this, mReviewAdapter);
            fetchReviews(movieId);
        }
    }

    private void fetchReviews(String movieId){
        URL reviewListUrl = NetworkUtils.buildReviewURL(movieId);

        ConnectivityManager connectivityManager =
                (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        boolean isConnected = activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();

        if(isConnected) {
            //hideErrorMessage();

            Bundle fetchBundle = new Bundle();
            if (reviewListUrl != null) {
                fetchBundle.putString(FETCH_REVIEW_URL_EXTRA, reviewListUrl.toString());
            }

            LoaderManager loaderManager = getSupportLoaderManager();
            Loader<List<Review>> fetchReviewLoader = loaderManager.getLoader(REVIEW_LOADER_ID);

            if(fetchReviewLoader == null){
                loaderManager.initLoader(REVIEW_LOADER_ID, fetchBundle, reviewLoader);
            }
            else {
                loaderManager.restartLoader(REVIEW_LOADER_ID, fetchBundle, reviewLoader);
            }
        }
        else{
            //showErrorMessage();
            Log.d("ERROR", "Error fetching reviews");
        }
    }

    private void fetchTrailers(String movieId){
        URL trailerListUrl = NetworkUtils.buildVideosURL(movieId);

        ConnectivityManager connectivityManager =
                (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        boolean isConnected = activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();

        if(isConnected) {
            //hideErrorMessage();

            Bundle fetchBundle = new Bundle();
            if (trailerListUrl != null) {
                fetchBundle.putString(FETCH_TRAILER_URL_EXTRA, trailerListUrl.toString());
            }

            LoaderManager loaderManager = getSupportLoaderManager();
            Loader<List<Trailer>> fetchTrailerLoader = loaderManager.getLoader(TRAILER_LOADER_ID);

            if(fetchTrailerLoader == null){
                loaderManager.initLoader(TRAILER_LOADER_ID, fetchBundle, trailerLoader);
            }
            else {
                loaderManager.restartLoader(TRAILER_LOADER_ID, fetchBundle, trailerLoader);
            }
        }
        else{
            //showErrorMessage();
            Log.d("ERROR", "Error fetching trailers");
        }
    }

    @Override
    public void onClick(Trailer currentTrailer) {

    }

    public void addToFavorites(View view) {
        ContentValues movieContentValues = new ContentValues();
        ContentValues trailersContentValues = new ContentValues();
        ContentValues reviewsContentValues = new ContentValues();

        List<Review> reviews = reviewLoader.getReviews();
        List<Trailer> trailers = trailerLoader.getTrailers();

        movieContentValues.put(FavoriteMoviesContract.MoviesEntry.COLUMN_MOVIE_TITLE, movieTitle);
        movieContentValues.put(FavoriteMoviesContract.MoviesEntry.COLUMN_POSTER_URL, imageUrl);
        movieContentValues.put(FavoriteMoviesContract.MoviesEntry.COLUMN_MOVIE_OVERVIEW, movieOverview);
        movieContentValues.put(FavoriteMoviesContract.MoviesEntry.COLUMN_RELEASE_DATE, movieReleaseDate);
        movieContentValues.put(FavoriteMoviesContract.MoviesEntry.COLUMN_VOTE_AVERAGE, movieRating);

        Log.d("TRAILER SIZE", trailers.size() + "");

        for (Trailer trailer: trailers) {
            trailersContentValues.put(TrailersContract.TrailersEntry.COLUMN_NAME, trailer.getName());
            trailersContentValues.put(TrailersContract.TrailersEntry.COLUMN_KEY, trailer.getKey());
            trailersContentValues.put(TrailersContract.TrailersEntry.COLUMN_MOVIE_ID, movieId);
        }

        for (Review review: reviews) {
            reviewsContentValues.put(ReviewsContract.ReviewsEntry.COLUMN_AUTHOR, review.getAuthor());
            reviewsContentValues.put(ReviewsContract.ReviewsEntry.COLUMN_CONTENT, review.getContent());
            reviewsContentValues.put(ReviewsContract.ReviewsEntry.COLUMN_MOVIE_ID, movieId);
        }

        Uri movieUri = getApplicationContext().getContentResolver()
                .insert(FavoriteMoviesContract.MoviesEntry.CONTENT_URI, movieContentValues);
        Uri trailerUri = getApplicationContext().getContentResolver()
                .insert(TrailersContract.TrailersEntry.CONTENT_URI, trailersContentValues);
        Uri reviewUri = getContentResolver()
                .insert(ReviewsContract.ReviewsEntry.CONTENT_URI, reviewsContentValues);

        if(movieUri != null) {
            Toast.makeText(getBaseContext(), movieUri.toString(), Toast.LENGTH_LONG).show();
        }
        if(trailerUri != null) {
            Toast.makeText(getBaseContext(), trailerUri.toString(), Toast.LENGTH_LONG).show();
        }
        if(reviewUri != null) {
            Toast.makeText(getBaseContext(), reviewUri.toString(), Toast.LENGTH_LONG).show();
        }
    }
}
