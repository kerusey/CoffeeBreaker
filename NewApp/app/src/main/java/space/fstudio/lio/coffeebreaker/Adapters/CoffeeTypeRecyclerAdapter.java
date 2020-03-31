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

public class CoffeeTypeRecyclerAdapter extends RecyclerView.Adapter<CoffeeTypeRecyclerAdapter.TypeHolder> /*implements RecyclerView.OnItemTouchListener*/ {
    /*  private GestureDetector gestureDetector;
      private ClickListener clickListener;*/
    private List<CoffeeTypeObject> coffeeTypesList;//24 52 61

    /* private  View itemView;*/
    /*private Object applicationContext;*/
    public CoffeeTypeRecyclerAdapter(List<CoffeeTypeObject> coffeeTypesList/*,final ClickListener clickListener*/) {
        this.coffeeTypesList = coffeeTypesList;
        //    this.clickListener = clickListener;
    }

//    public Object getApplicationContext() {
//        return applicationContext;
//    }

//    public void setApplicationContext(Object applicationContext) {
//        this.applicationContext = applicationContext;
//    }

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

//    public CoffeeTypeRecyclerAdapter(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
//     this.clickListener = clickListener;
//        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
//        @Override
//        public boolean onSingleTapUp(MotionEvent e) {
//            return true;
//        }
//
//
//    });
//}

/*
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

        View child = rv.findChildViewUnder(e.getX(), e.getY());
        if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
            clickListener.onClick(child, rv.getChildPosition(child));
        }
        return false;
    }*/

    /* public void onTouchEvent(RecyclerView rv, MotionEvent e) {
    }*/


    /* public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }*/


    @Override
    public void onBindViewHolder(@NonNull TypeHolder holder, int position) {
        final CoffeeTypeObject coffeeTypeObject = coffeeTypesList.get(position);

        Picasso.get().load(coffeeTypeObject.getIcon()).into(holder.imgCoffeePreview);
        holder.txtCoffeeName.setText(coffeeTypeObject.getCoffeeName());//отображение элементов списка
        holder.txtCoffeeDescription.setText(coffeeTypeObject.getCoffeeDescription());


////здесь надо записать вью элемента списка
//        itemView.addOnItemTouchListener(new CoffeeTypeRecyclerAdapter(coffeeTypesList, new ClickListener() {
//            @Override
//            public void onClick(View view, int position) {
//
//              String type=coffeeTypeObject.getCoffeeName();
//            }
//
//
//        }));
    }

//            itemView.addOnItemTouchListener(new CoffeeTypeRecyclerAdapter(getApplicationContext(),coffeeTypesList, new ClickListener() {
//        @Override
//        public void onClick(View view, int position) {
//
//            String type=coffeeTypeObject.getCoffeeName();
//        }
//
//
//    }));
//}
//    clickListener.addOnItemTouchListener(new CoffeeTypeRecyclerAdapter(getApplicationContext(), clickListener, new ClickListener() {
//        @Override
//        public void onClick(View view, int position) {
//
//            String type=coffeeTypeObject.getCoffeeName();
//        }
//
//        @Override
//        public void onLongClick(View view, int position) {
//
//        }
//    }));}


/* public interface ClickListener {
        void onClick(View view, int position);
    }*/

    @Override
    public int getItemCount() {
        return coffeeTypesList.size();//размер листа
    }
}
