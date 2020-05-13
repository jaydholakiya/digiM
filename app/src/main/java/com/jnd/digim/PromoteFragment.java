package com.jnd.digim;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
        FirebaseDatabase.getInstance().getReference().child("lineData").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue(Integer.class).toString().equals("1")){
                    Snackbar.make(container,"Please pay us on paytm before filling the form",BaseTransientBottomBar.LENGTH_INDEFINITE).setAction("CLOSE", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Snackbar.make(v,"Please pay us on paytm before filling the form",BaseTransientBottomBar.LENGTH_INDEFINITE).dismiss();
                        }
                    }).setActionTextColor(Color.RED).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}});
        final String[] promotion = new String[1];
        final String[] data = new String[1];
        final FirebaseDatabase db = FirebaseDatabase.getInstance();
        final DatabaseReference[] databaseReference = new DatabaseReference[1];
        final View view = inflater.inflate(R.layout.promote_fragment, container, false);
        final RadioGroup radioGroup = (RadioGroup)view.findViewById(R.id.socialRadio);
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
                ConnectivityManager conn_Manager = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetwork =   conn_Manager.getActiveNetworkInfo();
                if( activeNetwork != null && activeNetwork.isConnected() ) {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if(user!=null) {
                        RadioButton rInstagram = (RadioButton)v.findViewById(R.id.instagramRadio);
                        RadioButton rFacebook = (RadioButton)v.findViewById(R.id.facebookRadio);
                        RadioButton rTwitter = (RadioButton)v.findViewById(R.id.twitterRadio);
                        RadioButton rTikTok = (RadioButton)v.findViewById(R.id.tiktokRadio);
                        RadioButton rYoutube = (RadioButton)v.findViewById(R.id.youtubeRadio);
                        String order = promotion[0];
                        String orderType = selectedText.getText().toString();
                        UUID uuid = UUID.randomUUID();
                        String orderId = Long.toString(uuid.getMostSignificantBits(),36) + Long.toString(uuid.getLeastSignificantBits(),36).replace("-","");
                        String email = user.getEmail();
                        String orderDateTime = Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + "/" + ( Calendar.getInstance().get(Calendar.MONTH) + 1) + "/" + Calendar.getInstance().get(Calendar.YEAR) + " " + Calendar.getInstance().get(Calendar.HOUR) + ":" + Calendar.getInstance().get(Calendar.MINUTE);
                        String urlLink = url.getText().toString();
                        String transactionId = transaction.getText().toString();
                        String contactNo = mobileNo.getText().toString();
                        Long timeStamp = System.currentTimeMillis();
                        Boolean orderReviewed = false;
                            if( orderType.equals("No promotions selected") || urlLink.length() == 0 || transactionId.length() == 0 || contactNo.length() == 0 ) {
                                Snackbar.make(v,"All fields are required",BaseTransientBottomBar.LENGTH_LONG).show();
                            }
                            else if( contactNo.length() < 10 ) {
                                Toast.makeText(getContext(), "Mobile no. should be 10 digits long", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                databaseReference[0] = db.getReference("Orders");
                                Order orders = new Order(order, orderType, orderId, email, orderDateTime, urlLink, transactionId, contactNo, timeStamp, orderReviewed);
                                databaseReference[0].child(user.getDisplayName() + "_" + user.getEmail().substring(0, user.getEmail().indexOf("."))+"_"+FirebaseAuth.getInstance().getCurrentUser().getUid()).child(databaseReference[0].push().getKey()).setValue(orders);
                                Toast.makeText(getContext(), "Order placed successfully", Toast.LENGTH_SHORT).show();
                                selectedText.setText("No promotions selected");
                                url.setText("");
                                transaction.setText("");
                                mobileNo.setText("");
                            }
                    }
                }
                else {
                    Toast.makeText(getContext(), "Network error", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }
}