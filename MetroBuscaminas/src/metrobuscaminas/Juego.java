/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package metrobuscaminas;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.view.Viewer;
import javax.swing.*;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.filechooser.FileNameExtensionFilter;
/**
 *
 * @author diego
 */
public class Juego {
    private Tablero tablero;
    private boolean juegoActivo;
    private VisualizadorGrafo visualizador;

    public Juego(int filas, int columnas, int minas) {
        this.tablero = new Tablero(filas, columnas, minas);
        this.juegoActivo = true;
        this.visualizador = new VisualizadorGrafo();
    }

    public boolean getJuegoActivo() {
        return juegoActivo;
    }

    public void guardarJuego() {
        try {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Guardar Juego");
            fileChooser.setFileFilter(new FileNameExtensionFilter("Archivos CSV (*.csv)", "csv"));

            int userSelection = fileChooser.showSaveDialog(null);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                String rutaArchivo = fileChooser.getSelectedFile().getAbsolutePath();
                if (!rutaArchivo.endsWith(".csv")) {
                    rutaArchivo += ".csv";
                }

                FileWriter writer = new FileWriter(rutaArchivo);
                for (int i = 0; i < tablero.getFilas(); i++) {
                    for (int j = 0; j < tablero.getColumnas(); j++) {
                        Casilla casilla = tablero.getCasillas()[i][j];
                        String estado;
                        if (casilla.tieneMina()) {
                            estado = "M";
                        } else if (casilla.estaMarcada()) {
                            estado = "F";
                        } else if (casilla.estaBarrida()) {
                            estado = "B" + tablero.contarMinasAdyacentes(casilla);
                        } else {
                            estado = "N";
                        }
                        writer.write(i + "," + j + "," + estado + "\n");
                    }
                }
                writer.close();
                JOptionPane.showMessageDialog(null, "Juego guardado correctamente.", "Información", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error al guardar el juego: " + ex.getMessage(), "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void cargarJuego() {
        try {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Cargar Juego");
            fileChooser.setFileFilter(new FileNameExtensionFilter("Archivos CSV (*.csv)", "csv"));

            int userSelection = fileChooser.showOpenDialog(null);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                String rutaArchivo = fileChooser.getSelectedFile().getAbsolutePath();

                BufferedReader reader = new BufferedReader(new FileReader(rutaArchivo));
                String linea;
                while ((linea = reader.readLine()) != null) {
                    String[] partes = linea.split(",");
                    if (partes.length == 3) {
                        int fila = Integer.parseInt(partes[0]);
                        int columna = Integer.parseInt(partes[1]);
                        String estado = partes[2];

                        Casilla casilla = tablero.getCasillas()[fila][columna];
                        switch (estado.charAt(0)) {
                            case 'M':
                                casilla.setTieneMina(true);
                                break;
                            case 'F':
                                casilla.setMarcada(true);
                                break;
                            case 'B':
                                casilla.setBarrida(true);
                                break;
                            case 'N':
                                break;
                        }
                    }
                }
                reader.close();
                JOptionPane.showMessageDialog(null, "Juego cargado correctamente.", "Información", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error al cargar el juego: " + ex.getMessage(), "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void barreCasilla(String idCasilla, String metodoBusqueda) {
        if (!juegoActivo) return;

        String[] partes = idCasilla.split(",");
        int fila = Integer.parseInt(partes[0]);
        int columna = Integer.parseInt(partes[1]);

        Casilla casilla = tablero.getCasillas()[fila][columna];

        if (casilla.tieneMina()) {
            juegoActivo = false;
            JOptionPane.showMessageDialog(null, "¡Has perdido! La casilla " + casilla.getId() + " tenía una mina.");
        } else {
            tablero.barrerCasilla(casilla, metodoBusqueda, visualizador);
            if (tablero.verificarVictoria()) {
                juegoActivo = false;
                JOptionPane.showMessageDialog(null, "¡Felicidades! Has ganado el juego.");
            }
        }
    }

    public void mostrarRecorrido(String idCasilla, String metodoBusqueda) {
        String[] partes = idCasilla.split(",");
        int fila = Integer.parseInt(partes[0]);
        int columna = Integer.parseInt(partes[1]);

        Casilla casilla = tablero.getCasillas()[fila][columna];

        if (metodoBusqueda.equals("BFS")) {
            barridoBFS(casilla);
        } else if (metodoBusqueda.equals("DFS")) {
            barridoDFS(casilla);
        }

        visualizador.mostrarGrafo();
    }

    private void barridoBFS(Casilla casilla) {
        Cola cola = new Cola();
        cola.enqueue(casilla);

        while (!cola.isEmpty()) {
            Casilla actual = cola.dequeue(tablero);

            if (!actual.estaBarrida()) {
                actual.setBarrida(true);
                visualizador.agregarNodo(actual.getId());
                visualizador.mostrarOrdenVisita(actual.getId());

                int minasAdyacentes = tablero.contarMinasAdyacentes(actual);
                if (minasAdyacentes == 0) {
                    Lista adyacentes = tablero.getGrafo().obtenerAdyacentes(actual.getNodo());
                    Nodo aux = adyacentes.getpFirst();

                    while (aux != null) {
                        int[] coordenadas = tablero.getGrafo().convertirNodoACoordenadas(aux.getClave());
                        Casilla adyacente = tablero.getCasillas()[coordenadas[0]][coordenadas[1]];

                        if (!adyacente.estaBarrida() && !adyacente.tieneMina()) {
                            visualizador.agregarNodo(adyacente.getId());
                            cola.enqueue(adyacente);
                            visualizador.agregarArista(actual.getId() + "-" + adyacente.getId(), actual.getId(), adyacente.getId());
                        }

                        aux = aux.getpNext();
                    }
                }
            }
        }
    }

    private void barridoDFS(Casilla casilla) {
        Pila pila = new Pila();
        pila.push(casilla);

        while (!pila.isEmpty()) {
            Casilla actual = pila.pop(tablero);

            if (!actual.estaBarrida()) {
                actual.setBarrida(true);
                visualizador.agregarNodo(actual.getId());
                visualizador.mostrarOrdenVisita(actual.getId());

                int minasAdyacentes = tablero.contarMinasAdyacentes(actual);
                if (minasAdyacentes == 0) {
                    Lista adyacentes = tablero.getGrafo().obtenerAdyacentes(actual.getNodo());
                    Nodo aux = adyacentes.getpFirst();

                    while (aux != null) {
                        int[] coordenadas = tablero.getGrafo().convertirNodoACoordenadas(aux.getClave());
                        Casilla adyacente = tablero.getCasillas()[coordenadas[0]][coordenadas[1]];

                        if (!adyacente.estaBarrida() && !adyacente.tieneMina()) {
                            visualizador.agregarNodo(adyacente.getId());
                            pila.push(adyacente);
                            visualizador.agregarArista(actual.getId() + "-" + adyacente.getId(), actual.getId(), adyacente.getId());
                        }

                        aux = aux.getpNext();
                    }
                }
            }
        }
    }

    public void marcaCasilla(String idCasilla) {
        if (!juegoActivo) return;

        String[] partes = idCasilla.split(",");
        int fila = Integer.parseInt(partes[0]);
        int columna = Integer.parseInt(partes[1]);

        Casilla casilla = tablero.getCasillas()[fila][columna];
        if (!casilla.estaBarrida()) {
            casilla.setMarcada(!casilla.estaMarcada());
        }
    }

    public Tablero getTablero() {
        return tablero;
    }
}