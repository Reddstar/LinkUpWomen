package com.ruralis.linkupwomen.linkupwomen.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.ruralis.linkupwomen.linkupwomen.R;
import com.ruralis.linkupwomen.linkupwomen.controller.ControladorGrupo;
import com.ruralis.linkupwomen.linkupwomen.model.Grupo;
import com.ruralis.linkupwomen.linkupwomen.model.Sessao;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class GruposActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Sessao sessao = Sessao.getInstance();
    private ControladorGrupo controladorGrupo = new ControladorGrupo();
    private ArrayList<Grupo> grupos = new ArrayList<>();
    private RecyclerView recycler;
    private static Date dataAgora = new Date();
    private FloatingActionButton atualizar;
    private FloatingActionButton criar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grupos);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        atualizar = findViewById(R.id.fab_atualizar);
        criar = findViewById(R.id.fab_add);
        criar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                criarNovoGrupo();
            }
        });
        atualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atualizarGrupos();
            }
        });

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        recycler = findViewById(R.id.recycler);


        atualizarGrupos();
    }

    @Override
    public void onBackPressed() {
        Intent voltarLogin = new Intent(GruposActivity.this, LoginActivity.class);
        finish();
        startActivity(voltarLogin);
    }

    private void criarNovoGrupo() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.dialog_layout, null);

        final int[] spinnerPosition = {0};

        final String[] options = {"CEAGRI 1", "CEAGRI 2", "PESCA", "DLCH", "Biblioteca Central", "DCE", "CEGOE", "Casa dos Estudantes (CEAGRI)", "Casa dos Estudantes (Feminina)", "Casa dos Estudantes (Masculino)", "Hospital Veterinário", "Zootecnia"};
        final Spinner spinner = layout.findViewById(R.id.spinner);
        final EditText descricao = layout.findViewById(R.id.descricao);
        final EditText tempoAdd = layout.findViewById(R.id.edt_tempo);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(GruposActivity.this, android.R.layout.simple_spinner_item, options);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerPosition[0] = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                spinnerPosition[0] = 0;
            }
        });

        //Building dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(layout);
        builder.setPositiveButton("CRIAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Grupo grupo = new Grupo();
                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm");
                grupo.setTitulo(String.valueOf(spinnerPosition[0] + 1));
                grupo.setDestino(grupo.getTitulo());
                Calendar c = Calendar.getInstance();
                Date tempo = new Date();
                c.setTime(tempo);
                int add = Integer.parseInt(tempoAdd.getText().toString());
                c.add(Calendar.MINUTE, add);
                grupo.setTempo(format.format(c.getTime()));
                grupo.setDescricao(descricao.getText().toString());
                grupo.setIdDono(sessao.getUsuario().getId());
                grupo.setPartida(String.valueOf(Sessao.getIdPorNome("CEAGRI 2")));
                controladorGrupo.setGrupo(grupo);
                controladorGrupo.enviarGrupo();

            }
        });
        builder.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getButton(dialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#a5098f"));
        dialog.getButton(dialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#a5098f"));
    }

    public void atualizarGrupos(){
        grupos = new ArrayList<>();
        String resposta = communicate();
        Log.d("RESPOSTA", resposta);
        tratarResposta(resposta);
        dataAgora = new Date();
        adapt();
    }

    public void adapt(){
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(llm);
        AdaptadorGrupo adaptador = new AdaptadorGrupo(grupos);
        recycler.setAdapter(adaptador);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_alerta) {
            Intent intentAlerta = new Intent(getApplicationContext(), AlertasActivity.class);
            startActivity(intentAlerta);


        } else if (id == R.id.nav_perfil) {
            Intent intentPerfil = new Intent(this, PerfilsActivity.class);
            startActivity(intentPerfil);


        } else if (id == R.id.nav_grupo) {



        } else if (id == R.id.nav_mapa) {


        } else if (id == R.id.nav_sobre) {
            Intent intentSobre = new Intent(this, SobresActivity.class);
            startActivity(intentSobre);



        } else if (id == R.id.nav_ranking) {
            Intent intentRanking = new Intent(this, RankingsActivity.class);
            startActivity(intentRanking);

        }



        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public Grupo criarGrupo(JSONObject grupoData){
        Grupo grupo = null;
        try {
            grupo = new Grupo();
            JSONObject json = grupoData;
            grupo.setDescricao(json.getString("Descricao").replace("\n", ""));
            grupo.setDestino(json.getString("Destino").replace("\n", ""));
            grupo.setPartida(json.getString("Local_Partida").replace("\n", ""));
            grupo.setTempo(json.getString("Partida").replace("\n", ""));
            grupo.setTitulo(json.getString("Local_Partida").replace("\n", ""));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return grupo;
    }

    public void tratarResposta(String data){
        try {
            JSONObject jsonObject = new JSONObject(data);
            Iterator<String> iterator = jsonObject.keys();
            while (iterator.hasNext()){
                String jc = iterator.next();
                JSONObject object = jsonObject.getJSONObject(jc);
                grupos.add(criarGrupo(object));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public String communicate(){
        final GruposActivity.ControladorGrupos controller = new GruposActivity.ControladorGrupos();
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
        public String request ="/gettodosgrupos";

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
                writer.write("null");
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

    private class AdaptadorGrupo extends RecyclerView.Adapter<AdaptadorGrupo.ViewHolder> {

        private List<Grupo> grupos;

        public AdaptadorGrupo(List<Grupo> grupos){
            this.grupos = grupos;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grupo_card_layout, parent, false);
            ViewHolder holder = new ViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
            SimpleDateFormat formatoTexto = new SimpleDateFormat("HH:mm");
            holder.setIsRecyclable(false);
            holder.tituloGrupo.setText(grupos.get(position).getTitulo());
            holder.pontoDePartida.setText(holder.pontoDePartida.getText().toString() + grupos.get(position).getPartida());
            holder.destino.setText(holder.destino.getText().toString() + grupos.get(position).getDestino());
            String[] horaPartes = grupos.get(position).getTempo().split(" ");
            String tempoRestante = horaPartes[horaPartes.length - 2];
            try {
                Date data = format.parse(tempoRestante);
                tempoRestante = formatoTexto.format(data);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            holder.tempo.setText(holder.tempo.getText().toString() + tempoRestante + "Hrs");
            holder.descricao.setText(holder.descricao.getText().toString() + grupos.get(position).getDescricao());
        }

        @Override
        public int getItemCount() {
            return grupos.size();
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }

        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            CardView cardView;
            TextView tituloGrupo;
            TextView pontoDePartida;
            TextView destino;
            TextView tempo;
            TextView descricao;

            public ViewHolder(View item) {
                super(item);
                cardView = item.findViewById(R.id.card_view);
                tituloGrupo = item.findViewById(R.id.titulo);
                pontoDePartida = item.findViewById(R.id.pontoDePartida);
                destino = item.findViewById(R.id.destino);
                tempo = item.findViewById(R.id.tempo);
                descricao = item.findViewById(R.id.descricao_local);
            }
        }
    }
}
