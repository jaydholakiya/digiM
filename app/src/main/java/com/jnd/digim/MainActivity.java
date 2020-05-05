package com.jnd.digim;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setWindowAnimations(R.style.WindowAnimationTransition);
        RelativeLayout myLayout = findViewById(R.id.loginLayout);
        myLayout.clearFocus();
        TextView digiM = findViewById(R.id.digimLogoLogin);
        EditText emailLogin = findViewById(R.id.emailLogin);
        EditText passwordLogin = findViewById(R.id.passwordLogin);
        Button loginBtn = findViewById(R.id.loginBtn);
        TextView noAccount = findViewById(R.id.noAccount);
        Animation myAnimation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.myanimation);
        Animation fade = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade);
        digiM.startAnimation(myAnimation);
        emailLogin.startAnimation(fade);
        passwordLogin.startAnimation(fade);
        loginBtn.startAnimation(fade);
        noAccount.startAnimation(fade);
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

    public void signIn(View view) {
        EditText emailLogin = findViewById(R.id.emailLogin);
        EditText passwordLogin = findViewById(R.id.passwordLogin);
        String email = emailLogin.getText().toString();
        String password = passwordLogin.getText().toString();
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if ( task.isSuccessful() ) {
                    Toast.makeText(MainActivity.this, mAuth.getCurrentUser().getDisplayName() + " signed in", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MainActivity.this, "Authentication error", Toast.LENGTH_SHORT).show();
                }
            }
        });
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