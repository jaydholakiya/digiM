package com.jnd.digim;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

import static android.content.Context.NOTIFICATION_SERVICE;

public class RateUs extends Fragment {
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private DatabaseReference mDatabase;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.rate_us,container,false);

        BottomNavigationView bottomNavigationView = (BottomNavigationView)getActivity().findViewById(R.id.bottomNav);
        bottomNavigationView.setVisibility(View.GONE);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Rate Us");

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Complains").child(FirebaseAuth.getInstance().getCurrentUser().getDisplayName()+"_"+FirebaseAuth.getInstance().getCurrentUser().getEmail().substring(0,FirebaseAuth.getInstance().getCurrentUser().getEmail().indexOf("."))+"_"+FirebaseAuth.getInstance().getCurrentUser().getUid());
        mDatabase.keepSynced(true);
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerRate);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        FloatingActionButton rate = (FloatingActionButton)view.findViewById(R.id.rateFloatBtn);
        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext(),R.style.BottomSheet);
                final View bottomSheet = LayoutInflater.from(getActivity()).inflate(R.layout.bottom_sheet_rating,(ConstraintLayout)v.findViewById(R.id.bottomDialogRate));

                bottomSheet.findViewById(R.id.rateUsSubmit).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final EditText complaint = (EditText)bottomSheet.findViewById(R.id.complaint);
                        final RatingBar rating = (RatingBar)bottomSheet.findViewById(R.id.ratingBar);
                        final Float[] star = new Float[1];
                        ConnectivityManager conn_manager = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                        NetworkInfo activeNetwork = conn_manager.getActiveNetworkInfo();
                        if( activeNetwork!=null && activeNetwork.isConnected() ){
                            if( complaint.getText().toString().length() == 0 ) {
                                Toast.makeText(getContext(), "Please write feedback", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                final FirebaseDatabase db = FirebaseDatabase.getInstance();
                                final DatabaseReference[] databaseReference = new DatabaseReference[1];
                                String complaineeName = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
                                String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                                String complain = complaint.getText().toString();
                                Long complainTime = System.currentTimeMillis();
                                UUID complainid = UUID.randomUUID();
                                String complainId = Long.toString(complainid.getMostSignificantBits(),36) + Long.toString(complainid.getLeastSignificantBits(),36).replace("-","");
                                Float starRate = rating.getRating();
                                String starRating = starRate + " out of 5";
                                if(starRating.equals("0.0 out of 5")){
                                    Toast.makeText(getContext(), "Please rate us out of 5", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    databaseReference[0] = db.getReference("Complains").child(complaineeName+"_"+email.substring(0,email.indexOf("."))+"_"+FirebaseAuth.getInstance().getCurrentUser().getUid());
                                    Rating ratings = new Rating(complaineeName,email,complain,complainTime,complainId,starRating);
                                    databaseReference[0].child(databaseReference[0].push().getKey()).setValue(ratings);
                                    complaint.setText("");
                                    if(Build.VERSION.SDK_INT < Build.VERSION_CODES.O){
                                        Toast.makeText(getContext(), "You rated us with " + starRating, Toast.LENGTH_SHORT).show();
                                    }
                                    if(bottomSheetDialog.isShowing()) {
                                        bottomSheetDialog.dismiss();
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                            NotificationManager notificationManager = (NotificationManager)getActivity().getSystemService(NOTIFICATION_SERVICE);
                                            String id = "id_product";
                                            // The user-visible name of the channel.
                                            CharSequence name = "Feedbacks";
                                            // The user-visible description of the channel.
                                            String description = "For feedback submitting";
                                            NotificationChannel mChannel = new NotificationChannel(id, name, NotificationManager.IMPORTANCE_HIGH);
                                            // Configure the notification channel.
                                            mChannel.setDescription(description);
                                            mChannel.enableLights(true);
                                            // Sets the notification light color for notifications posted to this
                                            // channel, if the device supports this feature.
                                            mChannel.setLightColor(Color.RED);
                                            notificationManager.createNotificationChannel(mChannel);
                                            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getContext(),"id_product")
                                                    .setSmallIcon(R.drawable.ic_notification_digim) //your app icon
                                                    .setChannelId(id)
                                                    .setContentTitle("Promotion order")
                                                    .setAutoCancel(true)
                                                    .setNumber(2)
                                                    .setColor(255)
                                                    .setContentText("Your rated us with \"" + starRating + "\" with \"" + complain + "\" feedback")
                                                    .setWhen(System.currentTimeMillis())
                                                    .setDefaults(NotificationCompat.DEFAULT_ALL);
                                            notificationManager.notify((int) System.currentTimeMillis(), notificationBuilder.build());
                                        }
                                        else{
                                            NotificationManager notificationManager = (NotificationManager)getActivity().getSystemService(NOTIFICATION_SERVICE);
                                            Notification notification = new Notification.Builder(getActivity()).setContentTitle("Idea submission")
                                                    .setContentText("Your rated us with \"" + starRating + "\" with \"" + complain + "\" feedback")
                                                    .setSmallIcon(R.drawable.instagram_icon)
                                                    .setAutoCancel(true)
                                                    .build();
                                            notificationManager.notify((int) System.currentTimeMillis(),notification);
                                        }
                                    }
                                }
                            }
                        }
                        else Toast.makeText(getContext(), "Network error", Toast.LENGTH_SHORT).show();
                    }
                });
                bottomSheetDialog.setContentView(bottomSheet);
                bottomSheetDialog.show();
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<RatingGet,RatingViewHolder>FirebaserecyclerAdapter = new FirebaseRecyclerAdapter<RatingGet, RatingViewHolder>
                (RatingGet.class,R.layout.recycler_rating,RatingViewHolder.class,mDatabase) {
            @Override
            protected void populateViewHolder(RatingViewHolder ratingViewHolder, RatingGet ratingGet, int i) {
                ratingViewHolder.setComplain(ratingGet.getComplain());
                ratingViewHolder.setComplainTime(ratingGet.getComplainTime());
                ratingViewHolder.setStarRating(ratingGet.getStarRating());
            }
        };
        recyclerView.setAdapter(FirebaserecyclerAdapter);
    }

    public static class RatingViewHolder extends RecyclerView.ViewHolder
    {
        View mView;
        public RatingViewHolder(View complainView)
        {
            super(complainView);
            mView = complainView;
        }
        public void setComplain(String complain)
        {
            TextView complains = (TextView)mView.findViewById(R.id.complains);
            complains.setText(complain);
        }
        public void setStarRating(String starRating)
        {
            TextView stars = (TextView)mView.findViewById(R.id.givenStars);
            stars.setText(starRating);
        }
        public void setComplainTime(long complainTime)
        {
            TextView rateTime = (TextView)mView.findViewById(R.id.timeStampRate);
            rateTime.setText(TimeAgo.from(complainTime));
        }
    }
}