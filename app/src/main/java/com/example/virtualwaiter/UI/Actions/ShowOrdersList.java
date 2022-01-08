package com.example.virtualwaiter.UI.Actions;

import android.app.Activity;
import android.content.Context;
import android.widget.LinearLayout;

import androidx.cardview.widget.CardView;

import com.example.virtualwaiter.CommonClasses.Order;
import com.example.virtualwaiter.Net.StaticData;
import com.example.virtualwaiter.R;
import com.example.virtualwaiter.UI.Components.ChefOrderCardView;
import com.example.virtualwaiter.UI.Components.WaiterOrderCardView;

import java.util.ArrayList;

public class ShowOrdersList {


    public static void show(String tab, Context c, int currentOrderAmount, int notificationID, Activity a){
        LinearLayout ly = null;
        ArrayList<Order> data;
        if(tab.equals("current")){
            if(currentOrderAmount < StaticData.CURRENT_ORDERS.size()){
                PushNofitication.notify(c, c.getString(R.string.notification_title), c.getString(R.string.notification_body), notificationID);
            }

            ly = a.findViewById(R.id.lyCurrentOrders);
            data = StaticData.CURRENT_ORDERS;
        }
        else if(tab.equals("closed")){
            ly = a.findViewById(R.id.lyPastOrders);
            data = StaticData.PAST_ORDERS;
        }
        else{
            ly = a.findViewById(R.id.lyHistoryOrders);
            data = StaticData.HISTORY_ORDERS;
        }

        if(ly != null){
            ly.removeAllViews();
            for(Order o : data){
                CardView card;
                switch (StaticData.WORKER_TYPE){
                    case "chef":
                        card = new ChefOrderCardView(c, o);
                        break;
                    case "waiter":
                        card = new WaiterOrderCardView(c, o);
                        break;
                    default:
                        card = new ChefOrderCardView(c, o);
                        break;
                }

                ly.addView(card);
            }
        }
    }
}
