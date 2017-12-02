package com.ruralis.linkupwomen.linkupwomen;

/**
 * Created by ricardo on 02/12/2017.
 */

public class Grupo {
    private String titulo;
    private String tempo;
    private String partida;
    private String destino;

    public Grupo(String titulo, String tempo, String partida, String destino){
        this.titulo = titulo;
        this.tempo = tempo;
        this.partida = partida;
        this.destino = destino;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTempo() {
        return tempo;
    }

    public void setTempo(String tempo) {
        this.tempo = tempo;
    }

    public String getPartida() {
        return partida;
    }

    public void setPartida(String partida) {
        this.partida = partida;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }
}
