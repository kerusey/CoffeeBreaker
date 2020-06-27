package space.fstudio.lio.coffeebreaker.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;
import space.fstudio.lio.coffeebreaker.R;
import space.fstudio.lio.coffeebreaker.utils.managers.ThemeManager;

public class SettingsActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.settings_activity);

    if (savedInstanceState == null) {
      getSupportFragmentManager()
          .beginTransaction()
          .replace(R.id.settings, new SettingsFragment())
          .commit();
    }

    if (getSupportActionBar() != null) {
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

  }

  public static class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
      setPreferencesFromResource(R.xml.root_preferences, rootKey);

      SwitchPreference switchPreference = getPreferenceManager()
          .findPreference("dark_theme_switch_pref");

      if (switchPreference != null) {
        switchPreference.setOnPreferenceChangeListener((preference, newValue) -> {
          ThemeManager.setDarkTheme((Boolean) newValue);
          return true;
        });
      }

    }
  }

  @Override
  public boolean onSupportNavigateUp() {
    onBackPressed();
    return true;
  }
}