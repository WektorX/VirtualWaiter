package com.example.virtualwaiter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.virtualwaiter.databinding.ActivityManageMenuBinding;

public class ManageMenuActivity extends AppCompatActivity {

    private ActivityManageMenuBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityManageMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        binding.addNewFoodBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ManageMenuActivity.this, AddEditFoodActivity.class);
                Bundle b = new Bundle();
                b.putBoolean("editFood", false);
                i.putExtras(b);
                ManageMenuActivity.this.startActivity(i);
            }
        });
    }
}