package com.aryan.notemaker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

public class SplashScreen extends AppCompatActivity {
    LottieAnimationView lottieAnimation;
    TextView splashtext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        splashtext = findViewById(R.id.splashtext);
        lottieAnimation = findViewById(R.id.lottieAnimation);

        Animation animation= AnimationUtils.loadAnimation(this,R.anim.splash_screen_content);
        splashtext.startAnimation(animation);
        Animation animation_lottie= AnimationUtils.loadAnimation(this,R.anim.splash_animation);
        lottieAnimation.startAnimation(animation_lottie);

        Handler h = new Handler();
        h.postDelayed(() -> {
            Intent intent = new Intent(SplashScreen.this  , MainActivity.class);
            SplashScreen.this.startActivity(intent);
            finish();
        },3500);
    }
}