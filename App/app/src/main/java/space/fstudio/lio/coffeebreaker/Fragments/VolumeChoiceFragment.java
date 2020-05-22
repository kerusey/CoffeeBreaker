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
    Button btn_2, btn_4;
    Bundle bundle;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_volume_choise, container, false);//обозначаем, куда будем загружать объекты
        answer = getActivity().getSharedPreferences("answer", Context.MODE_PRIVATE);
        bundle = getActivity().getIntent().getExtras();


//            button = (Button) v.findViewById(R.id.btnLogin);
//            button.setOnClickListener(this); // присвоение кнопки листенеру
//            return v;
//        }
        btn_2 = view.findViewById(R.id.btn_2);
        btn_4 = view.findViewById(R.id.btn_4);

        btn_2.setOnClickListener(this);
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
            case R.id.btn_2:
                Toast.makeText(getActivity(), "Выбрали объём напитка", Toast.LENGTH_SHORT).show();
                editor.putFloat("volume", 0.2F);//Включаем режим редактирования файла
                break;
            case R.id.btn_4:
                Toast.makeText(getActivity(), "Выбрали объём напитка", Toast.LENGTH_SHORT).show();
                editor.putFloat("volume", 0.4F);//Включаем режим редактирования файла
                break;
        }
        editor.apply();

    }
}
