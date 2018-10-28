package com.example.arezz.datenightdesmoines;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import io.realm.RealmResults;

public class PlannedNightAdapter extends RecyclerView.Adapter<PlannedNightAdapter.PlannedNightViewHolder>{
    private Context context;
    private RealmResults<Night> nights;
    private RecyclerViewClickListener mListener;

    public PlannedNightAdapter(Context context, RealmResults<Night> dataSet, RecyclerViewClickListener
            clickListener) {
        this.context = context;
        this.nights = dataSet;
        this.mListener = clickListener;
    }

    public static class PlannedNightViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView dateNameView;
        public TextView dateView;
        private RecyclerViewClickListener mListener;
        public PlannedNightViewHolder(View v, RecyclerViewClickListener listener) {
            super(v);
            dateNameView = v.findViewById(R.id.night_name_planned);
            dateView = v.findViewById(R.id.date_planned);
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
    public PlannedNightAdapter.PlannedNightViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.planned_cell, parent, false);
        PlannedNightAdapter.PlannedNightViewHolder vh = new PlannedNightAdapter.PlannedNightViewHolder(v, mListener);
        return vh;
    }

    @Override
    public void onBindViewHolder(PlannedNightAdapter.PlannedNightViewHolder holder, int position) {
        holder.dateNameView.setText(nights.get(position).getDateName());
        //holder.dateView.setText((CharSequence) nights.get(position).getDate());
        holder.dateView.setText(dateToString(nights.get(position).getDate()));
    }

    public String dateToString(Date date) {
        if(date != null) {
            SimpleDateFormat stringFormat = new SimpleDateFormat("MM/dd/yyyy");
            String stringDate = stringFormat.format(date);
            return stringDate;
        } else {
            return("");
        }
    }
}
