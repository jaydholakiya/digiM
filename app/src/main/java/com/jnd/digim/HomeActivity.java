package com.jnd.digim;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

public class HomeActivity extends AppCompatActivity {
    CarouselView carouselView;
    int[] images = {
            R.drawable.firbanner,
            R.drawable.secbanner,
            R.drawable.thirbanner
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        carouselView = findViewById(R.id.carouselView);
        carouselView.setPageCount(images.length);
        carouselView.setImageListener(imageListener);
        openSnackbar(carouselView);
        getWindow().setWindowAnimations(R.style.WindowAnimationTransition);
        TextView loginTxt = findViewById(R.id.login);
        loginTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public void openSnackbar(View coordinatorLayout) {
        Snackbar snackbar = Snackbar.make(coordinatorLayout,"Please sign-in to active services",BaseTransientBottomBar.LENGTH_INDEFINITE).setAction("CLOSE", new View.OnClickListener() {
            @Override
            public void onClick(View coordinatorLayout) {
                Snackbar.make(coordinatorLayout,"Please sign-in to active services",BaseTransientBottomBar.LENGTH_LONG).dismiss();
            }
        });
        snackbar.setActionTextColor(Color.RED);
        snackbar.show();
    }

    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(images[position]);
        }
    };
}