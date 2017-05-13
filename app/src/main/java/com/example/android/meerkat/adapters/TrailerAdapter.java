package com.example.android.meerkat.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.meerkat.R;
import com.example.android.meerkat.model.Trailer;

import java.util.List;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerAdapterViewHolder>{
    private List<Trailer> mTrailers;

    private final TrailerAdapterOnClickListener mClickHandler;

    @Override
    public TrailerAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.trailers_list;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new TrailerAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TrailerAdapterViewHolder holder, int position) {
        Trailer trailer = mTrailers.get(position);
        String name = trailer.getName();
        String key = trailer.getKey();

        holder.itemView.setTag(key);
        holder.mTrailerName.setText(name);
    }

    @Override
    public int getItemCount() {
        if (mTrailers == null ) return 0;
        return mTrailers.size();
    }

    private Trailer getTrailerByPosition(int position){
        return mTrailers.get(position);
    }

    public interface TrailerAdapterOnClickListener{
        void onClick(Trailer currentTrailer);
    }

    public TrailerAdapter(TrailerAdapterOnClickListener clickListener){
        mClickHandler = clickListener;
    }

    public List<Trailer> getTrailers(){
        return mTrailers;
    }

    public void setTrailerData(List<Trailer> trailers){
        mTrailers = trailers;
        notifyDataSetChanged();
    }

    public class TrailerAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView mTrailerName;

        public TrailerAdapterViewHolder(View view){
            super(view);
            mTrailerName = (TextView) view.findViewById(R.id.tv_trailer_name);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            mClickHandler.onClick(getTrailerByPosition(adapterPosition));
        }
    }
}
