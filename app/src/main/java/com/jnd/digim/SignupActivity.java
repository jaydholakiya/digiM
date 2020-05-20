package com.jnd.digim;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Button signUpBtn;
    Map<String, Object> Users = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mAuth = FirebaseAuth.getInstance();

        //Setting animations for the Activity

        getWindow().setWindowAnimations(R.style.WindowAnimationTransition);

        RelativeLayout myLayout = findViewById(R.id.loginLayout);
        TextView digiM = findViewById(R.id.digimLogoSignup);
        EditText firstName = findViewById(R.id.firstName);
        EditText lastName = findViewById(R.id.lastName);
        EditText emailSignup = findViewById(R.id.emailSignup);
        EditText passwordSignup = findViewById(R.id.passwordSignup);
        signUpBtn = findViewById(R.id.signUpBtn);
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
        alreadyAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login = new Intent(SignupActivity.this,SigninActivity.class);
                startActivity(login);
                finish();
            }
        });
    }

    //Storing data to Firebase
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
        db.collection("Users").document(mAuth.getCurrentUser().getUid()).set(Users).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                firstName.setText("");
                lastName.setText("");
                emailSignup.setText("");
                passwordSignup.setText("");
            }
        });

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(fname + " " + lname).setPhotoUri(Uri.parse("https://firebasestorage.googleapis.com/v0/b/digim-9795e.appspot.com/o/digiM.png?alt=media&token=b8237f69-ec73-4956-83c0-66ab56527a25")).build();
        mAuth.getCurrentUser().updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if( task.isSuccessful() ) {}
        }
    });
}

    //For SignUp data
    public void signUp(View coordinatorLayout) {
        final Button signUpBtn = (Button)findViewById(R.id.signUpBtn);
        final ProgressBar progressBar = (ProgressBar)findViewById(R.id.progressBarSignUp);
        InputMethodManager im = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        im.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(),0);
        EditText firstName = findViewById(R.id.firstName);
        EditText lastName = findViewById(R.id.lastName);
        EditText emailSignup = findViewById(R.id.emailSignup);
        EditText passwordSignup = findViewById(R.id.passwordSignup);
        String fname = firstName.getText().toString();
        String lname = lastName.getText().toString();
        final String email = emailSignup.getText().toString();
        final String password = passwordSignup.getText().toString();

        ConnectivityManager conn_Manager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork =   conn_Manager.getActiveNetworkInfo();

        if( activeNetwork != null && activeNetwork.isConnected() ) {

            if( firstName.length() == 0 || lastName.length() == 0 || emailSignup.length() == 0 || passwordSignup.length() == 0 ) {
                Snackbar.make(coordinatorLayout, "All fields are required",Snackbar.LENGTH_LONG).show();
            }

            else if ( fname.length() >= 3 && lname.length() >= 3 && ( email.length() > 1 || email.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+") ) && password.length() >= 6 ) {
                signUpBtn.setEnabled(false);
                progressBar.setVisibility(View.VISIBLE);
                mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if ( task.isSuccessful() ) {
                            storeData();
                            signUpBtn.setEnabled(true);
                            Toast.makeText(SignupActivity.this, "Sign up with : " + mAuth.getCurrentUser().getEmail(), Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(SignupActivity.this, SigninActivity.class);
                            i.putExtra("Email",email);
                            progressBar.setVisibility(View.GONE);
                            startActivity(i);
                            finish();
                        }
                        else {
                            signUpBtn.setEnabled(true);
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(SignupActivity.this, "Sign up error.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
            else {
                progressBar.setVisibility(View.GONE);
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