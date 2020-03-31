package space.fstudio.lio.coffeebreaker.Activities;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//возвращение к навигационной панели (кнопка назад)
        //    View view = inflater.inflate(R.layout.fragment_type_choice, container, false);//обозначаем, куда будем загружать объекты

        //   Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).setSubtitle("Choice coffee type");
        final ViewPager2 pager2 = findViewById(R.id.viewPager2);
        pager2.setAdapter(new ViewPagerAdapter(this));
        //  recyclerView = findViewById(R.id.recyclerCoffeeTypes);

        //    coffeeTypeRecyclerAdapter = new CoffeeTypeRecyclerAdapter(coffeeTypesList);
        //    recyclerView.setHasFixedSize(true);
        //     RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        //     recyclerView.setLayoutManager(layoutManager);
        //    recyclerView.setItemAnimator(new DefaultItemAnimator());
        //    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        //  recyclerView.setLayoutManager(mLayoutManager);
        //   recyclerView.setAdapter(coffeeTypeRecyclerAdapter);
//        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
//            @Override
//            public void onClick(View view, int position) {
//                final CoffeeTypeObject coffeeTypeObject = coffeeTypesList.get(position);
//                type = coffeeTypeObject.getCoffeeName();
//        //        pager2.setAdapter(new ViewPagerAdapter(MilkChoiceFragment));//переход к следующей активности
//            }
//
//        }));
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
/*

public class MainActivity extends AppCompatActivity {


    protected void onCreate(Bundle savedInstanceState) {








        prepareMovieData();
    }

    /**
     * Prepares sample data to provide data set to adapter


private void prepareMovieData() {
    Movie movie = new Movie("Mad Max: Fury Road", "Action & Adventure", "2015");
    movieList.add(movie);

    movie = new Movie("Inside Out", "Animation, Kids & Family", "2015");
    movieList.add(movie);

    movie = new Movie("Star Wars: Episode VII - The Force Awakens", "Action", "2015");
    movieList.add(movie);

    movie = new Movie("Shaun the Sheep", "Animation", "2015");
    movieList.add(movie);

    movie = new Movie("The Martian", "Science Fiction & Fantasy", "2015");
    movieList.add(movie);

    movie = new Movie("Mission: Impossible Rogue Nation", "Action", "2015");
    movieList.add(movie);

    movie = new Movie("Up", "Animation", "2009");
    movieList.add(movie);

    movie = new Movie("Star Trek", "Science Fiction", "2009");
    movieList.add(movie);

    movie = new Movie("The LEGO Movie", "Animation", "2014");
    movieList.add(movie);

    movie = new Movie("Iron Man", "Action & Adventure", "2008");
    movieList.add(movie);

    movie = new Movie("Aliens", "Science Fiction", "1986");
    movieList.add(movie);

    movie = new Movie("Chicken Run", "Animation", "2000");
    movieList.add(movie);

    movie = new Movie("Back to the Future", "Science Fiction", "1985");
    movieList.add(movie);

    movie = new Movie("Raiders of the Lost Ark", "Action & Adventure", "1981");
    movieList.add(movie);

    movie = new Movie("Goldfinger", "Action & Adventure", "1965");
    movieList.add(movie);

    movie = new Movie("Guardians of the Galaxy", "Science Fiction & Fantasy", "2014");
    movieList.add(movie);

    // notify adapter about data set changes
    // so that it will render the list with new data
    mAdapter.notifyDataSetChanged();
}

}
 */