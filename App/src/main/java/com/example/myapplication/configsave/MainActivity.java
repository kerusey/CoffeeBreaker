package space.fstudio.configsave;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;
import space.fstudio.configsave.Objects.Result;
import space.fstudio.configsave.Utils.ConfigManager;

public class MainActivity extends AppCompatActivity {

  ConfigManager cfgManager;

  @Override
  protected void onStart() {
    super.onStart();

    ConfigManager.permissionCheck(this);   //Запрашиваем права

  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    Gson gson = new Gson();

     cfgManager = new ConfigManager(this, gson.toJson(new Result(1,"C", "SU", "M", 0F, 0F, "ST")), "somefile");  //Создаём конфиг

  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    ConfigManager.onRequestPermissionsResult(this, requestCode, grantResults);   //Получаем резхультат проверки прав

  }
}
