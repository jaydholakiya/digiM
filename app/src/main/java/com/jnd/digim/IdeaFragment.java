package com.jnd.digim;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;


public class IdeaFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.idea_fragment,container,false);
        final FirebaseDatabase db = FirebaseDatabase.getInstance();
        final DatabaseReference[] databaseReference = new DatabaseReference[1];
        FloatingActionButton idea = (FloatingActionButton)view.findViewById(R.id.shareIdea);
        final ImageButton postIdea = (ImageButton)view.findViewById(R.id.postIdea);
        idea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext(),R.style.BottomSheet);
                final View bottomSheet = LayoutInflater.from(getActivity()).inflate(R.layout.bottom_sheet,(ConstraintLayout)v.findViewById(R.id.bottomDialog));
                bottomSheet.findViewById(R.id.postIdea).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText idea = (EditText)bottomSheet.findViewById(R.id.ideaTxtBox);
                        String uniqueIdea = idea.getText().toString();
                        UUID uuid = UUID.randomUUID();
                        String ideaId = Long.toString(uuid.getMostSignificantBits(),36) + Long.toString(uuid.getLeastSignificantBits(),36).replace("-","");
                        String senderName = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
                        Long timeStamp = System.currentTimeMillis();
                        databaseReference[0] = db.getReference("Ideas");
                        Idea ideas = new Idea(uniqueIdea,ideaId,senderName,timeStamp);
                        databaseReference[0].child(databaseReference[0].push().getKey()).setValue(ideas);
                    }
                });
                bottomSheetDialog.setContentView(bottomSheet);
                bottomSheetDialog.show();
            }
        });
        return view;
    }
}
