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
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.synnapps.carouselview.CarouselView;

public class ContactUs extends Fragment implements OnMapReadyCallback {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //Initializing the view
        View view = inflater.inflate(R.layout.contact_us,container,false);

        //Carousel hiding
        CarouselView carouselView;
        carouselView = getActivity().findViewById(R.id.carouselView);
        carouselView.setVisibility(View.GONE);

        //YouTube video hiding
        YouTubePlayerFragment youTubePlayerFragment =
                (YouTubePlayerFragment) getActivity().getFragmentManager().findFragmentById(R.id.youTube);
        youTubePlayerFragment.getView().setVisibility(View.GONE);

        /*
        * Social media links
        * */

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

        //Email intent with mail id

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

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng marker = new LatLng(23.007927,72.507171);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker,15));
        googleMap.addMarker(new MarkerOptions().title("Suyog Bunglows").position(marker));
    }
}
