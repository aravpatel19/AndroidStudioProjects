package com.example.finalproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproject.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.example.finalproject.Activity2.EXTRA_TEXT4;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_TEXT = "com.example.finalproject.EXTRA_TEXT";
    public static final String EXTRA_TEXT2 = "com.example.finalproject.EXTRA_TEXT2";
    public static final String EXTRA_TEXT3 = "com.example.finalproject.EXTRA_TEXT3";
    public static final String EXTRA_TEXT6 = "com.example.finalproject.EXTRA_TEXT6";

    Button button;
    Button saved;
    EditText editText;

    JSONObject object;
    String genreImage = "";

    ArrayList<String> list;

    @Override
    protected void onPostResume() {
        super.onPostResume();

        Intent intent = getIntent();
        String gameName = intent.getStringExtra(Activity3.EXTRA_TEXT5);
        list.add(gameName);
        saveData();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadData();

        button = findViewById(R.id.click_button);
        editText = findViewById(R.id.user_type);
        saved = findViewById(R.id.savedgames);

        saved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity3(list.get(0));
            }
        });

        object = new JSONObject();

        list = new ArrayList<>();

        final String[][] listf = new String[19][6];

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://rawg-video-games-database.p.rapidapi.com/genres")
                .get()
                .addHeader("x-rapidapi-host", "rawg-video-games-database.p.rapidapi.com")
                .addHeader("x-rapidapi-key", "dcd265526amshbf82698b1c3cc8ap10ddacjsnf99bf0514d7f")
                .build();

        try {
            final JSONObject[] jsonObject = new JSONObject[1];
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    e.printStackTrace();
                }


                @Override
                public void onResponse(@NotNull Call call, @NotNull final Response response) throws IOException {
                    if (response.isSuccessful()) {
                        final String myResponse = response.body().string();
                        JSONArray genres = new JSONArray();

                        try {
                            jsonObject[0] = new JSONObject(myResponse);

                            final JSONObject finalJsonObject = jsonObject[0];

                            //Log.d("HEY", finalJsonObject.toString());

                            for(int i=0;i<listf.length;i++) {
                                final JSONArray games = new JSONArray();
                                final int finalI = i;
                                final String genre = finalJsonObject.getJSONArray("results").getJSONObject(finalI).getString("name");
                                for (int j = 0; j < listf[0].length; j++) {
                                    final int finalJ = j;
                                    MainActivity.this.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {

                                            try {
                                                String name = finalJsonObject.getJSONArray("results").getJSONObject(finalI).getJSONArray("games").getJSONObject(finalJ).getString("name");
                                                String slug = finalJsonObject.getJSONArray("results").getJSONObject(finalI).getJSONArray("games").getJSONObject(finalJ).getString("slug");
                                                String image = finalJsonObject.getJSONArray("results").getJSONObject(finalI).getString("image_background");
                                                //Log.d("HEY", genreImage);


                                                JSONObject game = new JSONObject();
                                                game.put("name", name);
                                                game.put("slug", slug);
                                                game.put("genre_image", image);

                                                games.put(game);


                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

                                        }
                                    });
                                }
                                object.put(genre.toLowerCase(), games);
                            }
                            Log.d("HEY", object.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String input = editText.getText().toString().toLowerCase();
                            if(object.toString().contains(input) && input.length()>1){

                                try {
                                    genreImage = object.getJSONArray(input).getJSONObject(0).getString("genre_image");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                openActivity2(object.toString());
                            }
                            else {
                                Toast.makeText(MainActivity.this, "Please enter a correct game genre", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void openActivity2(String object){
        String chosenGenre = editText.getText().toString();

        Intent intent = new Intent(this, Activity2.class);
        intent.putExtra(EXTRA_TEXT, chosenGenre);
        intent.putExtra(EXTRA_TEXT2, genreImage);
        intent.putExtra(EXTRA_TEXT3, object);
        startActivity(intent);

    }

    private void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString("task list", json);
        editor.apply();
    }

    private void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("task list", null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        list = gson.fromJson(json, type);

        if(list == null){
            list = new ArrayList<>();
        }
    }
    public void openActivity3(String object){
        Intent intent = new Intent(this, Activity3.class);
        intent.putExtra(EXTRA_TEXT4, object);
        startActivity(intent);

    }
}

