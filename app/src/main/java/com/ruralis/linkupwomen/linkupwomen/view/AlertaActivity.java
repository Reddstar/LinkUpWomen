package com.ruralis.linkupwomen.linkupwomen.view;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;
import com.ruralis.linkupwomen.linkupwomen.R;

/**
 * Created by Stefany on 02/12/2017.
 */

public class AlertaActivity extends AppCompatActivity{

    Button btn_add_alerta;
    TextView txtView_titulo_alerta;
    TextView txtView_descricao_alerta;
    TextView txtView_horario;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.alertas_layout);

        btn_add_alerta.findViewById(R.id.btn_add_alerta);
        txtView_titulo_alerta.findViewById(R.id.titulo_alerta);
        txtView_descricao_alerta.findViewById(R.id.descricao_alerta);
        txtView_horario.findViewById(R.id.horario_alerta);
    }
}
