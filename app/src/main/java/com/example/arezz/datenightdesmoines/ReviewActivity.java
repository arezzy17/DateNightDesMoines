package com.example.arezz.datenightdesmoines;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.RatingBar;

import org.w3c.dom.Text;

public class ReviewActivity extends AppCompatActivity {

    private Button completeButton;
    private TextView nightName;
    private ImageView nightImage;
    private RatingBar ratingBar;
    private EditText commentReview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        completeButton = (Button) findViewById(R.id.complete_button_review);
        nightName = (TextView) findViewById(R.id.night_name_review);
        nightImage = (ImageView) findViewById(R.id.night_image);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar_review);
        commentReview = (EditText) findViewById(R.id.comment_review);

        completeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), CreateNewNight.class);
                startActivity(intent);
            }
        });
    }
}
