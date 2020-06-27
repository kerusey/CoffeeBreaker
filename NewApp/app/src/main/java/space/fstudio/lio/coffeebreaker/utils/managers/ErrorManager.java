package space.fstudio.lio.coffeebreaker.utils.managers;

import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import androidx.appcompat.app.AlertDialog.Builder;

public final class ErrorManager {

  public final static int ERR_CONSTANTS_MARKER_REQUEST_FROM_SERVER = 113;
  public final static int ERR_CONSTANTS_PERMISSION_DENIED = 321;

  public static void errorAlert(Context context, int code, String btnText, OnClickListener listener) {
    new Builder(context).setTitle("Something went wrong")
        .setMessage("Error code is: (" + code + ")")
        .setCancelable(true)
        .setNeutralButton(btnText, listener)
        .create()
        .show();
  }
}
