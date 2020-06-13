package space.fstudio.lio.coffeebreaker.adapters;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import space.fstudio.lio.coffeebreaker.fragments.MilkChoiceFragment;
import space.fstudio.lio.coffeebreaker.fragments.StrengthChoiceFragment;
import space.fstudio.lio.coffeebreaker.fragments.SugarChoiceFragment;
import space.fstudio.lio.coffeebreaker.fragments.TypeChoiceFragment;
import space.fstudio.lio.coffeebreaker.fragments.VolumeChoiceFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {
    FragmentTransaction tran;
    private static final int FRAGMENT_COUNT = 5;

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
//        SharedPreferences answer = fragmentActivity.getSharedPreferences("answer", Context.MODE_PRIVATE);
//        int sugar = answer.getInt("sugar", 10);
//        int volume = answer.getInt("volume", 500);
//        int strength = answer.getInt("strength", 10);
//        String coffeeType = answer.getString("coffeeType", "NO");
//        System.out.println(position+"\r\n"+sugar + "\r\n" + volume + "\r\n" + strength + "\r\n" + coffeeType);
//        if (position==0) {
//            return new TypeChoiceFragment();
//        }
//        else if(position==1 && !coffeeType.equals("NO") && sugar==10 && volume==500 && strength==10){
//           return new VolumeChoiceFragment();
//        }else if(position==2 && volume!=500 && !coffeeType.equals("NO") && sugar==10 && strength==10){
//            return new SugarChoiceFragment();
//        } else if(position==3 && sugar!=10 && volume!=500 && !coffeeType.equals("NO")  && strength==10){
//            Fragment strF=new StrengthChoiceFragment();
//         tran= fragmentActivity.getSupportFragmentManager().beginTransaction();
//            tran.replace(R.id.viewPager2,strF );
//            tran.commit();
//
//          return new StrengthChoiceFragment();
//        } else if(position==4 && strength!=10 && sugar!=10 && volume!=500 && !coffeeType.equals("NO") ){
//            return new MilkChoiceFragment();
//        }  return new TypeChoiceFragment();
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











