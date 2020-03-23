package space.fstudio.lio.coffeebreaker.Activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;

import space.fstudio.lio.coffeebreaker.Adapters.ViewPagerAdapter;
import space.fstudio.lio.coffeebreaker.Fragments.TypeChoiceFragment;
import space.fstudio.lio.coffeebreaker.R;

public class ChoiceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);

        ViewPager2 pager2 = findViewById(R.id.viewPager2);

        List<Fragment> list = new ArrayList<>();
        list.add(new TypeChoiceFragment());

        pager2.setAdapter(new ViewPagerAdapter(this));

    }
}
