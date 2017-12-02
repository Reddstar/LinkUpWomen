package com.ruralis.linkupwomen.linkupwomen.model;

/**
 * Created by ricardo on 02/12/2017.
 */

public class Grupo {
    private String titulo;
    private String tempo;
    private String partida;
    private String destino;
    private String descricao;

    public Grupo(String titulo, String tempo, String partida, String destino, String descricao){
        this.titulo = titulo;
        this.tempo = tempo;
        this.partida = partida;
        this.destino = destino;
        this.descricao = descricao;
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
