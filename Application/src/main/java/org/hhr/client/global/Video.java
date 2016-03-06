package org.hhr.client.global;

import android.net.Uri;

/**
 * Created by ke on 7/22/2015.
 */
public class Video implements Comparable<Video>{
    private String name;

    private Uri uri;

    private int count = 1;

    private double maxPercentage = 0;

    public Video(){}

    public String getName(){return name;}

    public void setName(String name){this.name = name;}

    public int getCount(){return count;}

    public void setCount(int count){this.count = count;}

    public void setPercentage(double percentage){
        if(percentage > maxPercentage){
            maxPercentage = percentage;
        }
    }

    public double getMaxPercentage(){return maxPercentage;}

    public void setUri(Uri uri){this.uri = uri;}

    public Uri getUri(){return uri;}

    public String toString(){
        return name;
    }

    public int compareTo(Video v){
        return this.getName().compareTo(v.getName());
    }
}
