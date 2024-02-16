import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;

class Grafo {
    private int numVertices;
    private ArrayList<int[]>[] listaAdjacencia;

    public Grafo(int numVertices) {
        this.numVertices = numVertices;
        this.listaAdjacencia = new ArrayList[numVertices];
        for (int i = 0; i < numVertices; i++) {
            this.listaAdjacencia[i] = new ArrayList<>();
        }
    }

    public void adicionarAresta(int verticeOrigem, int verticeDestino, int peso) {
        this.listaAdjacencia[verticeOrigem].add(new int[]{verticeDestino, peso});
        this.listaAdjacencia[verticeDestino].add(new int[]{verticeOrigem, peso}); // Aresta bidirecional, se o grafo for não direcionado
    }

    public int[] dijkstra(int verticeOrigem) {
        int[] distancias = new int[numVertices];
        Arrays.fill(distancias, Integer.MAX_VALUE);
        distancias[verticeOrigem] = 0;

        PriorityQueue<int[]> filaPrioridade = new PriorityQueue<>((a, b) -> Integer.compare(a[1], b[1]));
        filaPrioridade.add(new int[]{verticeOrigem, 0});

        while (!filaPrioridade.isEmpty()) {
            int[] par = filaPrioridade.poll();
            int verticeAtual = par[0];

            for (int[] aresta : listaAdjacencia[verticeAtual]) {
                int verticeVizinho = aresta[0];
                int pesoAresta = aresta[1];

                if (distancias[verticeVizinho] > distancias[verticeAtual] + pesoAresta) {
                    distancias[verticeVizinho] = distancias[verticeAtual] + pesoAresta;
                    filaPrioridade.add(new int[]{verticeVizinho, distancias[verticeVizinho]});
                }
            }
        }

        return distancias;
    }

    public static void main(String[] args) {
        Grafo grafo = new Grafo(5);

        grafo.adicionarAresta(0, 1, 4);
        grafo.adicionarAresta(0, 2, 2);
        grafo.adicionarAresta(0, 3, 5);
        grafo.adicionarAresta(1, 4, 1);
        grafo.adicionarAresta(2, 1, 1);
        grafo.adicionarAresta(2, 3, 2);
        grafo.adicionarAresta(2, 4, 1);
        grafo.adicionarAresta(3, 4, 1);

        int[] distancias = grafo.dijkstra(0);

        System.out.println("Distâncias mínimas a partir do vértice 0:");
        for (int i = 0; i < distancias.length; i++) {
            System.out.println("Para o vértice " + i + ": " + distancias[i]);
        }
    }
}