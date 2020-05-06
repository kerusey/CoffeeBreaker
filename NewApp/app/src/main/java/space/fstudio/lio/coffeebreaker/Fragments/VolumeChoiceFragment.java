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


public class VolumeChoiceFragment extends Fragment implements View.OnClickListener {
    SharedPreferences answer;
    Button btnM, btnB;
    Bundle bundle;
    int volume_M;
    int volume_B;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_volume_choise, container, false);//обозначаем, куда будем загружать объекты
        answer = getActivity().getSharedPreferences("answer", Context.MODE_PRIVATE);
        bundle = getActivity().getIntent().getExtras();
        SharedPreferences answer = getActivity().getSharedPreferences("answer", Context.MODE_PRIVATE);
        String type = answer.getString("coffeeType", "MMM");
        switch (type) {
            case "Espresso":
                volume_M = 30;
                volume_B = 60;
                break;
            case "Latte Macchiato":
                volume_M = 200;
                volume_B = 400;
                break;
            case "Crema":
                volume_M = 100;
                volume_B = 200;
                break;
            case "Cappuccino":
                volume_M = 200;
                volume_B = 300;
                break;
            case "Coffee":
                volume_M = 200;
                volume_B = 400;
                break;
        }
        btnM = view.findViewById(R.id.btnM);
        btnB = view.findViewById(R.id.btnB);

        btnM.setOnClickListener(this);
        btnB.setOnClickListener(this);
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
            case R.id.btnM:
                Toast.makeText(getActivity(), "Выбрали размер кружки", Toast.LENGTH_SHORT).show();
                editor.putInt("volume", volume_M);//Включаем режим редактирования файла
                break;
            case R.id.btnB:
                Toast.makeText(getActivity(), "Выбрали размер кружки", Toast.LENGTH_SHORT).show();
                editor.putInt("volume", volume_B);//Включаем режим редактирования файла
                break;
        }
        editor.apply();

    }
}
