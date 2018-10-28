package com.example.arezz.datenightdesmoines;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

public class PastNightAdapter extends RecyclerView.Adapter<PastNightAdapter.PastNightsViewHolder> {
    private Context context;
    private ArrayList<Night> nights;
    private RecyclerViewClickListener mListener;

    public PastNightAdapter(Context context, ArrayList<Night> dataset, RecyclerViewClickListener
            clickListener) {
        this.context = context;
        this.nights = dataset;
        this.mListener = clickListener;
    }

    public static class PastNightsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView dateNameView;
        public RatingBar ratingView;
        private RecyclerViewClickListener mListener;
        public PastNightsViewHolder(View v, RecyclerViewClickListener listener) {
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
        return nights.size();
    }

    @Override
    public PastNightAdapter.PastNightsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.past_night_cell, parent, false);
        PastNightAdapter.PastNightsViewHolder vh = new PastNightAdapter.PastNightsViewHolder(v, mListener);
        return vh;
    }

    @Override
    public void onBindViewHolder(PastNightsViewHolder holder, int position) {
        holder.dateNameView.setText(nights.get(position).getDateName());
        holder.ratingView.setRating(nights.get(position).getRating());
    }
}
