package org.hhr.client.fragment;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;

import org.hhr.client.R;

import java.util.List;

/**
 * Created by wk on 1/30/2016.
 */
public class BtSyncFragment extends Fragment {

    WebView btSync;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.bt_layout,container,false);
        btSync = (WebView)rootView.findViewById(R.id.webview);
        btSync.loadUrl("file:///android_asset/HHRPickALanguage-HTML5.html");

        return rootView;
    }
}
