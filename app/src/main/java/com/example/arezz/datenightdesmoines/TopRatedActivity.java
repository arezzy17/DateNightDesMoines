package com.example.arezz.datenightdesmoines;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.ImageButton;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.sql.Time;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;

public class TopRatedActivity extends AppCompatActivity {

    private RecyclerView topRatedList;
    private RecyclerView eventList;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.LayoutManager eventLayoutManager;
    private RecyclerView.Adapter eventAdapter;
    private RecyclerView.Adapter ratingAdapter;
    private Button CreateNewButton;
    private Button PlannedNightsButton;
    private Button PastNightButton;
    Dialog popup;
    private ImageButton LogOutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_rated);

        popup = new Dialog(this);

        final SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        final SharedPreferences.Editor editor = pref.edit();

        topRatedList = (RecyclerView) findViewById(R.id.top_rated_list);
        CreateNewButton = (Button) findViewById(R.id.create_new_night_rating);
        PlannedNightsButton = (Button) findViewById(R.id.planned_night_rating);
        PastNightButton = (Button) findViewById(R.id.past_night_rating);
        LogOutButton = (ImageButton) findViewById(R.id.log_out_button);

        Realm realm = Realm.getDefaultInstance();
        final RealmResults<Night> topRatedNights = realm.where(Night.class)
                .greaterThan("rating", 2).findAll();
        if(topRatedNights.size() == 0) {
            populateTopRated();
        }

        RecyclerViewClickListener listener = new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                Night rating = (Night) topRatedNights.get(position);
                //showPopup(rating);
                DateDetailPopup datePopup = new DateDetailPopup();
                datePopup.showPopup(rating, popup);
//                Intent intent = new Intent(view.getContext(), LoginActivity.class);
//                intent.putExtra("navigate_to", "Top Rated");
//                intent.putExtra("rating",rating.getId());
//                startActivity(intent);
            }
        };


        layoutManager = new LinearLayoutManager(this);
        topRatedList.setLayoutManager(layoutManager);

        ratingAdapter = new TopRatedAdapter(this, topRatedNights, listener);
        topRatedList.setAdapter(ratingAdapter);

        CreateNewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String loggedInUser = pref.getString("username", null);
                if(loggedInUser == null) {
                    Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                    intent.putExtra("navigate_to", "CreateNewNight");
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getBaseContext(), CreateNewNight.class);
                    startActivity(intent);
                }
            }
        });

        PlannedNightsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String loggedInUser = pref.getString("username", null);
                if(loggedInUser == null) {
                    Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                    intent.putExtra("navigate_to", "PlannedNight");
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getBaseContext(), PlannedNight.class);
                    startActivity(intent);
                }
            }
        });

        PastNightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String loggedinUser = pref.getString("username", null);
                if(loggedinUser == null) {
                    Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                    intent.putExtra("navigate_to", "PastNights");
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getBaseContext(), PastNightActivity.class);
                    startActivity(intent);
                }
            }
        });

        LogOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.remove("username");
                editor.apply();
                Toast.makeText(getBaseContext(), "User logged out", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void populateTopRated(){
        Realm realm= Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Event event1 = new Event();
                Date time1 =  new Date();

                Night night1 = new Night();
                night1.setId("1");
                night1.setUsername("martymartin16");
                night1.setDateName("Fun Times");
                night1.setDate(new Date(12,13,2014));
                night1.setRating(5);
                realm.copyToRealm(night1);

                event1.setEventType("Dinner");
                event1.setStartTime(new Time(6,30,00));
                event1.setEndTime(new Time(7,30,00));
                event1.setNight(realm.where(Night.class).equalTo("Id", "1").findFirst());
                realm.copyToRealm(event1);

                Night night2 = new Night();
                Date date2 =  new Date();
                date2.setMonth(12);
                date2.setDate(13);
                date2.setYear(2014);
                night2.setId("2");
                night2.setUsername("draketherapper");
                night2.setDateName("Drake at Drake");
                night2.setDate(date2);
                night2.setRating(3);
                realm.copyToRealm(night2);
            }
        });
    }

//    private void showPopup(final Night rating) {
//        popup.setContentView(R.layout.top_rated_details_popup);
//        Realm realm = Realm.getDefaultInstance();
//        RealmResults<Event> events = rating.getEvents();
//
//        TextView dateName = (TextView) popup.findViewById(R.id.date_name);
//        dateName.setText(rating.getDateName());
//
//        ImageView datePicture = (ImageView) popup.findViewById(R.id.date_picture);
//        if(rating.getImageId() != null) {
//            Image image = realm.where(Image.class).equalTo("id", rating.getImageId()).findFirst();
//            Bitmap bmp = BitmapFactory.decodeByteArray(image.getImageBitmap(),0,image.getId().length());
//            datePicture.setImageBitmap(bmp);
//        }
//
//        RatingBar dateRating = (RatingBar) popup.findViewById(R.id.date_rating);
//        dateRating.setIsIndicator(true);
//        dateRating.setRating(rating.getRating());
//
//        eventList = (RecyclerView) popup.findViewById(R.id.event_list);
//        eventLayoutManager = new LinearLayoutManager(this);
//        eventList.setLayoutManager(eventLayoutManager);
//
//        eventAdapter = new EventTopRatedAdapter(this, events);
//        eventList.setAdapter(eventAdapter);
//
//        Button selectButton = (Button) popup.findViewById(R.id.select_button);
//        selectButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                popup.dismiss();
//                Intent intent = new Intent(getBaseContext(), ConfirmNightActivity.class);
//                intent.putExtra("nightId", rating.getId());
//                startActivity(intent);
//            }
//        });
//
//        Button closeButton = (Button) popup.findViewById(R.id.close_button);
//        closeButton.setOnClickListener( new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                popup.dismiss();
//            }
//        });
//
//        popup.show();
//    }
}
