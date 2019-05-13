package com.example.foregroundservice.utils;

import android.os.Environment;

public class StorageUtils {

    public static final String DOWNLOAD_PATH = Environment
        .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
    public static final String EXTERNAL_STORAGE_ERROR = "External storage is not available";

    public static boolean externalStorageIsAvailable() {
        return Environment.getExternalStorageState() != null;
    }



}
