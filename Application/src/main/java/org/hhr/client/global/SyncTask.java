package org.hhr.client.global;

import android.os.AsyncTask;

import org.json.JSONObject;

/**
 * Created by ke on 8/1/2015.
 */
public class SyncTask extends AsyncTask<JSONObject, Integer, Long> {
    private SyncService syncService;

    private SyncService.Type type;

    public SyncTask(SyncService syncService,SyncService.Type type){
        this.syncService = syncService;
        this.type = type;
    }

    protected Long doInBackground(JSONObject... jsObj) {

        syncService.sync(type,jsObj[0]);
        return new Long(0);
    }

    protected void onProgressUpdate(Integer... progress) {

    }

    protected void onPostExecute(Long result) {

    }
}