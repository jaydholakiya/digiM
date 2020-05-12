package com.jnd.digim;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

public class ContactUs extends Fragment {
    MapView mapView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contact_us,container,false);

        ImageView facebookIcon = (ImageView)view.findViewById(R.id.facebookIcon);
        facebookIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent facebookIntent = new Intent(Intent.ACTION_VIEW).addCategory(Intent.CATEGORY_BROWSABLE).setData(Uri.parse("https://www.facebook.com/yash.vyas.336"));
                startActivity(facebookIntent);
            }
        });

        ImageView instagramIcon = (ImageView)view.findViewById(R.id.instagramIcon);
        instagramIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent instagramIcon = new Intent(Intent.ACTION_VIEW).addCategory(Intent.CATEGORY_BROWSABLE).setData(Uri.parse("https://www.instagram.com/digi.storm/"));
                startActivity(instagramIcon);
            }
        });

        ImageView twitterIcon = (ImageView)view.findViewById(R.id.twitterIcon);
        twitterIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent twitterIcon = new Intent(Intent.ACTION_VIEW).addCategory(Intent.CATEGORY_BROWSABLE).setData(Uri.parse("https://twitter.com/energetic_motiv"));
                startActivity(twitterIcon);
            }
        });

        ImageView linkedinIcon = (ImageView)view.findViewById(R.id.linkedinIcon);
        linkedinIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent linkedInIcon = new Intent(Intent.ACTION_VIEW).addCategory(Intent.CATEGORY_BROWSABLE).setData(Uri.parse("https://www.linkedin.com/in/yashvyas3/"));
                startActivity(linkedInIcon);
            }
        });

//        mapView = (MapView)view.findViewById(R.id.map);
//        mapView.onCreate(savedInstanceState);
//        mapView.getMapAsync(this);
        return view;
    }

//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        GoogleMap map = googleMap;
//        map.getUiSettings().setMyLocationButtonEnabled(false);
//        map.setMyLocationEnabled(true);
//
//        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(21.5222, 70.4579),21);
//        map.animateCamera(cameraUpdate);
//        map.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(21.5222, 70.4579)));
//    }
//
//    @Override
//    public void onResume() {
//        mapView.onResume();
//        super.onResume();
//    }
//
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        mapView.onPause();
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        mapView.onDestroy();
//    }
//
//    @Override
//    public void onLowMemory() {
//        super.onLowMemory();
//        mapView.onLowMemory();
//    }

}
