/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package metrobuscaminas;

/**
 *
 * @author diego
 */
public class Main {
    public static void main(String[] args) {
        // Usar SwingUtilities.invokeLater para asegurar que la interfaz se ejecute en el hilo de eventos de Swing
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new InterfazGrafica(); // Crear y mostrar la interfaz gráfica
            }
        });
    } 
}
