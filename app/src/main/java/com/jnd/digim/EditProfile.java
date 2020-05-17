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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
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
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Map<String, Object> Users = new HashMap<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_profile,container,false);
        final EditText firstNameEdit = (EditText)view.findViewById(R.id.firstNameEdit);
        final EditText lastNameEdit = (EditText)view.findViewById(R.id.lastNameEdit);
//        NavigationView navigationView = (NavigationView)view.findViewById(R.id.nvView);
//        View headerView = navigationView.getHeaderView(0);
//        final TextView username = (TextView)headerView.findViewById(R.id.username);
//        final EditText emailEdit = (EditText)view.findViewById(R.id.emailEdit);
        Button submitProfile = (Button)view.findViewById(R.id.submitProfile);

        db.collection("Users").document(FirebaseAuth.getInstance().getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if ( task.isSuccessful() ) {
                    try {
                        JSONObject json = new JSONObject(task.getResult().getData());
                        firstNameEdit.setText(json.getString("firstname"));
                        lastNameEdit.setText(json.getString("lastname"));
//                        emailEdit.setText(json.getString("email"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    Toast.makeText(getContext(), "No document", Toast.LENGTH_SHORT).show();
                }
            }
        });
        submitProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
//                    Users.put("firstname",emailEdit.getText().toString());
                        Users.put("lastname",lastNameEdit.getText().toString());
                        db.collection("Users").document(FirebaseAuth.getInstance().getUid())
                                .set(Users, SetOptions.merge());
                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(firstNameEdit.getText().toString() + " " + lastNameEdit.getText().toString()).build();
                        FirebaseAuth.getInstance().getCurrentUser().updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getContext(), "Profile updated successfully", Toast.LENGTH_SHORT).show();
                                    Intent dashboard = new Intent(getActivity(),DashboardActivity.class);
                                    startActivity(dashboard);
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