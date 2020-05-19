package com.jnd.digim;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EditProfile extends Fragment {
    private Toolbar toolbar;
    FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Map<String, Object> Users = new HashMap<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Initializing the view
        View view = inflater.inflate(R.layout.edit_profile,container,false);

        //EditText value getting
        final EditText firstNameEdit = (EditText)view.findViewById(R.id.firstNameEdit);
        final EditText lastNameEdit = (EditText)view.findViewById(R.id.lastNameEdit);

        //Set the title for "Edit Profile"
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Edit Profile");

        //Hiding the Bottom Navigation view
        BottomNavigationView bottomNavigationView = (BottomNavigationView)getActivity().findViewById(R.id.bottomNav);
        bottomNavigationView.setVisibility(View.GONE);

        //For progressbar for loading the data from server
        final ProgressBar progressBarDashboard = (ProgressBar)((AppCompatActivity)getActivity()).findViewById(R.id.progressBarDashboard);
        progressBarDashboard.setVisibility(View.VISIBLE);


        //Removing the image
        ImageButton removePic = (ImageButton)view.findViewById(R.id.removePic);

        //Checking that user is logged in or not
        if(mAuth.getInstance().getCurrentUser().getPhotoUrl()==null){
            removePic.setVisibility(View.GONE);
        }

        //Submitting the change - button
        Button submitProfile = (Button)view.findViewById(R.id.submitProfile);

        //Profile Picture image removing
        ImageView profilePicture = (ImageView)view.findViewById(R.id.profilePicture);
        profilePicture.setImageURI(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl());

        removePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBarDashboard.setVisibility(View.VISIBLE);
                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setPhotoUri(null).build();
                FirebaseAuth.getInstance().getCurrentUser().updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            progressBarDashboard.setVisibility(View.GONE);
                            Intent intent = new Intent(getActivity().getApplicationContext(),DashboardActivity.class);
                            startActivity(intent);
                            getActivity().finish();
                        }
                    }
                });
            }
        });

        //Checking network connection
        ConnectivityManager conn_Manager = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = conn_Manager.getActiveNetworkInfo();
        if( activeNetwork != null && activeNetwork.isConnected() ) {
            db.collection("Users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        try {

                            //Pasrsing the Firestore server data of user with JSONObject

                            JSONObject json = new JSONObject(task.getResult().getData().toString());
                            firstNameEdit.setText(json.getString("firstname"));
                            lastNameEdit.setText(json.getString("lastname"));
                            progressBarDashboard.setVisibility(View.GONE);
                            //emailEdit.setText(json.getString("email"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    else
                    {
                        Toast.makeText(getContext(), "No document", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        else Toast.makeText(getContext(), "Network error", Toast.LENGTH_SHORT).show();

        submitProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBarDashboard.setVisibility(View.VISIBLE);

                //Checking network connection
                InputMethodManager im = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                im.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),0);
                ConnectivityManager conn_Manager = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetwork =   conn_Manager.getActiveNetworkInfo();

                if( activeNetwork != null && activeNetwork.isConnected() ) {
                    if( firstNameEdit.length() == 0 || lastNameEdit.length() == 0 ) {
                        Snackbar.make(v, "All fields are required",Snackbar.LENGTH_LONG).show();
                    }

                    else{
                        Users.put("firstname",firstNameEdit.getText().toString());
//                      Users.put("email",emailEdit.getText().toString());
                        Users.put("lastname",lastNameEdit.getText().toString());

                        //Changing firstname and lastname to server of FireStore
                        db.collection("Users").document(FirebaseAuth.getInstance().getUid())
                                .set(Users, SetOptions.merge());
                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(firstNameEdit.getText().toString() + " " + lastNameEdit.getText().toString()).build();
                        FirebaseAuth.getInstance().getCurrentUser().updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    progressBarDashboard.setVisibility(View.GONE);
                                    Toast.makeText(getContext(), "Profile updated successfully", Toast.LENGTH_SHORT).show();
                                    Intent dashboard = new Intent(getActivity(),DashboardActivity.class);
                                    startActivity(dashboard);
                                    getActivity().finish();
                                }
                            }
                        });
                    }
                }
                else {
                    Toast.makeText(getContext(), "Network error.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }
}