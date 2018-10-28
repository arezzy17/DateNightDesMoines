package com.example.arezz.datenightdesmoines;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmResults;

public class CurrentNight extends AppCompatActivity implements IYelpList{

    private TextView nightName;
    private RecyclerView eventList;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter currentAdapter;
    private Button addEventButton;
    private Button reviewButton;
    private Button completeButton;
    private ImageButton imageButton;
    private ImageButton homeButton;
    private Dialog popup;


    public void setList(JSONObject obj){
        YelpItem displayItem = YelpItem.GetYelpItemFromBusinessJSON(obj);
        ShowPopup(displayItem);
    }
    private void ShowPopup(YelpItem yelpItem) {
        popup.setContentView(R.layout.yelp_details_popup);

        TextView title=(TextView) popup.findViewById(R.id.yelp_details_name);
        title.setText(yelpItem.getName());

        TextView address =(TextView) popup.findViewById(R.id.yelp_details_address);
        address.setText(yelpItem.getAddress());

        TextView phone =(TextView) popup.findViewById(R.id.yelp_details_phone);
        phone.setText(yelpItem.getDisplayPhone());

        TextView price =(TextView) popup.findViewById(R.id.yelp_details_price);
        price.setText(yelpItem.getPrice());

        TextView rating =(TextView) popup.findViewById(R.id.yelp_details_rating);
        rating.setText(yelpItem.getStars());

        ImageView picture = (ImageView) popup.findViewById(R.id.yelp_details_image);
        Picasso.get().load(yelpItem.getImageUrl()).into(picture);

        Button closeButton = (Button)popup.findViewById(R.id.yelp_details_close_button);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup.dismiss();
            }
        });

        popup.show();
    }


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
        popup = new Dialog(this);
        String user = pref.getString("username", null);
        final String nightId = getIntent().getStringExtra("nightId");

        final Realm realm = Realm.getDefaultInstance();
        final Night currentNight = realm.where(Night.class).equalTo("Id", nightId).findFirst();

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
                saveImage(nightId);
                startActivity(intent);
            }
        });

        ArrayList<Event> events = new ArrayList<>();

        RealmResults<Event> realmEvents = realm.where(Night.class).equalTo("Id", nightId).findFirst().getEvents();
        for (Event e: realmEvents) {
            events.add(e);
        }

        final ArrayList<Event> events2 = (ArrayList<Event>) events.clone();
        final IYelpList thisAct = this;

        RecyclerViewClickListener listener = new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                Event event = (Event) events2.get(position);
                RequestQueue queue = MySingleton.getInstance(getApplicationContext()).getRequestQueue();
                JsonObjectRequest myReq = MySingleton.getInstance(getBaseContext()).GetJsonRequestYelpId("https://api.yelp.com/v3/businesses/",getString(R.string.bearer_key),event.getYelpID(), thisAct);
                MySingleton.getInstance(getBaseContext()).addToRequestQueue(myReq);
            }
        };

        layoutManager = new LinearLayoutManager(this);
        eventList.setLayoutManager(layoutManager);

        currentAdapter = new CurrentNightAdapter(this, realmEvents, listener);
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
                saveImage(nightId);
                intent.putExtra("nightId", nightId);
                startActivity(intent);
            }
        });

        completeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), TopRatedActivity.class);
                intent.putExtra("nightId", nightId);
                saveImage(nightId);
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

    public void saveImage(final String nightId) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Image image = new Image();
                image.setId(UUID.randomUUID().toString());
                Night night = realm.where(Night.class).equalTo("Id", nightId).findFirst();
                BitmapDrawable imageBitmap = (BitmapDrawable) imageButton.getDrawable();

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                imageBitmap.getBitmap().compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] imageinBytes = baos.toByteArray();
                image.setImageBitmap(imageinBytes);
                night.setImageId(image.getId());
                realm.copyToRealm(image);
                realm.copyToRealm(night);
            }
        });
    }
}
