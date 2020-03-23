package space.fstudio.lio.coffeebreaker.Activities;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import space.fstudio.lio.coffeebreaker.Adapters.ViewPagerAdapter;
import space.fstudio.lio.coffeebreaker.R;

public class ChoiceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ViewPager2 pager2 = findViewById(R.id.viewPager2);
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
