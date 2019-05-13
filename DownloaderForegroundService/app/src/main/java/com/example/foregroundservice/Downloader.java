package com.example.foregroundservice;

import com.example.foregroundservice.models.FileInfo;
import com.example.foregroundservice.models.FileInfoStatus;
import com.example.foregroundservice.utils.FileUtils;
import com.example.foregroundservice.utils.StorageUtils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Date;

public class Downloader {
    private static final int BUFFER_SIZE = 1024 * 60;
    private String mAddress;
    private int mProgress;
    private FileInfo mFileInfo;
    private Runnable mOnReady;
    private Runnable mOnStart;
    private Runnable mOnDownloadSuccess;
    private Runnable mOnDownloadFail;

    public Downloader(String address) {
        this.mAddress = address;
    }

    public int getProgress() {
        return mProgress;
    }

    public boolean isProgressAvailable() {
        return mFileInfo != null && mFileInfo.getFileSize() > -1;
    }

    public void download() {
        mProgress = 0;

        try {
            mFileInfo = FileUtils.getRemoteFileSize(mAddress);

            if (mFileInfo.getFileInfoStatus() == FileInfoStatus.OK) {
                if (mOnReady != null) {
                    mOnReady.run();
                }

                downloadFile();
            }
        } catch (IOException e) {
            if (mOnDownloadFail != null) {
                mOnDownloadFail.run();
            }
            mProgress = -1;
        }
    }

    private void downloadFile() throws IOException {
        long totalBytesRead = 0;

        try (
            InputStream inputFile = new URL(mAddress).openStream();
            OutputStream outputFile = new FileOutputStream(StorageUtils.DOWNLOAD_PATH + "/" +
                generateOutputFileName())
        ) {
            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead;

            if (mOnStart != null) {
                mOnStart.run();
            }

            while ((bytesRead = inputFile.read(buffer, 0, BUFFER_SIZE)) > 0) {
                outputFile.write(buffer, 0, bytesRead);
                totalBytesRead += bytesRead;
                mProgress = calculateProgressPercentage(totalBytesRead);
            }
            mProgress = 100;
            if (mOnDownloadSuccess != null) {
                mOnDownloadSuccess.run();
            }
        }
    }

    private int calculateProgressPercentage(long totalBytesRead){
        return (int) Math.floor(((float) totalBytesRead / mFileInfo.getFileSize()) * 100);
    }

    private String generateOutputFileName() {
        String extension = FileUtils.getFileExtension(mAddress);
        extension = (extension.isEmpty()) ? "out" : extension;
        String fileName = String.valueOf(new Date().getTime()) + "." + extension;

        return fileName;
    }

    public void setOnReady(Runnable onReady) {
        mOnReady = onReady;
    }

    public void setOnStart(Runnable onStart) {
        mOnStart = onStart;
    }

    public void setOnSuccess(Runnable onSuccess) {
        this.mOnDownloadSuccess = onSuccess;
    }

    public void setOnFail(Runnable onFail) {
        this.mOnDownloadFail = onFail;
    }
}
