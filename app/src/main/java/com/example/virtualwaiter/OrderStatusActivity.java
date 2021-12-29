package com.example.virtualwaiter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.virtualwaiter.Net.ConnectDB;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class OrderStatusActivity extends AppCompatActivity {

    private int requestInterval = 5;
    private TimeUnit intervalTimeUnit = TimeUnit.MINUTES;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        executorService.scheduleWithFixedDelay(new Runnable() {
            public void run() {
                //put query logic here
                new getOrderStatus().execute();
            }
        }, 10, requestInterval, intervalTimeUnit);

    }



    public class getOrderStatus extends AsyncTask {

        @Override
        protected String doInBackground(Object... objects) {
            return ConnectDB.getOrderStatus();
        }

        @Override
        protected void onPostExecute(Object o) {
            String status = (String) o;
            if(status != null){
                if(status.equals("in progress")){
                    TextView tv = findViewById(R.id.status_in_progress);
                    tv.setBackgroundColor(getColor(R.color.order_status_ready));
                }
                else if(status.equals("ready")){
                    TextView tv = findViewById(R.id.status_ready);
                    tv.setBackgroundColor(getColor(R.color.order_status_ready));
                    intervalTimeUnit = TimeUnit.SECONDS;
                    requestInterval = 30;
                }
                else if(status.equals("delivered")){
//                    przejdź do kolejnej activity
                }
                else{
                    Log.d("status" , status);
                }
            }

        }
    }
}