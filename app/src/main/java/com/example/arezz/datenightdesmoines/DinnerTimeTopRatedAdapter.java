package com.example.arezz.datenightdesmoines;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import io.realm.RealmResults;

public class DinnerTimeTopRatedAdapter extends RecyclerView.Adapter<DinnerTimeTopRatedAdapter.DinnerTimeViewHolder> {
    private Context context;
    private RealmResults<Event> selectedNight;

    public DinnerTimeTopRatedAdapter(Context context, RealmResults<Event> dataset) {
        this.context = context;
        this.selectedNight = dataset;
    }

    public static class DinnerTimeViewHolder extends RecyclerView.ViewHolder {
        public TextView dinnerTimeRange;
        public TextView restaurant;
        public DinnerTimeViewHolder(View v) {
            super(v);
            dinnerTimeRange = v.findViewById(R.id.dinner_time_range);
            restaurant = v.findViewById(R.id.restaurant_name);
        }
    }

    @Override
    public int getItemCount() {
        return selectedNight.size();
    }

    @Override
    public DinnerTimeTopRatedAdapter.DinnerTimeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dinner_time_cell, parent, false);
        DinnerTimeViewHolder vh = new DinnerTimeTopRatedAdapter.DinnerTimeViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(DinnerTimeTopRatedAdapter.DinnerTimeViewHolder holder, int position) {
        if(selectedNight.get(position).getEventType().equals("Dinner")) {
            holder.dinnerTimeRange.setText(selectedNight.get(position).getStartTime()+"-"+selectedNight.get(position).getEndTime());
            holder.restaurant.setText("Biaggis");
        }
    }
}
