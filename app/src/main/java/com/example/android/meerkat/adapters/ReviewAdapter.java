package com.example.android.meerkat.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.meerkat.R;
import com.example.android.meerkat.model.Review;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewAdapterViewHolder>{

    private List<Review> mReviews;

    @Override
    public ReviewAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.reviews_list;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new ReviewAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewAdapterViewHolder holder, int position) {
        Review review = mReviews.get(position);
        String author = review.getAuthor();
        String content = review.getContent();

        holder.mAuthor.setText(author);
        holder.mContent.setText(content);
    }

    @Override
    public int getItemCount(){
        if (mReviews == null ) return 0;
        return mReviews.size();
    }

    public void setReviewData(List<Review> reviews){
        mReviews= reviews;
        notifyDataSetChanged();
    }

    class ReviewAdapterViewHolder extends RecyclerView.ViewHolder {

        private final TextView mAuthor;
        private final TextView mContent;

        ReviewAdapterViewHolder(View view){
            super(view);
            mAuthor = (TextView)view.findViewById(R.id.tv_author_name);
            mContent = (TextView)view.findViewById(R.id.tv_author_review);
        }
    }
}
