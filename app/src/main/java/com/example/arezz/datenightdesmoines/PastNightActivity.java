package com.example.arezz.datenightdesmoines;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

public class PastNightActivity extends AppCompatActivity {

    private RecyclerView pastNightsList;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter pastNightsAdapter;
    private ImageButton homeButton;
    Dialog popup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_night);
        final SharedPreferences pref = getBaseContext().getSharedPreferences("MyPref", MODE_PRIVATE);

        pastNightsList = (RecyclerView) findViewById(R.id.past_nights_list);
        homeButton = (ImageButton) findViewById(R.id.home_button);
        popup = new Dialog(this);

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), TopRatedActivity.class);
                startActivity(intent);
            }
        });

        Realm realm = Realm.getDefaultInstance();
        final RealmResults<Night> pastNights = realm.where(Night.class).findAll();

        RecyclerViewClickListener listener = new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                Night rating = (Night) pastNights.get(position);
                Intent intent = new Intent();
                //intent.putExtra("navigated_from", "Past Nights");
                DateDetailPopup datePopup = new DateDetailPopup();
                datePopup.showPopup(rating, popup, pref, "Past Nights");
//                Intent intent = new Intent(view.getContext(), LoginActivity.class);
//                intent.putExtra("navigate_to", "Top Rated");
//                intent.putExtra("rating",rating.getId());
//                startActivity(intent);
            }
        };

        layoutManager = new LinearLayoutManager(this);
        pastNightsList.setLayoutManager(layoutManager);

        pastNightsAdapter = new PastNightAdapter(this, pastNights, listener);
        pastNightsList.setAdapter(pastNightsAdapter);

    }
}
