package com.example.arezz.datenightdesmoines;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;


public class ConfirmNightAdapter extends RecyclerView.Adapter<ConfirmNightAdapter.ConfirmNightViewHolder> {
    private Context context;
    private ArrayList<Event> events;
    private RecyclerViewClickListener mListener;


    public ConfirmNightAdapter(Context context, ArrayList<Event> dataSet, RecyclerViewClickListener
                               clickListener) {
        this.context = context;
        this.events = dataSet;
        this.mListener = clickListener;
    }

    public static class ConfirmNightViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView eventNameView;

        private RecyclerViewClickListener mListener;

        public ConfirmNightViewHolder(View v, RecyclerViewClickListener listener) {
            super(v);
            eventNameView = v.findViewById(R.id.event_name_confirm);

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
        public ConfirmNightAdapter.ConfirmNightViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = (View) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.confirm_cell, parent, false);
            ConfirmNightViewHolder vh = new ConfirmNightViewHolder(v, mListener);
            return vh;
        }

        @Override
        public void onBindViewHolder(ConfirmNightViewHolder holder, int position) {
            holder.eventNameView.setText(events.get(position).getEventName());

        }



    }
