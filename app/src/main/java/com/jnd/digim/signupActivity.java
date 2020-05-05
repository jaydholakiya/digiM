package com.jnd.digim;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class signupActivity extends AppCompatActivity {
    Map<String, Object> Users = new HashMap<>();
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mAuth = FirebaseAuth.getInstance();
        getWindow().setWindowAnimations(R.style.WindowAnimationTransition);
        RelativeLayout myLayout = findViewById(R.id.loginLayout);
        myLayout.clearFocus();
        TextView digiM = findViewById(R.id.digimLogoSignup);
        EditText firstName = findViewById(R.id.firstName);
        EditText lastName = findViewById(R.id.lastName);
        EditText emailSignup = findViewById(R.id.emailSignup);
        EditText passwordSignup = findViewById(R.id.passwordSignup);
        Button signUpBtn = findViewById(R.id.signUpBtn);
        TextView alreadyAccount = findViewById(R.id.alreadyAccount);
        Animation myAnimation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.myanimation);
        Animation fade = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade);
        digiM.startAnimation(myAnimation);
        firstName.startAnimation(fade);
        lastName.startAnimation(fade);
        emailSignup.startAnimation(fade);
        passwordSignup.startAnimation(fade);
        signUpBtn.startAnimation(fade);
        alreadyAccount.startAnimation(fade);
        final ConnectivityManager conn_Manager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo activeNetwork =   conn_Manager.getActiveNetworkInfo();
        if(activeNetwork != null && activeNetwork.isConnected()) {
            signUpBtn.setEnabled(true);
        }
        else {
            signUpBtn.setEnabled(false);
            Toast.makeText(this, "You are offline!", Toast.LENGTH_SHORT).show();
        }
    }

    public void storeData() {
        final EditText name = findViewById(R.id.firstName);
        final EditText surName = findViewById(R.id.lastName);
        final EditText email = findViewById(R.id.emailSignup);
        final EditText password = findViewById(R.id.passwordSignup);
        String nameString = name.getText().toString();
        String surNameString = surName.getText().toString();
        String emailString = email.getText().toString();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Users.put("email",emailString);
        Users.put("firstname",nameString);
        Users.put("lastname",surNameString);
        db.collection("Users").add(Users).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                name.setText("");
                surName.setText("");
                email.setText("");
                password.setText("");
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(signupActivity.this, "Error adding document - " + e, Toast.LENGTH_SHORT).show();
                    }
                });

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(nameString + " " + surNameString).build();
        mAuth.getCurrentUser().updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if( task.isSuccessful() ) {}
        }
    });
}

    public void openLogin(View view) {
        Intent login = new Intent(signupActivity.this,MainActivity.class);
        startActivity(login);
    }

    public void signUp(final View view) {
        final EditText emailSignup = findViewById(R.id.emailSignup);
        final EditText passwordSignup = findViewById(R.id.passwordSignup);
        String email = emailSignup.getText().toString();
        String password = passwordSignup.getText().toString();
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if ( task.isSuccessful() ) {
                    storeData();
                    Toast.makeText(signupActivity.this, "Sign up with : " + mAuth.getCurrentUser().getEmail(), Toast.LENGTH_SHORT).show();
                    openLogin(view);
                }
                else {
                    Toast.makeText(signupActivity.this, "Sign up error.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}