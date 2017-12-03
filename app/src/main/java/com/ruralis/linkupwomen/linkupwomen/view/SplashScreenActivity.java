package com.ruralis.linkupwomen.linkupwomen.view;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ruralis.linkupwomen.linkupwomen.R;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Handler handle = new Handler();
        handle.postDelayed(new Runnable() {
            @Override
            public void run() {
                irLogin();
            }
        }, 2000);
    }

    public void irLogin(){
        Intent irLogin = new Intent(SplashScreenActivity.this, LoginActivity.class);
        finish();
        startActivity(irLogin);
    }

}
