package com.example.virtualwaiter.UI.Components;

import android.content.Context;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.example.virtualwaiter.CommonClasses.Order;
import com.example.virtualwaiter.CommonClasses.OrderItem;
import com.example.virtualwaiter.Net.ConnectDB;
import com.example.virtualwaiter.Net.StaticData;
import com.example.virtualwaiter.R;
import com.example.virtualwaiter.WaiterMainActivity;

import java.text.DecimalFormat;

public class WaiterOrderCardView extends CardView {

    private String action;
    private int id;

    public WaiterOrderCardView(@NonNull Context context) {
        super(context);
    }

    public WaiterOrderCardView(@NonNull Context context, Order o) {
        super(context);
        LinearLayout.LayoutParams cardParams = new LinearLayout.LayoutParams((StaticData.DEVICE_WIDTH*4)/5, ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setLayoutParams(cardParams);
        cardParams.setMargins(0, 60, 0 , 60);
        this.setCardBackgroundColor(context.getColor(R.color.transparent));

        id = o.getId();

        LinearLayout ly = new LinearLayout(context);
        LinearLayout.LayoutParams params  = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        ly.setLayoutParams(params);
        ly.setBackgroundColor(context.getColor(R.color.almost_transparent));
        ly.setOrientation(LinearLayout.VERTICAL);

        LinearLayout.LayoutParams componentParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        componentParams.setMargins(20,0,20,0);

        TextView table = new TextView(context);
        table.setText(context.getString(R.string.btnTableLabel) + " " + o.getTable().getTableId());
        table.setTextSize(25);
        table.setTextAlignment(TEXT_ALIGNMENT_CENTER);
        table.setLayoutParams(componentParams);
        table.setTextColor(context.getColor(R.color.white));
        ly.addView(table);

        TextView status = new TextView(context);
        String packageName = context.getPackageName();
        String id = "status_" + o.getStatus().replace(" ", "_");
        int resId = getResources().getIdentifier(id, "string", packageName);
        status.setText("Status: " + context.getString(resId));
        status.setTextSize(20);
        status.setTextAlignment(TEXT_ALIGNMENT_CENTER);
        status.setLayoutParams(componentParams);
        status.setTextColor(context.getColor(R.color.white));
        ly.addView(status);

        View line = new View(context);
        LinearLayout.LayoutParams lineParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 5);
        line.setBackgroundColor(context.getColor(R.color.white));
        line.setLayoutParams(lineParams);
        ly.addView(line);

        TextView dishList = new TextView(context);
        dishList.setLayoutParams(componentParams);
        String list = "\n";

        for(OrderItem item : o.getList()){
            list += item.getFoodName() +" x"+item.getAmount() +"\n";
        }

        dishList.setText(list);
        dishList.setTextColor(context.getColor(R.color.white));
        ly.addView(dishList);

        View line2 = new View(context);
        line2.setBackgroundColor(context.getColor(R.color.white));
        line2.setLayoutParams(lineParams);
        ly.addView(line2);

        DecimalFormat f = new DecimalFormat("##.00");
        TextView total = new TextView(context);
        total.setText(context.getString(R.string.total_label) + f.format(o.getTotal()) + "zł");
        total.setTextColor(context.getColor(R.color.white));
        total.setTextAlignment(TEXT_ALIGNMENT_TEXT_END);
        total.setLayoutParams(componentParams);
        ly.addView(total);


        if(!o.getStatus().equals("canceled") && !o.getStatus().equals("paid")){


        Button btn = new Button(context);
        btn.setText(context.getString(R.string.waiter_delivered));
        if(!o.getStatus().equals("ready")){
            btn.setEnabled(false);
        }
        if(o.getStatus().equals("delivered")){
            btn.setText(context.getString(R.string.waiter_paid));
        }
        if(o.getStatus().equals("ready to pay")){
            btn.setEnabled(true);
            btn.setText(context.getString(R.string.waiter_paid));
        }
        ly.addView(btn);

        btn.setOnClickListener(v ->{


            if(o.getStatus().equals("ready")){
                action = "delivered";
                int i = StaticData.CURRENT_ORDERS.indexOf(o);
                StaticData.CURRENT_ORDERS.get(i).setStatus(action);
                btn.setText(context.getString(R.string.waiter_paid));
                btn.setEnabled(false);
                status.setText("Status: " + context.getString(R.string.status_delivered));

            }
            if(o.getStatus().equals("ready to pay")){
                action = "paid";
                int i = StaticData.CURRENT_ORDERS.indexOf(o);
                StaticData.CURRENT_ORDERS.get(i).setStatus(action);
                status.setText("paid");
                WaiterOrderCardView.this.removeAllViews();
                cardParams.setMargins(0,0,0,0);
                this.setLayoutParams(cardParams);
            }
            new changeStatus().execute();

        });

        Button cancel = new Button(context);
        cancel.setText(context.getString(R.string.cancel));
        cancel.setOnClickListener(v->{
            action = "canceled";
            status.setText("canceled");
            WaiterOrderCardView.this.removeAllViews();
            cardParams.setMargins(0,0,0,0);
            this.setLayoutParams(cardParams);
            new changeStatus().execute();
        });

        ly.addView(cancel);
        }
        this.addView(ly);
    }

    public WaiterOrderCardView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

    }

    public WaiterOrderCardView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public class changeStatus extends AsyncTask {

        @Override
        protected String doInBackground(Object... objects) {
            return ConnectDB.changeStatus(action, id);
        }

        @Override
        protected void onPostExecute(Object o) {
            Log.d("Status" , o.toString());
        }
    }

}
