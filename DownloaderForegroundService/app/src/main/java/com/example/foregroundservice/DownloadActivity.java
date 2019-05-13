package com.example.foregroundservice;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.foregroundservice.utils.StorageUtils;

public class DownloadActivity extends AppCompatActivity {
    private EditText mAddress;
    private Button mDownload;
    private Button mFinish;
    private Button mClearAddress;

    private Intent mServiceIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        mAddress = findViewById(R.id.address);
        mDownload = findViewById(R.id.download);
        mFinish = findViewById(R.id.finish);
        mClearAddress = findViewById(R.id.clear);

        setListeners();
    }

    private void setListeners() {
        mDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StorageUtils.externalStorageIsAvailable()) {
                    mServiceIntent = new Intent(DownloadActivity.this, DownloadForegroundService.class);
                    mServiceIntent.putExtra(DownloadForegroundService.DOWNLOAD_ADDRESS, mAddress.getText().toString().trim());
                    startService(mServiceIntent);
                } else {
                    Snackbar.make(v, StorageUtils.EXTERNAL_STORAGE_ERROR, Snackbar.LENGTH_LONG).show();
                }
            }
        });

        mFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mClearAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAddress.setText("");
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(mServiceIntent);
    }
}
