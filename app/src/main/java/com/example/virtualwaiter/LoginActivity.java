package com.example.virtualwaiter;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.virtualwaiter.DB.DB;
import com.example.virtualwaiter.UI.Login;

public class LoginActivity extends AppCompatActivity {

    public Button btnLogin;
    public Button btnTable;
    public EditText etlogin;
    public EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        btnLogin = findViewById(R.id.btnLogin);
        btnTable = findViewById(R.id.btnTable);
        etlogin = findViewById(R.id.etLogin);
        etPassword = findViewById(R.id.etPassword);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Login l = new Login(LoginActivity.this);
        new initDB().execute();

        btnLogin.setOnClickListener(v -> {

            String login = etlogin.getText().toString();
            String password  = etPassword.getText().toString();
            l.login(login,password);
        });

        btnTable.setOnClickListener(v -> {
            Intent i = new Intent(LoginActivity.this, ChooseTableActivity.class);
            LoginActivity.this.startActivity(i);
        });

    }


    public class initDB extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] objects) {
            String status = DB.initConnection();
            if(status.equals("error")){
                Toast toast = Toast.makeText(LoginActivity.this, "Connection error! Try again later!", Toast.LENGTH_LONG);
                toast.show();
            }

            return null;
        }
    }
}