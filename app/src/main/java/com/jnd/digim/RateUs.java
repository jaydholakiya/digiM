package com.jnd.digim;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import java.util.UUID;

public class RateUs extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.rate_us,container,false);

        FloatingActionButton rate = (FloatingActionButton)view.findViewById(R.id.rateFloatBtn);
        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext(),R.style.BottomSheet);
                final View bottomSheet = LayoutInflater.from(getActivity()).inflate(R.layout.bottom_sheet_rating,(ConstraintLayout)v.findViewById(R.id.bottomDialogRate));
                final EditText complaint = (EditText)bottomSheet.findViewById(R.id.complaint);
                final RatingBar rating = (RatingBar)bottomSheet.findViewById(R.id.ratingBar);
                bottomSheet.findViewById(R.id.rateUsSubmit).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ConnectivityManager conn_manager = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                        NetworkInfo activeNetwork = conn_manager.getActiveNetworkInfo();
                        if( activeNetwork!=null && activeNetwork.isConnected() ){
                            if( complaint.getText().toString().length() == 0 ) {
                                Toast.makeText(getContext(), "Please write feedback", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                                    @Override
                                    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                                        final Float ratingValue = ratingBar.getRating();
                                    }
                                });
                                String complaineeName = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
                                String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                                String complain = complaint.getText().toString();
                                Long complainTime = System.currentTimeMillis();
                                UUID complainid = UUID.randomUUID();
                                String complainId = Long.toString(complainid.getMostSignificantBits(),36) + Long.toString(complainid.getLeastSignificantBits(),36).replace("-","");
                                Float starRating = rating.getRating();
                                Toast.makeText(getContext(), "" + starRating+complaineeName+email+complain+complainTime+complainId, Toast.LENGTH_SHORT).show();
                            }
                        }
                        else Toast.makeText(getContext(), "Network error", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        return view;
    }
}
