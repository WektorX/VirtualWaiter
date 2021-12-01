package com.example.virtualwaiter;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.virtualwaiter.DB.DB;
import com.example.virtualwaiter.UI.Login;

public class MainActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN = 4000;
    Animation logoAnim;
    TextView appName;
    ImageView logo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        // set full screen (delete menu bar at the top)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        appName = findViewById(R.id.tvAppName);
        logo = findViewById(R.id.ivLogo);
        logoAnim = AnimationUtils.loadAnimation(this, R.anim.logo_and_appname_animation );

        logo.setAnimation(logoAnim);
        appName.setAnimation(logoAnim);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        },SPLASH_SCREEN);

    }



}