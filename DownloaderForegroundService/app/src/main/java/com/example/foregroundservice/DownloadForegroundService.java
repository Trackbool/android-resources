package com.example.foregroundservice;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;

public class DownloadForegroundService extends Service {
    public static final String DOWNLOAD_ADDRESS = "DOWNLOAD_ADDRESS";
    private Downloader mDownloader;
    private DownloadStatus mDownloadStatus = DownloadStatus.NOT_STARTED;
    private boolean mDownloadFinished = false;

    private static final String HANDLER_THREAD_NAME = "DownloadProcess";
    private HandlerThread mHandlerThread;
    private Looper mLooper;

    private static final int NOTIFICATION_ID = 1000;
    private Notification.Builder mNotificationBuilder;
    private NotificationManager mNotificationManager;


    public DownloadForegroundService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        initializeNotificationBuilder();

        String address = intent.getStringExtra(DOWNLOAD_ADDRESS);
        doTask(address);

        return Service.START_NOT_STICKY;
    }

    private void initializeNotificationBuilder() {
        mNotificationBuilder = new Notification.Builder(this);
        mNotificationBuilder.setContentTitle("Dummy download")
            .setTicker("Dummy download")
            .setContentText("Downloading...")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setProgress(0, 0, false)
            .setOngoing(true);
    }

    private void doTask(final String address) {
        initializeDownloader(address);
        mDownloadFinished = false;

        mHandlerThread = new HandlerThread(HANDLER_THREAD_NAME);
        mHandlerThread.start();
        mLooper = mHandlerThread.getLooper();
        new Thread(new Runnable() {
            @Override
            public void run() {
                mDownloader.download();
            }
        }).start();
    }

    private void initializeDownloader(String address) {
        mDownloader = new Downloader(address);
        mDownloader.setOnReady(new Runnable() {
            @Override
            public void run() {
                mDownloadStatus = DownloadStatus.READY;
                startForeground(NOTIFICATION_ID, mNotificationBuilder.build());
                if (mDownloader.isProgressAvailable()) {
                    initProgressCheckerThread();
                } else {
                    setIndeterminateProgress();
                }
            }
        });
        mDownloader.setOnSuccess(new Runnable() {
            @Override
            public void run() {
                mDownloadStatus = DownloadStatus.SUCCESS;
                onDownloadFinish();
            }
        });
        mDownloader.setOnFail(new Runnable() {
            @Override
            public void run() {
                mDownloadStatus = DownloadStatus.FAIL;
                onDownloadFinish();
            }
        });
    }

    private void onDownloadFinish() {
        stopForeground(true);
        mDownloadFinished = true;
        notifyDownloadStatus();
    }

    private void initProgressCheckerThread() {
        final Handler checkProgress = new Handler(mLooper);
        checkProgress.post(new Runnable() {
            @Override
            public void run() {
                if (!mDownloadFinished) {
                    checkProgress.postDelayed(this, 100);
                    notifyCurrentProgress();
                }
            }
        });
    }

    private void notifyCurrentProgress() {
        mNotificationBuilder.setProgress(100, mDownloader.getProgress(), false);
        mNotificationManager.notify(NOTIFICATION_ID, mNotificationBuilder.build());
    }

    private void setIndeterminateProgress() {
        mNotificationBuilder.setProgress(0, 0, true);
        mNotificationManager.notify(NOTIFICATION_ID, mNotificationBuilder.build());
    }

    private void notifyDownloadStatus() {
        if (mDownloadStatus == DownloadStatus.SUCCESS) {
            mNotificationBuilder.setContentText("Download succeeded")
                .setProgress(100, 100, false)
                .setOngoing(false);
        } else if (mDownloadStatus == DownloadStatus.FAIL) {
            mNotificationBuilder.setContentText("Download failed")
                .setProgress(100, 0, false)
                .setOngoing(false);
        }
        mNotificationManager.notify(NOTIFICATION_ID, mNotificationBuilder.build());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandlerThread.quit();
    }

    enum DownloadStatus {
        NOT_STARTED, READY, STARTED, SUCCESS, FAIL, CANCELED
    }
}
