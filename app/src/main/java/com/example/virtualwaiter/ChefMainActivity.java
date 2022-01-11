package com.example.virtualwaiter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.viewpager2.widget.ViewPager2;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.virtualwaiter.CommonClasses.Order;
import com.example.virtualwaiter.Net.ConnectDB;
import com.example.virtualwaiter.Net.StaticData;
import com.example.virtualwaiter.UI.Actions.PushNofitication;
import com.example.virtualwaiter.UI.Actions.ShowOrdersList;
import com.example.virtualwaiter.UI.Components.ChefOrderCardView;
import com.example.virtualwaiter.UI.Components.ViewPagerAdapterChef;
import com.example.virtualwaiter.UI.Components.ViewPagerAdapterWaiter;
import com.example.virtualwaiter.UI.Components.WaiterOrderCardView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ChefMainActivity extends AppCompatActivity {
    private int currentOrderAmount;
    private int NOTIFICATION_ID =0 ;
    private ViewPager2 vp2;
    private TabLayout tabs;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_main);
        context = this;

        vp2 = this.findViewById(R.id.vp2Chef);
        tabs = this.findViewById(R.id.tlChefTabs);

        ViewPagerAdapterChef adapter = new ViewPagerAdapterChef(this);
        vp2.setAdapter(adapter);

        new TabLayoutMediator(tabs, vp2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position){
                    case 0:
                        tab.setText(context.getText(R.string.tab_current));
                        break;
                    case 1:
                        tab.setText(context.getText(R.string.tab_past));
                        break;
                    case 2:
                        tab.setText(context.getText(R.string.tab_history));
                        break;
                }
            }
        }).attach();


        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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
                                    }, 0, 5, TimeUnit.SECONDS);
                                }
                            }, 100
                    );
                }
                else if(tab.getPosition() == 1){
                    new android.os.Handler(Looper.getMainLooper()).postDelayed(
                            new Runnable() {
                                @Override
                                public void run() {
                                    executorService.scheduleWithFixedDelay(new Runnable() {
                                        public void run() {
                                            new getPastOrderList().execute();
                                        }
                                    }, 0, 10, TimeUnit.SECONDS);
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
                                            new getHistoryOrderList().execute();
                                        }
                                    }, 0, 1, TimeUnit.HOURS);
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


        vp2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

                if (position == 0) { //if order tab opened
                    executorService.scheduleWithFixedDelay(new Runnable() {
                        public void run() {
                            new getCurrentOrderList().execute();
                        }
                    }, 0, 5, TimeUnit.SECONDS);
                }
                else if(position == 1){
                    executorService.scheduleWithFixedDelay(new Runnable() {
                        public void run() {
                            new getPastOrderList().execute();
                        }
                    }, 0, 10, TimeUnit.SECONDS);
                }
                else{
                    executorService.scheduleWithFixedDelay(new Runnable() {
                        public void run() {
                            new getHistoryOrderList().execute();
                        }
                    }, 0, 1, TimeUnit.HOURS);
                }
            }

        });
    }




    public class getCurrentOrderList extends AsyncTask {

        @Override
        protected String doInBackground(Object... objects) {
            currentOrderAmount = StaticData.CURRENT_ORDERS.size();
            return ConnectDB.getOrder("current");
        }

        @Override
        protected void onPostExecute(Object o) {
            if(o.equals("success")){
                ShowOrdersList.show("current", context, currentOrderAmount, NOTIFICATION_ID, ChefMainActivity.this);
                NOTIFICATION_ID +=1;
            }
        }
    }

    public class getPastOrderList extends AsyncTask {

        @Override
        protected String doInBackground(Object... objects) {
            return ConnectDB.getOrder("closed");
        }

        @Override
        protected void onPostExecute(Object o) {
            if(o.equals("success")){
                ShowOrdersList.show("closed", context, currentOrderAmount, NOTIFICATION_ID, ChefMainActivity.this);
            }
        }
    }


    public class getHistoryOrderList extends AsyncTask {

        @Override
        protected String doInBackground(Object... objects) {
            return ConnectDB.getOrder("history");
        }

        @Override
        protected void onPostExecute(Object o) {
            if(o.equals("success")){
                ShowOrdersList.show("history", context, currentOrderAmount, NOTIFICATION_ID, ChefMainActivity.this);
            }
        }
    }

}