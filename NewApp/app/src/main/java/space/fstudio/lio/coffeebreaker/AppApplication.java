package space.fstudio.lio.coffeebreaker;

import android.app.Application;
import androidx.preference.PreferenceManager;
import space.fstudio.lio.coffeebreaker.utils.managers.ThemeManager;

public class AppApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();
      ThemeManager.setDarkTheme(PreferenceManager.getDefaultSharedPreferences(this).getBoolean("dark_theme_switch_pref", false));
  }
}
