package com.jnd.digim;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class SplashActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);
//        SharedPreferences.Editor my = sharedPreferences.edit();
//        my.putString("Email","jaydholakiya01@gmail.com");
//        my.commit();
//        Toast.makeText(this,sharedPreferences.getString("Email",null)    , Toast.LENGTH_SHORT).show();
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        getWindow().setWindowAnimations(R.style.WindowAnimationTransition);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.bounce);
        Animation slide = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade);
        slide.setStartOffset(600);
        TextView digiM = findViewById(R.id.digimLogo);
        TextView tagLine = findViewById(R.id.tagLineText);
        digiM.startAnimation(animation);
        tagLine.startAnimation(slide);
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