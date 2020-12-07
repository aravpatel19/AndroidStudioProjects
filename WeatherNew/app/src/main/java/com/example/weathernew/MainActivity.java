package com.example.weathernew;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    TextView tempTextView;
    TextView placeTextView;
    TextView quoteTextView;
    TextView dateTextView;
    TextView date1;
    TextView date2;
    TextView date3;
    TextView date4;
    TextView date5;
    TextView low1;
    TextView low2;
    TextView low3;
    TextView low4;
    TextView low5;
    TextView high1;
    TextView high2;
    TextView high3;
    TextView high4;
    TextView high5;



    Button button;


    ImageView currentImageView;
    ImageView image1;
    ImageView image2;
    ImageView image3;
    ImageView image4;
    ImageView image5;
    ImageView pineapple;
    ImageView rock;
    ImageView spongebob;
    ImageView patrick;
    ImageView grass;


    String zipCode;
    JSONObject jsonObject;
    EditText editText;
    Context context;
    JSONObject jsonCurrent;
    DecimalFormat df = new DecimalFormat("0.00");


    protected void onCreate(Bundle savedInstanceState) {
        Log.d("TAG", "TEST");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;

        placeTextView = findViewById(R.id.placeTextView);
        tempTextView = findViewById(R.id.textView2);
        quoteTextView = findViewById(R.id.textView3);
        dateTextView = findViewById(R.id.textView4);
        date1 = findViewById(R.id.date1);
        date2 = findViewById(R.id.date2);
        date3 = findViewById(R.id.date3);
        date4 = findViewById(R.id.date4);
        date5 = findViewById(R.id.date5);
        low1 = findViewById(R.id.low1);
        low2 = findViewById(R.id.low2);
        low3 = findViewById(R.id.low3);
        low4 = findViewById(R.id.low4);
        low5 = findViewById(R.id.low5);
        high1 = findViewById(R.id.high1);
        high2 = findViewById(R.id.high2);
        high3 = findViewById(R.id.high3);
        high4 = findViewById(R.id.high4);
        high5 = findViewById(R.id.high5);

        image1 = findViewById(R.id.image1);
        image2 = findViewById(R.id.image2);
        image3 = findViewById(R.id.image3);
        image4 = findViewById(R.id.image4);
        image5 = findViewById(R.id. image5);
        /*pineapple = findViewById(R.id.pineapple);
        pineapple.setImageResource(R.drawable.pineapple2);
        rock = findViewById(R.id.rock);
        rock.setImageResource(R.drawable.rock);
        spongebob = findViewById(R.id.spongebob);
        spongebob.setImageResource(R.drawable.spongebob);
        patrick = findViewById(R.id.patrick);
        patrick.setImageResource(R.drawable.patrick);
        grass = findViewById(R.id.grass);
        grass.setImageResource(R.drawable.ground);*/

        currentImageView = findViewById(R.id.imageView);



        editText = findViewById(R.id.editText);

        button = findViewById(R.id.button);

        AsyncThread myThread = new AsyncThread();
        myThread.execute("08852");

        zipCode = editText.getText().toString();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncThread weatherThread = new AsyncThread();
                weatherThread.execute(editText.getText().toString());
            }
        });
    }

    private class AsyncThread extends AsyncTask<String, Void, String> {

        String infoCurrent = "";
        String infoFive = "";

        @Override
        protected String doInBackground(String... strings) {
            Log.d("TAG", strings[0]);
            try {

                URL urlCurrent = new URL("http://api.openweathermap.org/data/2.5/weather?zip="+strings[0]+"&appid=9ce3e8db89f984d7bd791a4189a43dae&units=imperial");
                URLConnection connectionCurrent = urlCurrent.openConnection();
                InputStream streamCurrent = connectionCurrent.getInputStream();
                BufferedReader bufferedReaderCurrent= new BufferedReader(new InputStreamReader(streamCurrent));
                infoCurrent = bufferedReaderCurrent.readLine();;

                URL urlFive = new URL("http://api.openweathermap.org/data/2.5/forecast?zip="+strings[0]+"&appid=9ce3e8db89f984d7bd791a4189a43dae&units=imperial");
                URLConnection connectionFive = urlFive.openConnection();
                InputStream streamFive = connectionFive.getInputStream();
                BufferedReader bufferedReaderFive= new BufferedReader(new InputStreamReader(streamFive));

                String line1 = "";
                line1 = bufferedReaderFive.readLine();
                infoFive = line1;


            } catch (Exception e) {
                Log.d("TAG",e.toString());
            }

            Log.d("TAG", infoCurrent);
            return infoCurrent;
        }

        @Override
        protected void onPostExecute(String infoCurrent) {
            super.onPostExecute(infoCurrent);

            try {
                jsonCurrent = new JSONObject(infoCurrent);
                Log.d("EXECUTE", jsonCurrent+"");
                JSONObject jsonFive = new JSONObject(infoFive);

                //Log.d("EXECUTE", String.valueOf(jsonCurrent));

                //current
                placeTextView.setText(jsonCurrent.getString("name"));
                tempTextView.setText(df.format(Double.parseDouble(jsonCurrent.getJSONObject("main").getString("temp")))+" °F");
                String iconText = jsonCurrent.getJSONArray("weather").getJSONObject(0).getString("icon");
                Picasso.with(context).load("http://openweathermap.org/img/wn/"+iconText+".png").into(currentImageView);
                dateTextView.setText(jsonFive.getJSONArray("list").getJSONObject(0).getString("dt_txt"));

                //quote 1
                if(iconText.equals("01d") || iconText.equals("01n")){
                    quoteTextView.setText("It's a perfect, clear day to go jellyfishing!");
                }

                //quote 2
                else if(iconText.equals("02d") || iconText.equals("02n")){
                    quoteTextView.setText("Eating Krusty Krabs under a few clouds!");
                }

                //quote 3
                else if(iconText.equals("03d")||iconText.equals("03d")){
                    quoteTextView.setText("Sounds like a perfect day to blow bubbles!");
                }

                //quote 4
                else if(iconText.equals("04d") || iconText.equals("04n")) {
                    quoteTextView.setText("Time to listen to Squidward's clarinet to cheer us up from this gloomy weather");
                }

                //quote 5
                else if(iconText.equals("09d") || iconText.equals("09n")) {
                    quoteTextView.setText("Sandy's home would cover be good cover from this rain");
                }

                //quote 6
                else if(iconText.equals("10d") || iconText.equals("10n")){
                    quoteTextView.setText("Sandy's home would cover be good cover from this rain");
                }

                //quote 7
                else if(iconText.equals("11d") || iconText.equals("11n")){
                    quoteTextView.setText("It's best to hide under Patrick's rock from the incoming thunderstorm!");
                }

                //quote 8
                else if(iconText.equals("13d") || iconText.equals("13n")){
                    quoteTextView.setText("SNOWBALL FIGHT!!");
                }

                //quote 9
                else if(iconText.equals("50d") || iconText.equals("50n")){
                    quoteTextView.setText("This spooky weather could only mean that Plankton is up to no good");
                }

                //1
                low1.setText("Low: "+ df.format(Double.parseDouble(jsonFive.getJSONArray("list").getJSONObject(7).getJSONObject("main").getString("temp_min"))) + " °F");
                high1.setText("High: "+ df.format(Double.parseDouble(jsonFive.getJSONArray("list").getJSONObject(7).getJSONObject("main").getString("temp_max"))) + " °F");
                String icon1 = jsonFive.getJSONArray("list").getJSONObject(7).getJSONArray("weather").getJSONObject(0).getString("icon");
                Picasso.with(context).load("http://openweathermap.org/img/wn/"+icon1+".png").into(image1);
                date1.setText(jsonFive.getJSONArray("list").getJSONObject(7).getString("dt_txt"));

                //2
                low2.setText("Low: "+ df.format(Double.parseDouble(jsonFive.getJSONArray("list").getJSONObject(15).getJSONObject("main").getString("temp_min"))) + " °F");
                high2.setText("High: "+ df.format(Double.parseDouble(jsonFive.getJSONArray("list").getJSONObject(15).getJSONObject("main").getString("temp_max"))) + " °F");
                String icon2 = jsonFive.getJSONArray("list").getJSONObject(15).getJSONArray("weather").getJSONObject(0).getString("icon");
                Picasso.with(context).load("http://openweathermap.org/img/wn/"+icon2+".png").into(image2);
                date2.setText(jsonFive.getJSONArray("list").getJSONObject(15).getString("dt_txt"));

                //3
                low3.setText("Low: "+ df.format(Double.parseDouble(jsonFive.getJSONArray("list").getJSONObject(23).getJSONObject("main").getString("temp_min"))) + " °F");
                high3.setText("High: "+ df.format(Double.parseDouble(jsonFive.getJSONArray("list").getJSONObject(23).getJSONObject("main").getString("temp_max"))) + " °F");
                String icon3 = jsonFive.getJSONArray("list").getJSONObject(23).getJSONArray("weather").getJSONObject(0).getString("icon");
                Picasso.with(context).load("http://openweathermap.org/img/wn/"+icon3+".png").into(image3);
                date3.setText(jsonFive.getJSONArray("list").getJSONObject(23).getString("dt_txt"));

                //4
                low4.setText("Low: "+ df.format(Double.parseDouble(jsonFive.getJSONArray("list").getJSONObject(31).getJSONObject("main").getString("temp_min"))) + " °F");
                high4.setText("High: "+ df.format(Double.parseDouble(jsonFive.getJSONArray("list").getJSONObject(31).getJSONObject("main").getString("temp_max"))) + " °F");
                String icon4 = jsonFive.getJSONArray("list").getJSONObject(31).getJSONArray("weather").getJSONObject(0).getString("icon");
                Picasso.with(context).load("http://openweathermap.org/img/wn/"+icon4+".png").into(image4);
                date4.setText(jsonFive.getJSONArray("list").getJSONObject(31).getString("dt_txt"));

                //5
                low5.setText("Low: "+ df.format(Double.parseDouble(jsonFive.getJSONArray("list").getJSONObject(39).getJSONObject("main").getString("temp_min"))) + " °F");
                high5.setText("High: "+ df.format(Double.parseDouble(jsonFive.getJSONArray("list").getJSONObject(39).getJSONObject("main").getString("temp_max"))) + " °F");
                String icon5 = jsonFive.getJSONArray("list").getJSONObject(39).getJSONArray("weather").getJSONObject(0).getString("icon");
                Picasso.with(context).load("http://openweathermap.org/img/wn/"+icon5+".png").into(image5);
                date5.setText(jsonFive.getJSONArray("list").getJSONObject(39).getString("dt_txt"));


            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }



    /*public class CustomAdapter extends ArrayAdapter<String> {

        Context parentContext;
        int xmlResource;
        List<String> list;

        public CustomAdapter(@NonNull Context context, int resource, @NonNull List<String> objects) {
            super(context, resource, objects);
            parentContext = context;
            xmlResource = resource;
            list = objects;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            LayoutInflater layoutInflater = (LayoutInflater) parentContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.adapter_custom, null);

            TextView textView1 = findViewById(R.id.adapter_textView1);
            TextView textView2 = findViewById(R.id.adapter_textView2);
            TextView textView3 = findViewById(R.id.adapter_textView3);
            ImageView imageView = findViewById(R.id.adapter_imageView);



            return view;
        }
    }
*/
}


