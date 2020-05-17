package com.jnd.digim;

import android.app.ActionBar;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

import static android.content.Context.NOTIFICATION_SERVICE;

public class IdeaFragment extends Fragment {
    private RecyclerView recyclerView;
    private DatabaseReference mDatabase;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.idea_fragment,container,false);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Ideas/");
        mDatabase.keepSynced(true);
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerIdea);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        final FirebaseDatabase db = FirebaseDatabase.getInstance();
        final DatabaseReference[] databaseReference = new DatabaseReference[1];
        final FloatingActionButton idea = (FloatingActionButton)view.findViewById(R.id.shareIdea);
        idea.setOnClickListener(new View.OnClickListener() {
//            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext(),R.style.BottomSheet);
                final View bottomSheet = LayoutInflater.from(getActivity()).inflate(R.layout.bottom_sheet_ideas,(ConstraintLayout)v.findViewById(R.id.bottomDialog));
                bottomSheet.findViewById(R.id.postIdea).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ConnectivityManager conn_Manager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                        NetworkInfo activeNetwork =   conn_Manager.getActiveNetworkInfo();
                        if( activeNetwork != null && activeNetwork.isConnected() ) {
                            EditText idea = (EditText)bottomSheet.findViewById(R.id.ideaTxtBox);
                            if( idea.getText().toString().length() == 0 ) {
                                Toast.makeText(getContext(), "Please write your idea", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                String uniqueIdea = "\"" + idea.getText().toString() + "\"";
                                UUID uuid = UUID.randomUUID();
                                String ideaId = Long.toString(uuid.getMostSignificantBits(),36) + Long.toString(uuid.getLeastSignificantBits(),36).replace("-","");
                                String senderName = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
                                Long timeStamp = System.currentTimeMillis();
                                databaseReference[0] = db.getReference("Ideas");
                                Idea ideas = new Idea(uniqueIdea,ideaId,senderName,timeStamp);
                                databaseReference[0].child(databaseReference[0].push().getKey()).setValue(ideas);
                                idea.setText("");
                                if(Build.VERSION.SDK_INT < Build.VERSION_CODES.O){
                                    Toast.makeText(getContext(), "Idea shared successfully", Toast.LENGTH_SHORT).show();
                                }
                                if(bottomSheetDialog.isShowing()) {
                                    bottomSheetDialog.dismiss();
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                        NotificationManager notificationManager = (NotificationManager)getActivity().getSystemService(NOTIFICATION_SERVICE);
                                        String id = "id_product";
                                        // The user-visible name of the channel.
                                        CharSequence name = "Ideas";
                                        // The user-visible description of the channel.
                                        String description = "For Ideas submission";
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
                                                .setContentTitle("Idea Sharing")
                                                .setAutoCancel(true)
                                                .setNumber(2)
                                                .setColor(255)
                                                .setContentText("Idea " + uniqueIdea + " submitted successfully")
                                                .setWhen(System.currentTimeMillis())
                                                .setDefaults(NotificationCompat.DEFAULT_ALL);
                                        notificationManager.notify((int) System.currentTimeMillis(), notificationBuilder.build());
                                    }
                                    else{
                                        NotificationManager notificationManager = (NotificationManager)getActivity().getSystemService(NOTIFICATION_SERVICE);
                                        Notification notification = new Notification.Builder(getActivity()).setContentTitle("Idea submission")
                                                .setContentText("Idea " + uniqueIdea + " submitted successfully")
                                                .setSmallIcon(R.drawable.instagram_icon)
                                                .setAutoCancel(true)
                                                .build();
                                        notificationManager.notify((int) System.currentTimeMillis(),notification);
                                    }
                                }
                            }
                        }
                        else {
                            Toast.makeText(getContext(), "Network error", Toast.LENGTH_SHORT).show();
                        }
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
        FirebaseRecyclerAdapter<IdeaGet, IdeaViewHolder> FirebaseRecyclerAdapter = new FirebaseRecyclerAdapter<IdeaGet, IdeaViewHolder>
                (IdeaGet.class,R.layout.idea_card,IdeaViewHolder.class,mDatabase) {
            @Override
            protected void populateViewHolder(IdeaViewHolder ideaViewHolder, IdeaGet ideaGet, int i) {
                ideaViewHolder.setUniqueIdea(ideaGet.getUniqueIdea());
                ideaViewHolder.setSenderName(ideaGet.getSenderName());
                ideaViewHolder.setTimeStamp(ideaGet.getTimeStamp());
            }
        };
        recyclerView.setAdapter(FirebaseRecyclerAdapter);
    }

    public static class IdeaViewHolder extends RecyclerView.ViewHolder
    {
        View mView;
        public IdeaViewHolder(View ideaView)
        {
            super(ideaView);
            mView = ideaView;
        }
        public void setUniqueIdea(String uniqueIdea){
            TextView idea = (TextView)mView.findViewById(R.id.uniqueIdea);
            idea.setText(uniqueIdea);
        }
        public void setSenderName(String senderName){
            TextView name = (TextView)mView.findViewById(R.id.senderName);
            name.setText(senderName);
        }
        public void setTimeStamp(long timeStamp){
            TextView time = (TextView)mView.findViewById(R.id.timeStamp);
            time.setText(TimeAgo.from(timeStamp));
        }
    }
}