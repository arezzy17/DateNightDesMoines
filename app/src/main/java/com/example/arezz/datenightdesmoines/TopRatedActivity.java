package com.example.arezz.datenightdesmoines;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class TopRatedActivity extends AppCompatActivity {

    private RecyclerView TopRatedList;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter ratingAdapter;
    private Button TopRatedButton;
    private Button PastNightsButton;
    private Button CurrentNightButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_rated);

        TopRatedList = (RecyclerView) findViewById(R.id.top_rated_list);
        TopRatedButton = (Button) findViewById(R.id.top_rated_rating);
        PastNightsButton = (Button) findViewById(R.id.past_night_rating);
        CurrentNightButton = (Button) findViewById(R.id.current_night_rating);

        final ArrayList<Rating> ratings = new ArrayList<Rating>();

        Rating rating1 = new Rating();
        rating1.setDateName("Test 1");
        rating1.setRating(4);

        Rating rating2 = new Rating();
        rating2.setDateName("Test 2");
        rating2.setRating(5);

        ratings.add(rating1);
        ratings.add(rating2);

        layoutManager = new LinearLayoutManager(this);
        TopRatedList.setLayoutManager(layoutManager);

        ratingAdapter = new TopRatedAdapter(this, ratings);
        TopRatedList.setAdapter(ratingAdapter);

        TopRatedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), TopRatedActivity.class);
                startActivity(intent);
            }
        });

        PastNightsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        CurrentNightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
