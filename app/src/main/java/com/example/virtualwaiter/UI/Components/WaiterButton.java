package com.example.virtualwaiter.UI.Components;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.virtualwaiter.Net.StaticData;
import com.example.virtualwaiter.R;

public class WaiterButton extends LinearLayout {


    public WaiterButton(Context context, String value) {
        super(context);


        this.setOrientation(HORIZONTAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(StaticData.DEVICE_WIDTH *2 /3, ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setGravity(Gravity.CENTER);
        this.setLayoutParams(params);


        ImageView img = new ImageView(context);
        img.setImageDrawable(context.getDrawable(R.drawable.waiter_icon));
        LinearLayout.LayoutParams imgParams = new LinearLayout.LayoutParams(200, 200);
        imgParams.setMargins(0,20,300,20);
        img.setLayoutParams(imgParams);
        img.setScaleType(ImageView.ScaleType.FIT_CENTER);
        this.addView(img);

        TextView name = new TextView(context);
        name.setText(value);
        name.setTextColor(context.getColor(R.color.black));
        name.setTextAlignment(TEXT_ALIGNMENT_CENTER);
        LinearLayout.LayoutParams nameParams = new LayoutParams(StaticData.DEVICE_WIDTH / 3, ViewGroup.LayoutParams.MATCH_PARENT);
        nameParams.setMargins(-100,75,0,20);
        name.setLayoutParams(nameParams);
        this.addView(name);

    }
}
