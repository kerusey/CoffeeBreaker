package space.fstudio.lio.coffeebreaker.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Objects;

import space.fstudio.lio.coffeebreaker.Adapters.CoffeeTypeRecyclerAdapter;
import space.fstudio.lio.coffeebreaker.Adapters.RecyclerTouchListener;
import space.fstudio.lio.coffeebreaker.Objects.CoffeeTypeObject;
import space.fstudio.lio.coffeebreaker.R;

public class TypeChoiceFragment extends Fragment {
    SharedPreferences answer;
    private CoffeeTypeRecyclerAdapter coffeeTypeRecyclerAdapter;
    private ArrayList<CoffeeTypeObject> coffeeTypesList = new ArrayList<>();
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_type_choice, container, false);//обозначаем, куда будем загружать объекты
        answer = getActivity().getSharedPreferences("answer", Context.MODE_PRIVATE);
        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).setSubtitle("Choice coffee type");//установка второго названия

        recyclerView = view.findViewById(R.id.recyclerCoffeeTypes);

        coffeeTypeRecyclerAdapter = new CoffeeTypeRecyclerAdapter(coffeeTypesList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setAdapter(coffeeTypeRecyclerAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                final CoffeeTypeObject coffeeTypeObject = coffeeTypesList.get(position);
                String type = coffeeTypeObject.getCoffeeName();
                SharedPreferences.Editor editor = answer.edit();
                editor.putString("coffeeType", type);
                Toast.makeText(getActivity(), type, Toast.LENGTH_SHORT).show();
                editor.apply();

            }
        }));
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        prepareCoffeeTypes();


    }

    private void prepareCoffeeTypes() {
        CoffeeTypeObject coffeeTypeObject = new CoffeeTypeObject(
                R.drawable.espresso,//картинка кофе
                getString(R.string.coffeeTypeEspresso),//название
                getString(R.string.coffeeDescriptionEspresso));// описание кофе
        coffeeTypesList.add(coffeeTypeObject);

        coffeeTypeObject = new CoffeeTypeObject(
                R.drawable.coffee,
                getString(R.string.coffeeTypeCoffee),
                getString(R.string.coffeeDescriptionCoffee));
        coffeeTypesList.add(coffeeTypeObject);

        coffeeTypeObject = new CoffeeTypeObject(
                R.drawable.cappuccino,
                getString(R.string.coffeeTypeCappuccino),
                getString(R.string.coffeeDescriptionCappuccino));
        coffeeTypesList.add(coffeeTypeObject);

        coffeeTypeObject = new CoffeeTypeObject(
                R.drawable.latte_macchiato,
                getString(R.string.coffeeTypeLatteMacchiato),
                getString(R.string.coffeeDescriptionLatteMacchiato));
        coffeeTypesList.add(coffeeTypeObject);

//        coffeeTypeObject = new CoffeeTypeObject(
//                R.drawable.caffe_latte,
//                getString(R.string.coffeeTypeCaffeLatte),
//                getString(R.string.coffeeDescriptionCaffeLatte));
//        coffeeTypesList.add(coffeeTypeObject);

        coffeeTypeObject = new CoffeeTypeObject(
                R.drawable.milk_froth,
                getString(R.string.coffeeTypeMilkFroth),
                getString(R.string.coffeeDescriptionMilkFroth));
        coffeeTypesList.add(coffeeTypeObject);

        coffeeTypeObject = new CoffeeTypeObject(
                R.drawable.warm_milk,
                getString(R.string.coffeeTypeWarmMilk),
                getString(R.string.coffeeDescriptionWarmMilk));
        coffeeTypesList.add(coffeeTypeObject);

//        coffeeTypeObject = new CoffeeTypeObject(
//                R.drawable.hot_water,
//                getString(R.string.coffeeTypeHotWater),
//                getString(R.string.coffeeDescriptionHotWater));
//        coffeeTypesList.add(coffeeTypeObject);

        coffeeTypeRecyclerAdapter.notifyDataSetChanged();//устанавливаем изменения
    }
}
