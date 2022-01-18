package com.example.virtualwaiter;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.virtualwaiter.CommonClasses.Table;
import com.example.virtualwaiter.Net.ConnectDB;
import com.example.virtualwaiter.Net.StaticData;
import com.example.virtualwaiter.UI.Components.ManagerTableCardView;
import com.example.virtualwaiter.databinding.ActivityManageTablesBinding;

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
                finish();
            }
        });

        new getTables().execute();
    }
    public void showAllTables() {
        LinearLayout allTablesLinearLayout = (LinearLayout) findViewById(R.id.tablesList);
        allTablesLinearLayout.removeAllViews();

        for (Table t: StaticData.TABLES
        ) {
            allTablesLinearLayout.addView(new ManagerTableCardView(ManageTablesActivity.this, t));
        }
    }

    public class getTables extends AsyncTask {
        @Override
        protected String doInBackground(Object... objects) {
            return ConnectDB.getTables();
        }

        @Override
        protected void onPostExecute(Object o) {
            String status = (String) o;
            Log.d("Status" , status);
            if(status.equals("success")){
                showAllTables();
            }
            else {
                Toast.makeText(ManageTablesActivity.this,
                        getString(R.string.get_tables_fail),
                        Toast.LENGTH_SHORT).show();
                Intent i = new Intent(ManageTablesActivity.this, ManagerMainActivity.class);
                ManageTablesActivity.this.startActivity(i);
            }
        }
    }
}