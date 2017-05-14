package com.example.android.meerkat.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.meerkat.R;
import com.example.android.meerkat.data.TrailersContract;

public class FavoriteMovieTrailerAdapter extends
        RecyclerView.Adapter<FavoriteMovieTrailerAdapter.FavoriteMovieTrailerAdapterViewHolder>{

    private final FavoriteMovieTrailerOnClickListener mClickHandler;

    private Cursor mCursor;

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
    public FavoriteMovieTrailerAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.trailers_list;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new FavoriteMovieTrailerAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FavoriteMovieTrailerAdapterViewHolder holder, int position) {
        if(!mCursor.moveToPosition(position))
            return;

        String name = mCursor.getString(mCursor.getColumnIndex(
                TrailersContract.TrailersEntry.COLUMN_NAME));
        String key = mCursor.getString(mCursor.getColumnIndex(
                TrailersContract.TrailersEntry.COLUMN_KEY));

        holder.itemView.setTag(key);
        holder.mTrailerName.setText(name);
    }

    @Override
    public int getItemCount() {
        if(mCursor == null){
            return 0;
        }
        return mCursor.getCount();
    }

    public interface FavoriteMovieTrailerOnClickListener{
        void onClick(Cursor cursor);
    }

    public FavoriteMovieTrailerAdapter(FavoriteMovieTrailerOnClickListener clickListener){
        mClickHandler = clickListener;
    }

    class FavoriteMovieTrailerAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView mTrailerName;

        FavoriteMovieTrailerAdapterViewHolder(View view){
            super(view);
            mTrailerName = (TextView) view.findViewById(R.id.tv_trailer_name);
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
