package com.example.fragmentdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements BottomFragment.SendInfo{

    Button replaceButton;
    FragmentTransaction fragmentTransaction;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        replaceButton = findViewById(R.id.button);

        fragmentManager = getSupportFragmentManager();

        fragmentTransaction = fragmentManager.beginTransaction();

        BottomFragment bottomFragment = new BottomFragment();
        fragmentTransaction.add(R.id.id_bottom,bottomFragment);

        fragmentTransaction.commit();


    }

    public void update(String str){
        replaceButton.setText(str);
    }
}
