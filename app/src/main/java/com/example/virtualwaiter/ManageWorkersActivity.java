package com.example.virtualwaiter;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.virtualwaiter.CommonClasses.Worker;
import com.example.virtualwaiter.Net.ConnectDB;
import com.example.virtualwaiter.Net.StaticData;
import com.example.virtualwaiter.UI.Components.ManagerWorkerCardView;
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
                finish();
            }
        });

        new getWorkers().execute();
    }

    public void showAllWorkers() {
        LinearLayout allWorkersLinearLayout = (LinearLayout) findViewById(R.id.workersList);
        allWorkersLinearLayout.removeAllViews();

        for (Worker w: StaticData.EMPLOYEES
        ) {
            allWorkersLinearLayout.addView(new ManagerWorkerCardView(ManageWorkersActivity.this, w));
        }
    }

    public class getWorkers extends AsyncTask {
        @Override
        protected String doInBackground(Object... objects) {
            return ConnectDB.getWorkers();
        }

        @Override
        protected void onPostExecute(Object o) {
            String status = (String) o;
            Log.d("Status" , status);
            if(status.equals("success")){
                showAllWorkers();
            }
            else {
                Toast.makeText(ManageWorkersActivity.this,
                        getString(R.string.get_workers_fail),
                        Toast.LENGTH_SHORT).show();
                Intent i = new Intent(ManageWorkersActivity.this, ManagerMainActivity.class);
                ManageWorkersActivity.this.startActivity(i);
            }
        }
    }
}