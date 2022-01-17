package com.example.virtualwaiter.UI.Components;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.example.virtualwaiter.CommonClasses.Order;
import com.example.virtualwaiter.CommonClasses.OrderItem;
import com.example.virtualwaiter.Net.StaticData;
import com.example.virtualwaiter.R;

import java.text.DecimalFormat;

public class ManagerOrderCardView extends CardView {
    public ManagerOrderCardView(@NonNull Context context) {
        super(context);
    }

    public ManagerOrderCardView(@NonNull Context context, Order o) {
        super(context);
        LinearLayout.LayoutParams cardParams = new LinearLayout.LayoutParams((StaticData.DEVICE_WIDTH*4)/5, ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setLayoutParams(cardParams);
        cardParams.setMargins(0, 60, 0 , 60);
        this.setCardBackgroundColor(context.getColor(R.color.transparent));

        LinearLayout ly = new LinearLayout(context);
        LinearLayout.LayoutParams params  = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        ly.setLayoutParams(params);
        ly.setBackgroundColor(context.getColor(R.color.almost_transparent));
        ly.setOrientation(LinearLayout.VERTICAL);

        LinearLayout.LayoutParams componentParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        componentParams.setMargins(20,0,20,0);

        TextView date = new TextView(context);
        date.setText(context.getString(R.string.date) + o.getDate());
        date.setTextSize(10);
        date.setTextAlignment(TEXT_ALIGNMENT_VIEW_END);
        date.setLayoutParams(componentParams);
        date.setTextColor(context.getColor(R.color.white));
        ly.addView(date);

        TextView table = new TextView(context);
        table.setText(context.getString(R.string.btnTableLabel)+" "+o.getTable().getTableId());
        table.setTextSize(15);
        table.setTextAlignment(TEXT_ALIGNMENT_CENTER);
        table.setLayoutParams(componentParams);
        table.setTextColor(context.getColor(R.color.white));
        ly.addView(table);

        TextView waiter = new TextView(context);
        waiter.setText(context.getString(R.string.waiter) + ": " + o.getWaiterName());
        waiter.setTextSize(15);
        waiter.setTextAlignment(TEXT_ALIGNMENT_CENTER);
        waiter.setLayoutParams(componentParams);
        waiter.setTextColor(context.getColor(R.color.white));
        ly.addView(waiter);

        TextView status = new TextView(context);
        String packageName = context.getPackageName();
        String id = "status_" + o.getStatus().replace(" ", "_");
        int resId = getResources().getIdentifier(id, "string", packageName);
        status.setText("Status: " + context.getString(resId));
        status.setTextSize(15);
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

        this.addView(ly);
    }

    public ManagerOrderCardView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

    }

    public ManagerOrderCardView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

}
