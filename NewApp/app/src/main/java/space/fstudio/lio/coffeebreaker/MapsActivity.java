package space.fstudio.lio.coffeebreaker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import androidx.fragment.app.FragmentActivity;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import space.fstudio.lio.coffeebreaker.activities.SettingActivity;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

  FloatingActionButton fab;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_maps);

    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
        .findFragmentById(R.id.map);

    fab = findViewById(R.id.fab);

    if (mapFragment != null) {
      mapFragment.getMapAsync(this);
    }
  }

  @Override
  public void onMapReady(final GoogleMap googleMap) {

    googleMap.setMyLocationEnabled(true);
    googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

    //TODO Map location initialization

    /*  Marker example

    BitmapDescriptor icon = new MapUtil().getBitmapDescriptor(this);

    googleMap.addMarker(new MarkerOptions()
        .position(new LatLng(37.4629101, -122.2449094))
        .title("Coffee Breaker Oy")
        .snippet("Discount 20%")
        .icon(icon));

    googleMap.setOnMarkerClickListener(new OnMarkerClickListener() {
      @Override
      public boolean onMarkerClick(Marker marker) {
        Toast.makeText(MapsActivity.this, marker.getTitle() + "\n" + marker.getSnippet(), Toast.LENGTH_SHORT).show();
        return true;
      }
    });

    */

    fab.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        startActivity(new Intent(MapsActivity.this, SettingActivity.class));
      }
    });
  }
}
