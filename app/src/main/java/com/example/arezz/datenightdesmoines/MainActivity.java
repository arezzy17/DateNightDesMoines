package com.example.arezz.datenightdesmoines;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    private ImageButton CreateNewButton;
    private ImageButton TopRatedButton;
    private ImageButton PastNightsButton;
    private ImageButton CurrentNightButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CreateNewButton = (ImageButton) findViewById(R.id.create_new_home);
        TopRatedButton = (ImageButton) findViewById(R.id.top_rated_home);
        PastNightsButton = (ImageButton) findViewById(R.id.past_nights_home);
        CurrentNightButton = (ImageButton) findViewById(R.id.current_night_home);

        CreateNewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

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
