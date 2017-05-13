package com.example.android.meerkat.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.meerkat.R;
import com.example.android.meerkat.data.FavoriteMoviesContract;
import com.squareup.picasso.Picasso;

public class FavoriteMovieAdapter extends RecyclerView.Adapter<FavoriteMovieAdapter.FavoriteMovieAdapterViewHolder>{

    private Cursor mCursor;
    private Context context;
    private final FavoriteMovieAdapterOnClickListener mClickHandler;

    public FavoriteMovieAdapter(FavoriteMovieAdapterOnClickListener clickListener){
        mClickHandler = clickListener;
        //mCursor = cursor;
    }

    public interface FavoriteMovieAdapterOnClickListener{
        void onClick(Cursor cursor);
    }

    public void swapCursor(Cursor newCursor) {
        if (mCursor != null){
            mCursor.close();
        }
        mCursor = newCursor;
        if (newCursor != null) {
            this.notifyDataSetChanged();
        }
    }

    @Override
    public FavoriteMovieAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        int layoutIdForListItem = R.layout.favorite_movie_grid;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new FavoriteMovieAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FavoriteMovieAdapterViewHolder holder, int position) {
        if(!mCursor.moveToPosition(position))
            return;

        String imageUrl = mCursor.getString(mCursor.getColumnIndex(
                FavoriteMoviesContract.MoviesEntry.COLUMN_POSTER_URL));
        Picasso.with(context)
                .load(imageUrl)
                .placeholder(R.drawable.image_placeholder)
                .error(R.drawable.image_placeholder_error)
                .into(holder.mMoviePoster);
    }

    @Override
    public int getItemCount() {
        if(mCursor == null){
            return 0;
        }
        return mCursor.getCount();
    }

    public class FavoriteMovieAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final ImageView mMoviePoster;

        public FavoriteMovieAdapterViewHolder(View view){
            super(view);
            mMoviePoster = (ImageView)view.findViewById(R.id.iv_movie_poster);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            mCursor.moveToPosition(adapterPosition);
            mClickHandler.onClick(mCursor);
        }
    }
}
