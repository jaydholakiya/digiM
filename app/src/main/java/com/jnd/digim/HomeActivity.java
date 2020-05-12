package com.jnd.digim;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

public class HomeActivity extends AppCompatActivity {
    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;
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

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("digiM");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDrawer = (DrawerLayout)findViewById(R.id.navDrawer);
        nvDrawer = (NavigationView)findViewById(R.id.nvView);
        setupDrawerContent(nvDrawer);

        NavigationView navigationView = (NavigationView)findViewById(R.id.nvView);
        View headerView = navigationView.getHeaderView(0);
        TextView digiMLogoDashboard = (TextView)headerView.findViewById(R.id.digiMHeaderHome);
        digiMLogoDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,SplashActivity.class);
                startActivity(intent);
                finish();
            }
        });

        TextView login = (TextView)findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(HomeActivity.this,SigninActivity.class);
                startActivity(loginIntent);
            }
        });

        carouselView = findViewById(R.id.carouselView);
        carouselView.setPageCount(images.length);
        carouselView.setImageListener(imageListener);
        openSnackbar(carouselView);
        getWindow().setWindowAnimations(R.style.WindowAnimationTransition);
    }

    public void openSnackbar(View coordinatorLayout) {
        if(FirebaseAuth.getInstance().getCurrentUser()==null){
            Snackbar snackbar = Snackbar.make(coordinatorLayout,"Please sign-in to active services",BaseTransientBottomBar.LENGTH_INDEFINITE).setAction("CLOSE", new View.OnClickListener() {
                @Override
                public void onClick(View coordinatorLayout) {
                    Snackbar.make(coordinatorLayout,"Please sign-in to active services",BaseTransientBottomBar.LENGTH_LONG).dismiss();
                }
            });
            snackbar.setActionTextColor(Color.RED);
            snackbar.show();
            findViewById(R.id.login).setVisibility(View.VISIBLE);
            ((NavigationView)findViewById(R.id.nvView)).getMenu().findItem(R.id.myDashboard).setVisible(false);
        }
        else findViewById(R.id.login).setVisibility(View.INVISIBLE);
    }

    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(images[position]);
        }
    };

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.home:
                mDrawer.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setupDrawerContent(NavigationView navigationView){
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.myDashboard){
                        Intent loginIntent = new Intent(HomeActivity.this, DashboardActivity.class);
                        startActivity(loginIntent);
                        finish();
                }
                else {
                    selectDrawerItem(item);
                }
                return true;
            }
        });
    }

    public void selectDrawerItem(MenuItem item) {
        Fragment fragment = null;
        Class fragmentClass = null;
        switch (item.getItemId()) {
            case R.id.working:
                fragmentClass = Working.class;
                break;
            case R.id.aboutUs:
                fragmentClass = AboutUs.class;
                break;
            case R.id.contactUs:
                fragmentClass = ContactUs.class;
                break;
            case R.id.developerInfo:
                fragmentClass = DeveloperInfo.class;
                break;
            case R.id.video:
                fragmentClass = VideoFragment.class;
                break;
        }
        try {
            fragment = (Fragment)fragmentClass.newInstance();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.homeFrame,fragment).commit();
        item.setChecked(true);
        setTitle(item.getTitle());
        mDrawer.closeDrawers();
    }

    @Override
    public void onBackPressed() {
        if(this.mDrawer.isDrawerOpen(GravityCompat.START)) {
            this.mDrawer.closeDrawer(GravityCompat.START);
        }
        else{
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setTitle("Exit");
                alert.setMessage("Are you sure?");
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alert.setCancelable(false);
                alert.show();
            }
    }
}