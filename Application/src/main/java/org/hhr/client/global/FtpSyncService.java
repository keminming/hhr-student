package org.hhr.client.global;

import android.content.Context;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by ke on 7/29/2015.
 */
public class FtpSyncService implements SyncService {

    private Context context;

    private HashMap<Type,String> typeToEndpointMap = new HashMap<Type,String>();

    public FtpSyncService(Context context){this.context = context;
        typeToEndpointMap.put(Type.LOGIN,"login");
        typeToEndpointMap.put(Type.VIDEO,"videos");
    }

    @Override
    public void sync(SyncService.Type type, JSONObject data) {
        String endpoint = typeToEndpointMap.get(type) + "_" + System.currentTimeMillis() + ".txt";

        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect("ftp.godanddeaf.org");
            ftpClient.login("hhrODKforms", "anonymous1.");
            ftpClient.changeWorkingDirectory("/hhr/");
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            String filename = "login.txt";
            String string = data.toString();
            FileOutputStream outputStream;

            try {
                outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
                outputStream.write(string.getBytes());
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            BufferedInputStream bf = new BufferedInputStream(new FileInputStream(context.getFilesDir() + "/" + filename));
            ftpClient.enterLocalPassiveMode();
            ftpClient.storeFile(endpoint, bf);
            bf.close();
            ftpClient.logout();
            ftpClient.disconnect();
        }catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
