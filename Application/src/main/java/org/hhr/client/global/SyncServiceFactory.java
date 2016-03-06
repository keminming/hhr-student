package org.hhr.client.global;

import android.content.Context;

/**
 * Created by ke on 7/29/2015.
 */
public class SyncServiceFactory {
    static public SyncService getSyncService(Context context){return new FtpSyncService(context);}
}
