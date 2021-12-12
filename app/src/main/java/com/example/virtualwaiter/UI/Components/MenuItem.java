package com.example.virtualwaiter.UI.Components;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

public class MenuItem  extends CardView {

    private TextView foodName;
    public MenuItem(@NonNull Context context) {
        super(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(900, 500);
        params.topMargin = 10;

        this.setLayoutParams(params);



        foodName = new TextView(context);
        this.addView(foodName);
    }

    public void setFoodName(String name){
        foodName.setText(name);
    }
}
