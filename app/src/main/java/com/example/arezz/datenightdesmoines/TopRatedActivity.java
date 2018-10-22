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
import java.util.ArrayList;

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

        final ArrayList<Rating> ratings = new ArrayList<Rating>();

        Rating rating1 = new Rating();
        rating1.setDateName("Test 1");
        rating1.setRating(4);

        Rating rating2 = new Rating();
        rating2.setDateName("Test 2");
        rating2.setRating(5);

        ratings.add(rating1);
        ratings.add(rating2);

        RecyclerViewClickListener listener = new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                Rating rating = (Rating) ratings.get(position); // change this to night
                Intent intent = new Intent(view.getContext(), LoginActivity.class); // navigate to confirmation page once it's created
                intent.putExtra("rating",(Serializable)rating); //change this to night
                startActivity(intent);
            }
        };

        layoutManager = new LinearLayoutManager(this);
        TopRatedList.setLayoutManager(layoutManager);

        ratingAdapter = new TopRatedAdapter(this, ratings, listener);
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
                // intent.putExtra("navigate_to", "PlannedNight");
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
}
