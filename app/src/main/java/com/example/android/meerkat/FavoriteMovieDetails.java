package com.example.android.meerkat;

import android.database.Cursor;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.meerkat.adapters.FavoriteMovieAdapter;
import com.example.android.meerkat.adapters.FavoriteMovieReviewAdapter;
import com.example.android.meerkat.adapters.FavoriteMovieTrailerAdapter;
import com.example.android.meerkat.adapters.ReviewAdapter;
import com.example.android.meerkat.adapters.TrailerAdapter;
import com.example.android.meerkat.loaders.ReviewLoader;
import com.example.android.meerkat.loaders.ReviewsDbLoader;
import com.example.android.meerkat.loaders.TrailerLoader;
import com.example.android.meerkat.loaders.TrailersDbLoader;
import com.example.android.meerkat.model.Review;
import com.example.android.meerkat.model.Trailer;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.android.meerkat.utilities.Constants.FETCH_REVIEW_URL_EXTRA;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_movie_details);
        ButterKnife.bind(this);

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

    private void fetchMovieDetails(){

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
    }

    @Override
    public void onClick(Cursor cursor) {

    }
}
