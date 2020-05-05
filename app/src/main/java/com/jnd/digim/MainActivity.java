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
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setWindowAnimations(R.style.WindowAnimationTransition);
        TextView digiM = findViewById(R.id.digimLogoLogin);
        EditText emailLogin = findViewById(R.id.emailLogin);
        EditText passwordLogin = findViewById(R.id.passwordLogin);
        Button loginBtn = findViewById(R.id.loginBtn);
        TextView noAccount = findViewById(R.id.noAccount);
        RelativeLayout myLayout = findViewById(R.id.loginLayout);
        myLayout.clearFocus();
        Animation myAnimation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.myanimation);
        Animation fade = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade);
        digiM.startAnimation(myAnimation);
        emailLogin.startAnimation(fade);
        passwordLogin.startAnimation(fade);
        loginBtn.startAnimation(fade);
        noAccount.startAnimation(fade);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if( bundle != null ) {
            String string = (String)bundle.get("Email");
            emailLogin.setText(string);
        }
        if( !emailLogin.getText().toString().isEmpty() ) {
            Toast.makeText(this, "Please sign in with your new account", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
        //Pending new layout for sign in
    }

    public void openRegister(View view) {
        Intent signup = new Intent(this,signupActivity.class);
        startActivity(signup);
    }

    public void onBackPressed(){
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

    public void signIn(View coordinatorLayout) {
        InputMethodManager im = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        im.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(),0);
        EditText emailLogin = findViewById(R.id.emailLogin);
        EditText passwordLogin = findViewById(R.id.passwordLogin);
        String email = emailLogin.getText().toString();
        String password = passwordLogin.getText().toString();
        ConnectivityManager conn_Manager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork =   conn_Manager.getActiveNetworkInfo();
        if( activeNetwork != null && activeNetwork.isConnected() ) {
            if ( email.length() == 0 || password.length() == 0 ) {
                Snackbar.make(coordinatorLayout, "All fields are required",Snackbar.LENGTH_LONG).show();
            }
            else {
                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if ( task.isSuccessful() ) {
                            Toast.makeText(MainActivity.this, mAuth.getCurrentUser().getDisplayName() + " signed in", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(MainActivity.this, "Authentication error...\nIt seems like wrong email or password", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
        else if ( email.length() == 0 || password.length() == 0 ) {
            Snackbar.make(coordinatorLayout, "All fields are required",Snackbar.LENGTH_LONG).show();
        }
        else {
            Snackbar.make(coordinatorLayout,"Network error",Snackbar.LENGTH_LONG).show();
        }
    }

    public void signOut() {
        FirebaseAuth.getInstance().signOut();
    }
//    public void getIdeas() {
//        FirebaseDatabase db = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = db.getReference("Ideas");
//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                Map<String,Object> map = (Map<String, Object>) dataSnapshot.getValue();
////                String value = dataSnapshot.getValue(String.class);
//                Toast.makeText(MainActivity.this, "Value is : \n" + map, Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Toast.makeText(MainActivity.this, "Failed to read value : " + databaseError.toException(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
}