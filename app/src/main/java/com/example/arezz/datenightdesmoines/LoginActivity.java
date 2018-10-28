package com.example.arezz.datenightdesmoines;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.UUID;

import io.realm.Realm;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameText;
    private EditText PasswordText;
    private Button LoginButton;
    private Button NewUserButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameText = (EditText) findViewById(R.id.username_text_login);
        PasswordText = (EditText) findViewById(R.id.password_text_login);
        LoginButton = (Button) findViewById(R.id.login_button_login);
        NewUserButton = (Button) findViewById(R.id.newuser_button_login);

        final String navigateTo = getIntent().getStringExtra("navigate_to");
        final String nightId = getIntent().getStringExtra("nightId");

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Realm realm = Realm.getDefaultInstance();
                User usernameTest = realm.where(User.class).equalTo("username",
                        usernameText.getText().toString()).findFirst();
                if(usernameTest != null) {
                    String passwordTest = usernameTest.getPassword();
                    if(passwordTest.equals(PasswordText.getText().toString())) {
                        final SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref",
                                MODE_PRIVATE);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("username", usernameTest.getUsername());
                        editor.apply();

                        if(navigateTo.equals("CreateNewNight")) {
                            Intent intent = new Intent(getBaseContext(), CreateNewNight.class);
                            startActivity(intent);
                        } else if(navigateTo.equals("PlannedNight")){
                            Intent intent = new Intent(getBaseContext(), PlannedNight.class);
                            startActivity(intent);
                        } else if (navigateTo.equals("PastNights")) {
                            Intent intent = new Intent(getBaseContext(), PastNightActivity.class);
                            startActivity(intent);
                        } else if (navigateTo.equals("Top Rated")) {
                            final String newNightId = UUID.randomUUID().toString();
                            realm.executeTransaction(new Realm.Transaction() {
                                @Override
                                public void execute(Realm realm) {
                                    realm.executeTransaction(new Realm.Transaction() {
                                        @Override
                                        public void execute(Realm realm) {
                                            Night newNight = realm.where(Night.class).equalTo("Id", nightId).findFirst();
                                            newNight.setUsername(pref.getString("username", null));
                                            newNight.setId(newNightId);
                                            realm.copyToRealm(newNight);
                                        }
                                    });
                                }
                            });
                            Intent intent = new Intent(getBaseContext(), CreateNewNight.class);
                            intent.putExtra("nightId", newNightId);
                            startActivity(intent);
                        }
                    } else {
                        Toast.makeText(getBaseContext(), "Username or password is incorrect",
                                Toast.LENGTH_LONG).show();
                        return;
                    }
                } else if((usernameTest == null) && !(PasswordText.getText().toString().isEmpty()) && !(usernameText.getText().toString().isEmpty())) {
                    Toast.makeText(getBaseContext(), "Username or password is incorrect",
                            Toast.LENGTH_LONG).show();
                    return;
                }
            }
        });

        NewUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), CreateUser.class);
                intent.putExtra("navigate_to", navigateTo);
                startActivity(intent);
            }
        });

    }
}
