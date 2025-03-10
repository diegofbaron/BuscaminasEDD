/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package metrobuscaminas;
import java.util.Random;
/**
 *
 * @author diego
 */
public class Tablero {
    private Grafo grafo;
    private Casilla[][] casillas;
    private int filas;
    private int columnas;
    private int minas;
    private int casillasSeguras;

    public Tablero(int filas, int columnas, int minas) {
        this.filas = filas;
        this.columnas = columnas;
        this.minas = minas;
        this.grafo = new Grafo(filas, columnas);
        this.casillas = new Casilla[filas][columnas];
        this.casillasSeguras = filas * columnas - minas;

        // Inicializar casillas
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                int nodo = grafo.convertirCoordenadasANodo(i, j);
                String id = String.valueOf((char) ('A' + i)) + (j + 1); // Identificador único (A1, A2, etc.)
                casillas[i][j] = new Casilla(nodo, id);
            }
        }

        colocarMinas(); // Colocar minas aleatoriamente
    }

    /**
     * Coloca las minas aleatoriamente en el tablero.
     */
    private void colocarMinas() {
        Random random = new Random();
        int minasColocadas = 0;

        while (minasColocadas < minas) {
            int fila = random.nextInt(filas);
            int columna = random.nextInt(columnas);

            if (!casillas[fila][columna].tieneMina()) {
                casillas[fila][columna].setTieneMina(true);
                minasColocadas++;
            }
        }
    }

    /**
     * Realiza el barrido de una casilla.
     */
    public void barrerCasilla(Casilla casilla, String metodoBusqueda, VisualizadorGrafo visualizador) {
        if (casilla.tieneMina()) {
            // El jugador pierde si toca una mina
            return;
        }

        if (metodoBusqueda.equals("BFS")) {
            barridoBFS(casilla, visualizador);
        } else if (metodoBusqueda.equals("DFS")) {
            barridoDFS(casilla, visualizador);
        } else {
            // Barrido tradicional (sin BFS/DFS)
            casilla.setBarrida(true);
            casillasSeguras--;
        }
    }

    /**
     * Realiza un barrido en amplitud (BFS) desde la casilla dada.
     */
    private void barridoBFS(Casilla casilla, VisualizadorGrafo visualizador) {
        Pila pila = new Pila();
        pila.push(casilla);

        while (!pila.isEmpty()) {
            Casilla actual = pila.pop(this);

            if (!actual.estaBarrida()) {
                actual.setBarrida(true);
                casillasSeguras--;

                // Agregar nodo al visualizador
                visualizador.agregarNodo(actual.getId());
                visualizador.mostrarOrdenVisita(actual.getId());

                int minasAdyacentes = contarMinasAdyacentes(actual);
                if (minasAdyacentes == 0) {
                    Lista adyacentes = grafo.obtenerAdyacentes(actual.getNodo());
                    Nodo aux = adyacentes.getpFirst();

                    while (aux != null) {
                        int[] coordenadas = grafo.convertirNodoACoordenadas(aux.getClave());
                        Casilla adyacente = casillas[coordenadas[0]][coordenadas[1]];

                        if (!adyacente.estaBarrida() && !adyacente.tieneMina()) {
                            pila.push(adyacente);

                            // Agregar arista al visualizador
                            visualizador.agregarArista(actual.getId() + "-" + adyacente.getId(), actual.getId(), adyacente.getId());
                        }

                        aux = aux.getpNext();
                    }
                }
            }
        }
    }

    /**
     * Realiza un barrido en profundidad (DFS) desde la casilla dada.
     */
    private void barridoDFS(Casilla casilla, VisualizadorGrafo visualizador) {
        Pila pila = new Pila();
        pila.push(casilla);

        while (!pila.isEmpty()) {
            Casilla actual = pila.pop(this);

            if (!actual.estaBarrida()) {
                actual.setBarrida(true);
                casillasSeguras--;

                // Agregar nodo al visualizador
                visualizador.agregarNodo(actual.getId());
                visualizador.mostrarOrdenVisita(actual.getId());

                int minasAdyacentes = contarMinasAdyacentes(actual);
                if (minasAdyacentes == 0) {
                    Lista adyacentes = grafo.obtenerAdyacentes(actual.getNodo());
                    Nodo aux = adyacentes.getpFirst();

                    while (aux != null) {
                        int[] coordenadas = grafo.convertirNodoACoordenadas(aux.getClave());
                        Casilla adyacente = casillas[coordenadas[0]][coordenadas[1]];

                        if (!adyacente.estaBarrida() && !adyacente.tieneMina()) {
                            pila.push(adyacente);

                            // Agregar arista al visualizador
                            visualizador.agregarArista(actual.getId() + "-" + adyacente.getId(), actual.getId(), adyacente.getId());
                        }

                        aux = aux.getpNext();
                    }
                }
            }
        }
    }

    /**
     * Cuenta el número de minas adyacentes a una casilla.
     */
    public int contarMinasAdyacentes(Casilla casilla) {
        int nodo = casilla.getNodo();
        Lista adyacentes = grafo.obtenerAdyacentes(nodo);
        int minasAdyacentes = 0;

        Nodo aux = adyacentes.getpFirst();
        while (aux != null) {
            int[] coordenadas = grafo.convertirNodoACoordenadas(aux.getClave());
            Casilla casillaAdyacente = casillas[coordenadas[0]][coordenadas[1]];
            if (casillaAdyacente.tieneMina()) {
                minasAdyacentes++;
            }
            aux = aux.getpNext();
        }

        return minasAdyacentes;
    }
    public boolean hayMinaExplotada() {
        for (int i = 0; i < casillas.length; i++) {
            for (int j = 0; j < casillas[i].length; j++) {
                if (casillas[i][j].tieneMina() && casillas[i][j].estaBarrida()) {
                    return true; // Se ha explotado una mina
                }
            }
        }
    return false; // No se ha explotado ninguna mina
    }
    /**
     * Verifica si el jugador ha ganado el juego.
     */
    public boolean verificarVictoria() {
        return casillasSeguras == 0;
    }

    // Getters
    public Casilla[][] getCasillas() {
        return casillas;
    }

    public Grafo getGrafo() {
        return grafo;
    }

    public int getFilas() {
        return filas;
    }

    public int getColumnas() {
        return columnas;
    }

    public int getMinas() {
        return minas;
    }

    public int getCasillasSeguras() {
        return casillasSeguras;
    }
}