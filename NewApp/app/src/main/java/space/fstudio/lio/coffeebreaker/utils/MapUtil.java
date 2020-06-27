package space.fstudio.lio.coffeebreaker.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.json.JSONObject;
import space.fstudio.lio.coffeebreaker.R;
import space.fstudio.lio.coffeebreaker.objects.Location;
import space.fstudio.lio.coffeebreaker.utils.managers.ErrorManager;

public class MapUtil {

  public BitmapDescriptor getBitmapDescriptor(Context context) {

    Drawable vectorDrawable = context.getDrawable(R.drawable.ic_map_marker);

    if (vectorDrawable != null) {

      int h = vectorDrawable.getIntrinsicHeight();
      int w = vectorDrawable.getIntrinsicHeight();

      Bitmap bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);

      vectorDrawable.setBounds(0, 0, w, h);
      vectorDrawable.draw(new Canvas(bm));

      return BitmapDescriptorFactory.fromBitmap(bm);

    }

    return new MarkerOptions().getIcon();

  }

  public void markerLocationsFromJSON(Activity activity, GoogleMap map) {

    /*  JSON parsing  */
    ExecutorService executorService = Executors.newSingleThreadExecutor();
    executorService.submit(() -> {

      try (BufferedReader reader = new BufferedReader(new InputStreamReader(
          new URL("http://chaos.fstudio.space:8000/get/CoffeeHouses/json-as-list").openStream()))) {

        Gson gson = new Gson();

        /*  Create massive of location's  */
        Location[] locations = gson
            .fromJson(String.valueOf(new JSONObject(reader.readLine()).getJSONArray("locations")),
                Location[].class);

        if (locations != null) {
          for (Location l : locations) {

            /*  Markers generation  */
            if (activity != null) {
              activity.runOnUiThread(() -> map.addMarker(new MarkerOptions()
                  .position(new LatLng(l.xCoord, l.yCoord))
                  .title(l.name)
                  .icon(getBitmapDescriptor(activity))));
            }
          }
        }
      } catch (Exception e) {
        if (activity != null) {
          activity.runOnUiThread(() -> {
            map.addMarker(new MarkerOptions()
                .position(new LatLng(59.9329475F, 30.3511607F))
                .title("Offline point")
                .icon(getBitmapDescriptor(activity)));
            ErrorManager.errorAlert(activity, ErrorManager.ERR_CONSTANTS_MARKER_REQUEST_FROM_SERVER,
                "reload", (dialogInterface, i) -> markerLocationsFromJSON(activity, map));
          });
        }
      }

    });

    executorService.shutdown();
  }
}
