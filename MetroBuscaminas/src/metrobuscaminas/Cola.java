/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package metrobuscaminas;

/**
 *
 * @author diego
 */
public class Cola {
    private NodoCola primero;
    private NodoCola ultimo;

    public Cola() {
        this.primero = null;
        this.ultimo = null;
    }

    /**
     * Verifica si la cola está vacía.
     */
    public boolean isEmpty() {
        return primero == null;
    }

    /**
     * Agrega un elemento al final de la cola.
     */
    public void enqueue(Casilla casilla) {
        NodoCola nuevoNodo = new NodoCola(casilla);
        if (isEmpty()) {
            primero = nuevoNodo;
            ultimo = nuevoNodo;
        } else {
            ultimo.setSiguiente(nuevoNodo);
            ultimo = nuevoNodo;
        }
    }

    /**
     * Elimina y devuelve el elemento al frente de la cola.
     */
    public Casilla dequeue(Tablero tablero) {
        if (isEmpty()) {
            throw new IllegalStateException("La cola está vacía.");
        }
        Casilla casilla = primero.getCasilla();
        primero = primero.getSiguiente();
        if (primero == null) {
            ultimo = null;
        }
        return casilla;
    }
}