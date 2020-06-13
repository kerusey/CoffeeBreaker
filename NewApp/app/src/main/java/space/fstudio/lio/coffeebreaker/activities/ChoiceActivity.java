package space.fstudio.lio.coffeebreaker.activities;

import android.os.Bundle;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import space.fstudio.lio.coffeebreaker.R;
import space.fstudio.lio.coffeebreaker.adapters.ViewPagerAdapter;

public class ChoiceActivity extends AppCompatActivity {

  @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//возвращение к навигационной панели (кнопка назад)
        final ViewPager2 pager2 = findViewById(R.id.viewPager2);
        pager2.setAdapter(new ViewPagerAdapter(this));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
