package com.example.android.meerkat;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.meerkat.adapters.FavoriteMovieReviewAdapter;
import com.example.android.meerkat.adapters.FavoriteMovieTrailerAdapter;
import com.example.android.meerkat.data.FavoriteMoviesContract;
import com.example.android.meerkat.data.ReviewsContract;
import com.example.android.meerkat.data.TrailersContract;
import com.example.android.meerkat.loaders.ReviewsDbLoader;
import com.example.android.meerkat.loaders.TrailersDbLoader;
import com.example.android.meerkat.model.Review;
import com.example.android.meerkat.model.Trailer;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.android.meerkat.utilities.Constants.MOVIE_ID;
import static com.example.android.meerkat.utilities.Constants.MOVIE_NAME;
import static com.example.android.meerkat.utilities.Constants.OVERVIEW;
import static com.example.android.meerkat.utilities.Constants.POSTER_URL;
import static com.example.android.meerkat.utilities.Constants.RATING;
import static com.example.android.meerkat.utilities.Constants.RELEASE_DATE;
import static com.example.android.meerkat.utilities.Constants.REVIEW_LOADER_ID;
import static com.example.android.meerkat.utilities.Constants.TRAILER_LOADER_ID;

public class FavoriteMovieDetails extends AppCompatActivity implements
        FavoriteMovieTrailerAdapter.FavoriteMovieTrailerOnClickListener{

    @BindView(R.id.iv_fav_mov_detail_poster)
    ImageView mMoviePoster;
    @BindView(R.id.tv_fav_mov_title)
    TextView mMovieTitle;
    @BindView(R.id.tv_fav_mov_release_date)
    TextView mMovieReleaseDate;
    @BindView(R.id.tv_fav_mov_rating)
    TextView mMovieRating;
    @BindView(R.id.tv_fav_mov_overview)
    TextView mMovieOverview;
    @BindView(R.id.rv_fav_mov_trailers_list)
    RecyclerView mTrailerRecyclerView;
    @BindView(R.id.rv_fav_mov_reviews_list)
    RecyclerView mReviewsRecyclerView;

    private ReviewsDbLoader reviewsDbLoader;
    private TrailersDbLoader trailersDbLoader;
    private String movieId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_movie_details);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        populateViews(intent);

        mTrailerRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mTrailerRecyclerView.setHasFixedSize(true);

        FavoriteMovieTrailerAdapter favoriteMovieTrailerAdapter = new FavoriteMovieTrailerAdapter(this);
        mTrailerRecyclerView.setAdapter(favoriteMovieTrailerAdapter);

        mReviewsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mReviewsRecyclerView.setHasFixedSize(true);

        FavoriteMovieReviewAdapter favoriteMovieReviewAdapter = new FavoriteMovieReviewAdapter();
        mReviewsRecyclerView.setAdapter(favoriteMovieReviewAdapter);

        trailersDbLoader = new TrailersDbLoader(this, favoriteMovieTrailerAdapter);
        fetchTrailers();

        reviewsDbLoader = new ReviewsDbLoader(this, favoriteMovieReviewAdapter);
        fetchReviews();
    }

    private void populateViews(Intent intent){
        if(intent.hasExtra(POSTER_URL)){
            String imageUrl = intent.getExtras().getString(POSTER_URL);
            Picasso.with(this)
                    .load(imageUrl)
                    .placeholder(R.drawable.image_placeholder)
                    .error(R.drawable.image_placeholder_error)
                    .into(mMoviePoster);
        }

        if(intent.hasExtra(MOVIE_NAME)){
            String movieName = intent.getExtras().getString(MOVIE_NAME);
            mMovieTitle.setText(movieName);
        }

        if(intent.hasExtra(OVERVIEW)){
            String overview = intent.getExtras().getString(OVERVIEW);
            mMovieOverview.setText(overview);
        }

        if(intent.hasExtra(RATING)){
            String rating = intent.getExtras().getString(RATING);
            mMovieRating.setText(rating);
        }

        if(intent.hasExtra(RELEASE_DATE)){
            String releaseDate = intent.getExtras().getString(RELEASE_DATE);
            mMovieReleaseDate.setText(releaseDate);
        }

        if(intent.hasExtra(MOVIE_ID)){
            movieId = intent.getExtras().getString(MOVIE_ID);
        }
    }

    private void fetchTrailers(){
        LoaderManager loaderManager = getSupportLoaderManager();
        Loader<List<Trailer>> fetchTrailerLoader = loaderManager.getLoader(TRAILER_LOADER_ID);

        if(fetchTrailerLoader == null){
            loaderManager.initLoader(TRAILER_LOADER_ID, null, trailersDbLoader);
        }
        else {
            loaderManager.restartLoader(TRAILER_LOADER_ID, null, trailersDbLoader);
        }
    }

    private void fetchReviews(){

        LoaderManager loaderManager = getSupportLoaderManager();
        Loader<List<Review>> fetchReviewLoader = loaderManager.getLoader(REVIEW_LOADER_ID);

        if(fetchReviewLoader == null){
            loaderManager.initLoader(REVIEW_LOADER_ID, null, reviewsDbLoader);
        }
        else {
            loaderManager.restartLoader(REVIEW_LOADER_ID, null, reviewsDbLoader);
        }
    }

    public void removeFromFavorites(View view) {
        Uri moviesUri = FavoriteMoviesContract.MoviesEntry.CONTENT_URI;
        moviesUri = moviesUri.buildUpon().appendPath(movieId).build();
        Uri reviewsUri = ReviewsContract.ReviewsEntry.CONTENT_URI;
        reviewsUri = reviewsUri.buildUpon().appendPath(movieId).build();
        Uri trailerUri = TrailersContract.TrailersEntry.CONTENT_URI;
        trailerUri = trailerUri.buildUpon().appendPath(movieId).build();

        getContentResolver().delete(moviesUri, null, null);
        getContentResolver().delete(reviewsUri, null, null);
        getContentResolver().delete(trailerUri, null, null);

        Intent intent = new Intent(FavoriteMovieDetails.this, FavoriteMovies.class);
        startActivity(intent);
    }

    @Override
    public void onClick(Cursor cursor) {

    }
}
