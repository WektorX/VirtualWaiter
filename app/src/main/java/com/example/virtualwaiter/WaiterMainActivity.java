package com.example.virtualwaiter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.widget.LinearLayout;

import com.example.virtualwaiter.CommonClasses.Order;
import com.example.virtualwaiter.Net.ConnectDB;
import com.example.virtualwaiter.Net.StaticData;
import com.example.virtualwaiter.UI.Components.WaiterOrderCardView;
import com.example.virtualwaiter.UI.Components.ViewPagerAdapterWaiter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class WaiterMainActivity extends AppCompatActivity {


    private ViewPager2 vp2Waiter;
    private TabLayout tabsWaiter;
    private Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiter_main);

        vp2Waiter = this.findViewById(R.id.vp2Waiter);
        tabsWaiter = this.findViewById(R.id.tlWaiterTabs);

        ViewPagerAdapterWaiter adapter = new ViewPagerAdapterWaiter(this);
        vp2Waiter.setAdapter(adapter);

        new TabLayoutMediator(tabsWaiter, vp2Waiter, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position){
                    case 0:
                        tab.setText(WaiterMainActivity.this.getText(R.string.waiter_tab_current));
                        break;
                    case 1:
                        tab.setText(WaiterMainActivity.this.getText(R.string.waiter_tab_past));
                        break;
                }
            }
        }).attach();


        tabsWaiter.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);


                if (tab.getPosition() == 0) {
                    new android.os.Handler(Looper.getMainLooper()).postDelayed(
                            new Runnable() {
                                @Override
                                public void run() {
                                    executorService.scheduleWithFixedDelay(new Runnable() {
                                        public void run() {
                                            //put query logic here
                                            new getCurrentOrderList().execute();
                                        }
                                    }, 10, 10, TimeUnit.SECONDS);
                                }
                            }, 100
                    );
                }
                else{
                    new android.os.Handler(Looper.getMainLooper()).postDelayed(
                            new Runnable() {
                                @Override
                                public void run() {
                                    executorService.scheduleWithFixedDelay(new Runnable() {
                                        public void run() {
                                            //put query logic here
                                            new getPastOrderList().execute();
                                        }
                                    }, 10, 10, TimeUnit.SECONDS);
                                }
                            }, 100
                    );
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });


        vp2Waiter.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

                if (position == 0) { //if order tab opened
                    executorService.scheduleWithFixedDelay(new Runnable() {
                        public void run() {
                            //put query logic here
                            new getCurrentOrderList().execute();
                        }
                    }, 1, 10, TimeUnit.SECONDS);
                }
                else{
                    executorService.scheduleWithFixedDelay(new Runnable() {
                        public void run() {
                            //put query logic here
                            new getPastOrderList().execute();
                        }
                    }, 1, 10, TimeUnit.SECONDS);
                }
            }

        });

    }


    private void showOrderList(Boolean current){

        if(current){
            LinearLayout ly =null;
            ly = findViewById(R.id.lyWaiterCurrentOrders);
            if(ly != null){
                ly.removeAllViews();
                for(Order o : StaticData.CURRENT_WAITER_ORDERS){
                    WaiterOrderCardView card = new WaiterOrderCardView(context, o);
                    ly.addView(card);
                }
            }
        }
        else{
            LinearLayout ly =null;
            ly = findViewById(R.id.lyWaiterPastOrders);
            if(ly != null){
                ly.removeAllViews();
                for(Order o : StaticData.PAST_WAITER_ORDERS){
                    WaiterOrderCardView card = new WaiterOrderCardView(context, o);
                    ly.addView(card);
                }
            }

        }

    }




    public class getCurrentOrderList extends AsyncTask {

        @Override
        protected String doInBackground(Object... objects) {
            return ConnectDB.getCurrentOrdersWaiter();
        }

        @Override
        protected void onPostExecute(Object o) {
            if(o.equals("success")){
               showOrderList(true);
            }
        }
    }

    public class getPastOrderList extends AsyncTask {

        @Override
        protected String doInBackground(Object... objects) {
            return ConnectDB.getPastOrdersWaiter();
        }

        @Override
        protected void onPostExecute(Object o) {
            if(o.equals("success")){
                showOrderList(false);
            }
        }
    }




}