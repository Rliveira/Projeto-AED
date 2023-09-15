package com.rony.elias.aedprojeto.auxclass;

import java.util.ArrayList;
import java.util.List;

public class Grafo {
    List<Cliente> nos;
    List<List<Aresta>> arestas;
    int numClientes;

    public Grafo(int numClientes) {
        this.nos = new ArrayList<>(numClientes);
        this.arestas = new ArrayList<>(numClientes);

        for (int i = 0; i < numClientes; i++) {
            nos.add(null);
            arestas.add(new ArrayList<>(numClientes));
            for (int j = 0; j < numClientes; j++) {
                arestas.get(i).add(null);
            }
        }
        this.numClientes = numClientes;
    }

    public List<Cliente> getNos() {
        return nos;
    }

    public void setNos(List<Cliente> nos) {
        this.nos = nos;
    }

    public List<List<Aresta>> getArestas() {
        return arestas;
    }

    public void setArestas(List<List<Aresta>> arestas) {
        this.arestas = arestas;
    }

    public int getNumClientes() {
        return numClientes;
    }

    public void setNumClientes(int numClientes) {
        this.numClientes = numClientes;
    }
}

