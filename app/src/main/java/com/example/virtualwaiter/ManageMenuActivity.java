package com.example.virtualwaiter;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.virtualwaiter.CommonClasses.FoodToAdd;
import com.example.virtualwaiter.CommonClasses.Worker;
import com.example.virtualwaiter.Net.ConnectDB;
import com.example.virtualwaiter.Net.StaticData;
import com.example.virtualwaiter.UI.Components.ManagerFoodCardView;
import com.example.virtualwaiter.UI.Components.ManagerWorkerCardView;
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
                finish();
            }
        });

        new getFoodFull().execute();
    }

    public void showAllFood() {
        LinearLayout allFoodLinearLayout = (LinearLayout) findViewById(R.id.foodList);
        allFoodLinearLayout.removeAllViews();

        for (FoodToAdd f: StaticData.ALL_FOOD
        ) {
            allFoodLinearLayout.addView(new ManagerFoodCardView(ManageMenuActivity.this, f));
        }
    }

    public class getFoodFull extends AsyncTask {
        @Override
        protected String doInBackground(Object... objects) {
            return ConnectDB.getFoodFull();
        }

        @Override
        protected void onPostExecute(Object o) {
            String status = (String) o;
            Log.d("Status" , status);
            if(status.equals("success")){
                showAllFood();
            }
            else {
                Toast.makeText(ManageMenuActivity.this,
                        getString(R.string.get_workers_fail),
                        Toast.LENGTH_SHORT).show();
                Intent i = new Intent(ManageMenuActivity.this, ManagerMainActivity.class);
                ManageMenuActivity.this.startActivity(i);
            }
        }
    }
}