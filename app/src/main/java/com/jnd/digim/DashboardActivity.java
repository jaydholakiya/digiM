package com.jnd.digim;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.theartofdev.edmodo.cropper.CropImage;

public class DashboardActivity extends AppCompatActivity {

    FirebaseAuth mAuth;

    //Bottom navigation view created
    BottomNavigationView bottomNavigationView;

    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        bottomNavigationView = findViewById(R.id.bottomNav);

        //Setting animation for starting and ending the activity
        getWindow().setWindowAnimations(R.style.WindowAnimationTransition);

        //If user is logged out, then Disable Home menu in navigation drawer
        if( mAuth.getInstance().getCurrentUser() == null ){
            ((NavigationView)findViewById(R.id.nvView)).getMenu().findItem(R.id.homeMain).setVisible(false);
        }

        //If user is logged in, then Disable login in Action bar of Dashboard Activity
        else {
            findViewById(R.id.login).setVisibility(View.GONE);
        }

        //Setting the toolbar
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawer.openDrawer(GravityCompat.START);
            }
        });

        setTitle("My Dashboard");

        mDrawer = (DrawerLayout)findViewById(R.id.navDrawer);
        nvDrawer = (NavigationView)findViewById(R.id.nvView);
        setupDrawerContent(nvDrawer);
        NavigationView navigationView = (NavigationView)findViewById(R.id.nvView);
        View headerView = navigationView.getHeaderView(0);
        TextView username = (TextView)headerView.findViewById(R.id.username);

        //Setting profilePicture in header of Navigation drawer of dashboard

        final ImageView profilePic = (ImageView)headerView.findViewById(R.id.profilePic);

        Glide.with(this)
                .load(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl())
                .into(profilePic);

        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawer.closeDrawer(GravityCompat.START);
                CropImage.activity().start(DashboardActivity.this);
            }
        });

        //Brand logo opening
        TextView digiMLogoDashboard = (TextView)headerView.findViewById(R.id.digiMHeaderDashboard);
        digiMLogoDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this,SplashActivity.class);
                startActivity(intent);
                finish();
            }
        });

        username.setText("Welcome, " + FirebaseAuth.getInstance().getCurrentUser().getDisplayName());

        //On load activity, set the base activity to Idea fragment
        if( savedInstanceState == null ) {
            getSupportFragmentManager().beginTransaction().replace(R.id.dashboardFrame,new IdeaFragment()).commit();
        }

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()) {
                    case R.id.ideaMenu:
                        fragment = new IdeaFragment();
                        break;
                    case R.id.promoteMenu:
                        fragment = new PromoteFragment();
                        break;
                    case R.id.ordersMenu:
                        fragment = new OrdersFragment();
                        break;
                    case R.id.educationsMenu:
                        fragment = new EducationFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.dashboardFrame,fragment).commit();
                return true;
            }
        });
    }

    //CropImage picking image from camera or gallery
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if(resultCode==RESULT_OK){
                final ProgressBar progressBarDashboard = (ProgressBar)findViewById(R.id.progressBarDashboard);
                progressBarDashboard.setVisibility(View.VISIBLE);
                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setPhotoUri(Uri.parse(String.valueOf(result.getUri()))).build();
                FirebaseAuth.getInstance().getCurrentUser().updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            progressBarDashboard.setVisibility(View.GONE);
                            Intent intent = new Intent(DashboardActivity.this,DashboardActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
            }
            else if(requestCode==CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                Exception e = result.getError();
                Toast.makeText(this, "" + e, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void setupDrawerContent(NavigationView navigationView){
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.logOut){
                    mDrawer.closeDrawer(GravityCompat.START);
                    ProgressBar progressBarDashboard = (ProgressBar)findViewById(R.id.progressBarDashboard);
                    progressBarDashboard.setVisibility(View.VISIBLE);
                    FirebaseAuth.getInstance().signOut();
                    if( FirebaseAuth.getInstance().getCurrentUser() == null ) {
                        progressBarDashboard.setVisibility(View.GONE);
                        Intent homeIntent = new Intent(DashboardActivity.this, HomeActivity.class);

                        //For clearing the tasks when multiple "Logout" occurs
                        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(homeIntent);
                        finish();
                    }
                    else Toast.makeText(getApplicationContext(), "Log Out error", Toast.LENGTH_SHORT).show();
                }
                else if(item.getItemId() == R.id.homeMain){
                    mDrawer.closeDrawer(GravityCompat.START);
                    Intent intent = new Intent(DashboardActivity.this,HomeActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
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
            case R.id.editProfile:
                fragmentClass = EditProfile.class;
                break;
            case R.id.changePass:
                fragmentClass = ChangePassword.class;
                break;
            case R.id.rateUs:
                fragmentClass = RateUs.class;
                break;
        }

        try {
            fragment = (Fragment)fragmentClass.newInstance();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.dashboardFrame,fragment).commit();
        item.setChecked(true);
        setTitle(item.getTitle());                  //Setting title of header menu to action bar
        mDrawer.closeDrawers();
    }

    @Override
    public void onBackPressed() {
        if(this.mDrawer.isDrawerOpen(GravityCompat.START)) {
            this.mDrawer.closeDrawer(GravityCompat.START);
        }
        else{
            //For navigating back through the profiles
            if( getTitle().equals("Edit Profile") || getTitle().equals("Change Password") || getTitle().equals("Rate Us") || getSupportActionBar().getTitle().equals("Edit Profile") || getSupportActionBar().getTitle().equals("Change Password") || getSupportActionBar().getTitle().equals("Rate Us") ){
                Intent dashboardIntent = new Intent(DashboardActivity.this,DashboardActivity.class);
                startActivity(dashboardIntent);
                finish();
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
}