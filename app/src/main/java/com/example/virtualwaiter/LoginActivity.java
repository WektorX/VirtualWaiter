package com.example.virtualwaiter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.virtualwaiter.Net.ConnectDB;
import com.example.virtualwaiter.UI.Actions.Login;

import java.util.Locale;
import static com.example.virtualwaiter.Net.StaticData.LANGUAGE;
public class LoginActivity extends AppCompatActivity {

    private Button btnLogin;
    private Button btnTable;
    private EditText etlogin;
    private EditText etPassword;
    private Button btnPl;
    private Button btnEn;
    private String lang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        btnLogin = findViewById(R.id.btnLogin);
        btnTable = findViewById(R.id.btnTable);
        etlogin = findViewById(R.id.etLogin);
        etPassword = findViewById(R.id.etPassword);
        btnPl = findViewById(R.id.btnPl);
        btnEn = findViewById(R.id.btnEn);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Login l = new Login(LoginActivity.this);
        lang = Locale.getDefault().getLanguage();
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

        btnPl.setOnClickListener(v->{
            Locale locale = new Locale("pl");
            LANGUAGE = "pl";
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.setLocale(locale);
            LoginActivity.this.getResources().updateConfiguration(config, LoginActivity.this.getResources().getDisplayMetrics());
            Intent intent = new Intent(LoginActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });

        btnEn.setOnClickListener(v->{
            Locale locale = new Locale("en");
            LANGUAGE = "en";
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.setLocale(locale);
            LoginActivity.this.getResources().updateConfiguration(config, LoginActivity.this.getResources().getDisplayMetrics());
            Intent intent = new Intent(LoginActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });

    }


    public class initDB extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] objects) {
            String status = ConnectDB.initConnection();
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