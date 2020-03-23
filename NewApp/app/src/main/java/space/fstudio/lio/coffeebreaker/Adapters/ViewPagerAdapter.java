package space.fstudio.lio.coffeebreaker.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import space.fstudio.lio.coffeebreaker.Fragments.MilkChoiceFragment;
import space.fstudio.lio.coffeebreaker.Fragments.StrengthChoiceFragment;
import space.fstudio.lio.coffeebreaker.Fragments.SugarChoiceFragment;
import space.fstudio.lio.coffeebreaker.Fragments.TypeChoiceFragment;
import space.fstudio.lio.coffeebreaker.Fragments.VolumeChoiceFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {

    private static final int FRAGMENT_COUNT = 5;

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new TypeChoiceFragment();
            case 1:
                return new VolumeChoiceFragment();
            case 2:
                return new SugarChoiceFragment();
            case 3:
                return new StrengthChoiceFragment();
            case 4:
                return new MilkChoiceFragment();
        }
        return new TypeChoiceFragment();
    }

    @Override
    public int getItemCount() {
        return FRAGMENT_COUNT;
    }
}
