package com.jnd.digim;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.youtube.player.YouTubePlayerFragment;
import com.synnapps.carouselview.CarouselView;

public class Working extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //Initializing the view for Working screen and PDF view
        View view = inflater.inflate(R.layout.working,container,false);
        Button viewPDF = (Button)view.findViewById(R.id.download);

        //Hiding the Carousel
        CarouselView carouselView;
        carouselView = getActivity().findViewById(R.id.carouselView);
        carouselView.setVisibility(View.GONE);

        //YouTube video hiding
        YouTubePlayerFragment youTubePlayerFragment =
                (YouTubePlayerFragment) getActivity().getFragmentManager().findFragmentById(R.id.youTube);
        youTubePlayerFragment.getView().setVisibility(View.GONE);
        viewPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pdfView = new Intent(getActivity(), PdfView.class);
                startActivity(pdfView);
            }
        });
        return view;
    }
}