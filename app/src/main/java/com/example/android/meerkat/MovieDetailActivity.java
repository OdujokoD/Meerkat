package com.example.android.meerkat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MovieDetailActivity extends AppCompatActivity {

    private ImageView mMoviePoster;
    private TextView mMovieTitle;
    private TextView mMovieReleaseDate;
    private TextView mMovieRating;
    private TextView mMovieOverview;

    private final static String MOVIE_TITLE_EXTRA_TEXT = "movie title";
    private final static String MOVIE_IMAGE_EXTRA_TEXT = "movie poster url";
    private final static String MOVIE_RELEASE_DATE_EXTRA_TEXT = "movie release date";
    private final static String MOVIE_RATING_EXTRA_TEXT = "movie rating";
    private final static String MOVIE_OVERVIEW_EXTRA_TEXT = "movie overview";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        mMoviePoster = (ImageView)findViewById(R.id.movie_detail_poster);
        mMovieTitle = (TextView)findViewById(R.id.tv_movie_title);
        mMovieReleaseDate = (TextView)findViewById(R.id.tv_movie_release_date);
        mMovieRating = (TextView)findViewById(R.id.tv_movie_rating);
        mMovieOverview = (TextView)findViewById(R.id.tv_movie_overview);

        Intent parentIntent = getIntent();

        if(parentIntent != null){
            if (parentIntent.getExtras() != null) {
                String imageUrl = parentIntent.getStringExtra(MOVIE_IMAGE_EXTRA_TEXT);
                Picasso.with(getApplicationContext()).load(imageUrl).into(mMoviePoster);

                String movieTitle = parentIntent.getStringExtra(MOVIE_TITLE_EXTRA_TEXT);
                mMovieTitle.setText(movieTitle);

                String movieReleaseDate = parentIntent.getStringExtra(MOVIE_RELEASE_DATE_EXTRA_TEXT);
                mMovieReleaseDate.setText(movieReleaseDate);

                String movieRating = parentIntent.getStringExtra(MOVIE_RATING_EXTRA_TEXT);
                mMovieRating.setText(movieRating);

                String movieOverview = parentIntent.getStringExtra(MOVIE_OVERVIEW_EXTRA_TEXT);
                mMovieOverview.setText(movieOverview);
            }
        }
    }
}
