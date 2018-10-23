package com.example.arezz.datenightdesmoines;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class YelpItemAdapter extends RecyclerView.Adapter<YelpItemAdapter.YelpItemViewHolder> {
    private Context context;
    private ArrayList<YelpItem> yelpItems;
    Dialog popup;

    public YelpItemAdapter(Context context, ArrayList<YelpItem> dataSet, Dialog p) {
        this.context = context;
        this.yelpItems = dataSet;
        this.popup = p;
    }

    public static class YelpItemViewHolder extends RecyclerView.ViewHolder{
        public TextView nameView;
        public TextView starsView;
        public Button openYelpDetails;

        public YelpItemViewHolder(View v) {
            super(v);
            nameView = v.findViewById(R.id.yelp_name_view);
            starsView = v.findViewById(R.id.yelp_stars_view);
            openYelpDetails = v.findViewById(R.id.open_yelp_details);
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
    public void onBindViewHolder(YelpItemViewHolder holder, final int position) {
        holder.nameView.setText(yelpItems.get(position).getName());
        holder.starsView.setText(yelpItems.get(position).getStars());

        holder.openYelpDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowPopup(position);
            }
        });
    }

    private void ShowPopup(int position) {
        popup.setContentView(R.layout.yelp_details_popup);
        YelpItem yelpItem = yelpItems.get(position);

        TextView title=(TextView) popup.findViewById(R.id.yelp_details_name);
        title.setText(yelpItem.getName());

        TextView address =(TextView) popup.findViewById(R.id.yelp_details_address);
        address.setText(yelpItem.getAddress());

        TextView phone =(TextView) popup.findViewById(R.id.yelp_details_phone);
        phone.setText(yelpItem.getDisplayPhone());

        TextView price =(TextView) popup.findViewById(R.id.yelp_details_price);
        price.setText(yelpItem.getPrice());

        ImageView picture = (ImageView) popup.findViewById(R.id.yelp_details_image);
        Picasso.get().load(yelpItem.getImageUrl()).into(picture);

        Button addButton = (Button)popup.findViewById(R.id.yelp_details_add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup.dismiss();
            }
        });

        Button closeButton = (Button)popup.findViewById(R.id.yelp_details_close_button);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup.dismiss();
            }
        });

        popup.show();
    }

}
