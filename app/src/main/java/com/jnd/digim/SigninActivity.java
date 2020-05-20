package com.jnd.digim;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
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
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SigninActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private ProgressBar progressBarSignin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Activity animations

        getWindow().setWindowAnimations(R.style.WindowAnimationTransition);
        TextView digiM = findViewById(R.id.digimLogoLogin);
        EditText emailLogin = findViewById(R.id.emailLogin);
        EditText passwordLogin = findViewById(R.id.passwordLogin);
        Button loginBtn = findViewById(R.id.loginBtn);
        TextView noAccount = findViewById(R.id.noAccount);
        TextView forgotPass = findViewById(R.id.forgotPass);
        RelativeLayout myLayout = findViewById(R.id.loginLayout);
        myLayout.clearFocus();
        Animation myAnimation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.myanimation);
        Animation fade = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade);
        digiM.startAnimation(myAnimation);
        emailLogin.startAnimation(fade);
        passwordLogin.startAnimation(fade);
        loginBtn.startAnimation(fade);
        noAccount.startAnimation(fade);
        forgotPass.startAnimation(fade);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        //Email getting from SignUp activity when SignedUp
        if( bundle != null ) {
            String string = (String)bundle.get("Email");
            emailLogin.setText(string);
        }

        if( !emailLogin.getText().toString().isEmpty() ) {
            Toast.makeText(this, "Please sign in with your new account", Toast.LENGTH_LONG).show();
        }

        noAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signUp = new Intent(SigninActivity.this,SignupActivity.class);
                startActivity(signUp);
                finish();
            }
        });
    }

    //For "Forgot Password" sending mail
    public void forgotPassword(View view) {

        final TextView forgotPass = findViewById(R.id.forgotPass);

        forgotPass.setEnabled(false);

        InputMethodManager im = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        im.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(),0);
        EditText emailForgot = findViewById(R.id.emailLogin);
        String email = emailForgot.getText().toString();
        final String emailReplaced = email.replace(" ","");
        ConnectivityManager conn_Manager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork =   conn_Manager.getActiveNetworkInfo();

        if( activeNetwork != null && activeNetwork.isConnected() ) {
            if ( emailReplaced.length() == 0 ) {
                Toast.makeText(this, emailReplaced, Toast.LENGTH_SHORT).show();
                Toast.makeText(this, "Email is required for forgot password", Toast.LENGTH_SHORT).show();
                forgotPass.setEnabled(true);
            }

            else {
                mAuth.getInstance().sendPasswordResetEmail(emailReplaced).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if ( task.isSuccessful() ) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
                                String id = "id_product";
                                // The user-visible name of the channel.
                                CharSequence name = "Password Reset";
                                // The user-visible description of the channel.
                                String description = "For Password reset";
                                NotificationChannel mChannel = new NotificationChannel(id, name, NotificationManager.IMPORTANCE_HIGH);
                                // Configure the notification channel.
                                mChannel.setDescription(description);
                                mChannel.enableLights(true);
                                // Sets the notification light color for notifications posted to this
                                // channel, if the device supports this feature.
                                mChannel.setLightColor(Color.RED);
                                notificationManager.createNotificationChannel(mChannel);
                                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(SigninActivity.this,"id_product")
                                        .setSmallIcon(R.drawable.ic_notification_digim) //your app icon
                                        .setChannelId(id)
                                        .setContentTitle("Password reset")
                                        .setAutoCancel(true)
                                        .setNumber(2)
                                        .setColor(255)
                                        .setContentText("Check your inbox of '" + emailReplaced + "' for password reset link")
                                        .setWhen(System.currentTimeMillis())
                                        .setDefaults(NotificationCompat.DEFAULT_ALL);
                                notificationManager.notify((int) System.currentTimeMillis(), notificationBuilder.build());
                            }
                            else{
                                NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
                                Notification notification = new Notification.Builder(getApplicationContext()).setContentTitle("Idea submission")
                                        .setContentText("Check your inbox of '" + emailReplaced + "' for password reset link")
                                        .setContentTitle("Password reset")
                                        .setSmallIcon(R.drawable.instagram_icon)
                                        .setAutoCancel(true)
                                        .build();
                                notificationManager.notify((int) System.currentTimeMillis(),notification);
                            }
                            forgotPass.setEnabled(true);
                        }
                        else {

                            //Notifications for password reset with Filtering OREO devices

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
                                String id = "id_product";
                                // The user-visible name of the channel.
                                CharSequence name = "Password reset";
                                // The user-visible description of the channel.
                                String description = "For Password reset";
                                NotificationChannel mChannel = new NotificationChannel(id, name, NotificationManager.IMPORTANCE_HIGH);
                                // Configure the notification channel.
                                mChannel.setDescription(description);
                                mChannel.enableLights(true);
                                // Sets the notification light color for notifications posted to this
                                // channel, if the device supports this feature.
                                mChannel.setLightColor(Color.RED);
                                notificationManager.createNotificationChannel(mChannel);
                                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(SigninActivity.this,"id_product")
                                        .setSmallIcon(R.drawable.ic_notification_digim) //your app icon
                                        .setChannelId(id)
                                        .setContentTitle("Password reset")
                                        .setAutoCancel(true)
                                        .setNumber(2)
                                        .setColor(255)
                                        .setContentText("It seems like the email you have entered is wrong")
                                        .setWhen(System.currentTimeMillis())
                                        .setDefaults(NotificationCompat.DEFAULT_ALL);
                                notificationManager.notify((int) System.currentTimeMillis(), notificationBuilder.build());
                            }
                            else{
                                NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
                                Notification notification = new Notification.Builder(getApplicationContext()).setContentTitle("Idea submission")
                                        .setContentText("It seems like the email you have entered is wrong")
                                        .setSmallIcon(R.drawable.instagram_icon)
                                        .setAutoCancel(true)
                                        .build();
                                notificationManager.notify((int) System.currentTimeMillis(),notification);
                            }
                            forgotPass.setEnabled(true);
                        }
                    }
                });
            }
        }
        else {
            Toast.makeText(this, "Network error", Toast.LENGTH_SHORT).show();
            forgotPass.setEnabled(true);
        }
    }

    //SignIn method
    public void signIn(View coordinatorLayout) {
        final Button loginBtn = (Button)findViewById(R.id.loginBtn);
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
                loginBtn.setEnabled(false);
                progressBarSignin = (ProgressBar)findViewById(R.id.progressBarSignin);
                progressBarSignin.setVisibility(View.VISIBLE);
                FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if ( task.isSuccessful() ) {
                            loginBtn.setEnabled(true);
                            progressBarSignin.setVisibility(View.GONE);
                            Intent dashboardIntent = new Intent(SigninActivity.this,DashboardActivity.class);
                            startActivity(dashboardIntent);
                            finish();
                            Toast.makeText(SigninActivity.this, "Signin succesfully", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            loginBtn.setEnabled(true);
                            progressBarSignin.setVisibility(View.GONE);
                            Toast.makeText(SigninActivity.this, "Authentication error...\nIt seems like wrong email or password", Toast.LENGTH_SHORT).show();
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
}