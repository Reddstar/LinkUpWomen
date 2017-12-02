package com.ruralis.linkupwomen.linkupwomen.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import android.widget.TextView;

import com.github.clans.fab.FloatingActionMenu;
import com.ruralis.linkupwomen.linkupwomen.R;
import com.ruralis.linkupwomen.linkupwomen.model.Grupo;

import java.util.ArrayList;
import java.util.List;

public class GruposActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ArrayList<Grupo> grupos = new ArrayList<>();
    private RecyclerView recycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grupos);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionMenu fab = findViewById(R.id.fab_menu);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        recycler = findViewById(R.id.recycler);

        initialize();
    }

    public void initialize(){
        Grupo grupo = new Grupo("TESTE", "20 mins", "CEAGRI II", "RU");
        grupos.add(grupo);
        grupos.add(grupo);
        grupos.add(grupo);
        grupos.add(grupo);
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
            Intent intentAlerta = new Intent(GruposActivity.this, AlertaActivity.class);
            startActivity(intentAlerta);


        } else if (id == R.id.nav_perfil) {
            Intent intentPerfil = new Intent(GruposActivity.this, PerfilActivity.class);
            startActivity(intentPerfil);


        } else if (id == R.id.nav_grupo) {



        } else if (id == R.id.nav_mapa) {


        } else if (id == R.id.nav_sobre) {
            Intent intentSobre = new Intent(GruposActivity.this, SobreActivity.class);
            startActivity(intentSobre);



        } else if (id == R.id.nav_ranking) {
            Intent intentRanking = new Intent(GruposActivity.this, RankingActivity.class);
            startActivity(intentRanking);

        }



        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
            holder.setIsRecyclable(false);
            holder.tituloGrupo.setText(grupos.get(position).getTitulo());
            holder.pontoDePartida.setText(holder.pontoDePartida.getText().toString() + grupos.get(position).getPartida());
            holder.destino.setText(holder.destino.getText().toString() + grupos.get(position).getDestino());
            holder.tempo.setText(holder.tempo.getText().toString() + grupos.get(position).getTempo());
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

            public ViewHolder(View item) {
                super(item);
                cardView = item.findViewById(R.id.card_view);
                tituloGrupo = item.findViewById(R.id.titulo);
                pontoDePartida = item.findViewById(R.id.pontoDePartida);
                destino = item.findViewById(R.id.destino);
                tempo = item.findViewById(R.id.tempo);
            }
        }
    }
}
