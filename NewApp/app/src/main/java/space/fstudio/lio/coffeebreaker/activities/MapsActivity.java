package space.fstudio.lio.coffeebreaker.activities;

import android.Manifest.permission;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import space.fstudio.lio.coffeebreaker.R;
import space.fstudio.lio.coffeebreaker.utils.MapUtil;

public class MapsActivity extends AppCompatActivity {

  private GoogleMap googleMap;
  private int mapValue;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_maps);

    /*  Get map type from saved settings  */
    mapValue = Integer.parseInt(PreferenceManager
        .getDefaultSharedPreferences(this).getString("map_type_list_pref", "4"));

    /*  Init map on view from SupportMapFragment  */
    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
        .findFragmentById(R.id.map);

    /*  Check mapFragment is not null  */
    if (mapFragment != null) {

      mapFragment.getMapAsync(googleMap -> {

        this.googleMap = googleMap;

        if (VERSION.SDK_INT >= 23 && checkSelfPermission(permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED)
          googleMap.setMyLocationEnabled(true);

        /*  Setup map view type  */
        googleMap.setMapType(mapValue);

        /*  Load markers using MapUtil's  */
        new MapUtil().markerLocationsFromJSON(this, googleMap);
      });
    }
  }

  protected void onResume() {
    /*  Reset map type on resumed to activity  */
    if (googleMap != null) {
      mapValue = Integer.parseInt(PreferenceManager
          .getDefaultSharedPreferences(this).getString("map_type_list_pref", "4"));
      googleMap.setMapType(mapValue);
    }
    super.onResume();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return super.onCreateOptionsMenu(menu);
  }

  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    if (item.getItemId() == R.id.action_settings) {
      startActivity(new Intent(this, SettingsActivity.class));
      return true;
    }
    return super.onOptionsItemSelected(item);
  }
}
