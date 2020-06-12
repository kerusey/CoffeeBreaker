package space.fstudio.lio.coffeebreaker.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import space.fstudio.lio.coffeebreaker.R;
import space.fstudio.lio.coffeebreaker.objects.Location;

public class MapUtil {

  public BitmapDescriptor getBitmapDescriptor(Context context) {

    Drawable vectorDrawable = context.getDrawable(R.drawable.ic_map_marker);

    if (vectorDrawable != null) {

      int h = vectorDrawable.getIntrinsicHeight();
      int w = vectorDrawable.getIntrinsicHeight();

      Bitmap bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);

      Canvas canvas = new Canvas(bm);

      vectorDrawable.setBounds(0, 0, w, h);
      vectorDrawable.draw(canvas);

      Toast.makeText(context, "WORK", Toast.LENGTH_SHORT).show();
      return BitmapDescriptorFactory.fromBitmap(bm);

    }

    Toast.makeText(context, "NOT WORK", Toast.LENGTH_SHORT).show();
    return null;

  }

  public void loadMarkersFromJSON(GoogleMap map , BitmapDescriptor icon) throws Exception {

    /*  JSON parsing  */
//    String json = readUrl("http://chaos.fstudio.space:8000/get/CoffeeHouses/json-as-list");
//
//    Gson gson = new Gson();
//    Location location = gson.fromJson(json, Location.class);
//
//    System.out.println(location.name);
//    System.out.println(location.xCoord);
//    System.out.println(location.yCoord);

    /*  Markers generation  */
//    map.addMarker(new MarkerOptions()
//            .position(new LatLng(1, 1))
//            .title("")
//            .icon(icon));
  }

}
