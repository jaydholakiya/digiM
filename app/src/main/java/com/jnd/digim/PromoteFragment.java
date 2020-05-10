package com.jnd.digim;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
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
import java.util.Arrays;
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
        final String[] promotion = new String[1];
        final String[] data = new String[1];
        final FirebaseDatabase db = FirebaseDatabase.getInstance();
        final DatabaseReference[] databaseReference = new DatabaseReference[1];
        final View view = inflater.inflate(R.layout.promote_fragment, container, false);
        RadioGroup radioGroup = (RadioGroup)view.findViewById(R.id.socialRadio);
        final TextView selectedText = (TextView)view.findViewById(R.id.promotionData);
        final EditText url = (EditText)view.findViewById(R.id.urlLink);
        final EditText mobileNo = (EditText)view.findViewById(R.id.mobileNo);
        final EditText transaction = (EditText)view.findViewById(R.id.transactionId);
        Button buttonOrder = (Button)view.findViewById(R.id.placeOrder);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId) {
                    case R.id.instagramRadio:
                        promotion[0] = "Instagram";
                        FirebaseDatabase db = FirebaseDatabase.getInstance();
                        DatabaseReference instagramDb = db.getReference("Promotion list/Instagram");
                        instagramDb.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                ArrayList<Object> promotions = new ArrayList<>();
                                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                    String data = postSnapshot.getValue(String.class);
                                    promotions.add(data);
                                }
                                final String a[] = promotions.toArray(new String[0]);
                                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                builder.setTitle("Select services of Instagram");
                                builder.setSingleChoiceItems(a, -1, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        data[0] =a[which];
                                    }
                                });
                                builder.setPositiveButton("DONE", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        selectedText.setText(Arrays.toString(data).replace("[","").replace("]",""));
                                    }
                                });
                                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        builder.create().dismiss();
                                    }
                                });
                                builder.setCancelable(false);
                                builder.create().setCanceledOnTouchOutside(false);
                                builder.show();
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Toast.makeText(getContext(), "" + databaseError.toException(), Toast.LENGTH_SHORT).show();
                            }
                        });
                        break;
                    case R.id.twitterRadio:
                        promotion[0] = "Twitter";
                        FirebaseDatabase dbInsta = FirebaseDatabase.getInstance();
                        DatabaseReference twitterDb = dbInsta.getReference("Promotion list/Twitter");
                        twitterDb.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                ArrayList<Object> promotions = new ArrayList<>();
                                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                    String data = postSnapshot.getValue(String.class);
                                    promotions.add(data);
                                }
                                final String a[] = promotions.toArray(new String[0]);
                                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                builder.setTitle("Select services of Twitter");
                                builder.setSingleChoiceItems(a, -1, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        data[0] =a[which];
                                    }
                                });
                                builder.setPositiveButton("DONE", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        selectedText.setText(Arrays.toString(data).replace("[","").replace("]",""));
                                    }
                                });
                                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        builder.create().dismiss();
                                    }
                                });
                                builder.setCancelable(false);
                                builder.create().setCanceledOnTouchOutside(false);
                                builder.show();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Toast.makeText(getContext(), "" + databaseError.toException(), Toast.LENGTH_SHORT).show();
                            }
                        });
                        break;
                    case R.id.tiktokRadio:
                        promotion[0] = "TikTok";
                        FirebaseDatabase dbTikTok = FirebaseDatabase.getInstance();
                        DatabaseReference tiktokDb = dbTikTok.getReference("Promotion list/TikTok");
                        tiktokDb.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                ArrayList<Object> promotions = new ArrayList<>();
                                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                    String data = postSnapshot.getValue(String.class);
                                    promotions.add(data);
                                }
                                final String a[] = promotions.toArray(new String[0]);
                                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                builder.setTitle("Select services of TikTok");
                                builder.setSingleChoiceItems(a, -1, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        data[0] =a[which];
                                    }
                                });
                                builder.setPositiveButton("DONE", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        selectedText.setText(Arrays.toString(data).replace("[","").replace("]",""));
                                    }
                                });
                                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        builder.create().dismiss();
                                    }
                                });
                                builder.setCancelable(false);
                                builder.create().setCanceledOnTouchOutside(false);
                                builder.show();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Toast.makeText(getContext(), "" + databaseError.toException(), Toast.LENGTH_SHORT).show();
                            }
                        });
                        break;
                    case R.id.youtubeRadio:
                        promotion[0] = "Youtube";
                        FirebaseDatabase dbYoutube = FirebaseDatabase.getInstance();
                        DatabaseReference youtubeDb = dbYoutube.getReference("Promotion list/Youtube");
                        youtubeDb.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                ArrayList<Object> promotions = new ArrayList<>();
                                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                    String data = postSnapshot.getValue(String.class);
                                    promotions.add(data);
                                }
                                final String a[] = promotions.toArray(new String[0]);
                                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                builder.setTitle("Select services of Youtube");
                                builder.setSingleChoiceItems(a, -1, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        data[0] =a[which];
                                    }
                                });
                                builder.setPositiveButton("DONE", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        selectedText.setText(Arrays.toString(data).replace("[","").replace("]",""));
                                    }
                                });
                                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        builder.create().dismiss();
                                    }
                                });
                                builder.setCancelable(false);
                                builder.create().setCanceledOnTouchOutside(false);
                                builder.show();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Toast.makeText(getContext(), "" + databaseError.toException(), Toast.LENGTH_SHORT).show();
                            }
                        });
                        break;
                    case R.id.facebookRadio:
                        promotion[0] = "Facebook";
                        FirebaseDatabase dbFacebook = FirebaseDatabase.getInstance();
                        DatabaseReference facebookDb = dbFacebook.getReference("Promotion list/Facebook");
                        facebookDb.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                ArrayList<Object> promotions = new ArrayList<>();
                                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                    String data = postSnapshot.getValue(String.class);
                                    promotions.add(data);
                                }
                                final String a[] = promotions.toArray(new String[0]);
                                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                builder.setTitle("Select services of Facebook");
                                builder.setSingleChoiceItems(a, -1, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        data[0] = a[which];
                                    }
                                });
                                builder.setPositiveButton("DONE", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        selectedText.setText(Arrays.toString(data).replace("[","").replace("]",""));
                                    }
                                });
                                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        builder.create().dismiss();
                                    }
                                });
                                builder.setCancelable(false);
                                builder.create().setCanceledOnTouchOutside(false);
                                builder.show();
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
                Snackbar.make(getView(),"Please pay us on paytm before filling the form",BaseTransientBottomBar.LENGTH_INDEFINITE).show();
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(user!=null) {
                    String order = promotion[0];
                    String orderType = selectedText.getText().toString();
                    UUID uuid = UUID.randomUUID();
                    String orderId = Long.toString(uuid.getMostSignificantBits(),36) + Long.toString(uuid.getLeastSignificantBits(),36).replace("-","");
                    Toast.makeText(getContext(), "" + orderId, Toast.LENGTH_SHORT).show();
                    String email = user.getEmail();
                    String orderDateTime = Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + "/" + ( Calendar.getInstance().get(Calendar.MONTH) + 1) + "/" + Calendar.getInstance().get(Calendar.YEAR) + " " + Calendar.getInstance().get(Calendar.HOUR) + ":" + Calendar.getInstance().get(Calendar.MINUTE);
                    String urlLink = url.getText().toString();
                    String transactionId = transaction.getText().toString();
                    String contactNo = mobileNo.getText().toString();
                    Long timeStamp = System.currentTimeMillis();
                    Boolean orderReviewed = false;
                    databaseReference[0] = db.getReference("Orders");
                    Order orders = new Order(order,orderType,orderId,email,orderDateTime,urlLink,transactionId,contactNo,timeStamp,orderReviewed);
                    databaseReference[0].child(user.getDisplayName() + "_" + user.getEmail().substring(0,user.getEmail().indexOf("."))).child(databaseReference[0].push().getKey()).setValue(orders);
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