//package com.example.myapplication;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.Manifest;
//import android.content.pm.PackageManager;
//import android.os.Build;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Toast;
//
//import com.budiyev.android.codescanner.CodeScanner;
//import com.budiyev.android.codescanner.CodeScannerView;
//import com.budiyev.android.codescanner.DecodeCallback;
//import com.google.zxing.Result;
//
//public class ScanActivity extends AppCompatActivity {
//
//    private static final int CAMERA_PERMISSION_REQUEST_CODE = 1;
//    private CodeScanner mCodeScanner;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_scan);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//                requestPermissions(new String[]{Manifest.permission.CAMERA}, 1);
//            }
//        }
//        CodeScannerView scannerView = findViewById(R.id.scanner_view);
//        mCodeScanner = new CodeScanner(ScanActivity.this, scannerView);
//        mCodeScanner.setDecodeCallback(new DecodeCallback() {
//            @Override
//            public void onDecoded(@NonNull final Result result) {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Toast.makeText(ScanActivity.this, result.getText(), Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//        });
//        scannerView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mCodeScanner.startPreview();
//            }
//        });
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        mCodeScanner.startPreview();
//    }
//
//    @Override
//    protected void onPause() {
//        mCodeScanner.releaseResources();
//        super.onPause();
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
//        switch (requestCode) {
//            case CAMERA_PERMISSION_REQUEST_CODE:
//                // If request is cancelled, the result arrays are empty.
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    //Start your camera handling here
//                } else {
//                    Toast.makeText(ScanActivity.this, "Error", Toast.LENGTH_SHORT).show();
//                }
//        }
//    }
//}