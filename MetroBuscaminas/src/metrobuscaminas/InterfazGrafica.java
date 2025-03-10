/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package metrobuscaminas;

/**
 *
 * @author diego
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class InterfazGrafica extends JFrame {
    private Juego juego;
    private JPanel panelTablero;
    private JButton[][] botonesCasillas;
    private JTextField filasField, columnasField, minasField;
    private JButton nuevoJuegoButton;
    private JRadioButton bfsRadioButton, dfsRadioButton;
    private JButton guardarButton, cargarButton;
    private JButton mostrarGrafoButton;

    public InterfazGrafica() {
        setTitle("MetroBuscaminas");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Configurar colores de la interfaz
        getContentPane().setBackground(Color.BLACK);
        setForeground(Color.WHITE);

        // Panel de configuración
        JPanel panelConfiguracion = new JPanel();
        panelConfiguracion.setLayout(new GridLayout(2, 1));
        panelConfiguracion.setBackground(Color.BLACK);
        panelConfiguracion.setForeground(Color.WHITE);

        // Panel superior para filas, columnas, minas y nuevo juego
        JPanel panelSuperior = new JPanel();
        panelSuperior.setLayout(new FlowLayout(FlowLayout.LEFT));
        panelSuperior.setBackground(Color.BLACK);
        panelSuperior.setForeground(Color.WHITE);

        panelSuperior.add(new JLabel("Filas:"));
        filasField = new JTextField(3);
        filasField.setBackground(Color.DARK_GRAY);
        filasField.setForeground(Color.WHITE);
        panelSuperior.add(filasField);

        panelSuperior.add(new JLabel("Columnas:"));
        columnasField = new JTextField(3);
        columnasField.setBackground(Color.DARK_GRAY);
        columnasField.setForeground(Color.WHITE);
        panelSuperior.add(columnasField);

        panelSuperior.add(new JLabel("Minas:"));
        minasField = new JTextField(3);
        minasField.setBackground(Color.DARK_GRAY);
        minasField.setForeground(Color.WHITE);
        panelSuperior.add(minasField);

        nuevoJuegoButton = new JButton("Nuevo Juego");
        nuevoJuegoButton.setBackground(Color.DARK_GRAY);
        nuevoJuegoButton.setForeground(Color.WHITE);
        nuevoJuegoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                iniciarNuevoJuego();
            }
        });
        panelSuperior.add(nuevoJuegoButton);

        // Panel inferior para BFS, DFS, guardar, cargar y mostrar grafo
        JPanel panelInferior = new JPanel();
        panelInferior.setLayout(new FlowLayout(FlowLayout.LEFT));
        panelInferior.setBackground(Color.BLACK);
        panelInferior.setForeground(Color.WHITE);

        bfsRadioButton = new JRadioButton("BFS");
        bfsRadioButton.setBackground(Color.BLACK);
        bfsRadioButton.setForeground(Color.WHITE);
        dfsRadioButton = new JRadioButton("DFS");
        dfsRadioButton.setBackground(Color.BLACK);
        dfsRadioButton.setForeground(Color.WHITE);
        ButtonGroup grupoBusqueda = new ButtonGroup();
        grupoBusqueda.add(bfsRadioButton);
        grupoBusqueda.add(dfsRadioButton);
        panelInferior.add(bfsRadioButton);
        panelInferior.add(dfsRadioButton);

        guardarButton = new JButton("Guardar Juego");
        guardarButton.setBackground(Color.DARK_GRAY);
        guardarButton.setForeground(Color.WHITE);
        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (juego != null) {
                    juego.guardarJuego();
                } else {
                    JOptionPane.showMessageDialog(null, "No hay un juego en curso.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        panelInferior.add(guardarButton);

        cargarButton = new JButton("Cargar Juego");
        cargarButton.setBackground(Color.DARK_GRAY);
        cargarButton.setForeground(Color.WHITE);
        cargarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (juego != null) {
                    juego.cargarJuego();
                    actualizarTablero();
                } else {
                    JOptionPane.showMessageDialog(null, "No hay un juego en curso.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        panelInferior.add(cargarButton);

        mostrarGrafoButton = new JButton("Mostrar Grafo");
        mostrarGrafoButton.setBackground(Color.DARK_GRAY);
        mostrarGrafoButton.setForeground(Color.WHITE);
        mostrarGrafoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (juego != null) {
                    Casilla[][] casillas = juego.getTablero().getCasillas();
                    boolean casillaBarridaEncontrada = false;

                    for (int i = 0; i < casillas.length; i++) {
                        for (int j = 0; j < casillas[i].length; j++) {
                            if (casillas[i][j].estaBarrida()) {
                                String metodoBusqueda = bfsRadioButton.isSelected() ? "BFS" : "DFS";
                                juego.mostrarRecorrido(i + "," + j, metodoBusqueda);
                                casillaBarridaEncontrada = true;
                                return;
                            }
                        }
                    }

                    if (!casillaBarridaEncontrada) {
                        JOptionPane.showMessageDialog(null, "No hay casillas barridas para mostrar el recorrido.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "No hay un juego en curso.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        panelInferior.add(mostrarGrafoButton);

        // Agregar los paneles superior e inferior al panel de configuración
        panelConfiguracion.add(panelSuperior);
        panelConfiguracion.add(panelInferior);

        // Agregar el panel de configuración en la parte superior de la ventana
        add(panelConfiguracion, BorderLayout.NORTH);

        // Panel del tablero
        panelTablero = new JPanel();
        panelTablero.setBackground(Color.BLACK);
        add(panelTablero, BorderLayout.CENTER);

        setVisible(true);
    }

    private void iniciarNuevoJuego() {
        try {
            int filas = Integer.parseInt(filasField.getText());
            int columnas = Integer.parseInt(columnasField.getText());
            int minas = Integer.parseInt(minasField.getText());

            if (filas < 3 || filas > 10 || columnas < 3 || columnas > 10 || minas < 1 || minas >= filas * columnas) {
                JOptionPane.showMessageDialog(this, "Valores inválidos. Filas y columnas deben estar entre 3 y 10, y minas deben ser menores que el total de casillas.");
                return;
            }

            juego = new Juego(filas, columnas, minas);
            inicializarTablero(filas, columnas);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, ingresa valores numéricos válidos.");
        }
    }

    private void inicializarTablero(int filas, int columnas) {
        panelTablero.removeAll();
        panelTablero.setLayout(new GridLayout(filas, columnas));
        botonesCasillas = new JButton[filas][columnas];

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                JButton boton = new JButton();
                boton.setBackground(Color.BLACK);
                boton.setForeground(Color.WHITE);
                botonesCasillas[i][j] = boton;
                panelTablero.add(boton);

                final int fila = i;
                final int columna = j;
                boton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        manejarClicCasilla(fila, columna);
                    }
                });
            }
        }

        panelTablero.revalidate();
        panelTablero.repaint();
    }

    private void manejarClicCasilla(int fila, int columna) {
        String metodoBusqueda = bfsRadioButton.isSelected() ? "BFS" : "DFS";
        juego.barreCasilla(fila + "," + columna, metodoBusqueda);
        actualizarTablero();
    }

private void actualizarTablero() {
    Casilla[][] casillas = juego.getTablero().getCasillas();
    boolean perdio = !juego.getJuegoActivo() && juego.getTablero().hayMinaExplotada();
    boolean gano = !juego.getJuegoActivo() && !juego.getTablero().hayMinaExplotada();

    for (int i = 0; i < casillas.length; i++) {
        for (int j = 0; j < casillas[i].length; j++) {
            JButton boton = botonesCasillas[i][j];
            Casilla casilla = casillas[i][j];

            if (perdio) {
                // Mostrar todas las minas al perder
                if (casilla.tieneMina()) {
                    boton.setText("💣"); // Mostrar mina
                    boton.setBackground(Color.RED);
                    boton.setForeground(Color.BLACK); // Texto negro para destacar la bomba
                } else if (casilla.estaBarrida()) {
                    int minasAdyacentes = juego.getTablero().contarMinasAdyacentes(casilla);
                    boton.setText(minasAdyacentes > 0 ? String.valueOf(minasAdyacentes) : "");
                    boton.setBackground(Color.DARK_GRAY);

                    // Asignar colores a los números
                    switch (minasAdyacentes) {
                        case 1:
                            boton.setForeground(Color.BLUE);
                            break;
                        case 2:
                            boton.setForeground(Color.GREEN);
                            break;
                        case 3:
                            boton.setForeground(Color.RED);
                            break;
                        case 4:
                            boton.setForeground(Color.MAGENTA);
                            break;
                        case 5:
                            boton.setForeground(Color.ORANGE);
                            break;
                        case 6:
                            boton.setForeground(Color.CYAN);
                            break;
                        case 7:
                            boton.setForeground(Color.YELLOW);
                            break;
                        case 8:
                            boton.setForeground(Color.PINK);
                            break;
                        default:
                            boton.setForeground(Color.WHITE);
                            break;
                    }
                } else if (casilla.estaMarcada()) {
                    boton.setText("🚩"); // Mostrar bandera
                    boton.setForeground(Color.RED);
                    boton.setBackground(Color.BLACK); // Fondo negro para la bandera
                } else {
                    boton.setText(""); // Casilla no barrida
                    boton.setBackground(Color.BLACK);
                    boton.setForeground(Color.WHITE);
                }
            } else if (gano) {
                // Mostrar todas las minas no marcadas al ganar
                if (casilla.tieneMina() && !casilla.estaMarcada()) {
                    boton.setText("🚩"); // Mostrar bandera en minas no marcadas
                    boton.setBackground(Color.GREEN); // Fondo verde para indicar victoria
                    boton.setForeground(Color.BLACK); // Texto negro para destacar la bandera
                } else if (casilla.estaMarcada()) {
                    boton.setText("🚩"); // Mantener banderas en minas marcadas
                    boton.setForeground(Color.RED);
                    boton.setBackground(Color.BLACK); // Fondo negro para la bandera
                } else if (casilla.estaBarrida()) {
                    int minasAdyacentes = juego.getTablero().contarMinasAdyacentes(casilla);
                    boton.setText(minasAdyacentes > 0 ? String.valueOf(minasAdyacentes) : "");
                    boton.setBackground(Color.DARK_GRAY);

                    // Asignar colores a los números
                    switch (minasAdyacentes) {
                        case 1:
                            boton.setForeground(Color.BLUE);
                            break;
                        case 2:
                            boton.setForeground(Color.GREEN);
                            break;
                        case 3:
                            boton.setForeground(Color.RED);
                            break;
                        case 4:
                            boton.setForeground(Color.MAGENTA);
                            break;
                        case 5:
                            boton.setForeground(Color.ORANGE);
                            break;
                        case 6:
                            boton.setForeground(Color.CYAN);
                            break;
                        case 7:
                            boton.setForeground(Color.YELLOW);
                            break;
                        case 8:
                            boton.setForeground(Color.PINK);
                            break;
                        default:
                            boton.setForeground(Color.WHITE);
                            break;
                    }
                } else {
                    boton.setText(""); // Casilla no barrida
                    boton.setBackground(Color.BLACK);
                    boton.setForeground(Color.WHITE);
                }
            } else if (casilla.estaBarrida()) {
                if (casilla.tieneMina()) {
                    boton.setText("💣"); // Mostrar mina
                    boton.setBackground(Color.RED);
                    boton.setForeground(Color.BLACK); // Texto negro para destacar la bomba
                } else {
                    int minasAdyacentes = juego.getTablero().contarMinasAdyacentes(casilla);
                    boton.setText(minasAdyacentes > 0 ? String.valueOf(minasAdyacentes) : "");
                    boton.setBackground(Color.DARK_GRAY);

                    // Asignar colores a los números
                    switch (minasAdyacentes) {
                        case 1:
                            boton.setForeground(Color.BLUE);
                            break;
                        case 2:
                            boton.setForeground(Color.GREEN);
                            break;
                        case 3:
                            boton.setForeground(Color.RED);
                            break;
                        case 4:
                            boton.setForeground(Color.MAGENTA);
                            break;
                        case 5:
                            boton.setForeground(Color.ORANGE);
                            break;
                        case 6:
                            boton.setForeground(Color.CYAN);
                            break;
                        case 7:
                            boton.setForeground(Color.YELLOW);
                            break;
                        case 8:
                            boton.setForeground(Color.PINK);
                            break;
                        default:
                            boton.setForeground(Color.WHITE);
                            break;
                    }
                }
            } else if (casilla.estaMarcada()) {
                boton.setText("🚩"); // Mostrar bandera
                boton.setForeground(Color.RED);
                boton.setBackground(Color.BLACK); // Fondo negro para la bandera
            } else {
                boton.setText(""); // Casilla no barrida
                boton.setBackground(Color.BLACK);
                boton.setForeground(Color.WHITE);
            }
        }
    }
}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new InterfazGrafica();
            }
        });
    }
}