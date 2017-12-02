package com.ruralis.linkupwomen.linkupwomen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText editLogin;
    EditText editSenha;
    Button btn_Acessar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editLogin = (EditText)findViewById(R.id.edt_txtLogin);
        editSenha = (EditText)findViewById(R.id.edt_txtSenha);
        btn_Acessar = (Button)findViewById(R.id.btn_acessar);

        btn_Acessar.setOnClickListener(new View.OnClickListener(){
            public void onClick (View v){

                if(editLogin.getText().length() == 0 || editSenha.getText().length() ==0){
                    Toast.makeText(getApplication(),
                            "Os campos login e senha são obrigatórios",
                            Toast.LENGTH_LONG).show();

                }else{
                    Toast.makeText(getApplication(),
                     "Welcome, baby!",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

    }




}
