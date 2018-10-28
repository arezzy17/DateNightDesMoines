package com.example.arezz.datenightdesmoines;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.ImageButton;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.sql.Time;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;

public class TopRatedActivity extends AppCompatActivity {

    private RecyclerView topRatedList;
    private RecyclerView eventList;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.LayoutManager eventLayoutManager;
    private RecyclerView.Adapter eventAdapter;
    private RecyclerView.Adapter ratingAdapter;
    private Button CreateNewButton;
    private Button PlannedNightsButton;
    private Button PastNightButton;
    Dialog popup;
    private ImageButton LogOutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_rated);

        popup = new Dialog(this);

        final SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        final SharedPreferences.Editor editor = pref.edit();

        topRatedList = (RecyclerView) findViewById(R.id.top_rated_list);
        CreateNewButton = (Button) findViewById(R.id.create_new_night_rating);
        PlannedNightsButton = (Button) findViewById(R.id.planned_night_rating);
        PastNightButton = (Button) findViewById(R.id.past_night_rating);
        LogOutButton = (ImageButton) findViewById(R.id.log_out_button);

        Realm realm = Realm.getDefaultInstance();
        final RealmResults<Night> topRatedNights = realm.where(Night.class)
                .greaterThan("rating", 2).lessThan("date", new Date()).findAll();

        RecyclerViewClickListener listener = new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                Night rating = (Night) topRatedNights.get(position);
                Intent intent = new Intent();
                DateDetailPopup datePopup = new DateDetailPopup();
                datePopup.showPopup(rating, popup, pref, "Top Rated");
            }
        };


        layoutManager = new LinearLayoutManager(this);
        topRatedList.setLayoutManager(layoutManager);

        ratingAdapter = new TopRatedAdapter(this, topRatedNights, listener);
        topRatedList.setAdapter(ratingAdapter);

        CreateNewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String loggedInUser = pref.getString("username", null);
                if(loggedInUser == null) {
                    Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                    intent.putExtra("navigate_to", "CreateNewNight");
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getBaseContext(), CreateNewNight.class);
                    startActivity(intent);
                }
            }
        });

        PlannedNightsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String loggedInUser = pref.getString("username", null);
                if(loggedInUser == null) {
                    Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                    intent.putExtra("navigate_to", "PlannedNight");
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getBaseContext(), PlannedNight.class);
                    startActivity(intent);
                }
            }
        });

        PastNightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String loggedinUser = pref.getString("username", null);
                if(loggedinUser == null) {
                    Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                    intent.putExtra("navigate_to", "PastNights");
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getBaseContext(), PastNightActivity.class);
                    startActivity(intent);
                }
            }
        });

        LogOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.remove("username");
                editor.apply();
                Toast.makeText(getBaseContext(), "User logged out", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
