package com.example.android.meerkat.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.meerkat.R;
import com.example.android.meerkat.data.ReviewsContract;

public class FavoriteMovieReviewAdapter extends
        RecyclerView.Adapter<FavoriteMovieReviewAdapter.FavoriteMovieReviewViewHolder>{

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
    public FavoriteMovieReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.reviews_list;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new FavoriteMovieReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FavoriteMovieReviewViewHolder holder, int position) {
        if(!mCursor.moveToPosition(position))
            return;

        String author = mCursor.getString(mCursor.getColumnIndex(
                ReviewsContract.ReviewsEntry.COLUMN_AUTHOR));
        String content = mCursor.getString(mCursor.getColumnIndex(
                ReviewsContract.ReviewsEntry.COLUMN_CONTENT));

        holder.mAuthor.setText(author);
        holder.mContent.setText(content);
    }

    @Override
    public int getItemCount() {
        if(mCursor == null){
            return 0;
        }
        return mCursor.getCount();
    }

    class FavoriteMovieReviewViewHolder extends RecyclerView.ViewHolder {

        private final TextView mAuthor;
        private final TextView mContent;

        FavoriteMovieReviewViewHolder(View view){
            super(view);
            mAuthor = (TextView)view.findViewById(R.id.tv_author_name);
            mContent = (TextView)view.findViewById(R.id.tv_author_review);
        }
    }
}
