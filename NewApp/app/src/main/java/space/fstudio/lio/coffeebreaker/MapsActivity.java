package space.fstudio.lio.coffeebreaker;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import androidx.core.app.ActivityCompat;
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
import space.fstudio.lio.coffeebreaker.utils.Variables;

public class MapsActivity extends FragmentActivity {

    FloatingActionButton fab;
    BitmapDescriptor icon;
    MapUtil mapUtil = new MapUtil();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Variables.ACCESS_FINE_LOCATION_REQUEST_CODE);
            }
        }

        icon = mapUtil.getBitmapDescriptor(MapsActivity.this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        fab = findViewById(R.id.fab);
        if (mapFragment != null) {
            mapFragment.getMapAsync(googleMap -> {

                googleMap.setMyLocationEnabled(true);
                googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

                //TODO Map location initialization

                try {
                    mapUtil.loadMarkersFromJSON(googleMap, icon);
                } catch (Exception e) {
                    e.printStackTrace();
                    finish();
                }

                fab.setOnClickListener(v -> startActivity(new Intent(MapsActivity.this, SettingActivity.class)));

            });
        }
    }
}
