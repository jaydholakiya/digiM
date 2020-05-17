package com.jnd.digim;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.synnapps.carouselview.CarouselView;

import java.io.File;

public class ContactUs extends Fragment implements OnMapReadyCallback {

    GoogleMap googleMap;
    MapView mapView;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(mapView!=null){
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contact_us,container,false);

        CarouselView carouselView;
        carouselView = getActivity().findViewById(R.id.carouselView);
        carouselView.setVisibility(View.GONE);

        YouTubePlayerFragment youTubePlayerFragment =
                (YouTubePlayerFragment) getActivity().getFragmentManager().findFragmentById(R.id.youTube);
        youTubePlayerFragment.getView().setVisibility(View.GONE);

        Context context = null;
        MapView mapView = (MapView)view.findViewById(R.id.map);
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

        TextView email = (TextView)view.findViewById(R.id.commuEmail);
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("email/text");
                i.putExtra(Intent.EXTRA_EMAIL,new String[]{"socialmefgi@gmail.com"});
                startActivity(Intent.createChooser(i,"Send Email"));
            }
        });

//        mapView = (MapView)view.findViewById(R.id.map);
//        mapView.onCreate(savedInstanceState);
//        mapView.getMapAsync(this);
        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        MapsInitializer.initialize(getContext());
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.addMarker(new MarkerOptions().position(new LatLng(19.169257,73.341601)).title("Suyog Bunglows").snippet("Communication Address"));
        CameraPosition Suyog = CameraPosition.builder().target(new LatLng(19.169257,73.341601)).zoom(16).bearing(0).tilt(45).build();
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(Suyog));
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
