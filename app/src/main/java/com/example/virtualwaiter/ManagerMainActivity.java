package com.example.virtualwaiter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ManagerMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_main);
    }

    public void moveToManageWorkersActivity(View view) {
        System.out.println(view);
        Intent i = new Intent(ManagerMainActivity.this, ManageWorkersActivity.class);
        ManagerMainActivity.this.startActivity(i);
    }

    public void moveToManageOrdersActivity(View view) {
        Intent i = new Intent(ManagerMainActivity.this, ManageOrdersActivity.class);
        ManagerMainActivity.this.startActivity(i);
    }

    public void moveToManageTablesActivity(View view) {
        Intent i = new Intent(ManagerMainActivity.this, ManageTablesActivity.class);
        ManagerMainActivity.this.startActivity(i);
    }

    public void moveToManageMenuActivity(View view) {
        Intent i = new Intent(ManagerMainActivity.this, ManageMenuActivity.class);
        ManagerMainActivity.this.startActivity(i);
    }
}