package com.example.virtualwaiter.UI.Components;

import android.content.Context;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.virtualwaiter.R;

public class WaiterButton extends androidx.appcompat.widget.AppCompatButton {


    public WaiterButton(Context context) {
        super(context);
        this.setBackground(context.getDrawable(R.drawable.waiter_icon));

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(500,500);
        params.topMargin = 100;
        this.setLayoutParams(params);
        this.setTextColor(context.getColor(R.color.black));
    }
}
