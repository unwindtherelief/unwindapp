package com.depression.relief.depressionissues.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.VideoView;

import com.airbnb.lottie.LottieAnimationView;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.depression.relief.depressionissues.R;

public class BreathMotionShowActivity extends AppCompatActivity {
    VideoView videoview;
    Button stopbutton;
    ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breath_motion_show);

        videoview = findViewById(R.id.lottie_breathanim);
        stopbutton = findViewById(R.id.stopButton);
        backBtn = findViewById(R.id.backbtn);

        Animation animation = AnimationUtils.loadAnimation(BreathMotionShowActivity.this, R.anim.slide_bottom);
        videoview.setAnimation(animation);


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                Animatoo.INSTANCE.animateCard(BreathMotionShowActivity.this);
            }
        });
        stopbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (videoview.isPlaying()) {
                    videoview.pause();
                } else {
                    videoview.start();
                }
            }
        });

        int animationCount = getIntent().getIntExtra("animationCount", 5);
        int selectedCategory = getIntent().getIntExtra("selectedCategory", -1);
        setVideo(selectedCategory, animationCount);

//        setLottieAnimation(selectedCategory);

//        startAnimationWithCount(animationCount);


    }

    private void setVideo(int selectedCategory, int animationCount) {
        Uri videoUri;

        switch (selectedCategory) {
            case 1:
                videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.relaxingbreathing);
                break;
            case 2:
                videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.deepbreathing);
                break;
            case 3:
                videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.bellybreathing);
                break;
            case 4:
                videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.countingbreathing);
                break;
            case 5:
                videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.resonantbreathing);
                break;
            default:
                videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.relaxingbreathing);
        }
        videoview.setVideoURI(videoUri);
        videoview.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            int currentCount = 0;

            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                if (currentCount < animationCount - 1) {
                    // Play the video again
                    videoview.start();
                    currentCount++;
                } else {
                    // All animations completed, go back
                    onBackPressed();
                }
            }
        });
        videoview.start();
    }
/*

    private void startAnimationWithCount(int animationCount) {
        videoview.setProgress(0f); // Reset the animation progress
        videoview.setRepeatCount(animationCount - 1); // Set the repeat count

        // Set an animation listener to handle completion
        videoview.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                // Animation started
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                // Animation ended
                // You can add any additional logic here if needed
                // For example, navigate back to the previous activity
                onBackPressed();
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

//        lottie_breathanim.playAnimation(); // Start the animation
    }
*/

/*
    private void setLottieAnimation(int selectedCategory) {
        switch (selectedCategory) {
            case 1:
                lottie_breathanim.setAnimation(R.raw.relaxingbreathing);
            case 2:
                lottie_breathanim.setAnimation(R.raw.deepbreathing);
            case 3:
                lottie_breathanim.setAnimation(R.raw.bellybreathing);
            case 4:
                lottie_breathanim.setAnimation(R.raw.countingbreathing);
            case 5:
                lottie_breathanim.setAnimation(R.raw.resonantbreathing);
            default:
                lottie_breathanim.setAnimation(R.raw.relaxingbreathing);
        }
    }*/
}