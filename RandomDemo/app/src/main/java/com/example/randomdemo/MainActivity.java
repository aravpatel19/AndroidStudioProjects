package com.example.randomdemo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView number;
    Button launch;

    static final int NUMBER_CODE = 1234;
    static final String INTENT_CODE = "number";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        number = findViewById(R.id.id_number);
        launch = findViewById(R.id.id_launch);

        launch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToLoad = new Intent(MainActivity.this, NumberActivity.class);
                intentToLoad.putExtra("TEST", "This is a Test");
                startActivityForResult(intentToLoad, NUMBER_CODE);


            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == NUMBER_CODE && resultCode == RESULT_OK){
            number.setText(data.getStringExtra(INTENT_CODE));
        }
    }
}
