package com.example.virtualwaiter;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import static com.example.virtualwaiter.Net.StaticData.DEVICE_HEIGHT;
import static com.example.virtualwaiter.Net.StaticData.DEVICE_WIDTH;
import static com.example.virtualwaiter.Net.StaticData.LANGUAGE;

import com.example.virtualwaiter.Net.StaticData;

import java.util.Locale;


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

        LANGUAGE = Locale.getDefault().getLanguage();
        new Handler().postDelayed(() -> {
            Intent i = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(i);
            finish();
        },SPLASH_SCREEN);


        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        DEVICE_HEIGHT = height;
        DEVICE_WIDTH = width;
    }



}