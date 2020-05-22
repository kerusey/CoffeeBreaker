package space.fstudio.lio.coffeebreaker.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
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
import space.fstudio.lio.coffeebreaker.NavigationActivity;
import space.fstudio.lio.coffeebreaker.Objects.TokenJsonObject;
import space.fstudio.lio.coffeebreaker.Objects.TokenStatusJsonObject;
import space.fstudio.lio.coffeebreaker.R;

import static space.fstudio.lio.coffeebreaker.Utils.Variables.SERVER_DEFAULT_ADDRESS;
import static space.fstudio.lio.coffeebreaker.Utils.Variables.SERVER_DEFAULT_PORT;

public class ScanActivity extends AppCompatActivity {

    private CodeScanner mCodeScanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CodeScannerView scannerView = new CodeScannerView(this);
        setContentView(scannerView);

        /*  Создаем обЪект для прямоугольного сканера  */
        mCodeScanner = new CodeScanner(this, scannerView);

        /*  Сохранение картинки со сканера decode???  */
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {

                /*  В фоновом потоке  */
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            //               startActivity(new Intent(ScanActivity.this, ChoiceActivity.class));
                            //               finish();
                            jsonToServer(result, "postToken");
                        } catch (Exception e) {
                            Toast.makeText(ScanActivity.this, "Uncorrected QR code", Toast.LENGTH_SHORT).show();
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

        /*  При активации начали показывать картинку  */
        mCodeScanner.startPreview();
    }

    @Override
    protected void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }

    private void jsonToServer(final Result result, final String post) {
        String[] array = result.getText().split("#", 3);
        /* 0 = URL, 1 = ID, 2 = TOKEN */
        String postUrl = "http://" + SERVER_DEFAULT_ADDRESS + ":" + SERVER_DEFAULT_PORT + "/" + post + "/" + array[1];
        RequestBody postBody = null;

        if (post.equals("postToken") || post.equals("postTokenStatus")) {
            MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
            String json;
            if (!post.equals("postTokenStatus"))
                json = new Gson().toJson(new TokenJsonObject(array[2]));
            else
                json = new Gson().toJson(new TokenStatusJsonObject("Scanned"));

            System.out.println("Post: " + json);
            postBody = RequestBody.create(mediaType, json);
        }

        OkHttpClient client = new OkHttpClient();
        Request request;

        if (post.equals("postToken") || post.equals("postTokenStatus")) {
            request = new Request.Builder()
                    .url(postUrl)
                    .post(postBody)
                    .build();
        } else {
            postUrl = "http://" + SERVER_DEFAULT_ADDRESS + ":" + SERVER_DEFAULT_PORT + "/getTokenStatus/" + array[1];
            request = new Request.Builder()
                    .url(postUrl)
                    .get()
                    .build();
        }

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                // Cancel the post on failure.
                call.cancel();

                // In order to access the TextView inside the UI thread, the code is executed inside runOnUiThread()
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ScanActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                });
            }

            @Override
            public void onResponse(final Call call, final Response response) {
                // In order to access the TextView inside the UI thread, the code is executed inside runOnUiThread()
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            assert response.body() != null;
                            String answer = response.body().string();
                            System.out.println(answer);
                            if (answer.equals("#") && post.equals("postToken")) {
                                jsonToServer(result, "postTokenStatus");

                            } else if (answer.equals("#") && post.equals("postTokenStatus")) {
                                jsonToServer(result, "getTokenStatus");

                            } else if (post.equals("getTokenStatus") && answer.equals("SCANNED")) {
                                jsonToServer(result, "getTokenStatus");

                            } else if (post.equals("getTokenStatus") && answer.equals("FAILED")) {
                                startActivity(new Intent(ScanActivity.this, NavigationActivity.class));

                            } else if (post.equals("getTokenStatus") && answer.equals("OK")) {
                                System.out.println("ALL DONE");
                                startActivity(new Intent(ScanActivity.this, ChoiceActivity.class));

                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    @SuppressLint("ResourceType")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {//it is for button skip
        if (item.getItemId() == R.id.menuSkipScan) {
            startActivity(new Intent(ScanActivity.this, ChoiceActivity.class));
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}

