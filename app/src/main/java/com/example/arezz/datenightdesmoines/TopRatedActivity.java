package com.example.arezz.datenightdesmoines;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;

public class TopRatedActivity extends AppCompatActivity {

    private RecyclerView TopRatedList;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter ratingAdapter;
    private Button CreateNewButton;
    private Button PlannedNightsButton;
    private Button PastNightButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_rated);

        TopRatedList = (RecyclerView) findViewById(R.id.top_rated_list);
        CreateNewButton = (Button) findViewById(R.id.create_new_night_rating);
        PlannedNightsButton = (Button) findViewById(R.id.planned_night_rating);
        PastNightButton = (Button) findViewById(R.id.past_night_rating);

        Realm realm = Realm.getDefaultInstance();
        final RealmResults<Night> topRatedNights = realm.where(Night.class).findAll();
        if(topRatedNights.size() == 0) {
            populateTopRated();
        }

        RecyclerViewClickListener listener = new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                Night rating = (Night) topRatedNights.get(position);
                Intent intent = new Intent(view.getContext(), LoginActivity.class);
                intent.putExtra("navigate_to", "Top Rated");
                intent.putExtra("rating",rating.getId());
                startActivity(intent);
            }
        };

        layoutManager = new LinearLayoutManager(this);
        TopRatedList.setLayoutManager(layoutManager);

        ratingAdapter = new TopRatedAdapter(this, topRatedNights, listener);
        TopRatedList.setAdapter(ratingAdapter);

        CreateNewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                intent.putExtra("navigate_to", "CreateNewNight");
                startActivity(intent);
            }
        });

        PlannedNightsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                intent.putExtra("navigate_to", "PlannedNight");
                startActivity(intent);
            }
        });

        PastNightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                intent.putExtra("navigate_to", "PastNights");
                startActivity(intent);
            }
        });
    }

    public void populateTopRated(){
        Realm realm= Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Night night1 = new Night();
                Date date =  new Date();
                date.setMonth(12);
                date.setDate(13);
                date.setYear(2014);
                night1.setId("1");
                night1.setUsername("martymartin16");
                night1.setDateName("Fun Times");
                night1.setDate(date);
                night1.setRating(5);
                realm.copyToRealmOrUpdate(night1);

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
                realm.copyToRealmOrUpdate(night2);
            }
        });
    }
}
