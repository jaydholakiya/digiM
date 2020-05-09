package com.jnd.digim;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class EducationFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.education_fragment,container,false);
            final WebView web = (WebView)view.findViewById(R.id.blogURL);
            web.loadUrl("https://neilpatel.com/blog");
            web.clearCache(true);
            web.clearHistory();
            web.getSettings().setSupportMultipleWindows(true);
            web.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        return view;
    }
}