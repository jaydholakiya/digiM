package com.jnd.digim;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class signupActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    Map<String, Object> Users = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mAuth = FirebaseAuth.getInstance();
        getWindow().setWindowAnimations(R.style.WindowAnimationTransition);
        RelativeLayout myLayout = findViewById(R.id.loginLayout);
        TextView digiM = findViewById(R.id.digimLogoSignup);
        EditText firstName = findViewById(R.id.firstName);
        EditText lastName = findViewById(R.id.lastName);
        EditText emailSignup = findViewById(R.id.emailSignup);
        EditText passwordSignup = findViewById(R.id.passwordSignup);
        Button signUpBtn = findViewById(R.id.signUpBtn);
        TextView alreadyAccount = findViewById(R.id.alreadyAccount);
        myLayout.clearFocus();
        Animation myAnimation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.myanimation);
        Animation fade = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade);
        digiM.startAnimation(myAnimation);
        firstName.startAnimation(fade);
        lastName.startAnimation(fade);
        emailSignup.startAnimation(fade);
        passwordSignup.startAnimation(fade);
        signUpBtn.startAnimation(fade);
        alreadyAccount.startAnimation(fade);
    }

    public void storeData() {
        final EditText firstName = findViewById(R.id.firstName);
        final EditText lastName = findViewById(R.id.lastName);
        final EditText emailSignup = findViewById(R.id.emailSignup);
        final EditText passwordSignup = findViewById(R.id.passwordSignup);
        String fname = firstName.getText().toString().trim();
        String lname = lastName.getText().toString().trim();
        String mail = emailSignup.getText().toString().trim();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Users.put("email",mail);
        Users.put("firstname",fname);
        Users.put("lastname",lname);
        db.collection("Users").add(Users).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                firstName.setText("");
                lastName.setText("");
                emailSignup.setText("");
                passwordSignup.setText("");
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(signupActivity.this, "Error adding document - " + e, Toast.LENGTH_SHORT).show();
                    }
                });

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(fname + " " + lname).build();
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

    public void signUp(View coordinatorLayout) {
        InputMethodManager im = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        im.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(),0);
        EditText firstName = findViewById(R.id.firstName);
        EditText lastName = findViewById(R.id.lastName);
        EditText emailSignup = findViewById(R.id.emailSignup);
        EditText passwordSignup = findViewById(R.id.passwordSignup);
        String fname = firstName.getText().toString().trim();
        String lname = lastName.getText().toString().trim();
        String email = emailSignup.getText().toString().trim();
        String password = passwordSignup.getText().toString().trim();
        ConnectivityManager conn_Manager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork =   conn_Manager.getActiveNetworkInfo();
        if( activeNetwork != null && activeNetwork.isConnected() ) {
            if( firstName.length() == 0 || lastName.length() == 0 || emailSignup.length() == 0 || passwordSignup.length() == 0 ) {
                Snackbar.make(coordinatorLayout, "All fields are required",Snackbar.LENGTH_LONG).show();
            }
            else if ( fname.length() >= 3 && lname.length() >= 3 && ( email.length() > 1 || email.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+") ) && password.length() >= 6 ) {
                mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if ( task.isSuccessful() ) {
                            storeData();
                            Toast.makeText(signupActivity.this, "Sign up with : " + mAuth.getCurrentUser().getEmail(), Toast.LENGTH_SHORT).show();
                            View view = null;
                            openLogin(view);
                        }
                        else {
                            Toast.makeText(signupActivity.this, "Sign up error.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
            else {
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setTitle("SignUp Criteria");
                alert.setMessage("All fields are required,\nFirst name and Last name length should be atleast 3 characters,\nPassword length should be atleast 3 characters");
                alert.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alert.setCancelable(false);
                alert.show();
            }
        }
        else {
            Toast.makeText(this, "Network error", Toast.LENGTH_SHORT).show();
        }
    }
}