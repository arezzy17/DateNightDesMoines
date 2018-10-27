package com.example.arezz.datenightdesmoines;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import io.realm.Realm;
import io.realm.RealmResults;

public class DateDetailPopup extends Activity {
    void showPopup(final Night rating, final Dialog popup) {
        popup.setContentView(R.layout.top_rated_details_popup);
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Event> events = rating.getEvents();

        TextView dateName = (TextView) popup.findViewById(R.id.date_name);
        dateName.setText(rating.getDateName());

        ImageView datePicture = (ImageView) popup.findViewById(R.id.date_picture);
        if(rating.getImageId() != null) {
            Image image = realm.where(Image.class).equalTo("id", rating.getImageId()).findFirst();
            Bitmap bmp = BitmapFactory.decodeByteArray(image.getImageBitmap(),0,image.getId().length());
            datePicture.setImageBitmap(bmp);
        }

        RatingBar dateRating = (RatingBar) popup.findViewById(R.id.date_rating);
        dateRating.setIsIndicator(true);
        dateRating.setRating(rating.getRating());

        RecyclerView eventList = (RecyclerView) popup.findViewById(R.id.event_list);
        LinearLayoutManager eventLayoutManager = new LinearLayoutManager(getBaseContext());
        eventList.setLayoutManager(eventLayoutManager);

        EventTopRatedAdapter eventAdapter = new EventTopRatedAdapter(this, events);
        eventList.setAdapter(eventAdapter);

        Button selectButton = (Button) popup.findViewById(R.id.select_button);
        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup.dismiss();
                Intent intent = new Intent(v.getContext(), ConfirmNightActivity.class);
                intent.putExtra("nightId", rating.getId());
                v.getContext().startActivity(intent);
            }
        });

        Button closeButton = (Button) popup.findViewById(R.id.close_button);
        closeButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup.dismiss();
            }
        });

        popup.show();
    }
}
