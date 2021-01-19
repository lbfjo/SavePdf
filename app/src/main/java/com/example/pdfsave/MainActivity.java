
package com.example.pdfsave;


import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import es.voghdev.pdfviewpager.library.PDFViewPager;
import es.voghdev.pdfviewpager.library.adapter.BasePDFPagerAdapter;
import es.voghdev.pdfviewpager.library.adapter.PDFPagerAdapter;

public class MainActivity extends BaseSampleActivity {
    PDFViewPager pdfViewPager;
    BasePDFPagerAdapter adapter;
    Button btnLoad, btnDeepLink;
    String sharedFolder = "default";
    final int REQUEST_CODE = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("MainAppPdf");
        setContentView(R.layout.activity_main);
        requestExternalStoragePermissions();
        btnLoad = findViewById(R.id.loadFile);
        btnDeepLink = findViewById(R.id.deepLink);
        pdfViewPager = findViewById(R.id.pdfViewPager);


        btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    File defaultFolder = new File(Environment.getExternalStorageDirectory(),sharedFolder);
                    if(!defaultFolder.exists()) {
                        defaultFolder.mkdirs();
                    }
                } catch (Exception e) {
                    e.printStackTrace();  Log.e("IO","IO"+e);
                }
                requestPermissionsThenOpen(AssetOnSDActivity.class);
            }
        });
        btnDeepLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File pdfFolder = new File(Environment.getExternalStorageDirectory(),sharedFolder);
                Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.example.printingapp");
                if (launchIntent != null) {
                    launchIntent.putExtra("path",pdfFolder.getAbsolutePath());
                    startActivity(launchIntent);//null pointer check in case package name was not found
                }
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        adapter.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_sample2) {
            launchActivity(RemotePDFActivity.class);
            return false;
        } else if (id == R.id.action_sample3) {
            requestPermissionsThenOpen(AssetOnSDActivity.class);
            return false;
        } else if (id == R.id.action_sample4) {
            Toast.makeText(this, R.string.dummy_msg, Toast.LENGTH_LONG).show();
        } else if (id == R.id.action_sample5) {
            requestPermissionsThenOpen(AssetOnXMLActivity.class);
        } else if (id == R.id.action_sample8) {
            requestPermissionsThenOpen(ZoomablePDFActivityPhotoView.class);
        } else if (id == R.id.action_sample9) {
            launchActivity(PDFWithScaleActivity.class);
        } else if (id == R.id.action_sample10) {
            launchActivity(InvalidPdfActivity.class);
        }

        return super.onOptionsItemSelected(item);
    }

    protected boolean hasExternalStoragePermissions() {
        boolean hasReadPermission = ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED;

        boolean hasWritePermission = ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED;

        return hasReadPermission && hasWritePermission;
    }

    protected void requestPermissionsThenOpen(Class activityClass) {
        if (hasExternalStoragePermissions()) {
            launchActivity(activityClass);
        } else {
            requestExternalStoragePermissions();
        }
    }

    protected void requestExternalStoragePermissions() {
        String[] permissions = {
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
        };

        ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE);
    }

    protected void launchActivity(Class activityClass) {
        Intent i = new Intent(this, activityClass);
        startActivity(i);
    }
}
