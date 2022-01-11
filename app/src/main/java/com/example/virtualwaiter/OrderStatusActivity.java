package com.example.virtualwaiter;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import com.example.virtualwaiter.Net.ConnectDB;
import com.example.virtualwaiter.Net.StaticData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class OrderStatusActivity extends AppCompatActivity {

    private int requestInterval = 5;
    private TimeUnit intervalTimeUnit = TimeUnit.SECONDS;
    private String[] statusArray;
    private Context context;
    private  ScheduledExecutorService executorService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);
        statusArray = new String[]{"placed", "received", "in progress", "ready", "delivered"};
        context = this;


        executorService = Executors.newScheduledThreadPool(1);
        executorService.scheduleWithFixedDelay(new Runnable() {
            public void run() {
                new getOrderStatus().execute();
            }
        }, 0, requestInterval, intervalTimeUnit);
    }

    private void animateChangeStatus(TextView tv){
        ValueAnimator textsizeAnimator = ValueAnimator.ofFloat(40, 50);
        textsizeAnimator.setDuration(600);
        textsizeAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedValue = (float) textsizeAnimator.getAnimatedValue();
                tv.setTextSize(animatedValue);
            }
        });



        Integer colorFrom = getColor(R.color.white);
        Integer colorTo = getColor(R.color.order_status_ready);
        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
              tv.setTextColor((Integer)colorAnimation.getAnimatedValue());
            }
        });

        textsizeAnimator.start();
        colorAnimation.start();
    }



    public class getOrderStatus extends AsyncTask {

        @Override
        protected String doInBackground(Object... objects) {
            return ConnectDB.getOrderStatus();
        }

        @Override
        protected void onPostExecute(Object o) {
            String status = (String) o;
            Log.d("Status" , status);
            if(status != null){
                StaticData.ORDER.setStatus(status);
                if(status.equals("delivered")){
                    executorService.shutdownNow();
                    Intent i = new Intent(OrderStatusActivity.this, PayActivity.class);
                    startActivity(i);
                    finish();
                }
                else{
                    int index = Arrays.asList(statusArray).indexOf(status);
                    for(int i= 0; i<= index; i++){
                        int textViewId = getResources().getIdentifier("status_" + statusArray[i].replace(" ","_"), "id", getPackageName());
                        TextView tv = findViewById(textViewId);
                        if(tv.getTextSize() <= 130){
                            Handler h = new Handler();
                            h.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    animateChangeStatus(tv);
                                }
                            }, 200 * i);


                        }
                    }
                }
            }
        }
    }
}