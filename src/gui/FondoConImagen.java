package gui;

import javax.swing.*;
import java.awt.*;

public class FondoConImagen extends JPanel {
    private Image fondo; // Variable para almacenar la imagen

    public FondoConImagen(String rutaImagen) {
        // Carga la imagen desde la ruta proporcionada
        fondo = new ImageIcon(rutaImagen).getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Llama al método original para dibujar los componentes
        if (fondo != null) {
            // Dibuja la imagen en el fondo del panel, ajustándola al tamaño del panel
            g.drawImage(fondo, 0, 0, getWidth(), getHeight(), this);
        }
    }
}

