package com.ruralis.linkupwomen.linkupwomen.model;

import android.content.Context;

/**
 * Created by Ricardo Silva on 02/12/2017.
 */

public class Sessao {

    private static String serverID;
    private static Context context;
    private Usuario usuario;

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
