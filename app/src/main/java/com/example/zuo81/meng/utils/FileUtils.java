package com.example.zuo81.meng.utils;

import android.content.Context;
import android.os.Environment;

import com.example.zuo81.meng.R;
import com.example.zuo81.meng.app.App;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;

/**
 * Created by zuo81 on 2017/11/22.
 */

public class FileUtils {

    private static String getAppDir() {
        //return Environment.getExternalStorageDirectory() +File.separator + "Meng" + File.separator;
        return Environment.getExternalStorageDirectory() +File.separator + App.getInstance().getApplicationContext().getString(R.string.app_name) + File.separator;
    }

    private static String mkdirs(String dir) {
        File file = new File(dir);
        if (!file.exists()) {
            file.mkdirs();
        }
        return dir;
    }

    public static String getExternalFileDir(String name) {
        String dir = getAppDir() + File.separator + name + File.separator;
        return mkdirs(dir);
    }

    public static String getInternalFileDir(Context context, String path) {
        String dir = context.getFilesDir() + File.separator + path + File.separator;
        return mkdirs(dir);
    }

    public static void restoreFromBackUp(String fromPath, String toPath) {
        try {
            FileChannel inChannel = new FileInputStream(new File(fromPath)).getChannel();
            FileChannel outChannel = new FileOutputStream(new File(toPath)).getChannel();
            inChannel.transferTo(0, inChannel.size(), outChannel);
            inChannel.close();
            outChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean writeInputStreamToDisk(InputStream inputStream, String path, String name) {
        try {
            // todo change the file location/name according to your needs
            File futureStudioIconFile = new File(new File(path), name);

            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                //long fileSize = body.contentLength();
                //long fileSizeDownloaded = 0;

                //inputStream = body.byteStream();
                outputStream = new FileOutputStream(futureStudioIconFile);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    //fileSizeDownloaded += read;
//                    Logger.d("file download: " + fileSizeDownloaded + " of " + fileSize);
                }

                outputStream.flush();

                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }
}
