package com.example.arezz.datenightdesmoines;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
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
    private ImageButton imageButton;
    private ImageButton homeButton;


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
        imageButton = (ImageButton) findViewById(R.id.current_image_button);
        homeButton = (ImageButton) findViewById(R.id.home_button);

        String user = pref.getString("username", null);
        final String nightId = getIntent().getStringExtra("nightId");

        Realm realm = Realm.getDefaultInstance();
        final Night currentNight = realm.where(Night.class).equalTo("Id", nightId).findFirst();
        final RealmResults<Event> events = currentNight.getEvents();

        nightName.setText(currentNight.getDateName());
        
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, 1);
                }
            }
        });
        
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), TopRatedActivity.class);
                startActivity(intent);
            }
        });

        RecyclerViewClickListener listener = new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                Event event = (Event) events.get(position);
                Intent intent = new Intent(view.getContext(), LoginActivity.class); // navigate to details card once it's created
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
                intent.putExtra("nightId", nightId);
                startActivity(intent);
            }
        });

        reviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), ReviewActivity.class);
                intent.putExtra("nightId", nightId);
                startActivity(intent);
            }
        });

        completeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), TopRatedActivity.class);
                startActivity(intent);
                
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageButton.setImageBitmap(imageBitmap);
        }
    }
}
