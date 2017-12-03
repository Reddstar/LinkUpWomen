package com.ruralis.linkupwomen.linkupwomen.model;

import android.content.Context;

/**
 * Created by Ricardo Silva on 02/12/2017.
 */

public class Sessao {

    private static String serverID;
    private static Context context;
    private Usuario usuario;
    private static Sessao instance = new Sessao();
    private static String[] locais = {"N-Existe", "CEAGRI 1", "CEAGRI 2", "PESCA", "DLCH", "Biblioteca Central", "DCE","Biologia", "CEGOE", "Casa dos Estudantes (CEAGRI)", "Casa dos Estudantes (Feminina)", "Casa dos Estudantes (Masculino)", "Hospital Veterinário", "Zootecnia"};

    private Sessao(){

    }

    public static int getIdPorNome(String nome){
        for (int i = 1; i < locais.length; i++){
            if (locais[i].contains(nome)){
                return i;
            }
        }
        return 0;
    }

    public static String getNomePorId(int id){
        return locais[id];
    }

    public static Sessao getInstance() {
        return instance;
    }

    public static String getServerID() {
        return serverID;
    }

    public static void setServerID(String serverID) {
        Sessao.serverID = serverID;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        Sessao.context = context;
    }
}
