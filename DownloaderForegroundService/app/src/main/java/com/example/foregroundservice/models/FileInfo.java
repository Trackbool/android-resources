package com.example.foregroundservice.models;

public class FileInfo {
    private FileInfoStatus mFileInfoStatus;
    private int mFileSize;

    public FileInfo(FileInfoStatus fileInfoStatus, int fileSize) {
        mFileInfoStatus = fileInfoStatus;
        mFileSize = fileSize;
    }

    public FileInfoStatus getFileInfoStatus() {
        return mFileInfoStatus;
    }

    public int getFileSize() {
        return mFileSize;
    }
}
