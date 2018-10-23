package com.example.arezz.datenightdesmoines;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

public class PastNightActivity extends AppCompatActivity {

    private RecyclerView pastNightsList;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter pastNightsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_night);

        pastNightsList = (RecyclerView) findViewById(R.id.past_nights_list);


        Realm realm = Realm.getDefaultInstance();
        final RealmResults<Night> pastNights = realm.where(Night.class).findAll();
        if(pastNights.size() == 0) {
            populatePlannedNights();
        }

        RecyclerViewClickListener listener = new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                Night rating = (Night) pastNights.get(position);
                Intent intent = new Intent(view.getContext(), LoginActivity.class);
                intent.putExtra("navigate_to", "Top Rated");
                intent.putExtra("rating",rating.getId());
                startActivity(intent);
            }
        };

        layoutManager = new LinearLayoutManager(this);
        pastNightsList.setLayoutManager(layoutManager);

        pastNightsAdapter = new TopRatedAdapter(this, pastNights, listener);
        pastNightsList.setAdapter(pastNightsAdapter);
    }

    public void populatePlannedNights() {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Night night = new Night();
                night.setDateName("Test");
                night.setRating(4);
                realm.copyToRealmOrUpdate(night);
            }
        });
    }
}
