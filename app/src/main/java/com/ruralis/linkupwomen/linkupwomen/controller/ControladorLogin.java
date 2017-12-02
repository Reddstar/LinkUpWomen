package com.ruralis.linkupwomen.linkupwomen.controller;

import android.os.AsyncTask;
import android.widget.Toast;

import com.ruralis.linkupwomen.linkupwomen.model.Sessao;
import com.ruralis.linkupwomen.linkupwomen.model.Usuario;
import com.ruralis.linkupwomen.linkupwomen.view.LoginActivity;

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
 * Created by Ricardo Silva on 02/12/2017.
 */

public class ControladorLogin {

    private static Usuario usuario;
    private Sessao sessao;

    private static String request;

    public void transformarEmDados(String data, Usuario usuario){
        request = "/login";
        String[] valores = data.split(",");
        for (String valor : valores){
            if (valor.contains("fullname")){
                definirNomeUsuario(valor, usuario);
            }
        }
    }

    public boolean loginECadastro(Usuario usuario, String data) {
        transformarEmDados(data, usuario);
        ControladorLogin.usuario = usuario;
        String result = comunicate();
        if (result == "0") {
            request = "/registrarusuario";
            result = comunicate();
            if (result == "0") {
                return false;
            }
        }
        return true;
    }

    public String comunicate() {
        final ControladorLogin.ControladorServidor controller = new ControladorLogin.ControladorServidor();
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

    public void definirNomeUsuario(String data, Usuario usuario){
        String[] partes = data.split(":");
        usuario.setNommeCompleto(partes[1].replace("\"", ""));
    }
    private class ControladorServidor extends AsyncTask<String, Void, String> {

        public String result;

        @Override
        protected String doInBackground(String... strings) {
            StringBuilder result = new StringBuilder();
            HttpURLConnection connection = null;
                try {
                    URL url = new URL(Sessao.getServerID() + request);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setDoOutput(true);
                    connection.setDoInput(true);
                    connection.setRequestMethod("POST");
                    OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());

                    JSONObject json = new JSONObject();
                    json.put("CPF", "null");
                    json.put("Login", usuario.getLogin());
                    json.put("Senha", usuario.getSenha());
                    json.put("Nome_completo", usuario.getNommeCompleto());
                    writer.write(json.toString());
                    Toast.makeText(Sessao.getContext(), json.toString(), Toast.LENGTH_LONG).show();
                    writer.close();
                    connection.connect();
                    InputStream in;
                    /*
                     Antes de abrir uma conexão com o inputStream, verificar o código de retorno da
                    requisição, caso a o código de retorno seja de erro a conexão de resposta virá
                    através de um ErrorStream, caso contrário virá através de um InputStream.
                    */
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
