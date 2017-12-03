package com.ruralis.linkupwomen.linkupwomen.controller;

import android.os.AsyncTask;
import android.util.Log;

import com.ruralis.linkupwomen.linkupwomen.model.Grupo;
import com.ruralis.linkupwomen.linkupwomen.model.Sessao;
import com.ruralis.linkupwomen.linkupwomen.view.GruposActivity;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

/**
 * Created by ricar on 03/12/2017.
 */

public class ControladorGrupo {

    private Grupo grupo;

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public void enviarGrupo(){
        Log.d("RESPOSTA_AO_INSERIR", communicate());
    }

    public String communicate(){
        final ControladorGrupo.ControladorGrupos controller = new ControladorGrupo.ControladorGrupos();
        try {
            controller.execute();
            controller.get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return controller.result;
    }

    private class ControladorGrupos extends AsyncTask<String, Void, String> {

        public String result;
        public String request ="/registrargrupo";

        @Override
        protected String doInBackground(String... strings) {
            StringBuilder result = new StringBuilder();
            HttpURLConnection connection = null;
            try {
                URL url = new URL(Sessao.getServerID() + request);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                connection.setDoOutput(true);
                connection.setDoInput(true);
                connection.setRequestMethod("POST");
                OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
                JSONObject json = new JSONObject();
                json.put("ID_Dono", grupo.getIdDono());
                json.put("Partida", grupo.getTempo());
                json.put("Destino", grupo.getDestino());
                json.put("Local_Partida", grupo.getPartida());
                json.put("Descricao", grupo.getDescricao());
                json.put("Quantidade", "99");
                Log.d("JSON ENVIADO", json.toString());
                writer.write(json.toString());
                writer.close();
                connection.connect();
                InputStream in;

                if (connection.getResponseCode() >= HttpURLConnection.HTTP_BAD_REQUEST)
                    in = new BufferedInputStream(connection.getErrorStream());
                else
                    in = new BufferedInputStream(connection.getInputStream());

                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
            this.result = result.toString();
            return this.result;
        }

    }
}
