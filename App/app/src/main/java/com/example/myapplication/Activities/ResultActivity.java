package com.example.myapplication.Activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

import java.util.Random;

public class ResultActivity extends AppCompatActivity implements View.OnClickListener {
    Bundle bundle;
    String[] array1;
    Button btn_accept, btn_m, btn_s, btn_v, btn_st;
    TextView txt_sugar, txt_milk, txt_volume, txt_strength, txt_type, txt_price;

    protected void onStart() {
        super.onStart();
        bundle = getIntent().getExtras();
    }
    @SuppressLint({"SetTextI18n", "DefaultLocale"})//игорирование английского текста
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        SharedPreferences answer = getSharedPreferences("answer", Context.MODE_PRIVATE);

        btn_accept = findViewById(R.id.btn_accept);
        btn_m = findViewById(R.id.btn_m);
        btn_s = findViewById(R.id.btn_s);
        btn_v = findViewById(R.id.btn_v);
        btn_st = findViewById(R.id.btn_st);
        txt_sugar = findViewById(R.id.txt_sugar);

        btn_accept.setOnClickListener(ResultActivity.this);
        btn_m.setOnClickListener(ResultActivity.this);
        btn_s.setOnClickListener(ResultActivity.this);
        btn_v.setOnClickListener(ResultActivity.this);
        btn_st.setOnClickListener(ResultActivity.this);

        txt_sugar.setText(String.format("%d", answer.getInt("sugar", -1)));

        txt_milk = findViewById(R.id.txt_milk);
        if (!answer.getBoolean("milk", true))
            txt_milk.setText("Нет");
        else
            txt_milk.setText("Да");

        txt_volume = findViewById(R.id.txt_volume);
        if (answer.getFloat("volume", -0F) != 0.2F)
            txt_volume.setText("0.4 Л");
        else
            txt_volume.setText("0.2 Л");

        txt_strength = findViewById(R.id.txt_strength);
        if (answer.getInt("strength", -1) <= 0)
            txt_strength.setText("0");
        else
            txt_strength.setText(String.format("%d", answer.getInt("strength", -1)));

        txt_type = findViewById(R.id.txt_coffee_type);
        txt_type.setText(String.format("%s", answer.getString("coffeeType", null)));

        txt_price = findViewById(R.id.txt_price);
        txt_price.setText((new Random().nextInt(999) + 1) + "\u20bd");

        array1 = bundle.getStringArray("array");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_accept:
                startActivity(new Intent(ResultActivity.this, OkayActivity.class));
                break;
            case R.id.btn_s:
                startActivity(new Intent(ResultActivity.this, SugarActivity.class).putExtra("editMode", true));
                break;
            case R.id.btn_m:
                startActivity(new Intent(ResultActivity.this, MilkActivity.class).putExtra("editMode", true));
                break;
            case R.id.btn_v:
                startActivity(new Intent(ResultActivity.this, VolumeActivity.class).putExtra("editMode", true));
                break;
            case R.id.btn_st:
                startActivity(new Intent(ResultActivity.this, StrengthActivity.class));
                break;
        }
    }


}
/*

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
        String[] array = result.getText().split("#", 3);
        /* 0 = URL, 1 = ID, 2 = TIME */
//        connectServer(array[0], array[1]);*\
/*connectServer(SERVER_DEFAULT_ADDRESS, array[1]);
}
private void jsonToServer1(String status_order){

        connectServer(SERVER_DEFAULT_ADDRESS,status_order );
        }
        void connectServer(String ipv4Address, String id) {
        String postUrl = "http://" + ipv4Address + ":" + SERVER_DEFAULT_PORT + "/postOrder/" + id;

        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        String json = new Gson().toJson(new com.example.myapplication.Object.Result(this, 1, "C", 1, false, 0, 1));
        System.out.println(json);
        RequestBody postBody = RequestBody.create(mediaType, json);

        postRequest(postUrl, postBody);
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
        System.out.println("======DEBUG======");
        System.out.println(response.body().string());
        System.out.println("======DEBUG======");
        if(response.body().string()=="#"){//????????????????????????
        runOnUiThread(new Runnable() {
@Override
public void run() {
        try {
//                            scannerStuck(result);
        jsonToServer1("Scaned");//передаем статус
        } catch (Exception e) {
        mCodeScanner.startPreview();
        e.printStackTrace();
        }
        }
        });
        startActivity(new Intent(ScanActivity.this, ChoiceActivity.class));
        }
        else {
        startActivity(new Intent(ScanActivity.this, MenuActivity.class));
        //                              Toast.makeText(MenuActivity.class , "Не удалось подключиться к Интернету", Toast.LENGTH_SHORT).show();
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
 */