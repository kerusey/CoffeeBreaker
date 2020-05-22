package space.fstudio.lio.coffeebreaker.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import space.fstudio.lio.coffeebreaker.R;

public class StrengthChoiceFragment extends Fragment {
    Button btn_strong;
    SeekBar skb_straight;
    SharedPreferences answer;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_strength_choice, container, false);
        answer = getActivity().getSharedPreferences("answer", Context.MODE_PRIVATE);
        btn_strong = view.findViewById(R.id.btn_strong);
        btn_strong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Выбрали крепкость напитка", Toast.LENGTH_SHORT).show();
            }
        });
        skb_straight = view.findViewById(R.id.skb_straight);
        skb_straight.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                SharedPreferences.Editor editor = answer.edit();               //Включаем режим редактирования файла
                editor.putInt("strength", progress);  //Устанавливаем переменной skbValue значения получаемое с SeekBar
                editor.apply();                       //Сохроняем настройки

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


}
