package com.example.virtualwaiter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.virtualwaiter.databinding.ActivityManageWorkersBinding;

public class ManageWorkersActivity extends AppCompatActivity {

    private ActivityManageWorkersBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityManageWorkersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        binding.addNewWorkerBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ManageWorkersActivity.this, AddEditWorkerActivity.class);
                Bundle b = new Bundle();
                b.putBoolean("editWorker", false);
                i.putExtras(b);
                ManageWorkersActivity.this.startActivity(i);
            }
        });
    }
}