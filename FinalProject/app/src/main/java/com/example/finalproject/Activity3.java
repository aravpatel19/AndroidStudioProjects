package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Activity3 extends AppCompatActivity {
    public static final String EXTRA_TEXT5 = "com.example.finalproject.EXTRA_TEXT5";

    String name;
    String imageString;
    String descriptionString;
    String releaseDate;
    String website;
    double ratingDouble;
    JSONObject object;
    ArrayList<JSONObject> list;

    TextView title;
    TextView description;
    TextView release;
    TextView rating;
    TextView web;

    ImageView image;

    Button save;

    String gameString = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);

        description = findViewById(R.id.description);
        image = findViewById(R.id.imageView7);
        title = findViewById(R.id.title_text3);
        release = findViewById(R.id.release);
        rating = findViewById(R.id.rating);
        web = findViewById(R.id.web);
        save = findViewById(R.id.save);

        list = new ArrayList<>();

        Intent intent = getIntent();
            gameString = intent.getStringExtra(Activity2.EXTRA_TEXT4);
        try {
            object = new JSONObject(gameString);
            name = object.getString("name");
            imageString = object.getString("background_image");
            descriptionString = object.getString("description_raw");
            releaseDate = object.getString("released");
            ratingDouble = object.getDouble("rating");
            website = object.getString("website");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        title.setText(name);
        Picasso.with(Activity3.this).load(imageString).into(image);
        description.setText(descriptionString);
        release.setText("Release Date: \t"+releaseDate);
        rating.setText("Rating: \t"+ratingDouble+" / 5.0");
        web.setText(website);


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity(object.toString());
            }
        });
    }
    public void openMainActivity(String object){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(EXTRA_TEXT5, object);
        startActivity(intent);
    }
}
