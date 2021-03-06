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
import java.util.Date;

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
        String user = pref.getString("username", "");
        Realm realm = Realm.getDefaultInstance();
        final RealmResults<Night> pastN = realm.where(Night.class).equalTo("Username",user).findAll();
        ArrayList<Night> pastNightsTemp = new ArrayList<Night>();
        for (Night n: pastN) {
            if(n.getDate() != null && n.getDate().before(new Date())){
                pastNightsTemp.add(n);
            }
        }
        final ArrayList<Night> pastNights = (ArrayList<Night>) pastNightsTemp.clone();

        RecyclerViewClickListener listener = new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                Night rating = (Night) pastNights.get(position);
                Intent intent = new Intent();
                //intent.putExtra("navigated_from", "Past Nights");
                DateDetailPopup datePopup = new DateDetailPopup();
                datePopup.showPopup(rating, popup, pref, "Past Nights");
            }
        };

        layoutManager = new LinearLayoutManager(this);
        pastNightsList.setLayoutManager(layoutManager);

        pastNightsAdapter = new PastNightAdapter(this, pastNights, listener);
        pastNightsList.setAdapter(pastNightsAdapter);

    }
}
