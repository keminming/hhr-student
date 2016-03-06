package org.hhr.client.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.hhr.client.R;
import org.hhr.client.global.Session;
import org.hhr.client.global.SyncService;
import org.hhr.client.global.SyncServiceFactory;
import org.hhr.client.global.SyncTask;
import org.hhr.client.global.Video;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by ke on 7/23/2015.
 */
public class VideoFragment extends Fragment {

    List<Video> videoList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState){
        final View rootView = inflater.inflate(R.layout.video_list,container,false);
        final ListView listView = (ListView)rootView.findViewById(R.id.listView);
        List<Video> videoList = loadVideo();
        Video[] Videos = new Video[videoList.size()];
        listView.setAdapter(new ArrayAdapter<Video>(rootView.getContext(), R.layout.video_item,videoList.toArray(Videos)));

        listView.setTextFilterEnabled(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Video video = (Video) listView.getItemAtPosition(position);

                syncData(rootView.getContext(),video);

                Intent intent = new Intent(getActivity(), org.hhr.client.activity.VideoPlayerActivity.class);
                intent.putExtra("path", video.getUri().toString());
                startActivity(intent);
            }
        });
        return rootView;
    }

    private void syncData(Context context,Video video){
        SyncService syncService = SyncServiceFactory.getSyncService(context);
        Session session = Session.getInstance();
        JSONObject jsObj = new JSONObject();
        try{
            jsObj.put("user",session.getUsername());
            jsObj.put("video_name", video.getName());
            jsObj.put("watch_time", new SimpleDateFormat().format(new Date(System.currentTimeMillis())));
        }catch(org.json.JSONException e){
            e.printStackTrace();
        }

        new SyncTask(syncService, SyncService.Type.VIDEO).execute(jsObj);
    }

    private List<Video> loadVideo(){

        String path = Environment.getExternalStorageDirectory() + "/hhr/";
        File f = new File(path);  //
        for(File file : f.listFiles()){
            final Uri videoUri = Uri.fromFile(file);
            Video video = new Video();
            video.setName(file.getName());
            video.setUri(videoUri);
            videoList.add(video);
        }

        Collections.sort(videoList);
        return videoList;
    }
}
