package com.example.arezz.datenightdesmoines;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    private EditText EmailText;
    private EditText PasswordText;
    private Button LoginButton;
    private Button NewUserButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EmailText = (EditText) findViewById(R.id.email_text_login);
        PasswordText = (EditText) findViewById(R.id.password_text_login);
        LoginButton = (Button) findViewById(R.id.login_button_login);
        NewUserButton = (Button) findViewById(R.id.newuser_button_login);

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), CreateNewNight.class);
                startActivity(intent);
            }
        });

        NewUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), CreateUser.class);
                startActivity(intent);
            }
        });

    }
}
