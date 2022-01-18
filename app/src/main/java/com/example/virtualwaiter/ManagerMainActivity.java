package com.example.virtualwaiter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class ManagerMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_main);

        LinearLayout employees = findViewById(R.id.employees_manage);
        employees.setOnClickListener(v->{
            this.moveToManageWorkersActivity();
        });

        LinearLayout orders = findViewById(R.id.orders_manage);
        orders.setOnClickListener(v->{
            this.moveToManageOrdersActivity();
        });

        LinearLayout tables = findViewById(R.id.tables_manage);
        tables.setOnClickListener(v->{
            this.moveToManageTablesActivity();
        });

        LinearLayout menu = findViewById(R.id.menu_manage);
        menu.setOnClickListener(v->{
            this.moveToManageMenuActivity();
        });

    }
    public void moveToManageWorkersActivity() {
        Intent i = new Intent(ManagerMainActivity.this, ManageWorkersActivity.class);
        ManagerMainActivity.this.startActivity(i);
    }



    public void moveToManageOrdersActivity() {
        Intent i = new Intent(ManagerMainActivity.this, ManageOrdersActivity.class);
        ManagerMainActivity.this.startActivity(i);
    }

    public void moveToManageTablesActivity() {
        Intent i = new Intent(ManagerMainActivity.this, ManageTablesActivity.class);
        ManagerMainActivity.this.startActivity(i);
    }

    public void moveToManageMenuActivity() {
        Intent i = new Intent(ManagerMainActivity.this, ManageMenuActivity.class);
        ManagerMainActivity.this.startActivity(i);
    }
}