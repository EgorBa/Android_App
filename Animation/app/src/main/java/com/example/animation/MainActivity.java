package com.example.animation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    ImageView image_rotate;
    ImageView image_up;
    ImageView image_down;
    ImageView image_right;
    ImageView image_left;
    Animation animation_text = null;
    Animation rotate = null;
    Animation up = null;
    Animation down = null;
    Animation right = null;
    Animation left = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.text);
        animation_text = AnimationUtils.loadAnimation(this, R.anim.alpha);
        textView.startAnimation(animation_text);
        rotate = AnimationUtils.loadAnimation(this, R.anim.rotate);
        up = AnimationUtils.loadAnimation(this, R.anim.scale_up);
        down = AnimationUtils.loadAnimation(this, R.anim.scale_down);
        right = AnimationUtils.loadAnimation(this, R.anim.scale_rigt);
        left = AnimationUtils.loadAnimation(this, R.anim.scale_left);
        image_rotate = findViewById(R.id.image);
        image_up = findViewById(R.id.square_up);
        image_down = findViewById(R.id.square_down);
        image_right = findViewById(R.id.square_right);
        image_left = findViewById(R.id.square_left);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                image_rotate.startAnimation(rotate);
            }
        }, 1000);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                image_up.startAnimation(up);
            }
        }, 1300);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                image_right.startAnimation(right);
            }
        }, 1600);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                image_down.startAnimation(down);
            }
        }, 1900);handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                image_left.startAnimation(left);
            }
        }, 2100);


    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}
