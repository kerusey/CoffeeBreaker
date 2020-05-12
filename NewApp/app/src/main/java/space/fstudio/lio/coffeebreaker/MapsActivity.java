package space.fstudio.lio.coffeebreaker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import androidx.fragment.app.FragmentActivity;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import space.fstudio.lio.coffeebreaker.activities.SettingActivity;
import space.fstudio.lio.coffeebreaker.utils.MapUtil;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

  FloatingActionButton fab;
  BitmapDescriptor icon;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_maps);

    icon = new MapUtil().getBitmapDescriptor(MapsActivity.this);

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

//    /*  Marker example

          googleMap.addMarker(new MarkerOptions()
              .position(new LatLng(x, y))
              .title(name)
              .snippet("Discount 20%")
              .icon(icon));

    googleMap.setOnMarkerClickListener(new OnMarkerClickListener() {
      @Override
      public boolean onMarkerClick(Marker marker) {

        return true;
      }
    });


    fab.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        startActivity(new Intent(MapsActivity.this, SettingActivity.class));
      }
    });
//    */

  }
}
