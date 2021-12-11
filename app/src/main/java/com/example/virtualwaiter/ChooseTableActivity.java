package com.example.virtualwaiter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.virtualwaiter.DB.DB;
import com.example.virtualwaiter.UI.Login;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public class ChooseTableActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_table);
        new getTablesDB().execute();
    }

    private void generateButtons(ArrayList<Integer> tables){
        for (Integer tableId : tables) {
            Button button = new Button(ChooseTableActivity.this);
            button.setText("Table "+tableId);
            button.setId(tableId);
            button.setOnClickListener(v -> {
                Log.d("Klik", String.valueOf(button.getId()));
                AlertDialog.Builder builder = new AlertDialog.Builder(ChooseTableActivity.this);
                builder.setView(findViewById(R.id.waiter_login_table_dialog));
                builder.setPositiveButton(R.string.signin, (dialog, id) -> {
                    // Waiter clicked Sign In button
                    Login l = new Login(ChooseTableActivity.this);
//                        new LoginActivity.initDB(ChooseTableActivity.this).execute();
                    EditText etlogin = findViewById(R.id.waiter_table_username);
                    EditText etpassword = findViewById(R.id.waiter_table_password);
                    String login = etlogin.getText().toString();
                    String password  = etpassword.getText().toString();
                    l.login(login,password, true);
                });
                builder.setNegativeButton(R.string.cancel, (dialog, id) -> {
                    // Waiter cancelled the dialog
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            });


            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            LinearLayout l = findViewById(R.id.buttons_view);
            l.addView(button, lp);
        }
        Log.d("Stoliki", "Dodano");
    }


    public class getTablesDB extends AsyncTask {


        @Override
        protected Object doInBackground(Object[] objects) {
            return DB.getFreeTables();


        }

        @Override
        protected void onPostExecute(Object o) {
            ArrayList<Integer> tables = (ArrayList<Integer>) o;
            generateButtons(tables);
        }
    }


}