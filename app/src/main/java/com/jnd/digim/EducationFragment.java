package com.jnd.digim;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class EducationFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Initializing the view

        View view = inflater.inflate(R.layout.education_fragment,container,false);

        //Progressbar for page load
        final ProgressBar progressBarDashboard = (ProgressBar)((AppCompatActivity)getActivity()).findViewById(R.id.progressBarDashboard);

        WebView web = (WebView)view.findViewById(R.id.blogURL);
            web.loadUrl("https://neilpatel.com/blog");
            web.clearCache(true);
            web.clearHistory();
            web.getSettings().setSupportMultipleWindows(true);
            web.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
            web.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    super.onPageStarted(view, url, favicon);
                    progressBarDashboard.setVisibility(View.VISIBLE);
                }

                @Override
                public void onPageCommitVisible(WebView view, String url) {
                    super.onPageCommitVisible(view, url);
                    progressBarDashboard.setVisibility(View.GONE);
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    progressBarDashboard.setVisibility(View.GONE);
                }

                @Override
                public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                    super.onReceivedError(view, request, error);
                    Toast.makeText(getContext(), "" + error, Toast.LENGTH_SHORT).show();
                }
            });
        return view;
    }
}