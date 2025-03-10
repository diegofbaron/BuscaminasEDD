/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package metrobuscaminas;

/**
 *
 * @author diego
 */
public class Casilla {
        private int nodo; // Índice del nodo en el grafo
    private String id; // Identificador único (por ejemplo, "A1")
    private boolean tieneMina; // Indica si la casilla tiene una mina
    private boolean barrida; // Indica si la casilla ha sido barrida
    private boolean marcada; // Indica si la casilla está marcada con una bandera

    public Casilla(int nodo, String id) {
        this.nodo = nodo;
        this.id = id;
        this.tieneMina = false;
        this.barrida = false;
        this.marcada = false;
}

// Getters y Setters
    public int getNodo() {
        return nodo;
    }

    public String getId() {
        return id;
    }

    public boolean tieneMina() {
        return tieneMina;
    }

    public void setTieneMina(boolean tieneMina) {
        this.tieneMina = tieneMina;
    }

    public boolean estaBarrida() {
        return barrida;
    }

    public void setBarrida(boolean barrida) {
        this.barrida = barrida;
    }

    public boolean estaMarcada() {
        return marcada;
    }

    public void setMarcada(boolean marcada) {
        this.marcada = marcada;
    }
}