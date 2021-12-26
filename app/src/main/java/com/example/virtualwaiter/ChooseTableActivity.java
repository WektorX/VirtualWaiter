package com.example.virtualwaiter;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

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

    private void generateButtons(ArrayList<Integer> tables){
        ProgressBar pb = findViewById(R.id.pbTables);
        pb.setVisibility(View.GONE);

        for (Integer tableId : tables) {
           // Button button = new Button(ChooseTableActivity.this);
            Button button = new TableButton(ChooseTableActivity.this);
            button.setText(getString(R.string.btnTableLabel)+" "+tableId);
            button.setId(tableId);
            button.setOnClickListener(v -> {
                StaticData.TABLE.setTableId(button.getId());
                StaticData.ORDER.setTable(StaticData.TABLE);
                Intent i = new Intent(ChooseTableActivity.this, ChooseWaiter.class);
                ChooseTableActivity.this.startActivity(i);
            });

            LinearLayout l = findViewById(R.id.buttons_view);
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
            ArrayList<Integer> tables = (ArrayList<Integer>) o;
            generateButtons(tables);
        }
    }

}