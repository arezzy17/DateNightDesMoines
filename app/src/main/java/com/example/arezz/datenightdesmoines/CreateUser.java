package com.example.arezz.datenightdesmoines;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CreateUser extends AppCompatActivity {

    private Button CreateAccountButton;
    private EditText emailField;
    private EditText passwordField;
    private EditText nicknameField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        CreateAccountButton = (Button) findViewById(R.id.create_button_user);
        emailField = (EditText) findViewById(R.id.email_field_user);
        passwordField = (EditText) findViewById(R.id.password_user);
        nicknameField = (EditText) findViewById(R.id.nickname_user);
        final String navigateTo = getIntent().getStringExtra("navigate_to");

        CreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(navigateTo.equals("CreateNewNight")) {
                    Intent intent = new Intent(getBaseContext(), CreateNewNight.class);
                    startActivity(intent);
                } else if(navigateTo.equals("PlannedNights")){
                    // Intent intent = new Intent(getBaseContext(), PlannedNight.class);
                    // startActivity(intent);
                } else if (navigateTo.equals("PastNights")) {
                    Intent intent = new Intent(getBaseContext(), PastNightActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}
