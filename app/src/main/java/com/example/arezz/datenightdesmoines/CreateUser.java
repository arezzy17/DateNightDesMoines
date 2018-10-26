package com.example.arezz.datenightdesmoines;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



public class CreateUser extends AppCompatActivity {

    private Button CreateAccountButton;
    private EditText emailField;
    private EditText passwordField;
    private EditText username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        CreateAccountButton = (Button) findViewById(R.id.create_button_user);
        emailField = (EditText) findViewById(R.id.email_field_user);
        passwordField = (EditText) findViewById(R.id.password_user);
        username = (EditText) findViewById(R.id.username_user);
        final String navigateTo = getIntent().getStringExtra("navigate_to");
        final String selectedRating = getIntent().getStringExtra("rating");

        CreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Realm realm = Realm.getDefaultInstance();
                User usernameTest = realm.where(User.class).equalTo("username",
                        username.getText().toString()).findFirst();
                if(usernameTest != null) {
                    Toast.makeText(getBaseContext(), "This username is already taken.",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                if(username.getText().toString().equals("") || passwordField.getText().toString().equals("") ||
                        emailField.getText().toString().equals("")) {
                    Toast.makeText(getBaseContext(), "Please fill out all of the required fields.",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref",
                        MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("username", username.getText().toString());
                editor.apply();

                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        User user = new User();

                        user.setUsername(username.getText().toString());
                        user.setPassword(passwordField.getText().toString());
                        user.setEmail(emailField.getText().toString());

                        realm.copyToRealm(user);

                        finish();
                    }
                });

                if(navigateTo.equals("CreateNewNight")) {
                    Intent intent = new Intent(getBaseContext(), CreateNewNight.class);
                    startActivity(intent);
                } else if(navigateTo.equals("PlannedNights")){
                    Intent intent = new Intent(getBaseContext(), PlannedNight.class);
                    startActivity(intent);
                } else if (navigateTo.equals("PastNights")) {
                    Intent intent = new Intent(getBaseContext(), PastNightActivity.class);
                    startActivity(intent);
                } else if (navigateTo.equals("Top Rated")) {
                    Intent intent = new Intent(getBaseContext(), CreateNewNight.class);
                    intent.putExtra("rating", selectedRating);
                    startActivity(intent);
                }

            }
        });
    }
}
