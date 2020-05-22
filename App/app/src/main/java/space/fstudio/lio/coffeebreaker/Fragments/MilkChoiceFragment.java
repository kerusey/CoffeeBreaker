package space.fstudio.lio.coffeebreaker.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import space.fstudio.lio.coffeebreaker.Activities.ResultActivity;
import space.fstudio.lio.coffeebreaker.R;

public class MilkChoiceFragment extends Fragment implements View.OnClickListener {

    Button btn_yes, btn_no;
    private SharedPreferences answer;
    int sugar;
    float volume;
    int strength;
    String coffeeType;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_milk_choice, container, false);// файл разметки;  родительский элемент разметки, откуда будут взяты LayoutParams для загружаемой вьюхи
        answer = getActivity().getSharedPreferences("answer", Context.MODE_PRIVATE);
        btn_yes = view.findViewById(R.id.btn_yes);
        btn_no = view.findViewById(R.id.btn_no);

        btn_yes.setOnClickListener(this);
        btn_no.setOnClickListener(this);
        sugar = answer.getInt("sugar", 10);
        volume = answer.getFloat("volume", 0.5F);
        strength = answer.getInt("strength", 10);
        coffeeType = answer.getString("coffeeType", "NO");
        System.out.println(sugar + "\r\n" + volume + "\r\n" + strength + "\r\n" + coffeeType);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void checking() {

        if (sugar == 10 || volume == 0.5F || strength == 10 || coffeeType.equals("NO")) {
            if (sugar == 10) {
                Toast.makeText(getActivity(), "Сахар не выбран ", Toast.LENGTH_SHORT).show();
            }
            if (volume == 0.5F) {
                Toast.makeText(getActivity(), "Объём не выбран ", Toast.LENGTH_SHORT).show();
            }
            if (strength == 10) {
                Toast.makeText(getActivity(), "Крепкость не выбрана ", Toast.LENGTH_SHORT).show();
            }
            if (coffeeType.equals("NO")) {
                Toast.makeText(getActivity(), "Тип кофе не выбран ", Toast.LENGTH_SHORT).show();
            }
        } else {
            startActivity(new Intent(getActivity(), ResultActivity.class));
        }

    }

    @Override
    public void onClick(View v) {
        SharedPreferences.Editor editor = answer.edit();
        switch (v.getId()) {
            case R.id.btn_yes:
                editor.putBoolean("milk", true);
                Toast.makeText(getActivity(), "Выбрали кофе с молоком", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_no:
                editor.putBoolean("milk", false);
                Toast.makeText(getActivity(), "Выбрали кофе без молока ", Toast.LENGTH_SHORT).show();
                break;
        }
        editor.apply();
        checking();
    }
}
