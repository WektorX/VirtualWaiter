package com.example.virtualwaiter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.example.virtualwaiter.CommonClasses.Food;
import com.example.virtualwaiter.Net.ConnectDB;
import com.example.virtualwaiter.UI.Components.MenuItem;

import java.util.ArrayList;
import java.util.Map;

public class OrderMenuActicity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_menu_acticity);
        new getMenu().execute();

    }


    public class getMenu extends AsyncTask<Void, Void, Map<String, ArrayList<Object>>> {

        @Override
        protected Map<String, ArrayList<Object>> doInBackground(Void... voids) {
            return ConnectDB.getFood();
        }

        @Override
        protected void onPostExecute(Map<String, ArrayList<Object>> o) {

            ArrayList<Object> food = o.get("food");
            LinearLayout lv = findViewById(R.id.MenuList);
            for(Object f : food){
                Food temp = (Food)f;

                MenuItem tx = new MenuItem(OrderMenuActicity.this, (Food)f);
              //  tx.setFoodName(((Food) f).getName());
                lv.addView(tx);
            }

        }

    }
}