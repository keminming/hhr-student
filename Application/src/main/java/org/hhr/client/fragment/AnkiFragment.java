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
import android.widget.Button;
import android.widget.EditText;

import org.hhr.client.R;

import java.util.List;

/**
 * Created by ke on 7/23/2015.
 */
public class AnkiFragment extends Fragment {

    Button anki;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.show_language_layout,container,false);
        anki = (Button)rootView.findViewById(R.id.button3);
        anki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchAnki(v, "com.ichi2.anki");
            }
        });

        return rootView;
    }

    public void launchAnki(View v, String packageName) {
        PackageManager packageManager = v.getContext().getPackageManager();
        PackageInfo pi = null;

        try {
            pi = packageManager.getPackageInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {

        }

        Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
        resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        resolveIntent.setPackage(pi.packageName);

        List<ResolveInfo> apps = packageManager.queryIntentActivities(resolveIntent, 0);

        ResolveInfo ri = apps.iterator().next();
        if (ri != null) {
            String className = ri.activityInfo.name;

            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);

            ComponentName cn = new ComponentName(packageName, className);

            intent.setComponent(cn);
            v.getContext().startActivity(intent);
        }
    }
}
