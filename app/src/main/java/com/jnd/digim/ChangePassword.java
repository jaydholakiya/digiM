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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePassword extends Fragment {

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.change_password,container,false);

        BottomNavigationView bottomNavigationView = (BottomNavigationView)getActivity().findViewById(R.id.bottomNav);
        bottomNavigationView.setVisibility(View.GONE);
        final EditText pass = (EditText) view.findViewById(R.id.pass);
        Button change = (Button)view.findViewById(R.id.change);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Change Password");
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager conn_Manager = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetwork =   conn_Manager.getActiveNetworkInfo();
                if( activeNetwork != null && activeNetwork.isConnected() ) {
                    if(pass.getText().toString().length()>=6){
                        user.updatePassword(pass.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(getContext(), "Password changed succesfully.", Toast.LENGTH_SHORT).show();
                                    FirebaseAuth.getInstance().signOut();
                                    Intent homeIntent = new Intent(getContext(),HomeActivity.class);
                                    startActivity(homeIntent);
                                    Toast.makeText(getContext(), "Please login first", Toast.LENGTH_SHORT).show();
                                }
                                else{
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
