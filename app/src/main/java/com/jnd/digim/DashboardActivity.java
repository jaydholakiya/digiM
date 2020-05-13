package com.jnd.digim;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class DashboardActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        bottomNavigationView = findViewById(R.id.bottomNav);
        if(FirebaseAuth.getInstance().getCurrentUser()==null){
            ((NavigationView)findViewById(R.id.nvView)).getMenu().findItem(R.id.homeMain).setVisible(false);
        }
        else{
            findViewById(R.id.login).setVisibility(View.INVISIBLE);
        }
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("My Dashboard");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDrawer = (DrawerLayout)findViewById(R.id.navDrawer);
        nvDrawer = (NavigationView)findViewById(R.id.nvView);
        setupDrawerContent(nvDrawer);
        NavigationView navigationView = (NavigationView)findViewById(R.id.nvView);
        View headerView = navigationView.getHeaderView(0);
        TextView username = (TextView)headerView.findViewById(R.id.username);
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
                if(item.getItemId() == R.id.logOut){
                        FirebaseAuth.getInstance().signOut();
                        if( FirebaseAuth.getInstance().getCurrentUser() == null ) {
                            Intent loginIntent = new Intent(DashboardActivity.this, HomeActivity.class);
                            startActivity(loginIntent);
                            finish();
                        }
                        else Toast.makeText(getApplicationContext(), "Log Out error", Toast.LENGTH_SHORT).show();
                }
                else if(item.getItemId() == R.id.homeMain){
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
            case R.id.changePass:
                fragmentClass = ChangePassword.class;
                bottomNavigationView.setVisibility(View.INVISIBLE);
                break;
            case R.id.rateUs:
                fragmentClass = RateUs.class;
                bottomNavigationView.setVisibility(View.INVISIBLE);
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