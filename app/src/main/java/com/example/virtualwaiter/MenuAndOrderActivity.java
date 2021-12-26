package com.example.virtualwaiter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.example.virtualwaiter.UI.Components.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MenuAndOrderActivity extends AppCompatActivity {

    private ViewPager2 vp2;
    private TabLayout tabs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_and_order);

        vp2 = this.findViewById(R.id.vp2Menu);
        tabs = this.findViewById(R.id.tlTabs);

        ViewPagerAdapter adapter = new ViewPagerAdapter(this);
        vp2.setAdapter(adapter);


        new TabLayoutMediator(tabs, vp2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
               switch (position){
                   case 0:
                       tab.setText(MenuAndOrderActivity.this.getText(R.string.menu_tab));
                       break;
                   case 1:
                       tab.setText(MenuAndOrderActivity.this.getText(R.string.order_tab));
                       break;
               }
            }
        }).attach();
    }
}