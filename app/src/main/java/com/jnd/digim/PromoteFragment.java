package com.jnd.digim;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AlertDialogLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.lang.String.valueOf;

public class PromoteFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.promote_fragment, container, false);
        RadioGroup radioGroup = (RadioGroup)view.findViewById(R.id.socialRadio);
        final TextView selectedText = (TextView)view.findViewById(R.id.promotionData);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId) {
                    case R.id.instagramRadio:
                        final String instagram = "Instagram";
                        selectedText.setText("Instagram");
                        FirebaseDatabase db = FirebaseDatabase.getInstance();
                        DatabaseReference instagramDb = db.getReference("Promotion list/Instagram");
                        instagramDb.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                ArrayList<Object> promotions = new ArrayList<>();
                                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                    String data = postSnapshot.getValue(String.class);
                                    promotions.add(data);
                                    Toast.makeText(getContext(), "" + promotions, Toast.LENGTH_SHORT).show();
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Toast.makeText(getContext(), "" + databaseError.toException(), Toast.LENGTH_SHORT).show();
                            }
                        });
                        break;
                    case R.id.twitterRadio:
                        selectedText.setText("Twitter");
                        FirebaseDatabase dbInsta = FirebaseDatabase.getInstance();
                        DatabaseReference twitterDb = dbInsta.getReference("Promotion list/Twitter");
                        twitterDb.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                ArrayList<Object> promotions = new ArrayList<>();
                                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                    String data = postSnapshot.getValue(String.class);
                                    promotions.add(data);
                                    Toast.makeText(getContext(), "" + promotions, Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Toast.makeText(getContext(), "" + databaseError.toException(), Toast.LENGTH_SHORT).show();
                            }
                        });
                        break;
                    case R.id.tiktokRadio:
                        selectedText.setText("TikTok");
                        FirebaseDatabase dbTikTok = FirebaseDatabase.getInstance();
                        DatabaseReference tiktokDb = dbTikTok.getReference("Promotion list/TikTok");
                        tiktokDb.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                ArrayList<Object> promotions = new ArrayList<>();
                                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                    String data = postSnapshot.getValue(String.class);
                                    promotions.add(data);
                                    Toast.makeText(getContext(), "" + promotions, Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Toast.makeText(getContext(), "" + databaseError.toException(), Toast.LENGTH_SHORT).show();
                            }
                        });
                        break;
                    case R.id.youtubeRadio:
                        selectedText.setText("Youtube");
                        FirebaseDatabase dbYoutube = FirebaseDatabase.getInstance();
                        DatabaseReference youtubeDb = dbYoutube.getReference("Promotion list/Youtube");
                        youtubeDb.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                ArrayList<Object> promotions = new ArrayList<>();
                                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                    String data = postSnapshot.getValue(String.class);
                                    promotions.add(data);
                                    Toast.makeText(getContext(), "" + promotions, Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Toast.makeText(getContext(), "" + databaseError.toException(), Toast.LENGTH_SHORT).show();
                            }
                        });
                        break;
                    case R.id.facebookRadio:
                        selectedText.setText("Facebook");
                        FirebaseDatabase dbFacebook = FirebaseDatabase.getInstance();
                        DatabaseReference facebookDb = dbFacebook.getReference("Promotion list/Facebook");
                        facebookDb.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                ArrayList<Object> promotions = new ArrayList<>();
                                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                    String data = postSnapshot.getValue(String.class);
                                    promotions.add(data);
                                    Toast.makeText(getContext(), "" + promotions, Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Toast.makeText(getContext(), "" + databaseError.toException(), Toast.LENGTH_SHORT).show();
                            }
                        });
                        break;
                }
            }
        });
        return view;
    }

//    public void placeOrder() {
//        this.customerGuid = Guid.create();
//        this.orderDate = new Date().getDate() + '/' + (new Date().getMonth()+1) + '/' + new Date().getFullYear() + ' ' + new Date().getHours() + ':' + new Date().getMinutes();
//        this.db.database.ref('Orders/'+localStorage.getItem('displayName')+'_'+localStorage.getItem('email').substr(0,localStorage.getItem('email').indexOf('.'||'@'))).push({
//                order : this.promotionForm.value.order,
//                orderType : this.promotionForm.value.selectPromoteType,
//                orderId : this.customerGuid.value,
//                email : localStorage.getItem('email'),
//                orderDateTime : this.orderDate,
//                urlLink : this.promotionForm.value.urlLink,
//                transactionId : this.promotionForm.value.transactionId,
//                contactNo : this.promotionForm.value.contactNo,
//                timeStamp : Date.now(),
//                orderReviewed : false
//    });
//        this.toastr.success('Order successfully placed...','Success!');
//        this.promotionForm.setValue({selectPromoteType:"",urlLink:"",transactionId:"",order:"",contactNo:""});
//        this.servicesList=[];
//    }
}