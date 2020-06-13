package space.fstudio.lio.coffeebreaker;

import android.Manifest.permission;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import space.fstudio.lio.coffeebreaker.activities.SettingActivity;
import space.fstudio.lio.coffeebreaker.utils.MapUtil;
import space.fstudio.lio.coffeebreaker.utils.VariableConstants;

public class MapsActivity extends AppCompatActivity {

  private ActionBar toolbar;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_maps);

    if (getSupportActionBar() != null)
      toolbar = getSupportActionBar();

    /*  Permission requesting for 6 and after's  */
    if (VERSION.SDK_INT >= 23 && checkSelfPermission(permission.ACCESS_FINE_LOCATION)
        != PackageManager.PERMISSION_GRANTED) {
      ActivityCompat
          .requestPermissions(this, new String[]{permission.ACCESS_FINE_LOCATION},
              VariableConstants.ACCESS_FINE_LOCATION_REQUEST_CODE);
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

        googleMap.setOnMarkerClickListener(marker -> {
          toolbar.setSubtitle(marker.getTitle());
          return true;
        });
      });
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return super.onCreateOptionsMenu(menu);
  }

  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    if (item.getItemId() == R.id.action_settings) {
      startActivity(new Intent(this, SettingActivity.class));
      return true;
    }
    return super.onOptionsItemSelected(item);
  }
}
