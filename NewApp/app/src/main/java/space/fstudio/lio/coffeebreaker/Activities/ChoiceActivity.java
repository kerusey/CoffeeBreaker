package space.fstudio.lio.coffeebreaker.Activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import java.util.List;

import space.fstudio.lio.coffeebreaker.Adapters.CoffeeTypeRecyclerAdapter;
import space.fstudio.lio.coffeebreaker.Adapters.ViewPagerAdapter;
import space.fstudio.lio.coffeebreaker.Objects.CoffeeTypeObject;
import space.fstudio.lio.coffeebreaker.R;
public class ChoiceActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<CoffeeTypeObject> coffeeTypesList;
    private String type;
    private CoffeeTypeRecyclerAdapter coffeeTypeRecyclerAdapter;
    SharedPreferences answer;

    private void defValues() {
        answer = getSharedPreferences("answer", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = answer.edit();
        editor.putString("coffeeType", "NO");
        editor.putInt("sugar", 10);
        editor.putInt("volume", 500);
        editor.putInt("strength", 10);
        editor.apply();
        int sugar = answer.getInt("sugar", 10);
        float volume = answer.getInt("volume", 500);
        int strength = answer.getInt("strength", 10);
        String coffeeType = answer.getString("coffeeType", "NO");
        System.out.println(sugar + "\r\n" + volume + "\r\n" + strength + "\r\n" + coffeeType);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);
        defValues();
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
