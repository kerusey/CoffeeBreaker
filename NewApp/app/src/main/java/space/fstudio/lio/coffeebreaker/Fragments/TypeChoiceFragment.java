package space.fstudio.lio.coffeebreaker.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import space.fstudio.lio.coffeebreaker.Objects.CoffeeTypeObject;
import space.fstudio.lio.coffeebreaker.R;

public class TypeChoiceFragment extends Fragment {

    private CoffeeTypeRecyclerAdapter coffeeTypeRecyclerAdapter;
    private ArrayList<CoffeeTypeObject> coffeeTypesList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_type_choice, container, false);

        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).setSubtitle("Choice coffee type");

        RecyclerView recyclerView = view.findViewById(R.id.recyclerCoffeeTypes);

        coffeeTypeRecyclerAdapter = new CoffeeTypeRecyclerAdapter(coffeeTypesList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setAdapter(coffeeTypeRecyclerAdapter);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        prepareCoffeeTypes();
    }

    private void prepareCoffeeTypes() {
        CoffeeTypeObject coffeeTypeObject = new CoffeeTypeObject(
                R.drawable.espresso,
                getString(R.string.coffeeTypeEspresso),
                getString(R.string.coffeeDescriptionEspresso));
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

        coffeeTypeObject = new CoffeeTypeObject(
                R.drawable.caffe_latte,
                getString(R.string.coffeeTypeCaffeLatte),
                getString(R.string.coffeeDescriptionCaffeLatte));
        coffeeTypesList.add(coffeeTypeObject);

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

        coffeeTypeObject = new CoffeeTypeObject(
                R.drawable.hot_water,
                getString(R.string.coffeeTypeHotWater),
                getString(R.string.coffeeDescriptionHotWater));
        coffeeTypesList.add(coffeeTypeObject);

        coffeeTypeRecyclerAdapter.notifyDataSetChanged();
    }
}
