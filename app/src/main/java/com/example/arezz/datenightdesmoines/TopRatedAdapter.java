package com.example.arezz.datenightdesmoines;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

public class TopRatedAdapter extends RecyclerView.Adapter<TopRatedAdapter.TopRatedViewHolder> {
    private Context context;
    private ArrayList<Rating> ratings;

    public TopRatedAdapter(Context context, ArrayList<Rating> dataset) {
        this.context = context;
        this.ratings = dataset;
    }

    public static class TopRatedViewHolder extends RecyclerView.ViewHolder {
        public TextView dateNameView;
        public RatingBar ratingView;
        public TopRatedViewHolder(View v) {
            super(v);
            dateNameView = v.findViewById(R.id.date_name_view);
            ratingView = v.findViewById(R.id.rating_view);
        }
    }

    @Override
    public int getItemCount() {
        return ratings.size();
    }

    @Override
    public TopRatedAdapter.TopRatedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rating_cell, parent, false);
        TopRatedViewHolder vh = new TopRatedViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(TopRatedViewHolder holder, int position) {
        holder.dateNameView.setText(ratings.get(position).getDateName());
        holder.ratingView.setRating(ratings.get(position).getRating());
    }
}
