package com.example.arezz.datenightdesmoines;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class ConfirmNightActivity extends AppCompatActivity {

    private ImageView ConfirmImage;
    private EditText NameNightText;
    private Button EditNightButton;
    private Button ConfirmNightButton;
    private RecyclerView NightList;
    private Button HomeButton;
    private EditText DateSelector;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_night);

        NightList = (RecyclerView) findViewById(R.id.night_list_confirm);
        ConfirmImage = (ImageView) findViewById(R.id.image_confirm);
        NameNightText = (EditText) findViewById(R.id.name_night_confirm);
        EditNightButton = (Button) findViewById(R.id.edit_night_button_confirm);
        ConfirmNightButton = (Button) findViewById(R.id.confirm_night_button_confirm);
        HomeButton = (Button) findViewById(R.id.home_button_confirm);
        DateSelector = (EditText) findViewById(R.id.date_selector_confirm);


        EditNightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), CreateNewNight.class);
                startActivity(intent);
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
                Intent intent = new Intent(getBaseContext(), CurrentNightActivity.class);
                startActivity(intent);
            }
        });
    }
}
