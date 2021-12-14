package com.example.virtualwaiter.UI.Components;

import static com.example.virtualwaiter.Net.StaticData.IP;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.virtualwaiter.CommonClasses.Dish;
import com.example.virtualwaiter.CommonClasses.Drink;
import com.example.virtualwaiter.CommonClasses.Food;
import com.example.virtualwaiter.R;

import java.text.DecimalFormat;

public class MenuItem  extends CardView {

    private TextView foodName;
    private LinearLayout ly;
    private Context context;
    private LinearLayout additionalInfo;
    private LinearLayout stampsView;
    private static final DecimalFormat df = new DecimalFormat("0.00");

    public MenuItem(@NonNull Context context, Food f) {
        super(context);
        this.context = context;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.topMargin = 50;
        this.setBackgroundColor(context.getColor(R.color.FoodItem_background));
        this.setLayoutParams(params);
        setLayout();
        setFoodImage(f.getPhotoName());
        setFoodName(f.getName());
        if(f instanceof Dish){
            setFoodDescription(((Dish) f).getDescription());
        }

        setPrice(f.getPrice());

        if(f instanceof  Dish){
            if(((Dish)f).getGlutenFree()){
                setStamp("gluten");
            }
            if(((Dish)f).getVegan()){
                setStamp("vegan");
            }
        }
    else{
            if(!((Drink)f).getAlcoholic()){
                setStamp("alcoholic");
            }
        }

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
        Typeface tf = ResourcesCompat.getFont(context, R.font.nova_flat);

        foodName.setTypeface(tf);
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
        additionalInfo = new LinearLayout(context);
        additionalInfo.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams pr = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        pr.weight = 1;
        additionalInfo.setPadding(20,20,20,20);
        additionalInfo.setLayoutParams(pr);


        stampsView = new LinearLayout(context);
        stampsView.setOrientation(LinearLayout.HORIZONTAL);
        stampsView.setLayoutParams(pr);
        additionalInfo.addView(stampsView);


        TextView foodPrice = new TextView(context);
        foodPrice.setText(df.format(price) +" zł");
        foodPrice.setTextColor(context.getColor(R.color.black));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.topMargin = 10;
        params.weight = 1;
        foodPrice.setTextSize(15);
        foodPrice.setLayoutParams(params);
        foodPrice.setTextAlignment(TEXT_ALIGNMENT_TEXT_END);
        additionalInfo.addView(foodPrice);

        ly.addView(additionalInfo);

    }

    private void setStamp(String stamp) {

        ImageView icon = new ImageView(context);
        if(stamp.equals("vegan")){
            icon.setImageDrawable(context.getDrawable(R.drawable.vegan));
        }
        else if(stamp.equals("alcoholic")){
            icon.setImageDrawable(context.getDrawable(R.drawable.non_alcohol));
        }
        else{
            icon.setImageDrawable(context.getDrawable(R.drawable.gluten_free));
        }

        icon.setScaleType(ImageView.ScaleType.FIT_CENTER);
        LinearLayout.LayoutParams pr = new LinearLayout.LayoutParams(100,100);
    //    pr.weight = 1;
        icon.setLayoutParams(pr);
        stampsView.addView(icon);
    }



}
