package com.example.virtualwaiter;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.virtualwaiter.DB.DB;
import com.example.virtualwaiter.UI.Login;

import java.sql.SQLException;
import java.util.ArrayList;

public class ChooseTableActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_table);

        try {
            ArrayList<Integer> tables = DB.getFreeTables();

            for (Integer tableId : tables
                 ) {
                Button button = new Button(this);
                button.setText(tableId);
                button.setOnClickListener(v -> {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ChooseTableActivity.this);
                    builder.setView(findViewById(R.id.waiter_login_table_dialog));
                    builder.setPositiveButton(R.string.signin, (dialog, id) -> {
                        // Waiter clicked Sign In button
                        Login l = new Login(ChooseTableActivity.this);
                        new LoginActivity.initDB(ChooseTableActivity.this).execute();
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

                ScrollView scrollView = findViewById(R.id.choose_table);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                scrollView.addView(button, lp);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


}