package com.example.arezz.datenightdesmoines;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmResults;

public class DateDetailPopup extends Activity {
    void showPopup(final Night rating, final Dialog popup, final SharedPreferences pref, final String navigatedFrom) {
        popup.setContentView(R.layout.top_rated_details_popup);
        //final String navigatedFrom = getIntent().getStringExtra("navigated_from");
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
                final String loggedInUser = pref.getString("username", null);
                if(navigatedFrom == "Top Rated" || navigatedFrom == "Past Nights") {
                    Realm realm = Realm.getDefaultInstance();
                    final RealmResults<Event> events = realm.where(Night.class).equalTo("Id",rating.getId()).findFirst().getEvents();
                    final String newNightId = UUID.randomUUID().toString();
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            Night newNight = new Night();
                            newNight.setId(newNightId);
                            newNight.setDateName(rating.getDateName());
                            newNight.setDate(rating.getDate());
                            if(loggedInUser != null) {
                                newNight.setUsername(loggedInUser);
                            }
                            realm.copyToRealm(newNight);

                            for (Event e: events) {
                                Event newEvent = new Event();
                                newEvent.setEventType(e.getEventType());
                                newEvent.setEventName(e.getEventName());
                                newEvent.setYelpID(e.getYelpID());
                                newEvent.setNight(realm.where(Night.class).equalTo("Id", newNightId).findFirst());

                                realm.copyToRealm(newEvent);
                            }
                        }
                    });
                    if (loggedInUser != null) {
                        popup.dismiss();
                        Intent intent = new Intent(v.getContext(), ConfirmNightActivity.class);
                        intent.putExtra("nightId", newNightId);
                        v.getContext().startActivity(intent);
                    } else {
                        popup.dismiss();
                        Intent intent = new Intent(v.getContext(), LoginActivity.class);
                        intent.putExtra("nightId", newNightId);
                        intent.putExtra("navigate_to", "Top Rated");
                        v.getContext().startActivity(intent);
                    }
                } else if (navigatedFrom == "Planned Nights") {

                }

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
