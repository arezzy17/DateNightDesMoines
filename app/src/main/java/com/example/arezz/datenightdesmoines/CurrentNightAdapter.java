package com.example.arezz.datenightdesmoines;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

public class CurrentNightAdapter extends RecyclerView.Adapter<CurrentNightAdapter.CurrentNightViewHolder> {
    private ArrayList<Event> events;
    private Context context;
    private RecyclerViewClickListener mListener;

    public CurrentNightAdapter(Context context, ArrayList<Event> dataSet, RecyclerViewClickListener
            clickListener) {
        this.context = context;
        this.events = dataSet;
        this.mListener = clickListener;
    }

    public static class CurrentNightViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView eventNameView;
        public TextView eventTypeView;
        private RecyclerViewClickListener mListener;
        public CurrentNightViewHolder(View v, RecyclerViewClickListener listener) {
            super(v);
            eventNameView = v.findViewById(R.id.event_name_current);
            eventTypeView = v.findViewById(R.id.event_type_current);
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
        return events.size();
    }

    @Override
    public CurrentNightAdapter.CurrentNightViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.current_cell, parent, false);
        CurrentNightAdapter.CurrentNightViewHolder vh = new CurrentNightAdapter.CurrentNightViewHolder(v, mListener);
        return vh;
    }

    @Override
    public void onBindViewHolder(CurrentNightAdapter.CurrentNightViewHolder holder, int position) {
        holder.eventNameView.setText(events.get(position).getEventName());
        holder.eventTypeView.setText(events.get(position).getEventType());
    }
}
