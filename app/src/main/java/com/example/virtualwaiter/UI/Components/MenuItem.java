package com.example.virtualwaiter.UI.Components;

import static com.example.virtualwaiter.Net.StaticData.IP;
import static com.example.virtualwaiter.Net.StaticData.MENU;
import static com.example.virtualwaiter.Net.StaticData.ORDER;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.example.virtualwaiter.CommonClasses.OrderItem;
import com.example.virtualwaiter.Net.StaticData;
import com.example.virtualwaiter.R;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class MenuItem  extends CardView {

    private Food item;
    private TextView foodName;
    private LinearLayout ly;
    private Context context;
    private LinearLayout additionalInfo;
    private LinearLayout stampsView;
    private Button addBtn;
    private Button removeBtn;
    private TextView itemAmount = null;
    private static final DecimalFormat df = new DecimalFormat("0.00");

    public MenuItem(@NonNull Context context, Food f) {
        super(context);
        this.context = context;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.topMargin = 50;
        this.setBackgroundColor(context.getColor(R.color.FoodItem_background));
        this.setLayoutParams(params);
        this.item = f;
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
        setButtons();
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

    private void setPrice(BigDecimal price) {
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
        foodPrice.setTextSize(20);
        foodPrice.setLayoutParams(params);
        foodPrice.setTextAlignment(TEXT_ALIGNMENT_TEXT_END);
        additionalInfo.addView(foodPrice);

        ly.addView(additionalInfo);

    }

    @SuppressLint("UseCompatLoadingForDrawables")
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

    @SuppressLint("ClickableViewAccessibility")
    private void setButtons(){

        int index;
        boolean isDish = (item instanceof Dish);
        if(isDish){
            index = MENU.getDishList().indexOf(item);
        }
        else{
            index = MENU.getDrinksList().indexOf(item);
        }

        LinearLayout buttons = new LinearLayout(context);
        ViewGroup.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        buttons.setOrientation(LinearLayout.HORIZONTAL);
        buttons.setLayoutParams(lp);
        buttons.setGravity(Gravity.END);

        addBtn = new Button(context);
        addBtn.setText("+");
        addBtn.setLayoutParams(new LinearLayout.LayoutParams(100, 100));
        addBtn.setBackgroundColor(context.getColor(R.color.orange));
        addBtn.setTextSize(18);
        addBtn.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        v.getBackground().setColorFilter(context.getColor(R.color.orange_light), PorterDuff.Mode.SRC_ATOP);
                        v.invalidate();
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        v.getBackground().clearColorFilter();
                        v.invalidate();
                        break;
                    }
                }
                return false;
            }
        });

        addBtn.setOnClickListener(v -> {
            Food f;
            if(isDish){
                f = MENU.getDishList().get(index);
            }
            else{
                f = MENU.getDrinksList().get(index);
            }

            ORDER.addToOrder(f);
            this.handleAmountChange(f);
        });

        removeBtn = new Button(context);
        removeBtn.setText("-");
        removeBtn.setLayoutParams(new LinearLayout.LayoutParams(100, 100));
        removeBtn.setBackgroundColor(context.getColor(R.color.remove_btn_color));
        removeBtn.setTextSize(18);

        removeBtn.setOnClickListener(v -> {
            Food f;
            if(isDish){
                f = MENU.getDishList().get(index);
            }
            else{
                f = MENU.getDrinksList().get(index);
            }
            ORDER.removeFromOrder(f);
            this.handleAmountChange(f);
        });


        removeBtn.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        v.getBackground().setColorFilter(context.getColor(R.color.remove_btn_color_dark), PorterDuff.Mode.SRC_ATOP);
                        v.invalidate();
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        v.getBackground().clearColorFilter();
                        v.invalidate();
                        break;
                    }
                }
                return false;
            }
        });
        removeBtn.setEnabled(false);
        buttons.addView(removeBtn);
        buttons.addView(addBtn);
        ly.addView(buttons);
    }

    @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
    private void handleAmountChange(Food f){
        int idx = -1;
        for(OrderItem item : ORDER.getList()){
            if(item.getFoodName().equals(f.getName())){
                idx = ORDER.getList().indexOf(item);
            }
        }
        int amount =0;
        if(idx != -1){
            amount = ORDER.getList().get(idx).getAmount();
        }
        if(amount == 0){
            removeBtn.setEnabled(false);
        }
        else{
            removeBtn.setEnabled(true);
        }

        if(itemAmount == null){
            itemAmount = new TextView(context);
            itemAmount.setBackground(context.getDrawable(R.drawable.amount_stamp));
            itemAmount.setText("x" + amount);
            itemAmount.setTextSize(20);
            itemAmount.setTextAlignment(TEXT_ALIGNMENT_CENTER);
            itemAmount.setGravity(Gravity.CENTER);
            itemAmount.setTextColor(context.getColor(R.color.black));
            LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(150,150);
            p.setMargins(this.getWidth() - 170,(-1 * this.getHeight()) + 20,0,0);
            itemAmount.setLayoutParams(p);
            ly.addView(itemAmount);
        }
        else{
            if(amount>0){
                itemAmount.setVisibility(VISIBLE);
                itemAmount.setText("x" + amount);
            }
            else{
                itemAmount.setVisibility(INVISIBLE);
            }
        }
    }



}
