package com.example.arezz.datenightdesmoines;

import android.app.Dialog;
import android.content.Intent;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

public class ConfirmNightActivity extends AppCompatActivity implements IYelpList{

    private ImageView ConfirmImage;
    private EditText NameNightText;
    private Button EditNightButton;
    private Button ConfirmNightButton;
    private RecyclerView NightList;
    private Button HomeButton;
    private EditText DateSelector;
    private RecyclerView.Adapter confirmAdapter;
    private RecyclerView.LayoutManager layoutManager;



    public void setList(JSONObject obj){
        YelpItem displayItem = YelpItem.GetYelpItemFromBusinessJSON(obj);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_night);

        NightList = (RecyclerView) findViewById(R.id.night_list_confirm);
        NameNightText = (EditText) findViewById(R.id.name_night_confirm);
        EditNightButton = (Button) findViewById(R.id.edit_night_button_confirm);
        ConfirmNightButton = (Button) findViewById(R.id.confirm_night_button_confirm);
        HomeButton = (Button) findViewById(R.id.home_button_confirm);
        DateSelector = (EditText) findViewById(R.id.date_selector_confirm);
        Intent intent = new Intent();
        String nightId = intent.getStringExtra("nightId");

        EditNightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), CreateNewNight.class);
                startActivity(intent);

                //need to return to edit night but keep all of the events set
            }
        });

        HomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        ConfirmNightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), CurrentNight.class);
                startActivity(intent);

                //need to set up realm to save the night
            }
        });

        Realm realm = Realm.getDefaultInstance();
        final RealmResults<Event> ConfirmNight = realm.where(Night.class)
                .equalTo("Id", nightId ).findFirst().getEvents();

        final IYelpList thisAct = this;
        RecyclerViewClickListener listener = new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                Event event = (Event) ConfirmNight.get(position);
                RequestQueue queue = MySingleton.getInstance(getApplicationContext()).getRequestQueue();
                JsonObjectRequest myReq = MySingleton.getInstance(getBaseContext()).GetJsonRequestYelpId("https://api.yelp.com/v3/businesses/","Bearer baYflpcDgbIhpcxDzfCTVY-8-MNrTaQKs-Xi7TkguApK9CW1ezFdxhlNAS754U7dQEou-gJzbZkP54dNIrFO_70lrO1cIcNS0ziaZBqslfvysRtzBZ04M-LFYt23W3Yx",event.getYelpID(), thisAct);
                MySingleton.getInstance(getBaseContext()).addToRequestQueue(myReq);
            }
        };

        layoutManager = new LinearLayoutManager(this);
        NightList.setLayoutManager(layoutManager);

        confirmAdapter = new ConfirmNightAdapter(getBaseContext(), ConfirmNight, new Dialog(getBaseContext()), listener);
        NightList.setAdapter(confirmAdapter);
    }
}
