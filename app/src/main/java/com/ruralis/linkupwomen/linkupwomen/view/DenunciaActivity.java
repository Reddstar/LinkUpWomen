package com.ruralis.linkupwomen.linkupwomen.view;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ruralis.linkupwomen.linkupwomen.R;

/**
 * Created by Stefany on 02/12/2017.
 */

public class DenunciaActivity extends AppCompatActivity{
    private EditText editTextUsuarioReportado;
    private EditText editTextRelatoDenuncia;
    private Button btnDenunciarUsuario;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.denuncia_layout);
        editTextUsuarioReportado = (EditText) findViewById(R.id.edit_text_usuarioReportado);
        editTextRelatoDenuncia = (EditText) findViewById(R.id.edit_text_relatoDenuncia);
        btnDenunciarUsuario = (Button) findViewById(R.id.btn_denunciarUsuario);

        btnDenunciarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


    }
}
