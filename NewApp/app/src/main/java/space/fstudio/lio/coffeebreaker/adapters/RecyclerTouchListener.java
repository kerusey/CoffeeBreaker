package space.fstudio.lio.coffeebreaker.adapters;


import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import org.jetbrains.annotations.NotNull;


public class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

  private GestureDetector gestureDetector;
  private ClickListener clickListener;

  public RecyclerTouchListener(Context context,
      final ClickListener clickListener) {
    this.clickListener = clickListener;
    gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
      @Override
      public boolean onSingleTapUp(MotionEvent e) {
        return true;
      }
    });
  }

  @Override
  public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

    View child = rv.findChildViewUnder(e.getX(), e.getY());
    if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
      clickListener.onClick(child, rv.getChildAdapterPosition(child));
    }
    return false;
  }

  @Override
  public void onTouchEvent(@NotNull RecyclerView rv, @NotNull MotionEvent e) {
  }

  @Override
  public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

  }

  public interface ClickListener {

    void onClick(View view, int position);

  }
}
