package com.example.virtualwaiter.UI.Components;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.example.virtualwaiter.CommonClasses.Order;
import com.example.virtualwaiter.CommonClasses.OrderItem;
import com.example.virtualwaiter.Net.ConnectDB;
import com.example.virtualwaiter.Net.StaticData;
import com.example.virtualwaiter.R;

import java.sql.Date;
import java.time.LocalDate;

public class ChefOrderCardView extends CardView {

    private int id;
    private String action;
    private String statusName;

    public ChefOrderCardView(@NonNull Context context) {
        super(context);
    }

    @SuppressLint("SetTextI18n")
    public ChefOrderCardView(@NonNull Context context, Order o) {
        super(context);

        statusName = o.getStatus();

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
        table.setText(context.getString(R.string.btnTableLabel)+" "+o.getTable().getTableId());
        table.setTextSize(25);
        table.setTextAlignment(TEXT_ALIGNMENT_CENTER);
        table.setLayoutParams(componentParams);
        table.setTextColor(context.getColor(R.color.white));
        ly.addView(table);


        TextView status = new TextView(context);
        String packageName = context.getPackageName();
        String id = "status_" + statusName.replace(" ", "_");
        int resId = getResources().getIdentifier(id, "string", packageName); //id of current state sting
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

        if ((statusName.equals("placed") || statusName.equals("received") || statusName.equals("in progress") ) &&  DateUtils.isToday(o.getDate().getTime())) {
            View line2 = new View(context);
            line2.setBackgroundColor(context.getColor(R.color.white));
            line2.setLayoutParams(lineParams);
            ly.addView(line2);
            Button btn = new Button(context);
            switch (statusName){
                case "placed":
                    btn.setText(context.getString(R.string.status_received));
                    break;
                case "received":
                    btn.setText(context.getString(R.string.status_in_progress));
                    break;
                case "in progress":
                    btn.setText(context.getString(R.string.status_ready));
            }
            ly.addView(btn);


            btn.setOnClickListener(v->{

                switch (statusName){
                    case "placed":
                        action = "received";
                        btn.setText(context.getString(R.string.status_in_progress));
                        status.setText("Status: "+context.getString(R.string.status_received));
                        break;
                    case "received":
                        action ="in progress";
                        btn.setText(context.getString(R.string.status_ready));
                        status.setText("Status: "+context.getString(R.string.status_in_progress));
                        break;
                    case "in progress":
                        action = "ready";
                        break;
                }
                int i = StaticData.CURRENT_ORDERS.indexOf(o);
                StaticData.CURRENT_ORDERS.get(i).setStatus(action);

                if(action.equals("ready")){
                    ChefOrderCardView.this.removeAllViews();
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
                ChefOrderCardView.this.removeAllViews();
                cardParams.setMargins(0,0,0,0);
                this.setLayoutParams(cardParams);
                new changeStatus().execute();
            });

            ly.addView(cancel);
        }
        this.addView(ly);

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

