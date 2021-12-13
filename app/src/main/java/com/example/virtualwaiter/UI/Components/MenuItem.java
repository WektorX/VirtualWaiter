package com.example.virtualwaiter.UI.Components;

import static com.example.virtualwaiter.Net.StaticData.IP;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.virtualwaiter.CommonClasses.Food;
import com.example.virtualwaiter.R;

public class MenuItem  extends CardView {

    private TextView foodName;
    private LinearLayout ly;
    private Context context;
    public MenuItem(@NonNull Context context, Food f) {
        super(context);
        this.context = context;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(1000, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.topMargin = 50;
        this.setBackgroundColor(context.getColor(R.color.FoodItem_background));
        this.setLayoutParams(params);
        setLayout();
        setFoodImage(f.getPhotoName());
        setFoodName(f.getName());
        setFoodDescription(f.getDescription());
        setPrice(f.getPrice());
    }




    private void setLayout() {
        ly = new LinearLayout(context);
        ly.setGravity(TEXT_ALIGNMENT_CENTER);
        ly.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        ly.setLayoutParams(p);
        this.addView(ly);
    }

    @SuppressLint("CheckResult")
    private void setFoodImage(String photoName) {
        if(photoName != null && !photoName.isEmpty()){
            ImageView img = new ImageView(context);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 400);
            img.setScaleType(ImageView.ScaleType.CENTER_CROP);

            img.setLayoutParams(params);

            RequestOptions rqOpt = new RequestOptions();
            rqOpt.placeholder(R.drawable.waiter_icon);
            rqOpt.error(R.drawable.waiter_icon);

            Glide.with(context)
                    .load("http://"+IP+"/virtualwaiter/img/"+photoName)
                    .apply(rqOpt)
                    .into(img);
            ly.addView(img);

        }


    }


    public void setFoodName(String name){

        foodName = new TextView(context);
        foodName.setText(name);
        foodName.setTextColor(context.getColor(R.color.black));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.topMargin = 10;
        foodName.setLayoutParams(params);
        foodName.setTextAlignment(TEXT_ALIGNMENT_CENTER);
        foodName.setTextSize(20);
//        foodName.setText
        ly.addView(foodName);
    }

    private void setFoodDescription(String description) {
        TextView foodDescription = new TextView(context);
        foodDescription.setText(description);
        foodDescription.setTextColor(context.getColor(R.color.black));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.topMargin = 10;
        foodDescription.setLayoutParams(params);
        foodDescription.setPadding(15,15,15,15);
//        foodDescription.setTextAlignment(TEXT_ALIGNMENT_CENTER);
        ly.addView(foodDescription);
    }

    private void setPrice(double price) {
        TextView foodPrice = new TextView(context);
        foodPrice.setText(price +" zł");
        foodPrice.setTextColor(context.getColor(R.color.black));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.topMargin = 10;
        foodPrice.setLayoutParams(params);
        foodPrice.setTextAlignment(TEXT_ALIGNMENT_TEXT_END);
        foodPrice.setPadding(25,25,25,25);
        ly.addView(foodPrice);


    }

}
