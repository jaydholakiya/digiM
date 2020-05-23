package com.jnd.digim;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

public class HomeActivity extends AppCompatActivity implements YouTubePlayer.OnInitializedListener {
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

        //Checking network connection for video
        ConnectivityManager conn_Manager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = conn_Manager.getActiveNetworkInfo();

        if( activeNetwork != null && activeNetwork.isConnected() ) {}
        else Toast.makeText(this, "Network error", Toast.LENGTH_SHORT).show();

        //Setting the toolbar as Action bar
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Setting the title for Actionbar of Home Activity
        setTitle("digiM");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Navigation icon of Actionbar
        toolbar.setNavigationIcon(R.drawable.ic_menu);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawer.openDrawer(GravityCompat.START);
            }
        });

        mDrawer = (DrawerLayout)findViewById(R.id.navDrawer);
        nvDrawer = (NavigationView)findViewById(R.id.nvView);

        setupDrawerContent(nvDrawer);

        //On brand logo click, view the branding scheme
        NavigationView navigationView = (NavigationView)findViewById(R.id.nvView);
        View headerView = navigationView.getHeaderView(0);
        TextView digiMLogoDashboard = (TextView)headerView.findViewById(R.id.digiMHeaderHome);
        digiMLogoDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //For navigating to the Main Activity(Login Activity)
        TextView login = (TextView)findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(HomeActivity.this,SigninActivity.class);
                startActivity(loginIntent);
            }
        });

        //Carousel View created
        carouselView = findViewById(R.id.carouselView);
        carouselView.setPageCount(images.length);
        carouselView.setImageListener(imageListener);
        openSnackbar(carouselView);

        //Setting animations for Activity
        getWindow().setWindowAnimations(R.style.WindowAnimationTransition);

        //YouTube video initializing
        YouTubePlayerFragment youTubePlayerFragment =
                (YouTubePlayerFragment) getFragmentManager().findFragmentById(R.id.youTube);
        youTubePlayerFragment.initialize(YouTubeConfig.getApiKey(),this);
    }

    //Sign-in suggetion Snackbar opening
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
        else findViewById(R.id.login).setVisibility(View.GONE);
    }

    //Images for Carousel
    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(images[position]);
        }
    };

    public void setupDrawerContent(NavigationView navigationView){
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.myDashboard){
                    mDrawer.closeDrawer(GravityCompat.START);
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
        setTitle(item.getTitle());          //Setting the title of the Action bar
        mDrawer.closeDrawers();
    }

    @Override
    public void onBackPressed() {
        if(this.mDrawer.isDrawerOpen(GravityCompat.START)) {
            this.mDrawer.closeDrawer(GravityCompat.START);
        }

        //Custom logic for navigating to back in HomeActivity
        else if(getTitle().equals("Working") || getTitle().equals("About Us") || getTitle().equals("Contact Us") || getTitle().equals("Developer Info") )
        {
            Intent i = new Intent(HomeActivity.this, HomeActivity.class);
            startActivity(i);
            finish();
        }

        else
        {
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

    //Youtube video link initializing
    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
            youTubePlayer.loadVideo("0trAMd1GYVc");
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        Toast.makeText(this, "Video loading failed", Toast.LENGTH_SHORT).show();
    }
}