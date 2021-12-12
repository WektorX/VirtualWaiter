package com.example.virtualwaiter.UI.Components;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.virtualwaiter.R;

public class TableButton extends androidx.appcompat.widget.AppCompatButton {


    public TableButton(Context context) {
        super(context);
        this.setBackground(context.getDrawable(R.drawable.table_icon));

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(500,500);
        params.topMargin = 10;
        this.setLayoutParams(params);

        this.setTextColor(context.getColor(R.color.black));
    }

}
