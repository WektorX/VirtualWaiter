package com.example.virtualwaiter;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.virtualwaiter.CommonClasses.Table;
import com.example.virtualwaiter.Net.ConnectDB;
import com.example.virtualwaiter.Net.StaticData;
import com.example.virtualwaiter.UI.Components.TableButton;

import java.util.ArrayList;

public class ChooseTableActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_table);
        new getTablesDB().execute();
    }

    private void generateButtons(ArrayList<Table> tables){
        ProgressBar pb = findViewById(R.id.pbTables);
        pb.setVisibility(View.GONE);
        GridLayout l = findViewById(R.id.tables_gallery);
        for (Table t : tables) {
            Button button = new TableButton(ChooseTableActivity.this);
            button.setText(getString(R.string.btnTableLabel)+" "+t.getTableId() +"\n"+ t.getNumberOfSeats()+ " - " +getString(R.string.table_capacity));
            button.setId(t.getTableId());
            button.setGravity(Gravity.BOTTOM | Gravity.CENTER);
            button.setOnClickListener(v -> {
                StaticData.TABLE.setTableId(button.getId());
                StaticData.ORDER.setTable(StaticData.TABLE);
                Intent i = new Intent(ChooseTableActivity.this, ChooseWaiter.class);
                ChooseTableActivity.this.startActivity(i);
            });
            l.addView(button);

        }
    }


    public class getTablesDB extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] objects) {
            return ConnectDB.getFreeTables();
        }

        @Override
        protected void onPostExecute(Object o) {
            ArrayList<Table> tables = (ArrayList<Table>) o;
            generateButtons(tables);
        }
    }

}