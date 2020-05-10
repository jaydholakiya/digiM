package com.jnd.digim;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class IdeaFragment extends Fragment {
    private RecyclerView recyclerView;
    private DatabaseReference mDatabase;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.idea_fragment,container,false);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Ideas");
        mDatabase.keepSynced(true);
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerIdeaCard);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        final FirebaseDatabase db = FirebaseDatabase.getInstance();
        final DatabaseReference[] databaseReference = new DatabaseReference[1];
        final FloatingActionButton idea = (FloatingActionButton)view.findViewById(R.id.shareIdea);
        final ImageButton postIdea = (ImageButton)view.findViewById(R.id.postIdea);
        idea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext(),R.style.BottomSheet);
                final View bottomSheet = LayoutInflater.from(getActivity()).inflate(R.layout.bottom_sheet,(ConstraintLayout)v.findViewById(R.id.bottomDialog));
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
                                Toast.makeText(getContext(), "Idea submitted successfully", Toast.LENGTH_SHORT).show();
                                if(bottomSheetDialog.isShowing()) {
                                    bottomSheetDialog.dismiss();
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

    public static class IdeaViewHolder extends RecyclerView.ViewHolder
    {
        View mView;
        public IdeaViewHolder(View item)
        {
            super(item);
            mView = item;
        }
        public setUniqueIdea(String uniqueIdea){
            TextView idea = mView.findViewById(R.id.uniqueIdea);
            idea.setText(uniqueIdea);
        }
        public setSenderName(String senderName){
            TextView name = mView.findViewById(R.id.senderName);
            name.setText(senderName);
        }
        public setTimeStamp(Long timeStamp){
            TextView time = mView.findViewById(R.id.timeStamp);
            time.setText(timeStamp);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<IdeaGet,IdeaViewHolder>FirebaseRecyclerAdapter = new FirebaseRecyclerAdapter<IdeaGet,IdeaViewHolder>
                (Idea.class,R.layout.idea_fragment,IdeaViewHolder.class,mDatabase) {
            @Override
            protected void populateViewHolder(IdeaViewHolder viewHolder, Idea model,int position) {
                viewHolder.setUniqueIdea(model.getUniqueIdea());
                viewHolder.setSenderName(model.getSenderName());
                viewHolder.setTimeStamp(model.getTimeStamp());
            }
        };
        recyclerView.setAdapter(FirebaseRecyclerAdapter);
    }
}