package com.example.myapplication;

        import androidx.appcompat.app.AppCompatActivity;

        import android.content.Intent;
        import android.graphics.Color;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {
    Button btn_scan_qr, btn_machine_near, btn_my_drinks, btn_price_list, btn_options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        btn_scan_qr = findViewById(R.id.btn_scan_qr);
        btn_scan_qr.setOnClickListener(MenuActivity.this);

        btn_machine_near = findViewById(R.id.btn_machine_near);
        btn_machine_near.setOnClickListener(MenuActivity.this);

        btn_my_drinks = findViewById(R.id.btn_my_drinks);
        btn_my_drinks.setOnClickListener(MenuActivity.this);

        btn_price_list = findViewById(R.id.btn_price_list);
        btn_price_list.setOnClickListener(MenuActivity.this);

        btn_options = findViewById(R.id.btn_options);
        btn_options.setOnClickListener(MenuActivity.this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_scan_qr:
                startActivity(new Intent(MenuActivity.this, ScanActivity.class));
                break;
            case R.id.btn_machine_near:
                break;
            case R.id.btn_options:
                btn_options.setText("Паша иди нафиг");
                btn_options.setTextColor(Color.argb(100, 238, 42, 27));
                btn_options.setBackgroundColor(Color.parseColor("#333333"));
                break;
            case R.id.btn_my_drinks:
                break;
            case R.id.btn_price_list:
                break;
        }

    }
}
