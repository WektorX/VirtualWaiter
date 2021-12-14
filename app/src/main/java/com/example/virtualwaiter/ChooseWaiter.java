package com.example.virtualwaiter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.example.virtualwaiter.Net.ConnectDB;
import com.example.virtualwaiter.Net.StaticData;
import com.example.virtualwaiter.UI.Components.WaiterButton;

import java.util.ArrayList;
import java.util.Map;

public class ChooseWaiter extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_waiter);
        new getWaiters().execute();
    }


    private void generateButtons(Map<String, ArrayList<Object>> waiters){
        if(waiters.get("status").get(0).toString().equals("success")){
            ProgressBar pb = findViewById(R.id.pbWaiters);
            pb.setVisibility(View.GONE);
            ArrayList<Object> waitersName = waiters.get("name");
            ArrayList<Object> waitersId = waiters.get("id");
            for (int i=0;i<waitersName.size();i++) {
                // Button button = new Button(ChooseTableActivity.this);
                Button button = new WaiterButton(ChooseWaiter.this);
                button.setText(waitersName.get(i).toString());
                button.setId((Integer) waitersId.get(i));
                button.setOnClickListener(v -> {

//                    StaticData.WAITER_ID = v.getId();
                    StaticData.TABLE.setWaiterId(v.getId());
                    Intent intent = new Intent(ChooseWaiter.this, OrderMenuActicity.class);
                    startActivity(intent);

                });

                LinearLayout l = findViewById(R.id.waiter_view);
                l.addView(button);

            }
        }

    }



    public class getWaiters extends AsyncTask<Void, Void, Map<String, ArrayList<Object>>>{

        @Override
        protected Map<String, ArrayList<Object>> doInBackground(Void... voids) {
            return ConnectDB.getWaitersList();
        }

        @Override
        protected void onPostExecute(Map<String, ArrayList<Object>> o) {
            generateButtons(o);
        }

    }

}