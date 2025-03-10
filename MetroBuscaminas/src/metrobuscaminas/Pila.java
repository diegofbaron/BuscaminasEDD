/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package metrobuscaminas;

/**
 *
 * @author diego
 */
public class Pila {
    private Lista lista;

    public Pila() {
        this.lista = new Lista();
    }

    /**
     * Agrega un elemento a la pila.
     */
    public void push(Casilla casilla) {
        lista.InsertarInicio(casilla.getNodo()); // Usamos el nodo de la casilla
    }

    /**
     * Elimina y devuelve el elemento en la cima de la pila.
     */
    public Casilla pop(Tablero tablero) {
        if (isEmpty()) {
            return null; // Si la pila está vacía, retornar null
        }
        int nodo = lista.getpFirst().getClave(); // Obtener el nodo en la cima de la pila
        lista.EliminarPrimero(); // Eliminar el nodo de la pila

        // Convertir el nodo en una casilla usando el tablero
        int[] coordenadas = tablero.getGrafo().convertirNodoACoordenadas(nodo);
        return tablero.getCasillas()[coordenadas[0]][coordenadas[1]];
    }

    /**
     * Verifica si la pila está vacía.
     */
    public boolean isEmpty() {
        return lista.getSize() == 0;
    }
}