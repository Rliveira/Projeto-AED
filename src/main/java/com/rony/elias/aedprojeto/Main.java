package com.rony.elias.aedprojeto;

import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.util.*;


class Cliente {
    String nome;
    String bairro;

    public Cliente(String nome, String bairro) {
        this.nome = nome;
        this.bairro = bairro;
    }
}

class Aresta {
    Cliente origem;
    Cliente destino;
    int peso;

    public Aresta(Cliente origem, Cliente destino, int peso) {
        this.origem = origem;
        this.destino = destino;
        this.peso = peso;
    }
}

class Grafo {
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
    public void addCliente(Cliente cliente) {
        for (int i = 0; i < numClientes; i++) {
            if (nos.get(i) == null) {
                nos.set(i, cliente);
                return;
            }
        }
    }

    public void removerCliente(int ind) {
        if (nos.get(ind) != null) {
            nos.set(ind, null);

            for (int i = 0; i < numClientes; i++) {
                if (arestas.get(ind).get(i) != null) {
                    arestas.get(ind).set(i, null);
                    System.out.println("Cliente removido!");
                }
                if (arestas.get(i).get(ind) != null) {
                    arestas.get(i).set(ind, null);
                    System.out.println("Cliente removido!");
                }
            }
        } else {
            System.out.println("Cliente não encontrado");
        }
    }

    public void addAresta(int origem, int destino, int distancia) {
        Aresta novaAresta = new Aresta(nos.get(origem), nos.get(destino), distancia);
        arestas.get(origem).set(destino, novaAresta);
    }

    public void removerAresta(int origem, int destino) {
        arestas.get(origem).set(destino, null);
    }

    public void imprimirGrafo() {
        System.out.println("Rotas\n");
        for (int i = 0; i < numClientes; i++) {
            for (int j = 0; j < numClientes; j++) {
                if (arestas.get(i).get(j) != null) {
                    System.out.println("Origem: " + arestas.get(i).get(j).origem.nome);
                    System.out.println("Destino: " + arestas.get(i).get(j).destino.nome);
                    System.out.println("Distancia: " + arestas.get(i).get(j).peso + "\n");
                }
            }
        }
    }

    public int minDis(int[] distancias, boolean[] visitados) {
        int minDistancia = Integer.MAX_VALUE;
        int minI = -1;

        for (int v = 0; v < numClientes; v++) {
            if (!visitados[v] && distancias[v] <= minDistancia) {
                minDistancia = distancias[v];
                minI = v;
            }
        }
        return minI;
    }

    public void imprimirCaminho(int[] anteriores, int destino) {
        if (anteriores[destino] == -1) {
            System.out.print(destino + " -> ");
            return;
        }

        imprimirCaminho(anteriores, anteriores[destino]);
        System.out.print(destino + " -> ");
    }

    public void melhorCaminho(int origem, int destino) {
        int[] distancias = new int[numClientes];
        boolean[] visitados = new boolean[numClientes];
        int[] anteriores = new int[numClientes];

        for (int i = 0; i < numClientes; i++) {
            distancias[i] = Integer.MAX_VALUE;
            visitados[i] = false;
            anteriores[i] = -1;
        }

        distancias[origem] = 0;

        for (int contador = 0; contador < numClientes - 1; contador++) {
            int u = minDis(distancias, visitados);
            visitados[u] = true;

            for (int v = 0; v < numClientes; v++) {
                if (!visitados[v] && arestas.get(u).get(v) != null && distancias[u] != Integer.MAX_VALUE
                        && distancias[u] + arestas.get(u).get(v).peso < distancias[v]) {
                    distancias[v] = distancias[u] + arestas.get(u).get(v).peso;
                    anteriores[v] = u;
                }
            }
        }

        if (distancias[destino] == Integer.MAX_VALUE) {
            System.out.println("Os clientes " + nos.get(origem).nome + " e " + nos.get(destino).nome + " não estão conectados.");
        } else {
            System.out.println("Melhor rota de " + nos.get(origem).nome + " para " + nos.get(destino).nome + " passam pelos clientes:");
            imprimirCaminho(anteriores, destino);
            System.out.println("\nDistancia total: " + distancias[destino]);
        }
    }

    public void mostrarClientes() {
        System.out.println("Lista de clientes:\n");
        for (int i = 0; i < numClientes; i++) {
            if (nos.get(i) != null) {
                System.out.println("Cliente [" + i + "]: " + nos.get(i).nome);
                System.out.println("Bairro: " + nos.get(i).bairro + "\n");
            }
        }
    }

    public int buscaPorNome(String nome) {
        for (int i = 0; i < numClientes; i++) {
            if (nos.get(i) != null && nos.get(i).nome != null && nos.get(i).nome.equals(nome)) {
                return i; // Retorna o índice do cliente encontrado
            }
        }
        return -1; // Retorna -1 quando o cliente não é encontrado
    }


    public void buscarPorIndice(int indice) {
        if (indice >= 0 && indice < numClientes && nos.get(indice) != null) {
            System.out.println("\nCliente [" + indice + "]: " + nos.get(indice).nome);
            System.out.println("Bairro: " + nos.get(indice).bairro + "\n");
        } else {
            System.out.println("Cliente não encontrado.");
        }
    }

    public void imprimirMapa() {
        System.out.println("Mapa do Grafo\n");


        System.out.print("        ");
        for (int i = 0; i < numClientes; i++) {
            if (nos.get(i) != null) {
                System.out.printf("%-10s", nos.get(i).nome);
            }
        }
        System.out.println();


        for (int i = 0; i < numClientes; i++) {
            if (nos.get(i) != null) {
                System.out.printf("%-8s", nos.get(i).nome);
                for (int j = 0; j < numClientes; j++) {
                    if (nos.get(j) != null) {
                        Aresta aresta = arestas.get(i).get(j);
                        if (aresta != null) {
                            System.out.printf("%-10d", aresta.peso);
                        } else {
                            System.out.print("          - ");
                        }
                    }
                }
                System.out.println();
            }
        }
    }
    public void conectarTodos(Grafo gr)
    {
        Random random = new Random();
        for(int i=0; i < numClientes; i++)
        {
            for(int j=0; j < numClientes; j++)
            {
                if(i != j && nos.get(i) != null && nos.get(j) != null && arestas.get(i).get(j) == null)
                {
                    int pesoAleatorio = random.nextInt(50);
                    gr.addAresta(i, j, pesoAleatorio);
                }
            }
        }
    }



    public static void main(String[] args) throws IOException{
        Grafo grafo = new Grafo(50);

        String nome, bairro;
        int escolha, origem, destino, distancia, or, des, remover, num, indice;
        Scanner scan = new Scanner(System.in);

        escolha = 0;
        while (escolha != 11) {
            escolha = abrirMenu();

            switch (escolha) {
                case 1:
                    System.out.println("Adicionar cliente\n");
                    System.out.print("Digite o nome do cliente: ");
                    limparBuffer();
                    nome = scan.nextLine();

                    System.out.print("Digite nome do bairro do cliente: ");
                    bairro = scan.nextLine();

                    Cliente novoCliente = new Cliente(nome, bairro);
                    grafo.addCliente(novoCliente);
                    System.out.println("Cliente adicionado!\n");
                    break;

                case 2:
                    grafo.mostrarClientes();
                    System.out.print("Digite o número do cliente a remover: ");
                    remover = Integer.parseInt(scan.nextLine());
                    grafo.removerCliente(remover);
                    break;

                case 3:
                    System.out.println("Adicionar trajeto\n");

                    grafo.mostrarClientes();
                    System.out.print("Digite o índice do cliente origem: ");
                    origem = Integer.parseInt(scan.nextLine());
                    System.out.print("Digite índice do cliente destino: ");
                    destino = Integer.parseInt(scan.nextLine());
                    System.out.print("Qual a distância entre os bairros? ");
                    distancia = Integer.parseInt(scan.nextLine());
                    grafo.addAresta(origem, destino, distancia);
                    System.out.println("Trajeto adicionado!\n");
                    break;

                case 4:
                    grafo.mostrarClientes();
                    System.out.print("Digite o índice da origem: ");
                    origem = Integer.parseInt(scan.nextLine());
                    System.out.print("Digite o índice do destino: ");
                    destino = Integer.parseInt(scan.nextLine());

                    grafo.removerAresta(origem, destino);
                    System.out.println("Caminho removido!\n");
                    break;

                case 5:
                    grafo.imprimirMapa();
                    System.out.println("\n\n");
                    grafo.imprimirGrafo();
                    break;

                case 6:
                    System.out.println("Para achar a melhor rota, preciso que forneça o índice dos clientes a serem conectados");
                    System.out.println("Segue a lista de clientes registrados e os respectivos índices\n");

                    grafo.mostrarClientes();

                    System.out.print("Digite o nº do cliente de origem: ");
                    or = Integer.parseInt(scan.nextLine());

                    System.out.print("Digite o nº do cliente de destino: ");
                    des = Integer.parseInt(scan.nextLine());

                    grafo.melhorCaminho(or, des);
                    break;

                case 7:
                    grafo.mostrarClientes();
                    break;

                case 8:
                    System.out.print("Digite o nome da pessoa a ser encontrada: ");
                    nome = scan.nextLine();
                    int indiceEncontrado = grafo.buscaPorNome(nome);
                    if (indiceEncontrado != -1) {
                        System.out.println("Cliente encontrado!\n");
                        System.out.println("Índice: " + indiceEncontrado);
                        Cliente clienteEncontrado = grafo.nos.get(indiceEncontrado);
                        System.out.println("Nome: " + clienteEncontrado.nome);
                        System.out.println("Bairro: " + clienteEncontrado.bairro);
                    } else {
                        System.out.println("Cliente não encontrado.");
                    }
                    break;

                case 9:
                    System.out.print("Digite o índice da pessoa a ser encontrada: ");
                    num = Integer.parseInt(scan.nextLine());
                    grafo.buscarPorIndice(num);
                    break;

                case 10:
                    grafo.conectarTodos(grafo);
                    System.out.println("Clientes conectados aleatoriamente!");
                    break;

                case 11:
                    System.out.println("Fechando programa...");
                    break;

                default:
                    System.out.println("Opção inválida, tente novamente\n");
                    break;
            }
        }
    }

    private static int abrirMenu() {
        Scanner scan = new Scanner(System.in);
        String escolhaStr;
        int escolha;

        System.out.println("\nMenu:");
        System.out.println("1. Adicionar cliente");
        System.out.println("2. Remover cliente");
        System.out.println("3. Adicionar caminho");
        System.out.println("4. Remover caminho");
        System.out.println("5. Imprimir grafo");
        System.out.println("6. Melhor caminho");
        System.out.println("7. Exibir lista de Clientes");
        System.out.println("8. Busca por nome");
        System.out.println("9. Busca por índice");
        System.out.println("10. Conectar todos");
        System.out.println("11. Sair");
        System.out.print("Escolha uma opção: \n\n");
        escolhaStr = scan.nextLine();

        try {
            escolha = Integer.parseInt(escolhaStr);
        } catch (NumberFormatException e) {
            System.out.println("Erro: digite um número.\n");
            escolha = -1;
        }

        return escolha;
    }

    private static void limparBuffer() throws IOException {
        while (System.in.available() > 0 && System.in.read() != '\n') {
        }
    }
}

