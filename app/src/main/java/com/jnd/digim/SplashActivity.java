package com.jnd.digim;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class SplashActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Setting the "FULL SCREEN" layout for the Splash Screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        //Setting the Activity animations
        getWindow().setWindowAnimations(R.style.WindowAnimationTransition);

        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.bounce);
        Animation slide = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade);
        slide.setStartOffset(600);
        TextView digiM = findViewById(R.id.digimLogo);
        TextView tagLine = findViewById(R.id.tagLineText);
        digiM.startAnimation(animation);
        tagLine.startAnimation(slide);

        //Thread for going to the Dashboard Activity if logged in, otherwise Signin Activity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(FirebaseAuth.getInstance().getCurrentUser()!=null){
                    Intent dashboardIntent = new Intent(SplashActivity.this,DashboardActivity.class);
                    startActivity(dashboardIntent);
                    finish();
                }
                else{
                    Intent HomeIntent = new Intent(SplashActivity.this,HomeActivity.class);
                    startActivity(HomeIntent);
                    finish();
                }
            }
        },2000);
    }
}