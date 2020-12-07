package com.example.apitest2;

public class MainActivity extends AppCompatActivity {

    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = findViewById(R.id.textView);

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://rawg-video-games-database.p.rapidapi.com/genres")
                .get()
                .addHeader("x-rapidapi-host", "rawg-video-games-database.p.rapidapi.com")
                .addHeader("x-rapidapi-key", "dcd265526amshbf82698b1c3cc8ap10ddacjsnf99bf0514d7f")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull final Response response) throws IOException {
                if(response.isSuccessful()){
                    final String myResponse = response.body().string();
                    final JSONObject jsonObject;
                    try {
                        jsonObject = new JSONObject(myResponse);
                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                try {
                                    Log.d("TAG", jsonObject.getJSONObject("results").getJSONObject("games").get(0));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
