package com.example.arezz.datenightdesmoines;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class YelpItemAdapter extends RecyclerView.Adapter<YelpItemAdapter.YelpItemViewHolder> {
    private Context context;
    private ArrayList<YelpItem> yelpItems;


    public YelpItemAdapter(Context context, ArrayList<YelpItem> dataSet) {
        this.context = context;
        this.yelpItems = dataSet;
    }

    public static class YelpItemViewHolder extends RecyclerView.ViewHolder {
        public TextView nameView;
        public TextView starsView;

        public YelpItemViewHolder(View v) {
            super(v);
            nameView = v.findViewById(R.id.yelp_name_view);
            starsView = v.findViewById(R.id.yelp_stars_view);
        }
    }
    @Override
    public int getItemCount() {
        return yelpItems.size();
    }
    @Override
    public YelpItemAdapter.YelpItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.yelp_item_cell, parent, false);
        YelpItemViewHolder vh = new YelpItemViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(YelpItemViewHolder holder, int position) {
        holder.nameView.setText(yelpItems.get(position).getName());
        holder.starsView.setText(yelpItems.get(position).getStars());
    }


}
