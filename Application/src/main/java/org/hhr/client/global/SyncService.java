package org.hhr.client.global;

import org.json.JSONObject;

/**
 * Created by ke on 7/29/2015.
 */
public interface SyncService {
    public static enum Type{
        LOGIN,
        VIDEO
    }
    void sync(SyncService.Type type, JSONObject data);
}
