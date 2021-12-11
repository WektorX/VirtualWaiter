package com.example.virtualwaiter;

import android.content.Context;
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

import java.lang.ref.WeakReference;

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
            l.login(login,password, false);
        });

        btnTable.setOnClickListener(v -> {
            Intent i = new Intent(LoginActivity.this, ChooseTableActivity.class);
            LoginActivity.this.startActivity(i);
        });

    }


    public class initDB extends AsyncTask {
//        private WeakReference<Context> contextRef;
//
//        public initDB(Context context) {
//            contextRef = new WeakReference<>(context);
//        }

        @Override
        protected Object doInBackground(Object[] objects) {
            String status = DB.initConnection();
            Context context = LoginActivity.this;
            if (context != null) {
                if (status.equals("error")) {
                    return "error";
                }
                else{
                    return "ok";
                }
            }

            return "error";
        }

        @Override
        protected void onPostExecute(Object o){
            String response = (String) o;
            if(response.equals("error")){
                Toast toast = Toast.makeText(LoginActivity.this, "Connection to database failed! Try again later!", Toast.LENGTH_LONG);
                toast.show();
            }
        }
    }
}