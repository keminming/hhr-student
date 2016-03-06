package org.hhr.client.activity;

import android.app.Activity;
import android.app.DownloadManager;
import android.app.SearchManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;
import android.widget.TextView;

import org.hhr.client.R;
import org.hhr.client.provider.LanguageContentProvider;
import org.hhr.client.provider.LanguageTableColumns;

/**
 * Created by ke on 7/14/2015.
 */
public class DownloadActivity extends Activity{
    private DownloadManager downloadManager;
    long downloadReference;
    String languageCode;
    String isInstalled;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        IntentFilter filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        registerReceiver(downloadReceiver, filter);

        String query = getIntent().getStringExtra(SearchManager.QUERY);
        String selection = LanguageTableColumns.COLUMN_NAME_CODE + "MATCH ?";
        String[] selectionArgs = new String[] {query};

        Cursor cursor = getContentResolver().query(LanguageContentProvider.LANGUAGE_CONTENT_URI, null, selection, selectionArgs, null);

        if (cursor == null) {
            finish();
        } else {
            cursor.moveToFirst();
            languageCode = cursor.getString(cursor.getColumnIndex(LanguageTableColumns.COLUMN_NAME_CODE));
            isInstalled =  cursor.getString(cursor.getColumnIndex(LanguageTableColumns.COLUMN_NAME_INSTALLED));
        }

        if("null".equals(isInstalled)){
            //show video panel
        }else {
            download();
        }
    }

    void download(){
        downloadManager = (DownloadManager)getSystemService(DOWNLOAD_SERVICE);
        Uri Download_Uri = Uri.parse("http://demo.mysamplecode.com/Sencha_Touch/CountryServlet?start=0&limit=999");
        DownloadManager.Request request = new DownloadManager.Request(Download_Uri);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        request.setAllowedOverRoaming(false);
        //Set the title of this download, to be displayed in notifications (if enabled).
        request.setTitle("My Data Download");
        //Set a description of this download, to be displayed in notifications (if enabled)
        request.setDescription("Android Data download using DownloadManager.");
        //Set the local destination for the downloaded file to a path within the application's external files directory
        request.setDestinationInExternalFilesDir(this, Environment.DIRECTORY_DOWNLOADS, "CountryList.json");

        downloadReference = downloadManager.enqueue(request);
    }

    private BroadcastReceiver downloadReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

            //check if the broadcast message is for our Enqueued download
            long referenceId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            if(downloadReference == referenceId){

            }
        }
    };
}
