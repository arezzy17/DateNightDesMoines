package com.example.arezz.datenightdesmoines;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import io.realm.RealmResults;

public class EventTopRatedAdapter extends RecyclerView.Adapter<EventTopRatedAdapter.EventViewHolder> {
    private Context context;
    private RealmResults<Event> selectedNight;

    public EventTopRatedAdapter(Context context, RealmResults<Event> dataset) {
        this.context = context;
        this.selectedNight = dataset;
    }

    public static class EventViewHolder extends RecyclerView.ViewHolder {
        public TextView eventType;
        public TextView place;
        public EventViewHolder(View v) {
            super(v);
            eventType = v.findViewById(R.id.event_type);
            place = v.findViewById(R.id.place_name);
        }
    }

    @Override
    public int getItemCount() {
        return selectedNight.size();
    }

    @Override
    public EventTopRatedAdapter.EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_time_cell, parent, false);
        EventViewHolder vh = new EventTopRatedAdapter.EventViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(EventTopRatedAdapter.EventViewHolder holder, int position) {
        if(selectedNight.get(position).getEventType().equals("Dinner")) {
            holder.eventType.setText(selectedNight.get(position).getEventType());
            holder.place.setText("Biaggis");
        }
    }
}
