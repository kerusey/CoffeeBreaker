package space.fstudio.lio.coffeebreaker.activities;

import static space.fstudio.lio.coffeebreaker.utils.сonstants.VariableConstants.ACCESS_FINE_LOCATION_REQUEST_CODE;

import android.Manifest.permission;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import space.fstudio.lio.coffeebreaker.R;
import space.fstudio.lio.coffeebreaker.utils.managers.ErrorManager;

public class SplashActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    /* Force navigation button's hide  */
    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

    setContentView(R.layout.activity_splash);

    /*  Permission requesting for 6 and after's  */
    if (checkSelfPermission(permission.ACCESS_FINE_LOCATION)
        != PackageManager.PERMISSION_GRANTED) {
      ActivityCompat.requestPermissions(this, new String[]{permission.ACCESS_FINE_LOCATION},
          ACCESS_FINE_LOCATION_REQUEST_CODE);
    } else {
      startActivity(new Intent(this, MapsActivity.class));
      finish();
    }
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    if (requestCode == ACCESS_FINE_LOCATION_REQUEST_CODE) {
      if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
        startActivity(new Intent(this, MapsActivity.class));
        finish();
      } else {
        if (ActivityCompat
            .shouldShowRequestPermissionRationale(this, permission.ACCESS_FINE_LOCATION)) {
          ActivityCompat.requestPermissions(this,
              new String[]{permission.ACCESS_FINE_LOCATION}, ACCESS_FINE_LOCATION_REQUEST_CODE);
        } else {
          ErrorManager.errorAlert(this, ErrorManager.ERR_CONSTANTS_PERMISSION_DENIED, "exit",
              (dialogInterface, i) -> finish());
        }
      }
    }
  }
}