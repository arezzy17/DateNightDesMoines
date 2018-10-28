package com.example.arezz.datenightdesmoines;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
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
    Dialog popup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planned_night);
        final SharedPreferences pref = getBaseContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        String user = pref.getString("username", "");

        planned_night_list = (RecyclerView) findViewById(R.id.list_plannedNights);
        titleView = (TextView) findViewById(R.id.title_view_planned);
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
        Date date = new Date();
        final RealmResults<Night> plannedN = realm.where(Night.class).equalTo("Username",user).findAll();
        ArrayList<Night> plannedNightsTemp = new ArrayList<Night>();
        for (Night n: plannedN) {
            if(n.getDate() != null && ((n.getDate().after(new Date())) ||
                    (n.getDate().getMonth() == date.getMonth() && n.getDate().getYear() == date.getYear()
                    && n.getDate().getDay() == date.getDay()))){
                plannedNightsTemp.add(n);
            }
        }
        final ArrayList<Night> plannedNights = (ArrayList<Night>) plannedNightsTemp.clone();

        RecyclerViewClickListener listener = new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                Night night = (Night) plannedNights.get(position);
                DateDetailPopup datePopup = new DateDetailPopup();
                datePopup.showPopup(night, popup, pref, "Planned Nights");
            }
        };

        layoutManager = new LinearLayoutManager(this);
        planned_night_list.setLayoutManager(layoutManager);

        plannedAdapter = new PlannedNightAdapter(this, plannedNights, listener);
        planned_night_list.setAdapter(plannedAdapter);
    }
}
