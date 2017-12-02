package com.ruralis.linkupwomen.linkupwomen.view_controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ruralis.linkupwomen.linkupwomen.R;

public class LoginActivity extends AppCompatActivity {

    EditText editLogin;
    EditText editSenha;
    Button btn_Acessar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editLogin = findViewById(R.id.edt_txtLogin);
        editSenha = findViewById(R.id.edt_txtSenha);
        btn_Acessar = findViewById(R.id.btn_acessar);

        btn_Acessar.setOnClickListener(new View.OnClickListener(){
            public void onClick (View v){
                if(editLogin.getText().length() == 0 || editSenha.getText().length() == 0){
                    /*Toast.makeText(getApplication(),
                            "Os campos login e senha são obrigatórios",
                            Toast.LENGTH_LONG).show();*/
                    Intent forMain = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(forMain);

                }else{
                    Intent forMain = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(forMain);
                    /*Toast.makeText(getApplication(),
                     "Welcome, baby!",
                            Toast.LENGTH_LONG).show();*/
                }
            }
        });

    }




}
