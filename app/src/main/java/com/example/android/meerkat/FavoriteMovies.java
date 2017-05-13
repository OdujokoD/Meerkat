package com.example.android.meerkat;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.widget.ProgressBar;

import com.example.android.meerkat.adapters.FavoriteMovieAdapter;
import com.example.android.meerkat.adapters.MovieAdapter;
import com.example.android.meerkat.loaders.FavoriteMovieDbLoader;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.android.meerkat.utilities.Constants.MOVIE_LOADER_ID;

public class FavoriteMovies extends AppCompatActivity implements
        FavoriteMovieAdapter.FavoriteMovieAdapterOnClickListener{

    @BindView(R.id.rv_favorite_movie_list)
    RecyclerView mFavoriteMovieList;
    @BindView(R.id.pb_favorite_movie_indicator)
    ProgressBar mProgressbar;

    private FavoriteMovieAdapter favoriteMovieAdapter;
    private FavoriteMovieDbLoader favoriteMovieDbLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_movies);
        ButterKnife.bind(this);
        GridLayoutManager layoutManager = new GridLayoutManager(this, getSpanCount());
        mFavoriteMovieList.setLayoutManager(layoutManager);
        mFavoriteMovieList.setHasFixedSize(true);

        favoriteMovieAdapter = new FavoriteMovieAdapter(this);
        mFavoriteMovieList.setAdapter(favoriteMovieAdapter);

        favoriteMovieDbLoader = new FavoriteMovieDbLoader(this, favoriteMovieAdapter);
        getSupportLoaderManager().initLoader(MOVIE_LOADER_ID, null, favoriteMovieDbLoader);
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

    @Override
    protected void onResume() {
        super.onResume();
        getSupportLoaderManager().restartLoader(MOVIE_LOADER_ID, null, favoriteMovieDbLoader);
    }

    @Override
    public void onClick(Cursor cursor) {

    }
}
