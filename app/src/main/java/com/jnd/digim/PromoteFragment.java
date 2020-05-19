package com.jnd.digim;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
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
import androidx.core.app.NotificationCompat;
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

import java.util.UUID;

import static android.content.Context.NOTIFICATION_SERVICE;

public class PromoteFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {

        //Initializing the view
        View view = inflater.inflate(R.layout.promote_fragment, container, false);

        //Checking the promotion paytm payment line from database
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

        //Getting buttons from xml
        final String[] promotion = new String[1];
        final String[] data = new String[1];
        final FirebaseDatabase db = FirebaseDatabase.getInstance();
        final DatabaseReference[] databaseReference = new DatabaseReference[1];
        final RadioGroup radioGroup = (RadioGroup)view.findViewById(R.id.socialRadio);
        final TextView selectedText = (TextView)view.findViewById(R.id.promotionData);
        final EditText url = (EditText)view.findViewById(R.id.urlLink);
        final EditText mobileNo = (EditText)view.findViewById(R.id.mobileNo);
        final EditText transaction = (EditText)view.findViewById(R.id.transactionId);
        Button buttonOrder = (Button)view.findViewById(R.id.placeOrder);

        //Radio button onClick services list load
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId) {
                    case R.id.instagramRadio:
                        radioGroup.setVisibility(View.GONE);
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

                                final String services[] = promotions.toArray(new String[0]);
                                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                                builder.setTitle("Select services of Instagram");

                                builder.setSingleChoiceItems(services, -1, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        data[0] =services[which];
                                    }
                                });

                                builder.setPositiveButton("DONE", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //Changing the Array to string
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
                                radioGroup.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Toast.makeText(getContext(), "" + databaseError.toException(), Toast.LENGTH_SHORT).show();
                            }

                        });
                        break;

                    case R.id.twitterRadio:
                        radioGroup.setVisibility(View.GONE);
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

                                final String services[] = promotions.toArray(new String[0]);
                                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                                builder.setTitle("Select services of Twitter");
                                builder.setSingleChoiceItems(services, -1, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        data[0] =services[which];
                                    }
                                });

                                builder.setPositiveButton("DONE", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //Changing the Array to string
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
                                radioGroup.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Toast.makeText(getContext(), "" + databaseError.toException(), Toast.LENGTH_SHORT).show();
                            }

                        });
                        break;

                    case R.id.tiktokRadio:
                        radioGroup.setVisibility(View.GONE);
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

                                final String services[] = promotions.toArray(new String[0]);
                                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                builder.setTitle("Select services of TikTok");

                                builder.setSingleChoiceItems(services, -1, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        data[0] =services[which];
                                    }
                                });

                                builder.setPositiveButton("DONE", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //Changing the Array to string
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
                                radioGroup.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Toast.makeText(getContext(), "" + databaseError.toException(), Toast.LENGTH_SHORT).show();
                            }

                        });
                        break;

                    case R.id.youtubeRadio:
                        radioGroup.setVisibility(View.GONE);
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

                                final String services[] = promotions.toArray(new String[0]);
                                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                builder.setTitle("Select services of Youtube");

                                builder.setSingleChoiceItems(services, -1, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        data[0] =services[which];
                                    }
                                });

                                builder.setPositiveButton("DONE", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //Changing the Array to string
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
                                radioGroup.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Toast.makeText(getContext(), "" + databaseError.toException(), Toast.LENGTH_SHORT).show();
                            }

                        });
                        break;

                    case R.id.facebookRadio:
                        radioGroup.setVisibility(View.GONE);
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

                                final String services[] = promotions.toArray(new String[0]);
                                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                builder.setTitle("Select services of Facebook");

                                builder.setSingleChoiceItems(services, -1, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        data[0] = services[which];
                                    }
                                });

                                builder.setPositiveButton("DONE", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //Changing the Array to string
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
                                radioGroup.setVisibility(View.VISIBLE);
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

        //Place order functionality
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
                        String order = selectedText.getText().toString();
                        String orderType = promotion[0];
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
                                selectedText.setText("No promotions selected");
                                url.setText("");
                                transaction.setText("");
                                mobileNo.setText("");

                                //Notifications for promote with Filtering OREO devices
                                if(Build.VERSION.SDK_INT < Build.VERSION_CODES.O){
                                    Toast.makeText(getContext(), "Order placed successfully", Toast.LENGTH_SHORT).show();
                                }

                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    NotificationManager notificationManager = (NotificationManager)getActivity().getSystemService(NOTIFICATION_SERVICE);
                                    String id = "id_product";
                                    // The user-visible name of the channel.
                                    CharSequence name = "Promotions";
                                    // The user-visible description of the channel.
                                    String description = "For order placing";
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
                                            .setContentText("Your order \"" + order + "\" for \"" + orderType + "\" successfully placed")
                                            .setWhen(System.currentTimeMillis())
                                            .setDefaults(NotificationCompat.DEFAULT_ALL);
                                    notificationManager.notify((int) System.currentTimeMillis(), notificationBuilder.build());
                                }

                                else{
                                    NotificationManager notificationManager = (NotificationManager)getActivity().getSystemService(NOTIFICATION_SERVICE);
                                    Notification notification = new Notification.Builder(getActivity()).setContentTitle("Idea submission")
                                            .setContentText("Your order \"" + order + "\" for \"" + orderType + "\" successfully placed")
                                            .setContentTitle("Promotion order")
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
        return view;
    }
}