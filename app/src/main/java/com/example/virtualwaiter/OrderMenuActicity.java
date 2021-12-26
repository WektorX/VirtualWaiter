package com.example.virtualwaiter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import static com.example.virtualwaiter.Net.StaticData.MENU;

import com.example.virtualwaiter.CommonClasses.Order;
import com.example.virtualwaiter.Net.ConnectDB;
import com.example.virtualwaiter.UI.Components.MenuItem;

public class OrderMenuActicity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_menu_acticity);
        new getMenuDishes().execute();

    }


    public class getMenuDishes extends AsyncTask {

        @Override
        protected String doInBackground(Object... objects) {
            return ConnectDB.getFood();
        }

        @Override
        protected void onPostExecute(Object o) {
            if(o.equals("success")){
                LinearLayout lvDishes = findViewById(R.id.MenuDishesList);
                LinearLayout lvDrinks = findViewById(R.id.MenuDrinksList);
                for(int i =0;i<MENU.getDishList().size();i++){
                    MenuItem tx = new MenuItem(OrderMenuActicity.this, MENU.getDishList().get(i));
                    lvDishes.addView(tx);
                }
                for(int i =0;i<MENU.getDrinksList().size();i++){
                    MenuItem tx = new MenuItem(OrderMenuActicity.this, MENU.getDrinksList().get(i));
                    lvDrinks.addView(tx);
                }
            }
        }
    }
}