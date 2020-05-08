package com.jnd.digim;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DashboardActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        bottomNavigationView = findViewById(R.id.bottomNav);

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
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.dashboardFrame,fragment).commit();
                return true;
            }
        });
    }
}