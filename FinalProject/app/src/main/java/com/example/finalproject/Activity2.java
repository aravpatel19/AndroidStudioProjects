package com.example.finalproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Activity2 extends AppCompatActivity {
    public static final String EXTRA_TEXT4 = "com.example.finalproject.EXTRA_TEXT4";

    TextView title2;
    ImageView genreImage;
    JSONObject object = new JSONObject();
    String genre;
    String genreImageString;
    String jsonString;
    ArrayList<ImageView> imageList;
    ArrayList<TextView> textList;

    ImageView imageView1;
    ImageView imageView2;
    ImageView imageView3;
    ImageView imageView4;
    ImageView imageView5;
    ImageView imageView6;

    TextView textView1;
    TextView textView2;
    TextView textView3;
    TextView textView4;
    TextView textView5;
    TextView textView6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        title2 = findViewById(R.id.title_text2);
        genreImage = findViewById(R.id.imageView);

        imageList = new ArrayList<>();
        textList = new ArrayList<>();

        imageView1 = findViewById(R.id.imageView1);
        imageView2 = findViewById(R.id.imageView2);
        imageView3 = findViewById(R.id.imageView3);
        imageView4 = findViewById(R.id.imageView4);
        imageView5 = findViewById(R.id.imageView5);
        imageView6 = findViewById(R.id.imageView6);

        textView1 = findViewById(R.id.textView1);
        textView2 = findViewById(R.id.textView2);
        textView3 = findViewById(R.id.textView3);
        textView4 = findViewById(R.id.textView4);
        textView5 = findViewById(R.id.textView5);
        textView6 = findViewById(R.id.textView6);

        imageList.add(imageView1);
        imageList.add(imageView2);
        imageList.add(imageView3);
        imageList.add(imageView4);
        imageList.add(imageView5);
        imageList.add(imageView6);

        textList.add(textView1);
        textList.add(textView2);
        textList.add(textView3);
        textList.add(textView4);
        textList.add(textView5);
        textList.add(textView6);


        Intent intent = getIntent();
        genre = intent.getStringExtra(MainActivity.EXTRA_TEXT);
        genreImageString = intent.getStringExtra(MainActivity.EXTRA_TEXT2);
        jsonString = intent.getStringExtra(MainActivity.EXTRA_TEXT3);
        //Log.d("HEY", object.toString());

        //Log.d("HEY", genreImageString);

        String imageUri = genreImageString;
        Picasso.with(Activity2.this).load(imageUri).into(genreImage);

        title2.setText("Genre: " + genre.toUpperCase());

        OkHttpClient client = new OkHttpClient();
        Request request;

        for (int i = 0; i < 6; i++) {
            try {
                object = new JSONObject(jsonString);
                String currentSlug = object.getJSONArray(genre).getJSONObject(i).getString("slug");
                //Log.d("HEY", currentSlug);
                request = new Request.Builder()
                        .url("https://rawg-video-games-database.p.rapidapi.com/games/" + currentSlug)
                        .get()
                        .addHeader("x-rapidapi-host", "rawg-video-games-database.p.rapidapi.com")
                        .addHeader("x-rapidapi-key", "dcd265526amshbf82698b1c3cc8ap10ddacjsnf99bf0514d7f")
                        .build();

                try {
                    final int finalI = i;
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(@NotNull Call call, @NotNull IOException e) {

                        }

                        @Override
                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                            if (response.isSuccessful()) {
                                final String myResponse = response.body().string();

                                try {
                                    final JSONObject gameObject = new JSONObject(myResponse);
                                    //Log.d("HEY", gameObject.toString());

                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            String gameName = null;
                                            try {
                                                gameName = gameObject.getString("name");
                                                textList.get(finalI).setText(gameName);
                                                String gameImageString = gameObject.getString("background_image");
                                                Picasso.with(Activity2.this).load(gameImageString).into(imageList.get(finalI));
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            imageList.get(finalI).setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    openActivity3(gameObject.toString());
                                                }
                                            });
                                            textList.get(finalI).setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    openActivity3(gameObject.toString());
                                                }
                                            });
                                        }
                                    });

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }

    public void openActivity3(String gameObject) {
        //Log.d("HEY", "it worked!");

        Intent intent = new Intent(this, Activity3.class);
        intent.putExtra(EXTRA_TEXT4, gameObject);
        startActivity(intent);
    }
}
