package com.example.arezz.datenightdesmoines;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Date;

import java.io.Serializable;

import io.realm.Realm;
import io.realm.RealmResults;

public class PlannedNight extends AppCompatActivity {

    private RecyclerView planned_night_list;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter plannedAdapter;
    private TextView titleView;
    private ImageButton homeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planned_night);

        planned_night_list = (RecyclerView) findViewById(R.id.list_plannedNights);
        titleView = (TextView) findViewById(R.id.title_view_planned);
        homeButton = (ImageButton) findViewById(R.id.home_button);

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), TopRatedActivity.class);
                startActivity(intent);
            }
        });

        Realm realm = Realm.getDefaultInstance();
        final RealmResults<Night> plannedNights = realm.where(Night.class).findAll();

        Night night1 = new Night();
        Date date1 = new Date();
        night1.setDateName("Test 1");
        night1.setDate(date1);

        Night night2 = new Night();
        Date date2 = new Date();
        night2.setDateName("Test 2");
        night2.setDate(date2);

        plannedNights.add(night1);
        plannedNights.add(night2);

        RecyclerViewClickListener listener = new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                Night night = (Night) plannedNights.get(position);
                Intent intent = new Intent(view.getContext(), LoginActivity.class); // navigate to confirmation page once it's created
                intent.putExtra("night",(Serializable)night);
                startActivity(intent);
            }
        };

        layoutManager = new LinearLayoutManager(this);
        planned_night_list.setLayoutManager(layoutManager);

        plannedAdapter = new PlannedNightAdapter(this, plannedNights, listener);
        planned_night_list.setAdapter(plannedAdapter);
    }
}
