package space.fstudio.lio.coffeebreaker.utils.managers;

import androidx.appcompat.app.AppCompatDelegate;

public final class ThemeManager {

  public static void setDarkTheme(Boolean darkTheme) {
    if (!darkTheme)
      AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    else
      AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
  }
}


