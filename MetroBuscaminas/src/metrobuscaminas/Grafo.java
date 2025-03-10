/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package metrobuscaminas;

/**
 *
 * @author diego
 */
public class Grafo {
        private Lista[] listaAdyacencias; // Lista de adyacencias para cada nodo
    private int filas;
    private int columnas;
    private int totalNodos;
    
    public Grafo(int filas, int columnas) {
        this.filas = filas;
        this.columnas = columnas;
        this.totalNodos = filas * columnas;
        this.listaAdyacencias = new Lista[totalNodos];

        // Inicializar listas de adyacencias
        for (int i = 0; i < totalNodos; i++) {
            listaAdyacencias[i] = new Lista();
        }

        generarGrafo();
    
}

    public Lista[] getListaAdyacencias() {
        return listaAdyacencias;
    }

    public void setListaAdyacencias(Lista[] listaAdyacencias) {
        this.listaAdyacencias = listaAdyacencias;
    }

    public int getFilas() {
        return filas;
    }

    public void setFilas(int filas) {
        this.filas = filas;
    }

    public int getColumnas() {
        return columnas;
    }

    public void setColumnas(int columnas) {
        this.columnas = columnas;
    }

    public int getTotalNodos() {
        return totalNodos;
    }

    public void setTotalNodos(int totalNodos) {
        this.totalNodos = totalNodos;
    }

    /**
     * Genera el grafo conectando cada nodo con sus adyacentes.
     */
    private void generarGrafo() {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                int nodoActual = i * columnas + j; // Convertir coordenadas (i, j) a índice del nodo

                // Conectar con casillas adyacentes (arriba, abajo, izquierda, derecha y diagonales)
                for (int x = -1; x <= 1; x++) {
                    for (int y = -1; y <= 1; y++) {
                        if (x == 0 && y == 0) continue; // Saltar la casilla actual

                        int nuevaFila = i + x;
                        int nuevaColumna = j + y;

                        // Verificar límites del tablero
                        if (nuevaFila >= 0 && nuevaFila < filas && nuevaColumna >= 0 && nuevaColumna < columnas) {
                            int nodoAdyacente = nuevaFila * columnas + nuevaColumna;
                            agregarArista(nodoActual, nodoAdyacente);
                        }
                    }
                }
            }
        }
    }

    /**
     * Agrega una arista entre dos nodos.
     */
    public void agregarArista(int nodo1, int nodo2) {
        listaAdyacencias[nodo1].InsertarInicio(nodo2);
    }

    /**
     * Obtiene la lista de nodos adyacentes a un nodo dado.
     */
    public Lista obtenerAdyacentes(int nodo) {
        return listaAdyacencias[nodo];
    }

    /**
     * Convierte coordenadas (fila, columna) a índice del nodo.
     */
    public int convertirCoordenadasANodo(int fila, int columna) {
        return fila * columnas + columna;
    }

    /**
     * Convierte índice del nodo a coordenadas (fila, columna).
     */
    public int[] convertirNodoACoordenadas(int nodo) {
        int fila = nodo / columnas;
        int columna = nodo % columnas;
        return new int[]{fila, columna};
    }
}