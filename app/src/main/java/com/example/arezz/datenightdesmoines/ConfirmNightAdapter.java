package com.example.arezz.datenightdesmoines;
import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import io.realm.RealmResults;


public class ConfirmNightAdapter extends RecyclerView.Adapter<ConfirmNightAdapter.ConfirmNightViewHolder> {
    private Context context;
    private RealmResults<Event> events;
    Dialog ConfirmPopup;
    private RecyclerViewClickListener mListener;



    public ConfirmNightAdapter(Context context, RealmResults<Event> dataSet, Dialog cPopup, RecyclerViewClickListener
                               clickListener) {
        this.context = context;
        this.events = dataSet;
        this.ConfirmPopup = cPopup;
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

        private void ShowPopup(YelpItem yelpItem) {
        ConfirmPopup.setContentView(R.layout.yelp_details_popup);

        TextView title=(TextView) ConfirmPopup.findViewById(R.id.yelp_details_name);
        title.setText(yelpItem.getName());

        TextView address =(TextView) ConfirmPopup.findViewById(R.id.yelp_details_address);
        address.setText(yelpItem.getAddress());

        TextView phone =(TextView) ConfirmPopup.findViewById(R.id.yelp_details_phone);
        phone.setText(yelpItem.getDisplayPhone());

        TextView price =(TextView) ConfirmPopup.findViewById(R.id.yelp_details_price);
        price.setText(yelpItem.getPrice());

        ImageView picture = (ImageView) ConfirmPopup.findViewById(R.id.yelp_details_image);
        Picasso.get().load(yelpItem.getImageUrl()).into(picture);

        Button addButton = (Button)ConfirmPopup.findViewById(R.id.yelp_details_add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConfirmPopup.dismiss();
            }
        });

        Button closeButton = (Button)ConfirmPopup.findViewById(R.id.yelp_details_close_button);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConfirmPopup.dismiss();
            }
        });

        ConfirmPopup.show();
    }

    }
