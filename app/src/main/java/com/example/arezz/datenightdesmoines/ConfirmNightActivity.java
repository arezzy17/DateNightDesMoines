package com.example.arezz.datenightdesmoines;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;

public class ConfirmNightActivity extends AppCompatActivity implements IYelpList{

    private ImageView ConfirmImage;
    private EditText NameNightText;
    private Button EditNightButton;
    private Button ConfirmNightButton;
    private RecyclerView NightList;
    private EditText selectDate;
    private RecyclerView.Adapter confirmAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ImageButton homeButton;



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
        homeButton = (ImageButton) findViewById(R.id.home_button);
        selectDate = (EditText) findViewById(R.id.select_date);

        Realm realm = Realm.getDefaultInstance();
        final String selectedNightId = getIntent().getStringExtra("nightId");
        final Night selectedNight = realm.where(Night.class).equalTo("Id", selectedNightId).findFirst();

        String date = dateToString(selectedNight.getDate());
        selectDate.setText(date);
        NameNightText.setText(selectedNight.getDateName());

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), TopRatedActivity.class);
                startActivity(intent);
            }
        });

        EditNightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToRealm(selectedNightId);
                Intent intent = new Intent(getBaseContext(), CreateNewNight.class);
                intent.putExtra("nightId", selectedNightId);
                startActivity(intent);

                //need to return to edit night but keep all of the events set
            }
        });

        ConfirmNightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToRealm(selectedNightId);
                Intent intent = new Intent(getBaseContext(), CurrentNight.class);
                intent.putExtra("nightId", selectedNightId);
                startActivity(intent);
            }
        });

        final ArrayList<Event> events = new ArrayList<>();
        final IYelpList thisAct = this;
        RecyclerViewClickListener listener = new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                Event event = (Event) events.get(position);
                RequestQueue queue = MySingleton.getInstance(getApplicationContext()).getRequestQueue();
                JsonObjectRequest myReq = MySingleton.getInstance(getBaseContext()).GetJsonRequestYelpId("https://api.yelp.com/v3/businesses/","Bearer baYflpcDgbIhpcxDzfCTVY-8-MNrTaQKs-Xi7TkguApK9CW1ezFdxhlNAS754U7dQEou-gJzbZkP54dNIrFO_70lrO1cIcNS0ziaZBqslfvysRtzBZ04M-LFYt23W3Yx",event.getYelpID(), thisAct);
                MySingleton.getInstance(getBaseContext()).addToRequestQueue(myReq);
            }
        };

        layoutManager = new LinearLayoutManager(this);
        NightList.setLayoutManager(layoutManager);

        confirmAdapter = new ConfirmNightAdapter(getBaseContext(), events, new Dialog(getBaseContext()), listener);
        NightList.setAdapter(confirmAdapter);
    }

    public Date stringToDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        Date date = null;
        try {
            date = (Date)formatter.parse(selectDate.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
            Toast.makeText(this, "Please enter proper date: mm/dd/yyyy", Toast.LENGTH_SHORT).show();
        }
        return date;
    }

    public String dateToString(Date date) {
        if(date != null) {
            SimpleDateFormat stringFormat = new SimpleDateFormat("MM/dd/yyyy");
            String stringDate = stringFormat.format(date);
            return stringDate;
        } else {
            return("");
        }
    }

    public void saveToRealm(String nightId) {
        final String dateName = NameNightText.getText().toString();
        final Date dateOfEvent = stringToDate();
        Realm realm = Realm.getDefaultInstance();
        final Night night = realm.where(Night.class).equalTo("Id", nightId).findFirst();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                night.setDate(dateOfEvent);
                night.setDateName(dateName);

                realm.copyToRealm(night);

            }
        });
    }
}
