package space.fstudio.lio.coffeebreaker.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.widget.Toast;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import space.fstudio.lio.coffeebreaker.R;

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

}
