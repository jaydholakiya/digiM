package com.jnd.digim;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ValueEventListener;
import com.squareup.okhttp.internal.DiskLruCache;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import static android.content.Context.MODE_PRIVATE;
import static android.os.Build.VERSION_CODES.O;
import static java.lang.String.valueOf;

public class PromoteFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.promote_fragment, container, false);
        RadioGroup radioGroup = (RadioGroup)view.findViewById(R.id.socialRadio);
        final TextView selectedText = (TextView)view.findViewById(R.id.promotionData);
        final EditText urlLink = (EditText)view.findViewById(R.id.urlLink);
        final EditText mobileNo = (EditText)view.findViewById(R.id.mobileNo);
        final EditText transactionId = (EditText)view.findViewById(R.id.transactionId);
        Button buttonOrder = (Button)view.findViewById(R.id.placeOrder);
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
        buttonOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Long timeStamp = System.currentTimeMillis();
                Toast.makeText(getContext(), "URL - " + urlLink.getText().toString() + "\nTransactionId - " +  transactionId.getText().toString() + "\nMobile No. - " + mobileNo.getText().toString() + "\nPromotion - " + selectedText.getText().toString() + "\nOrder Reviewed - " + false, Toast.LENGTH_SHORT).show();
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
                Integer a = Calendar.getInstance().get(Calendar.MONTH) + 1;
                Calendar.getInstance().get(Calendar.YEAR);
                Calendar.getInstance().get(Calendar.HOUR);
                Calendar.getInstance().get(Calendar.MINUTE);
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(user!=null) {
                    String email = user.getEmail();
                    Snackbar.make(getView(),email.substring(0,email.indexOf(".")), BaseTransientBottomBar.LENGTH_LONG).show();
                    Toast.makeText(getContext(), user.getDisplayName(), Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getContext(), "User is logged out", Toast.LENGTH_SHORT).show();
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