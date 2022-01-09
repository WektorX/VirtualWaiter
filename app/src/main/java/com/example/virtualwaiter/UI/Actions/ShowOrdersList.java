package com.example.virtualwaiter.UI.Actions;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.LinearLayout;

import androidx.cardview.widget.CardView;

import com.example.virtualwaiter.CommonClasses.Order;
import com.example.virtualwaiter.Net.StaticData;
import com.example.virtualwaiter.R;
import com.example.virtualwaiter.UI.Components.ChefOrderCardView;
import com.example.virtualwaiter.UI.Components.WaiterOrderCardView;

import java.util.ArrayList;
import java.util.Collections;

public class ShowOrdersList {

    public static void show(String tab, Context c, int currentOrderAmount, int notificationID, Activity a){
        LinearLayout ly = null;
        ArrayList<Order> data;
        if(tab.equals("current")){
            checkIfOrdersChanged(c, notificationID);
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


    public static void checkIfOrdersChanged(Context context, int notificationID){

        ArrayList<Order> previous = new ArrayList<>();
        ArrayList<Order> current = new ArrayList<>();

        previous.addAll(StaticData.CURRENT_ORDERS_COPY);
        current.addAll(StaticData.CURRENT_ORDERS);

        Log.d("Test" , previous.size() + " " + current.size());
        if(previous.size() == current.size()){
            for(Order p : previous){
                int id = p.getId();
                for(Order c : current){
                    if(c.getId() == id){
                        if(!c.getStatus().equals(p.getStatus())){
                            PushNofitication.notify(context, context.getString(R.string.notification_title_order_changed) + c.getTable().getTableId(),
                                    context.getString(R.string.notification_body_order_changed), notificationID);
                        }
                    }
                }
            }
        }
        else if(previous.size() < current.size()){
            PushNofitication.notify(context, context.getString(R.string.notification_title_new_order),
                    context.getString(R.string.notification_body_new_order), notificationID);
        }

    }
}
