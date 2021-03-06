package com.ruralis.linkupwomen.linkupwomen.view;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ruralis.linkupwomen.linkupwomen.R;
import com.ruralis.linkupwomen.linkupwomen.controller.ControladorLogin;
import com.ruralis.linkupwomen.linkupwomen.model.Sessao;
import com.ruralis.linkupwomen.linkupwomen.model.Usuario;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutionException;

public class LoginActivity extends AppCompatActivity {

    EditText editLogin;
    EditText editSenha;
    Button btn_Acessar;

    private Sessao sessao;
    private Usuario usuario;
    private static String urlParametros;
    private static String request;
    private static String infoUsuario;
    private ControladorLogin controlador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        Sessao.setServerID("http://10.244.156.122:5000");
        sessao = Sessao.getInstance();
        usuario = new Usuario();
        controlador = new ControladorLogin();
        Sessao.setContext(LoginActivity.this);

        editLogin = findViewById(R.id.edt_txtLogin);
        editSenha = findViewById(R.id.edt_txtSenha);
        btn_Acessar = findViewById(R.id.btn_acessar);


        btn_Acessar.setOnClickListener(new View.OnClickListener(){
            public void onClick (View v){
                if(editLogin.getText().length() >= 0 || editSenha.getText().length() >= 0){
                    if (validadeCPF(editLogin.getText().toString())){
                        usuario.setLogin(editLogin.getText().toString());
                        usuario.setSenha(editSenha.getText().toString());
                        request = "http://ava.ufrpe.br/login/token.php";
                        LoginActivity.urlParametros = "username=" + usuario.getLogin() + "&password=" + usuario.getSenha() + "&service=moodle_mobile_app";
                        String tokenData = communicate();
                        sessao.setUsuario(usuario);
                        if (!tokenData.contains("token")){
                            editSenha.setError("Senha ou Login incorreto");
                            return;
                        }
                        LoginActivity.urlParametros = "wsfunction=core_webservice_get_site_info&wstoken=" + getToken(tokenData);
                        request ="http://ava.ufrpe.br/webservice/rest/server.php?moodlewsrestformat=json";
                        infoUsuario = communicate();
                        Log.d("RETORNO", infoUsuario);
                    }
                    if (controlador.loginECadastro(usuario, infoUsuario)) {
                        Intent forMain = new Intent(LoginActivity.this, GruposActivity.class);
                        startActivity(forMain);
                        finish();
                    }

                }else{

                    Intent forMain = new Intent(LoginActivity.this, GruposActivity.class);
                    finish();
                    startActivity(forMain);
                }
            }
        });

    }

    public String getToken(String data){
        String token = "";
        String[] parts = data.split(":");
        token = parts[1];
        token = token.replace("\"", "");
        return  token.replace("}", "");
    }

    public boolean validadeCPF(String cpf){
        return true;
    }

    public void handleAnswer(){

    }

    public String communicate(){
        final LoginActivity.LoginController controller = new LoginActivity.LoginController();
        try {
            controller.execute();
            controller.get();
            handleAnswer();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return controller.result;
    }
    private class LoginController extends AsyncTask<String, Void, String> {

        public String result;

        @Override
        protected String doInBackground(String... strings) {

            StringBuilder result = new StringBuilder();

            byte[] postData = LoginActivity.urlParametros.getBytes(StandardCharsets.UTF_8);
            int postDataLength = postData.length;
            URL url = null;
            try {
                url = new URL(request);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            HttpURLConnection conn = null;
            try {
                conn = (HttpURLConnection) url.openConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
            conn.setDoOutput(true);
            conn.setInstanceFollowRedirects(false);
            try {
                conn.setRequestMethod("POST");
            } catch (ProtocolException e) {
                e.printStackTrace();
            }
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("charset", "utf-8");
            conn.setRequestProperty("Content-Length", Integer.toString(postDataLength));
            conn.setUseCaches(false);
            InputStream in;
            try (DataOutputStream wr = new DataOutputStream(conn.getOutputStream())) {
                wr.write(postData);
                conn.connect();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (conn.getResponseCode() >= HttpURLConnection.HTTP_BAD_REQUEST) {
                    in = new BufferedInputStream(conn.getErrorStream());
                } else {
                    in = new BufferedInputStream(conn.getInputStream());
                }
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.result = result.toString();
            return this.result;
        }

    }


}
