package com.example.arezz.datenightdesmoines;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.RatingBar;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

import io.realm.Realm;

public class ReviewActivity extends AppCompatActivity {

    private Button completeButton;
    private TextView nightName;
    private ImageView nightImage;
    private RatingBar ratingBar;
    private EditText commentReview;
    private ImageButton homeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        completeButton = (Button) findViewById(R.id.complete_button_review);
        nightName = (TextView) findViewById(R.id.night_name_review);
        nightImage = (ImageView) findViewById(R.id.night_image);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar_review);
        homeButton = (ImageButton) findViewById(R.id.home_button);

        Realm realm = Realm.getDefaultInstance();
        final String nightId = getIntent().getStringExtra("nightId");
        Night night = realm.where(Night.class).equalTo("Id", nightId).findFirst();

        nightName.setText(night.getDateName());


        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), TopRatedActivity.class);
                saveRating(nightId);
                startActivity(intent);
            }
        });

        completeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), TopRatedActivity.class);
                saveRating(nightId);
                startActivity(intent);

            }
        });


        if(night.getImageId() != null) {
            Image image = realm.where(Image.class).equalTo("id", night.getImageId()).findFirst();
            Bitmap bmp = BitmapFactory.decodeByteArray(image.getImageBitmap(),0,image.getImageBitmap().length);
            nightImage.setImageBitmap(bmp);
        }
    }

    public void saveRating(final String nightId) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Night night = realm.where(Night.class).equalTo("Id", nightId).findFirst();
                Float ratingNumber = ratingBar.getRating();
                night.setRating(ratingNumber);
                realm.copyToRealm(night);
            }
        });
    }
}
