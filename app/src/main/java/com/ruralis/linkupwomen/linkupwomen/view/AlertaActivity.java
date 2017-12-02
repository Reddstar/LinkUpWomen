package com.ruralis.linkupwomen.linkupwomen.view;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.ruralis.linkupwomen.linkupwomen.R;

/**
 * Created by Stefany on 02/12/2017.
 */

public class AlertaActivity extends AppCompatActivity{

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.alertas_layout);
    }
}
