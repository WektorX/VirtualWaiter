package com.example.virtualwaiter;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.virtualwaiter.DB.DB;

import java.sql.SQLException;
import java.util.ArrayList;

public class ChooseTableActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_table);

        try {
            ArrayList<Integer> tables = DB.getFreeTables();

            for (Integer tableId : tables
                 ) {
                Button button = new Button(this);
                button.setText(tableId);
                button.setOnClickListener(v -> {
                    try {
                        DB.setWaiterToTable(420);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    Intent i = new Intent(ChooseTableActivity.this, FoodMenuActivity.class);
                    ChooseTableActivity.this.startActivity(i);
                });

                ScrollView scrollView = findViewById(R.id.choose_table);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                scrollView.addView(button, lp);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}