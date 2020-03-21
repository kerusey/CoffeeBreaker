package com.example.myapplication.Activities;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.example.myapplication.Object.StatusScan;
import com.google.gson.Gson;
import com.google.zxing.Result;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.myapplication.Utils.Variables.SERVER_DEFAULT_ADDRESS;
import static com.example.myapplication.Utils.Variables.SERVER_DEFAULT_PORT;

public class ScanActivity extends AppCompatActivity {
    Result result;
    CodeScanner mCodeScanner;
    CodeScannerView scannerView;
    String[] array;

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
                    @Override
                    public void run() {
                        try {
//                            scannerStuck(result);
                            jsonToServer(result);//передаем id кофемашины на сервер (отдельная функция)
                        } catch (Exception e) {
                            Toast.makeText(ScanActivity.this, "Некоректный QR код", Toast.LENGTH_SHORT).show();
                            mCodeScanner.startPreview();
                            e.printStackTrace();
                        }
                    }
                });
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
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            System.out.println("this's landscape");
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            System.out.println("this's portrait");
        }
    }

    @SuppressLint("SetTextI18n")
    private void scannerStuck(Result result) {//заглушка
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        TextView textView = new TextView(ScanActivity.this);
        textView.setPadding(16, 16, 16, 16);
        builder.setView(textView);

        String[] array = result.getText().split("#", 3);
        textView.setText("Ссылка: " + array[0] + "\nИндификатор: " + array[1] + "\nДата: " + array[2]);

        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                mCodeScanner.startPreview();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void jsonToServer(Result result) {
        array = result.getText().split("#", 3);
        /* 0 = URL, 1 = ID, 2 = TIME */
//        connectServer(array[0], array[1]);
        connectServer(SERVER_DEFAULT_ADDRESS, array[1], array[2]);
    }

    private void jsonToServer1(String[] array) {


        connectServer1(SERVER_DEFAULT_ADDRESS, array[1]);
    }


    void connectServer(String ipv4Address, String id, String token) {
        String postUrl = "http://" + ipv4Address + ":" + SERVER_DEFAULT_PORT + "/postToken/" + id;

        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        String json = new Gson().toJson(new com.example.myapplication.Object.Token(this, token));
        System.out.println(json);
        RequestBody postBody = RequestBody.create(mediaType, json);

        postRequest(postUrl, postBody);
    }

    void connectServer1(String ipv4Address, String id) {
        String postUrl = "http://" + ipv4Address + ":" + SERVER_DEFAULT_PORT + "/postTokenStatus/" + id;

        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        String json = new Gson().toJson(new StatusScan(this, "Scaned"));
        System.out.println(json);
        RequestBody postBody = RequestBody.create(mediaType, json);

        postRequest1(postUrl, postBody);
    }

    void connectServer2(String ipv4Address, String id) {
        System.out.println("УРРРРРРРРАААААААААААААААА");
    }

    void postRequest1(String postUrl, RequestBody postBody) {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(postUrl)
                .post(postBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, final IOException e) {
                // Cancel the post on failure.
                call.cancel();

                // In order to access the TextView inside the UI thread, the code is executed inside runOnUiThread()
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        e.printStackTrace();
                        Toast.makeText(ScanActivity.this, "Не удалось подключиться к Интернету", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response1) throws IOException {
                // In order to access the TextView inside the UI thread, the code is executed inside runOnUiThread()

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            assert response1.body() != null;
                            String otv = response1.body().string();
                            System.out.println("======DEBUG======");
                            System.out.println(otv);
                            System.out.println("======DEBUG======");
                            if (otv.equals("#")) {
                                startActivity(new Intent(ScanActivity.this, ChoiceActivity.class).putExtra("array", array));
                            } else {
                                Toast.makeText(ScanActivity.this, "Пересканьте QR-код", Toast.LENGTH_SHORT).show();
                                finish();
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }


    void postRequest(String postUrl, RequestBody postBody) {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(postUrl)
                .post(postBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, final IOException e) {
                // Cancel the post on failure.
                call.cancel();

                // In order to access the TextView inside the UI thread, the code is executed inside runOnUiThread()
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        e.printStackTrace();
                        Toast.makeText(ScanActivity.this, "Не удалось подключиться к Интернету", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                // In order to access the TextView inside the UI thread, the code is executed inside runOnUiThread()
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            assert response.body() != null;
                            String otv = response.body().string();
                            System.out.println(otv);
                            System.out.println("======DEBUG======");
                            System.out.println(otv);
                            System.out.println("======DEBUG======");

                            if (otv.equals("#")) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
//
                                            jsonToServer1(array);//передаем статус
                                        } catch (Exception e) {
                                            mCodeScanner.startPreview();
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            } else {
                                Toast.makeText(ScanActivity.this, "Не удалось подключиться к Интернету", Toast.LENGTH_SHORT).show();


                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }
}
