package com.example.arezz.datenightdesmoines;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

import io.realm.RealmObjectChangeListener;
import io.realm.RealmResults;

public class TopRatedAdapter extends RecyclerView.Adapter<TopRatedAdapter.TopRatedViewHolder> {
    private Context context;
    private RealmResults<Night> topRatings;
    private RecyclerViewClickListener mListener;

    public TopRatedAdapter(Context context, RealmResults<Night> dataset, RecyclerViewClickListener
                           clickListener) {
        this.context = context;
        this.topRatings = dataset;
        this.mListener = clickListener;
    }

    public static class TopRatedViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView dateNameView;
        public RatingBar ratingView;
        private RecyclerViewClickListener mListener;
        public TopRatedViewHolder(View v, RecyclerViewClickListener listener) {
            super(v);
            dateNameView = v.findViewById(R.id.date_name_view);
            ratingView = v.findViewById(R.id.rating_view);
            ratingView.setIsIndicator(true);
            mListener = listener;
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mListener.onClick(view, getAdapterPosition());
        }
    }

    @Override
    public int getItemCount() {
        return topRatings.size();
    }

    @Override
    public TopRatedAdapter.TopRatedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rating_cell, parent, false);
        TopRatedViewHolder vh = new TopRatedViewHolder(v, mListener);
        return vh;
    }

    @Override
    public void onBindViewHolder(TopRatedViewHolder holder, int position) {
        holder.dateNameView.setText(topRatings.get(position).getDateName());
        holder.ratingView.setRating(topRatings.get(position).getRating());
    }
}
