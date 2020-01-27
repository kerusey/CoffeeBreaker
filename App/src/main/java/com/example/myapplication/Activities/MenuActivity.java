package com.example.myapplication.Activities;

        import androidx.appcompat.app.AppCompatActivity;

        import android.Manifest;
        import android.content.Intent;
        import android.content.pm.PackageManager;
        import android.graphics.Color;
        import android.os.Build;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.Toast;

        import com.example.myapplication.R;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {
    Button btn_scan_qr, btn_machine_near, btn_my_drinks, btn_price_list, btn_options;
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 1;

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

    //TODO перекинуть на мои напитки и вымышленный прайс лист
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_scan_qr:

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.CAMERA}, 1);
                    }
                    else {
                        startActivity(new Intent(MenuActivity.this, ScanActivity.class));
                    }
                }

                break;
            case R.id.btn_machine_near:
                break;
            case R.id.btn_options:
                btn_options.setText("Паша умница и красавица" );
                btn_options.setTextColor(Color.argb(100, 238, 42, 27));
                btn_options.setBackgroundColor(Color.parseColor("#333333"));
                break;
            case R.id.btn_my_drinks:
                break;
            case R.id.btn_price_list:
                startActivity(new Intent(MenuActivity.this, ChoiceActivity.class));
                break;
        }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case CAMERA_PERMISSION_REQUEST_CODE:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Start your camera handling here
                } else {
                    Toast.makeText(MenuActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
        }
    }
}
