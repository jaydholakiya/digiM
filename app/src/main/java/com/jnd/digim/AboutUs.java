package com.jnd.digim;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.youtube.player.YouTubePlayerFragment;
import com.synnapps.carouselview.CarouselView;

public class AboutUs extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        /*
        * For Carousel hiding
        * */
        CarouselView carouselView;
        carouselView = getActivity().findViewById(R.id.carouselView);
        carouselView.setVisibility(View.GONE);

        /*
        * For YouTube video hiding
        * */

        YouTubePlayerFragment youTubePlayerFragment =
                (YouTubePlayerFragment) getActivity().getFragmentManager().findFragmentById(R.id.youTube);
        youTubePlayerFragment.getView().setVisibility(View.GONE);

        return inflater.inflate(R.layout.about_us,container,false);
    }
}