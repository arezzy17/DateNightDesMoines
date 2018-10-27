package com.example.arezz.datenightdesmoines;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.Serializable;
import io.realm.Realm;
import io.realm.RealmResults;

public class CurrentNight extends AppCompatActivity {

    private TextView nightName;
    private RecyclerView eventList;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter currentAdapter;
    private Button addEventButton;
    private Button reviewButton;
    private Button completeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_night);

        nightName = (TextView) findViewById(R.id.night_name_view);
        eventList = (RecyclerView) findViewById(R.id.event_list_current);
        addEventButton = (Button) findViewById(R.id.add_event_button);
        reviewButton = (Button) findViewById(R.id.review_button);
        completeButton = (Button) findViewById(R.id.complete_button);

        String user = pref.getString("username", null);
        String nightId = getIntent().getStringExtra("nightID");

        Realm realm = Realm.getDefaultInstance();
        final RealmResults<Night> nights = realm.where(Night.class).equalTo("Id", nightId).findAll();
        Night currentNight = (Night) nights.get(0);
        final RealmResults<Event> events = currentNight.getEvents();


        RecyclerViewClickListener listener = new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                Event event = (Event) events.get(position);
                Intent intent = new Intent(view.getContext(), LoginActivity.class); // navigate to details card once it's created
                intent.putExtra("event",(Serializable)event);
                startActivity(intent);
            }
        };

        layoutManager = new LinearLayoutManager(this);
        eventList.setLayoutManager(layoutManager);

        currentAdapter = new CurrentNightAdapter(this, events, listener);
        eventList.setAdapter(currentAdapter);

        addEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), CreateNewNight.class);
                // intent.putExtra();
                startActivity(intent);
            }
        });

        reviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), ReviewActivity.class);
                // intent.putExtra();
                startActivity(intent);
            }
        });

        completeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), TopRatedActivity.class);
                // intent.putExtra();
                startActivity(intent);
            }
        });
    }
}
