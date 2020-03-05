package com.example.myapplication.Activities;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.example.myapplication.R;
import com.google.zxing.Result;

public class ScanActivity extends AppCompatActivity {

    CodeScanner mCodeScanner;
    static final int CAMERA_PERMISSION_REQUEST_CODE = 1;
    View view;
    CodeScannerView scannerView;
    WebView webView;

    @Override
    protected void onStart() {
        super.onStart();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, 1);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode
                == CAMERA_PERMISSION_REQUEST_CODE) {// If request is cancelled, the result arrays are empty.
            if (grantResults.length <= 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                finish();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scannerView = new CodeScannerView(ScanActivity.this);
        setContentView(scannerView);
        mCodeScanner = new CodeScanner(this, scannerView);
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                runOnUiThread(new Runnable() {
                    @SuppressLint({"SetJavaScriptEnabled", "SetTextI18n"})
                    @Override
                    public void run() {
                        LayoutInflater inflater = getLayoutInflater();

                        AlertDialog.Builder builder = new AlertDialog.Builder(ScanActivity.this);
                        View view = inflater.inflate(R.layout.dialog_stuck, null);
                        builder.setView(view);
                        TextView txt_parsed_url_data= view.findViewById(R.id.txt_parsed_url_data);
                        String[] array= result.getText().split("#",3);

                        txt_parsed_url_data.setText("Ссылка: "+array[0]+"\nИндификатор: "+array[1]+"\nДата: "+array[2]);

                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();

                    }
                });
            }
        });

        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCodeScanner.startPreview();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        mCodeScanner.startPreview();
    }

    @Override
    protected void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        // TODO Auto-generated method stub
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            System.out.println("this's landscape");

        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            System.out.println("this's portrait");
        }
    }

    @Override
    public void onBackPressed() {
        if (view == webView) {
            mCodeScanner.startPreview();
            setContentView(scannerView);
        }
    }
}
