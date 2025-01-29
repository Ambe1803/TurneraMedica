package gui;

import javax.swing.*;
import java.awt.*;

public class PanelManager {
    public FormSeleccionUsuario formSeleccionUsuario;
    public AdminForm adminForm;
    JFrame ventana;
    public PanelManager() {
        ventana = new JFrame("Turnera Medica");
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setSize(1000, 1000); // Tamaño inicial de la ventana
        ventana.setLocationRelativeTo(null); // Centrar la ventana en la pantalla

        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Alineación a la izquierda
        JLabel titulo = new JLabel("Turnera Medica");
        titulo.setFont(new Font("Arial", Font.BOLD, 18));







        // Configuración del formulario inicial
        formSeleccionUsuario = new FormSeleccionUsuario(this);
        mostrar(formSeleccionUsuario.getForm());

        ventana.setVisible(true);
    }

    public void mostrar(JPanel panel) {
        ventana.getContentPane().removeAll();
        ventana.getContentPane().add(panel, BorderLayout.NORTH); // Mostrar en el centro de la ventana
        ventana.revalidate();
        ventana.repaint();
        ventana.pack();
        ventana.setLocationRelativeTo(null);
    }

    public JFrame getVentana() {
        return ventana;
    }

    public void cerrarVentana() {
        ventana.dispose();
    }
}
