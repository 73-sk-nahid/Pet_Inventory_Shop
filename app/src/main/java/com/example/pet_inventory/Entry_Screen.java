package com.example.pet_inventory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;

import android.animation.Animator;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

public class Entry_Screen extends AppCompatActivity {
    LottieAnimationView lottieAnimationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_screen);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        lottieAnimationView = findViewById(R.id.animationView);

        lottieAnimationView.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                // Animation started
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                // Animation ended
                // Add logic to check if it's the end of the 5th section
                // For example, if the progress is 1.0, it's the end of the animation
                if (lottieAnimationView.getProgress() == 1.0f) {
                    // Start the LogIn activity
                    Intent intent = new Intent(getApplicationContext(), LogIn.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onAnimationCancel(Animator animator) {
                // Animation canceled
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
                // Animation repeated
            }
        });

    }
}