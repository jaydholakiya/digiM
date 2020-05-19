package com.jnd.digim;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePassword extends Fragment {
    //For getting the current user that it is logged in or not

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {

        //Initialized the view

        View view = inflater.inflate(R.layout.change_password,container,false);

        //Progressbar getting
        final ProgressBar progressBarDashboard = (ProgressBar)((AppCompatActivity)getActivity()).findViewById(R.id.progressBarDashboard);

        //BottomNavigationView created for hiding
        BottomNavigationView bottomNavigationView = (BottomNavigationView)getActivity().findViewById(R.id.bottomNav);
        bottomNavigationView.setVisibility(View.GONE);

        //Password getting
        final EditText pass = (EditText) view.findViewById(R.id.pass);

        //On initializing the view, set title "Change Password" to the action bar for horizontal/landscape view
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Change Password");

        //Change password button
        Button change = (Button)view.findViewById(R.id.change);

        //Password change functionality
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager conn_Manager = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetwork =   conn_Manager.getActiveNetworkInfo();
                if( activeNetwork != null && activeNetwork.isConnected() ) {
                    if( pass.getText().toString().length() >= 6 ){
                        progressBarDashboard.setVisibility(View.VISIBLE);
                        user.updatePassword(pass.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    //Hiding progressbar after success - changing password
                                    progressBarDashboard.setVisibility(View.GONE);

                                    Toast.makeText(getContext(), "Password changed succesfully.", Toast.LENGTH_SHORT).show();

                                    //Signing out user for security reasons
                                    FirebaseAuth.getInstance().signOut();

                                    //Navigating to Home Actvity
                                    Intent homeIntent = new Intent(getContext(),HomeActivity.class);
                                    startActivity(homeIntent);
                                    getActivity().finish();

                                    Toast.makeText(getContext(), "Please login first", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    //Hiding progressbar after error - changing password

                                    progressBarDashboard.setVisibility(View.GONE);
                                    Toast.makeText(getContext(), "Password change error", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                    else{
                        Snackbar.make(v,"Password should be atleast six characters long", BaseTransientBottomBar.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(getContext(), "Network error", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }
}
