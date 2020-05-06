package space.fstudio.lio.coffeebreaker.Fragments;

import android.content.Context;
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

import space.fstudio.lio.coffeebreaker.R;

public class SugarChoiceFragment extends Fragment implements View.OnClickListener {
    SharedPreferences answer;
    Button btn_0, btn_1, btn_2, btn_3, btn_4;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sugar_choice, container, false);
        answer = getActivity().getSharedPreferences("answer", Context.MODE_PRIVATE);
        btn_0 = view.findViewById(R.id.btn_0);
        btn_1 = view.findViewById(R.id.btn_1);
        btn_2 = view.findViewById(R.id.btn_2);
        btn_3 = view.findViewById(R.id.btn_3);
        btn_4 = view.findViewById(R.id.btn_4);
        btn_0.setOnClickListener(this);
        btn_1.setOnClickListener(this);
        btn_2.setOnClickListener(this);
        btn_3.setOnClickListener(this);
        btn_4.setOnClickListener(this);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        SharedPreferences.Editor editor = answer.edit();
        switch (v.getId()) {
            case R.id.btn_0:
                Toast.makeText(getActivity(), "Выбрали кофе без сахара", Toast.LENGTH_SHORT).show();
                editor.putInt("sugar", 0);  //Устанавливаем переменной btnValue значения true
                break;
            case R.id.btn_1:
                Toast.makeText(getActivity(), "Выбрали кофе с 1 лож.сахара", Toast.LENGTH_SHORT).show();
                editor.putInt("sugar", 1);  //Устанавливаем переменной btnValue значения true
                break;
            case R.id.btn_2:
                Toast.makeText(getActivity(), "Выбрали кофе с 2 лож.сахара", Toast.LENGTH_SHORT).show();
                editor.putInt("sugar", 2);  //Устанавливаем переменной btnValue значения true
                break;
            case R.id.btn_3:
                Toast.makeText(getActivity(), "Выбрали кофе с 3 лож.сахара", Toast.LENGTH_SHORT).show();
                editor.putInt("sugar", 3);  //Устанавливаем переменной btnValue значения true
                break;
            case R.id.btn_4:
                Toast.makeText(getActivity(), "Выбрали кофе с 4 лож.сахара", Toast.LENGTH_SHORT).show();
                editor.putInt("sugar", 4);  //Устанавливаем переменной btnValue значения true
                break;
        }
        editor.apply();// применить измиенения и закрытт редактор

    }
}
