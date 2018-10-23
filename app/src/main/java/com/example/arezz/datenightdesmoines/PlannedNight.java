package com.example.arezz.datenightdesmoines;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Date;

import java.io.Serializable;
import java.util.ArrayList;

public class PlannedNight extends AppCompatActivity {

    private RecyclerView planned_night_list;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter plannedAdapter;
    private TextView titleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planned_night);

        planned_night_list = (RecyclerView) findViewById(R.id.list_plannedNights);
        titleView = (TextView) findViewById(R.id.title_view_planned);

        final ArrayList<Night> nights = new ArrayList<Night>();

        Night night1 = new Night();
        Date date1 = new Date();
        night1.setDateName("Test 1");
        night1.setDate(date1);

        Night night2 = new Night();
        Date date2 = new Date();
        night2.setDateName("Test 2");
        night2.setDate(date2);

        nights.add(night1);
        nights.add(night2);

        RecyclerViewClickListener listener = new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                Night night = (Night) nights.get(position);
                Intent intent = new Intent(view.getContext(), LoginActivity.class); // navigate to confirmation page once it's created
                intent.putExtra("night",(Serializable)night);
                startActivity(intent);
            }
        };

        layoutManager = new LinearLayoutManager(this);
        planned_night_list.setLayoutManager(layoutManager);

        plannedAdapter = new PlannedNightAdapter(this, nights, listener);
        planned_night_list.setAdapter(plannedAdapter);
    }
}
