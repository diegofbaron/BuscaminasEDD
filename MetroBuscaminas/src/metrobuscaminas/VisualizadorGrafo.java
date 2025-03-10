/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package metrobuscaminas;

/**
 *
 * @author diego
 */

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.view.Viewer;
import org.graphstream.ui.swing_viewer.SwingViewer;


public class VisualizadorGrafo {
    private Graph grafo;
    private int ordenVisita; // Contador para el orden de visita
    private String nodoAnterior; // Almacena el último nodo visitado

    public VisualizadorGrafo() {
        System.setProperty("org.graphstream.ui", "swing");
        grafo = new SingleGraph("Recorrido BFS/DFS");
        grafo.setAttribute("ui.stylesheet", 
            "node { fill-color: blue; size: 20px; text-alignment: center; }" +
            "edge { fill-color: black; size: 2px; }");
        ordenVisita = 0; // Inicializar el contador de orden de visita
        nodoAnterior = null; // Inicializar el nodo anterior como nulo
    }

    public void limpiarGrafo() {
        grafo.clear();
        ordenVisita = 0; // Reiniciar el contador al limpiar el grafo
        nodoAnterior = null; // Reiniciar el nodo anterior
        System.out.println("");
    }

    public void agregarNodo(String id) {
        if (grafo.getNode(id) == null) {
            grafo.addNode(id).setAttribute("ui.label", id);
            System.out.println("");
        }
    }

    public void agregarArista(String idArista, String idNodoOrigen, String idNodoDestino) {
        if (grafo.getNode(idNodoOrigen) != null && grafo.getNode(idNodoDestino) != null) {
            grafo.addEdge(idArista, idNodoOrigen, idNodoDestino, true); // true para arista dirigida
            System.out.println("");
        }
    }

    public void mostrarGrafo() {
        if (grafo.getNodeCount() == 0) {
            System.out.println("");
        } else {
            Viewer viewer = grafo.display();
            viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.HIDE_ONLY);
        }
    }

    /**
     * Cambia el color del nodo según el orden de visita y lo conecta con el nodo anterior.
     * @param idNodo El identificador del nodo visitado.
     */
    public void mostrarOrdenVisita(String idNodo) {
        ordenVisita++; // Incrementar el contador de orden de visita
        String color = obtenerColorPorOrden(ordenVisita); // Obtener un color basado en el orden de visita
        grafo.getNode(idNodo).setAttribute("ui.style", "fill-color: " + color + ";");

        // Conectar con el nodo anterior (si existe)
        if (nodoAnterior != null) {
            agregarArista(nodoAnterior + "-" + idNodo, nodoAnterior, idNodo);
        }

        nodoAnterior = idNodo; // Actualizar el nodo anterior
        
    }

    /**
     * Devuelve un color basado en el orden de visita.
     * @param orden El orden de visita del nodo.
     * @return Un color en formato GraphStream (por ejemplo, "red", "green", etc.).
     */
    private String obtenerColorPorOrden(int orden) {
        // Lista de colores predefinidos para los nodos
        String[] colores = {"red", "green", "blue", "yellow", "orange", "purple", "pink", "cyan", "magenta", "brown"};
        return colores[orden % colores.length]; // Usar el módulo para repetir colores si hay muchos nodos
    }
}