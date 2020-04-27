package space.fstudio.lio.coffeebreaker.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import space.fstudio.lio.coffeebreaker.Objects.CoffeeTypeObject;
import space.fstudio.lio.coffeebreaker.R;

public class CoffeeTypeRecyclerAdapter extends RecyclerView.Adapter<CoffeeTypeRecyclerAdapter.TypeHolder> {
    private List<CoffeeTypeObject> coffeeTypesList;//24 52 61

    public CoffeeTypeRecyclerAdapter(List<CoffeeTypeObject> coffeeTypesList) {
        this.coffeeTypesList = coffeeTypesList;

    }

    static class TypeHolder extends RecyclerView.ViewHolder {

        ImageView imgCoffeePreview;

        TextView txtCoffeeName, txtCoffeeDescription;

        TypeHolder(@NonNull View itemView) {//интерфейс
            super(itemView);
            imgCoffeePreview = itemView.findViewById(R.id.imgCoffeePreview);
            txtCoffeeName = itemView.findViewById(R.id.txtCoffeeName);
            txtCoffeeDescription = itemView.findViewById(R.id.txtCoffeeDescription);
        }

    }

    @NonNull
    @Override
    public TypeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())//создаём объект для выбора кофе
                .inflate(R.layout.object_coffee_type, parent, false);
        return new TypeHolder(itemView);

    }




    @Override
    public void onBindViewHolder(@NonNull TypeHolder holder, int position) {
        final CoffeeTypeObject coffeeTypeObject = coffeeTypesList.get(position);

        Picasso.get().load(coffeeTypeObject.getIcon()).into(holder.imgCoffeePreview);
        holder.txtCoffeeName.setText(coffeeTypeObject.getCoffeeName());//отображение элементов списка
        holder.txtCoffeeDescription.setText(coffeeTypeObject.getCoffeeDescription());


    }


    @Override
    public int getItemCount() {
        return coffeeTypesList.size();//размер листа
    }
}
