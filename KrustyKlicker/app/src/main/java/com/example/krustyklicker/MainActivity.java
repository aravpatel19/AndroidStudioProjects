package com.example.krustyklicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.concurrent.atomic.AtomicInteger;

public class MainActivity extends AppCompatActivity {


    ImageView imageView;
    ConstraintLayout constraintLayout;
    TextView textView;
    TextView scoreText;
    TextView upgradeText;
    ConstraintLayout c2;

    AtomicInteger score = new AtomicInteger(0);
    AtomicInteger add = new AtomicInteger(1);
    AtomicInteger upgrade = new AtomicInteger(50);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);
        scoreText = findViewById(R.id.textView2);
        upgradeText = findViewById(R.id.textView3);

        final ScaleAnimation scaleAnimation = new ScaleAnimation(1.25f, 1.0f, 1.25f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(250);

        upgradeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(score.get() >= upgrade.get()){
                    upgradeText.setBackgroundColor(Color.parseColor("#57FFEB3B"));
                    int temp = -1 * upgrade.get();
                    score.getAndAdd(temp);
                    scoreText.setText("Score: "+score.get());
                    upgrade.getAndAdd(upgrade.get());
                    upgradeText.setText("Get Spongebob \n Cost: "+upgrade.get() + " patties");
                    //add.getAndAdd(3);
                    new Upgrade().start();

                    constraintLayout = findViewById(R.id.id_layout);
                    ImageView i = new ImageView(MainActivity.this);
                    i.setId(View.generateViewId());
                    i.setImageResource(R.drawable.spongebob);


                    ConstraintLayout.LayoutParams p2 = new ConstraintLayout.LayoutParams(
                            ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT
                    );
                    p2.width = 500;
                    p2.height = 500;

                    i.setLayoutParams(p2);

                    constraintLayout.addView(i);

                    ConstraintSet constraintSet = new ConstraintSet();
                    constraintSet.clone(constraintLayout);


                    constraintSet.connect(i.getId(), ConstraintSet.TOP, constraintLayout.getId(), ConstraintSet.TOP);
                    constraintSet.connect(i.getId(), ConstraintSet.BOTTOM, constraintLayout.getId(), ConstraintSet.BOTTOM);
                    constraintSet.connect(i.getId(), ConstraintSet.LEFT, constraintLayout.getId(), ConstraintSet.LEFT);
                    constraintSet.connect(i.getId(), ConstraintSet.RIGHT, constraintLayout.getId(), ConstraintSet.RIGHT);


                    float rand = (float)(Math.random()*1);
                    float rand2 = (float)(Math.random()*0.2)+(float)0.75;
                    constraintSet.setHorizontalBias(i.getId(), rand);
                    constraintSet.setVerticalBias(i.getId(), rand2);

                    constraintSet.applyTo(constraintLayout);
                }
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(scaleAnimation);
                score.getAndAdd(add.get());
                constraintLayout = findViewById(R.id.id_layout);
                textView = new TextView(MainActivity.this);
                textView.setId(View.generateViewId());
                textView.setText("+"+add.get());
                scoreText.setText("Score: "+score.get());

                if(score.get() >= upgrade.get()){
                    upgradeText.setBackgroundColor(Color.parseColor("#DDFFEB3B"));
                }

                ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(
                        ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT
                );

                textView.setLayoutParams(params);

                constraintLayout.addView(textView);

                ConstraintSet constraintSet = new ConstraintSet();
                constraintSet.clone(constraintLayout);


                constraintSet.connect(textView.getId(), ConstraintSet.TOP, constraintLayout.getId(), ConstraintSet.TOP);
                constraintSet.connect(textView.getId(), ConstraintSet.BOTTOM, constraintLayout.getId(), ConstraintSet.BOTTOM);
                constraintSet.connect(textView.getId(), ConstraintSet.LEFT, constraintLayout.getId(), ConstraintSet.LEFT);
                constraintSet.connect(textView.getId(), ConstraintSet.RIGHT, constraintLayout.getId(), ConstraintSet.RIGHT);


                float rand = (float)(Math.random()*.5)+.25f;
                constraintSet.setHorizontalBias(textView.getId(), rand);
                constraintSet.setVerticalBias(textView.getId(), .25f);

                constraintSet.applyTo(constraintLayout);

                ObjectAnimator animation = ObjectAnimator.ofFloat(textView, "translationY", 0f, -500f);
                animation.setDuration(1000);
                animation.start();
            }
        });

    }

    public class Upgrade extends Thread{
        @Override
        public void run() {
            while(true) {
                try {
                    Upgrade.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                score.getAndAdd(3);
                runOnUiThread(new Thread(new Runnable() {
                    @Override
                    public void run() {
                        scoreText.setText("Score: "+score);
                    }
                }));
            }
        }
    }

}
