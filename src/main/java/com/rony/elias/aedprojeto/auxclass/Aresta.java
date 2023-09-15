package com.rony.elias.aedprojeto.auxclass;

public class Aresta {
    private Cliente origem;
    private Cliente destino;
    private int peso;

    public Aresta(Cliente origem, Cliente destino, int peso) {
        this.origem = origem;
        this.destino = destino;
        this.peso = peso;
    }

    public Cliente getOrigem() {
        return origem;
    }

    public Cliente getDestino() {
        return destino;
    }

    public int getPeso() {
        return peso;
    }
}

