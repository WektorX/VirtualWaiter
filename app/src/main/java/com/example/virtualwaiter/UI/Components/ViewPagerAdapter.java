package com.example.virtualwaiter.UI.Components;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.virtualwaiter.MenuFragment;
import com.example.virtualwaiter.OrderFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {

    private Context context;
    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        context = fragmentActivity;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 1:
                return new OrderFragment();
            default:
                return new MenuFragment(context);
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
