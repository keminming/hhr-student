package org.hhr.client.global;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by ke on 7/22/2015.
 */
public class Session {
    private static Session instance = new Session();

    private long loginTime;

    private String username;

    public String getUsername(){return username;}

    public void setUsername(String name){username = username;}

    public long getLoginTime(){return loginTime;}

    public void setLoginTime(long loginTime){this.loginTime = loginTime;}

    private Map<String,Video> watchedVideos = new HashMap<String,Video>();

    private Session(){}

    public static Session getInstance(){return instance;}

    public Map<String,Video> getWatchVideos(){return watchedVideos;}
}
