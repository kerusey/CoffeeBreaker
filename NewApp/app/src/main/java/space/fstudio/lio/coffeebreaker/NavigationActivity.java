package space.fstudio.lio.coffeebreaker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import space.fstudio.lio.coffeebreaker.Activities.ScanActivity;

public class NavigationActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnScan, btnSetting, btnExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        btnScan = findViewById(R.id.btnScan);
        btnSetting = findViewById(R.id.btnSettings);
        btnExit = findViewById(R.id.btnExit);

        btnScan.setOnClickListener(this);
        btnSetting.setOnClickListener(this);
        btnExit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnScan:
                startActivity(new Intent(NavigationActivity.this, ScanActivity.class));
                break;
            case R.id.btnSettings:
                Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnExit:
                finish();
                break;
        }
    }
}
