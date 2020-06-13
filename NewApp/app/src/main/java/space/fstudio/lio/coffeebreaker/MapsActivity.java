package space.fstudio.lio.coffeebreaker;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import space.fstudio.lio.coffeebreaker.activities.SettingActivity;
import space.fstudio.lio.coffeebreaker.utils.MapUtil;
import space.fstudio.lio.coffeebreaker.utils.Variables;

public class MapsActivity extends FragmentActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_maps);

    /*  Permission requesting for 6 and after's  */
    if (Build.VERSION.SDK_INT >= 23) {
      if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
          != PackageManager.PERMISSION_GRANTED) {
        ActivityCompat
            .requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                Variables.ACCESS_FINE_LOCATION_REQUEST_CODE);
      }
    }

    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
        .findFragmentById(R.id.map);

    if (mapFragment != null) {
      mapFragment.getMapAsync(googleMap -> {

        googleMap.setMyLocationEnabled(true);

        /*  Setup map view type  */
        googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        /*  Load markers using MapUtil's  */
        new MapUtil().loadMarkersFromJSON(this, googleMap);

        /*  Go to setting's window when FAB pressed  */
        findViewById(R.id.fab).setOnClickListener(
            v -> startActivity(new Intent(MapsActivity.this, SettingActivity.class)));
      });
    }
  }
}
