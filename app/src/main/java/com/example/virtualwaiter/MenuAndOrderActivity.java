package com.example.virtualwaiter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.virtualwaiter.CommonClasses.OrderItem;
import com.example.virtualwaiter.Net.StaticData;
import com.example.virtualwaiter.UI.Components.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class MenuAndOrderActivity extends AppCompatActivity {

    private ViewPager2 vp2;
    private TabLayout tabs;
    private Context context = this;
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

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
               if(tab.getPosition() == 1){
                   new android.os.Handler(Looper.getMainLooper()).postDelayed(
                           new Runnable() {
                               @Override
                               public void run() {
                                   createOrderView();
                               }
                           },100
                   );
               }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });


        vp2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if(position == 1){ //if order tab opened
                    createOrderView();
                }
            }

        });
    }


    private void createOrderView(){
        GridLayout gl = null;
        GridLayout glt = null;
        gl = findViewById(R.id.order_list);
        glt = findViewById(R.id.order_list_total);
        if(gl != null && glt != null){
            gl.removeAllViews();
            glt.removeAllViews();
            ArrayList<OrderItem> list = StaticData.ORDER.getList();
            DecimalFormat f = new DecimalFormat("##.00");
            if(list.size() != 0){
            for(OrderItem i : list){
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((StaticData.DEVICE_WIDTH * 3 /5) - 20, ViewGroup.LayoutParams.WRAP_CONTENT);
                TextView orderName = new TextView(context);
                orderName.setText(i.getFoodName());
                orderName.setLayoutParams(params);
                orderName.setTextColor(context.getColor(R.color.white));
                orderName.setTextSize(15);
                LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams((StaticData.DEVICE_WIDTH /5), ViewGroup.LayoutParams.WRAP_CONTENT);
                TextView amountAndTotal = new TextView(context);
                amountAndTotal.setText(i.getAmount() + " x " + f.format(i.getPriceOfItem()) + "zł");
                amountAndTotal.setLayoutParams(params2);
                amountAndTotal.setTextSize(15);
                amountAndTotal.setTextColor(context.getColor(R.color.white));
                amountAndTotal.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
                gl.addView(orderName);
                gl.addView(amountAndTotal);
            }
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((StaticData.DEVICE_WIDTH * 3 /5) - 20, ViewGroup.LayoutParams.WRAP_CONTENT);
                TextView orderName = new TextView(context);
                orderName.setText(getText(R.string.total_label));
                orderName.setLayoutParams(params);
                orderName.setTextColor(context.getColor(R.color.white));
                orderName.setTextSize(15);
                orderName.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);

                LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams((StaticData.DEVICE_WIDTH /5), ViewGroup.LayoutParams.WRAP_CONTENT);
                TextView amountAndTotal = new TextView(context);
                amountAndTotal.setText(f.format(StaticData.ORDER.getTotal())+ "zł");
                amountAndTotal.setLayoutParams(params2);
                amountAndTotal.setTextSize(15);
                amountAndTotal.setTextColor(context.getColor(R.color.white));
                amountAndTotal.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);

                glt.addView(orderName);
                glt.addView(amountAndTotal);
            }


        }
    }


}