package com.example.virtualwaiter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.virtualwaiter.databinding.ActivityManageTablesBinding;
import com.google.android.material.snackbar.Snackbar;

public class ManageTablesActivity extends AppCompatActivity {

    private ActivityManageTablesBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityManageTablesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        binding.addNewTableBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ManageTablesActivity.this, AddEditTableActivity.class);
                Bundle b = new Bundle();
                b.putBoolean("editTable", false);
                i.putExtras(b);
                ManageTablesActivity.this.startActivity(i);
            }
        });
    }
}