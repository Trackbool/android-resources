package com.example.foregroundservice.utils;

import com.example.foregroundservice.models.FileInfo;
import com.example.foregroundservice.models.FileInfoStatus;

import org.apache.commons.io.FilenameUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class FileUtils {

    public static String getFileExtension(String address) {
        String extension = "";

        try {
            URL url = new URL(address);
            extension = FilenameUtils.getExtension(url.getPath());
        } catch (MalformedURLException e) {
        }

        return extension;
    }

    public static FileInfo getRemoteFileSize(String address) {
        FileInfo fileInfo;
        URLConnection conn = null;
        int contentLength;

        try {
            conn = new URL(address).openConnection();
            if (conn instanceof HttpURLConnection) {
                ((HttpURLConnection) conn).setRequestMethod("HEAD");
            }
            conn.getInputStream();
            contentLength = conn.getContentLength();
            fileInfo = new FileInfo(FileInfoStatus.OK, contentLength);
        } catch (IOException e) {
            fileInfo = new FileInfo(FileInfoStatus.CONNECTION_ERROR, -1);
        } finally {
            if (conn instanceof HttpURLConnection) {
                ((HttpURLConnection) conn).disconnect();
            }
        }

        return fileInfo;
    }
}
