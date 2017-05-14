package com.example.android.meerkat.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.meerkat.R;
import com.example.android.meerkat.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder>{

    private List<Movie> movieData;
    private Context context;

    private final MovieAdapterOnClickListener mClickHandler;

    @Override
    public MovieAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        int layoutIdForListItem = R.layout.movie_poster_grid;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new MovieAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieAdapterViewHolder holder, int position) {
        Movie movie = movieData.get(position);
        String imageUrl = movie.getMoviePosterURL();
        Picasso.with(context)
                .load(imageUrl)
                .placeholder(R.drawable.image_placeholder)
                .error(R.drawable.image_placeholder_error)
                .into(holder.mMoviePoster);
    }

    @Override
    public int getItemCount() {
        if (movieData == null ) return 0;
        return movieData.size();
    }

    private Movie getMovieByPosition(int position){
        return movieData.get(position);
    }

    public interface MovieAdapterOnClickListener{
        void onClick(Movie currentMovie);
    }

    public MovieAdapter(MovieAdapterOnClickListener clickListener){
        mClickHandler = clickListener;
    }

    public void setMovieData(List<Movie> movieData){
        this.movieData = movieData;
        notifyDataSetChanged();
    }

    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements OnClickListener {

        private final ImageView mMoviePoster;

        public MovieAdapterViewHolder(View view){
            super(view);
            mMoviePoster = (ImageView)view.findViewById(R.id.movie_poster);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            mClickHandler.onClick(getMovieByPosition(adapterPosition));
        }
    }
}
