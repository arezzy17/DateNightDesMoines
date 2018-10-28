package com.example.arezz.datenightdesmoines;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
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
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.UUID;

import io.realm.Realm;

import static android.content.Context.MODE_PRIVATE;

public class YelpItemAdapter extends RecyclerView.Adapter<YelpItemAdapter.YelpItemViewHolder> {
    private Context context;
    private ArrayList<YelpItem> yelpItems;
    Dialog popup;
    private RecyclerViewClickListener mListener;
    private String currentNightId;

    public YelpItemAdapter(Context context, ArrayList<YelpItem> dataSet, Dialog p, RecyclerViewClickListener r) {
        this.context = context;
        this.yelpItems = dataSet;
        this.popup = p;
        mListener = r;
        //currentNightId = cNight;
    }

    public static class YelpItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView nameView;
        public TextView starsView;
        public Button openYelpDetails;
        private RecyclerViewClickListener mListener;

        public YelpItemViewHolder(View v, RecyclerViewClickListener r) {
            super(v);
            nameView = v.findViewById(R.id.yelp_name_view);
            starsView = v.findViewById(R.id.yelp_stars_view);
            openYelpDetails = v.findViewById(R.id.open_yelp_details);
            mListener = r;
            v.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            mListener.onClick(view, getAdapterPosition());
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
        YelpItemViewHolder vh = new YelpItemViewHolder(v, mListener);
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

        TextView rating =(TextView) popup.findViewById(R.id.yelp_details_rating);
        rating.setText(yelpItem.getStars());

        ImageView picture = (ImageView) popup.findViewById(R.id.yelp_details_image);
        Picasso.get().load(yelpItem.getImageUrl()).into(picture);

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
