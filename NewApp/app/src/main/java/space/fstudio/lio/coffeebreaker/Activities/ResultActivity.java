package space.fstudio.lio.coffeebreaker.Activities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import java.util.Locale;
import java.util.Random;

import space.fstudio.lio.coffeebreaker.R;

public class ResultActivity extends AppCompatActivity implements View.OnClickListener {

    Bundle bundle;
    String array1;
    Button btn_accept, btn_m, btn_s, btn_v, btn_st;
    TextView txt_sugar, txt_milk, txt_volume, txt_strength, txt_type, txt_price;
    SharedPreferences answer;

    protected void onStart() {
        super.onStart();
        bundle = getIntent().getExtras();
    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})//игорирование английского текста
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        answer = getSharedPreferences("answer", Context.MODE_PRIVATE);

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
        if (answer.getInt("milk", 50) == 100)
            txt_milk.setText("Да");
        else
            txt_milk.setText("Нет");

        txt_volume = findViewById(R.id.txt_volume);

        String type = answer.getString("coffeeType", "MMM");

        txt_volume.setText(answer.getInt("volume", 500) + " МЛ");


        txt_strength = findViewById(R.id.txt_strength);
        if (answer.getInt("strength", -1) <= 0)
            txt_strength.setText("0");
        else
            txt_strength.setText(String.format("%d", answer.getInt("strength", -1)));

        txt_type = findViewById(R.id.txt_coffee_type);
        txt_type.setText(String.format("%s", answer.getString("coffeeType", null)));

        txt_price = findViewById(R.id.txt_price);
        txt_price.setText((new Random().nextInt(999) + 1) + "\u20bd");

        array1 = answer.getString("array", "1");
        /*

         runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    connectServer(SERVER_DEFAULT_ADDRESS, array1);//передаем id кофемашины на сервер (отдельная функция)
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });*/
    }
    /*
 void connectServer(String ipv4Address, String id) {
        String postUrl = "http://" + ipv4Address + ":" + SERVER_DEFAULT_PORT + "/postOrder/" + id;
        SharedPreferences answer = getSharedPreferences("answer", Context.MODE_PRIVATE);
        int milk = answer.getInt("milk", 50);
        int id1 = Integer.parseInt(id);
        String type = answer.getString("coffeeType", "MMM");
        int volume = answer.getInt("volume", 500);
        int strength = answer.getInt("strength", -1);
        int sugar = answer.getInt("sugar", -1);
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        String json = new Gson().toJson(new space.fstudio.lio.coffeebreaker.Objects.OrderJsonObject(id1, type, sugar, milk, volume, strength));
        System.out.println(json);
        RequestBody postBody = RequestBody.create(mediaType, json);
        postRequest(postUrl, postBody);
    }

    void connectServer1(String ipv4Address, String id) {

        String postUrl = "http://" + ipv4Address + ":" + SERVER_DEFAULT_PORT + "/postOrderStatus/" + id;
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        String json = new Gson().toJson(new space.fstudio.lio.coffeebreaker.Objects.OrderStatusJsonObject("COMPLETED"));
        System.out.println(json);
        RequestBody postBody = RequestBody.create(mediaType, json);

        postRequest1(postUrl, postBody);
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
                        Toast.makeText(ResultActivity.this, "Не удалось подключиться к Интернету", Toast.LENGTH_SHORT).show();
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
                            if (otv.equals("#")) {
                                Toast.makeText(ResultActivity.this, "Отправился запрос ", Toast.LENGTH_SHORT).show();
                                //                              startActivity(new Intent(ResultActivity.this, OkayActivity.class));
                            } else {
                                Toast.makeText(ResultActivity.this, "Пересканьте QR-код", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                            GetUrl(SERVER_DEFAULT_ADDRESS, array1);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

    }

    void GetUrl(String ipv4Address, String id) {
        String getUrl = "http://" + ipv4Address + ":" + SERVER_DEFAULT_PORT + "/getOrderStatus/" + id;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(getUrl)
                .get()
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
                        Toast.makeText(ResultActivity.this, "Не удалось подключиться к Интернету", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            assert response.body() != null;
                            String otv = response.body().string();
                            if (otv.equals("COMPLETED")) {

                                new Timer().schedule(
                                        new TimerTask() {

                                            @Override
                                            public void run() {
                                                GetUrl(SERVER_DEFAULT_ADDRESS, array1);

                                            }

                                        }, 2000);

                            } else if (otv.equals("FAILED")) {
                                // startActivity(new Intent(ResultActivity.this, OkayActivity.class));
                            } else if (otv.equals("OK")) {
                                Toast.makeText(ResultActivity.this, "Неправильный QR-код", Toast.LENGTH_SHORT).show();
                                //   startActivity(new Intent(ResultActivity.this, MenuActivity.class));
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
                        Toast.makeText(ResultActivity.this, "Не удалось подключиться к Интернету", Toast.LENGTH_SHORT).show();
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
                            if (otv.equals("#")) {
                                runOnUiThread(new Runnable() {

                                    @Override
                                    public void run() {
                                        try {
                                            connectServer1(SERVER_DEFAULT_ADDRESS, array1);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                                //  startActivity(new Intent(ResultActivity.this, OkayActivity.class));
                            } else {
                                Toast.makeText(ResultActivity.this, "Не удалось подключиться к Интернету", Toast.LENGTH_SHORT).show();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }
     */


    @Override
    public void onClick(View v) {
        FragmentManager manager = getSupportFragmentManager();
        switch (v.getId()) {
            case R.id.btn_accept:
                //   startActivity(new Intent(ResultActivity.this, OkayActivity.class));
                break;
            case R.id.btn_s:
                ShowSugarDialog();
//                SugarDialogFragment sugarDialogFragment = new SugarDialogFragment();
//                sugarDialogFragment.show(manager, "myDialog");
//                txt_sugar.setText(String.format(Locale.getDefault(), "%d", answer.getInt("sugar", -1)));
                break;
            case R.id.btn_m:
                ShowMilkDialog();
                break;
            case R.id.btn_v:
                ShowVolumeDialog();

                break;
            case R.id.btn_st:
                ShowStrengthDialog();

                break;
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public void ShowMilkDialog() {
        String title = "Молоко ";
        String button1String = "Да";
        String button2String = "Нет";
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);  // заголовок
        // добавляем переключатели
        builder.setPositiveButton(button1String, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Toast.makeText(ResultActivity.this, "Кофе с молоком", Toast.LENGTH_LONG).show();
                txt_milk.setText("Да");
            }
        });


        builder.setNegativeButton(button2String, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Toast.makeText(ResultActivity.this, "Кофе без молока", Toast.LENGTH_LONG).show();
                txt_milk.setText("Нет");
            }
        });
        builder.setCancelable(true);
        builder.create();
        builder.show();
    }

    public void ShowVolumeDialog() {
        int volume_M = 0;
        int volume_B = 0;
        String title = "Выберите размер стакана: ";
        String button1String = "Маленькая";
        String button2String = "Большая";
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);  // заголовок
        // добавляем переключатели

        String type = answer.getString("coffeeType", "MMM");

        switch (type) {
            case "Espresso":
                volume_M = 30;
                volume_B = 60;
                break;
            case "Latte Macchiato":
                volume_M = 200;
                volume_B = 400;
                break;
            case "Crema":
                volume_M = 100;
                volume_B = 200;
                break;
            case "Cappuccino":
                volume_M = 200;
                volume_B = 300;
                break;
            case "Coffee":
                volume_M = 200;
                volume_B = 400;
                break;
        }

        final int finalVolume_M = volume_M;
        final int finalVolume_B = volume_B;
        builder.setPositiveButton(button1String, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Toast.makeText(ResultActivity.this, "Выбрали размер кружки", Toast.LENGTH_SHORT).show();

                txt_volume.setText(String.format(Locale.getDefault(), "%s", finalVolume_M + " МЛ"));
            }
        });
        builder.setNegativeButton(button2String, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Toast.makeText(ResultActivity.this, "Выбрали размер кружки", Toast.LENGTH_SHORT).show();
                txt_volume.setText(String.format(Locale.getDefault(), "%s", finalVolume_B + " МЛ"));
            }
        });
        builder.setCancelable(true);
        builder.create();
        builder.show();
    }


    public void ShowStrengthDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final SeekBar seek = new SeekBar(this);
        seek.setMax(7);
        String title = "Выберите крепкость: ";
        builder.setTitle(title);  // заголовок
        builder.setView(seek);
        seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                txt_strength.setText(String.format(Locale.getDefault(), "%d", progress));
            }

            public void onStartTrackingTouch(SeekBar arg0) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }

        });
        builder.setPositiveButton("OK",

                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.create();
        builder.show();

    }

    public void ShowSugarDialog() {
        final String[] numberOfTeespoonsOfShugar = {"1", "2", "3", "4", "5"};
        String title = "Выберите количество чайных ложек сахара: ";
        String button1String = "OK";
        String button2String = "Cancel";
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);  // заголовок
        // добавляем переключатели
        builder.setSingleChoiceItems(numberOfTeespoonsOfShugar, -1,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        txt_sugar.setText(String.format(Locale.getDefault(), "%s", numberOfTeespoonsOfShugar[item] + " ч.л."));
                        Toast.makeText(ResultActivity.this, "Вы выбрали " + numberOfTeespoonsOfShugar[item] + " ч.л.", Toast.LENGTH_SHORT).show();
                    }
                });

        builder.setPositiveButton(button1String, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Toast.makeText(ResultActivity.this, "Изменения сохранены", Toast.LENGTH_LONG).show();
            }
        });
        builder.setNegativeButton(button2String, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Toast.makeText(ResultActivity.this, "Изменение отменено", Toast.LENGTH_LONG).show();
            }
        });
        builder.setCancelable(true);
        builder.create();
        builder.show();
    }
}





