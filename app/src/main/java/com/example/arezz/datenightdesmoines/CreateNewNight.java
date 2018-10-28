package com.example.arezz.datenightdesmoines;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmResults;

public class CreateNewNight extends AppCompatActivity implements IYelpId {
    Button addButton;
    Button nextButton;
    private ImageButton homeButton;

    Night currentNight;
    TabLayout.Tab currentTab;
    String currentId;
    String currentName;

    public String getYelpId(){
        return currentId;
    }
    public void setYelpId(String id){
        currentId = id;
    }


    public String getYelpName(){
        return currentName;
    }
    public void setYelpName(String id){
        currentName = id;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Realm realm = Realm.getDefaultInstance();
        if(getIntent().getStringExtra("nightId") != null && getIntent().getStringExtra("nightId").length() > 0){
            String nightId = getIntent().getStringExtra("nightId");
            currentNight = realm.where(Night.class).equalTo("Id", nightId).findFirst();
        }
        else{
            SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
            final String user = pref.getString("username", "");
            if (user.equals("")) {
                Toast.makeText(getBaseContext(), "Error accessing user", Toast.LENGTH_SHORT).show();
            }
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    currentNight = new Night();
                    currentNight.setUsername(user);
                    currentNight.setId(UUID.randomUUID().toString());
                    realm.copyToRealm(currentNight);
                }
            });
        }



        setContentView(R.layout.activity_create_new_night);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.addTab(tabLayout.newTab().setText("Food"));
        tabLayout.addTab(tabLayout.newTab().setText("Entertainment"));
        tabLayout.addTab(tabLayout.newTab().setText("Drinks"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        final TabPagerAdapter adapter = new TabPagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        currentTab = tabLayout.getTabAt(0);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                currentTab = tab;
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        addButton = (Button)findViewById(R.id.create_new_add_button);
        nextButton =(Button)findViewById(R.id.create_new_next_button);

        homeButton = (ImageButton) findViewById(R.id.home_button);

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), TopRatedActivity.class);
                startActivity(intent);
            }
        });
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Realm realm = Realm.getDefaultInstance();

                RealmResults<Event> curEvents = realm.where(Night.class).equalTo("Id",currentNight.getId()).findFirst().getEvents();
                for (Event e:curEvents) {
                    if(e.getYelpID().equals(currentId)){
                        Toast.makeText(getBaseContext(), "You already had " + currentName + " in your night!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        Event newEvent = new Event();
                        newEvent.setNight(realm.where(Night.class).equalTo("Id",currentNight.getId()).findFirst());

                        if(currentTab.getText().toString().toLowerCase().equals("food")){
                            newEvent.setEventType("Food");
                        }
                        else if(currentTab.getText().toString().toLowerCase().equals("entertainment")){
                            newEvent.setEventType("Entertainment");
                        }
                        else {
                            newEvent.setEventType("Drinks");
                        }

                        newEvent.setYelpID(currentId);
                        newEvent.setEventName(currentName);

                        realm.copyToRealm(newEvent);
                    }
                });

                Toast.makeText(getBaseContext(), "Successfully added " + currentName + " to your night!", Toast.LENGTH_SHORT).show();
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(getBaseContext(), ConfirmNightActivity.class );
               intent.putExtra("nightId", currentNight.getId());
               startActivity(intent);
            }
        });

    }

}
