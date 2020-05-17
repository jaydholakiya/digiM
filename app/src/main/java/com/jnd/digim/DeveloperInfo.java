package com.jnd.digim;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.synnapps.carouselview.CarouselView;

public class DeveloperInfo extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.developer_info,container,false);
        CarouselView carouselView;
        carouselView = getActivity().findViewById(R.id.carouselView);
        carouselView.setVisibility(View.GONE);

        YouTubePlayerFragment youTubePlayerFragment =
                (YouTubePlayerFragment) getActivity().getFragmentManager().findFragmentById(R.id.youTube);
        youTubePlayerFragment.getView().setVisibility(View.GONE);

        ImageView avatarDeveloper = (ImageView)view.findViewById(R.id.profilePicDeveloper);
        Glide.with(getActivity().getApplicationContext())
                .load(getImage("jay_croped"))
                .into(avatarDeveloper);
        avatarDeveloper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent instagramIcon = new Intent(Intent.ACTION_VIEW).addCategory(Intent.CATEGORY_BROWSABLE).setData(Uri.parse("https://www.instagram.com/jaysoni_9160/"));
                startActivity(instagramIcon);
            }
        });
        ImageView avatarConcept = (ImageView)view.findViewById(R.id.profilePicConcept);
        Glide.with(getActivity().getApplicationContext())
                .load("https://pbs.twimg.com/media/ESQqEIaXYAIBkZM?format=jpg&name=4096x4096")
                .into(avatarConcept);
        avatarConcept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent instagramIcon = new Intent(Intent.ACTION_VIEW).addCategory(Intent.CATEGORY_BROWSABLE).setData(Uri.parse("https://www.instagram.com/digi.storm/"));
                startActivity(instagramIcon);
            }
        });
        return view;
    }

    public int getImage(String image){
        int profilePic = this.getResources().getIdentifier("jay_croped","drawable",getActivity().getPackageName());
        return profilePic;
    }
}