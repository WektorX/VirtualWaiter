package com.example.virtualwaiter.UI.Components;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.virtualwaiter.CurrentOrdersFragment;
import com.example.virtualwaiter.MenuFragment;
import com.example.virtualwaiter.OrderFragment;
import com.example.virtualwaiter.PastOrderFragment;

public class ViewPagerAdapterWaiter extends FragmentStateAdapter {

    private Context context;
    public ViewPagerAdapterWaiter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        context = fragmentActivity;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 1:
                return new PastOrderFragment(context);
            default:
                return new CurrentOrdersFragment(context);
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
