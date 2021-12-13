package com.example.virtualwaiter;

import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.virtualwaiter.CommonClasses.Food;
import com.example.virtualwaiter.Net.ConnectDB;

import java.sql.SQLException;
import java.util.ArrayList;

public class FoodMenuActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_menu);

//        try {
//            ArrayList<Food> menu_items = ConnectDB.getMenu(1);
//
//            for (Food f : menu_items
//            ) {
//                Button button = new Button(this);
//                button.setText(String.format("%s\n%s", f.getName(), f.getPrice()));
//
//                ScrollView scrollView = findViewById(R.id.food_menu);
//                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//                scrollView.addView(button, lp);
//            }
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
    }
}
