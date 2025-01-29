package gui;

import gui.FondoConImagen;
import javax.swing.border.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FormSeleccionUsuario extends JPanel implements Formulario, DiseñoForm {
    FondoConImagen formSeleccionUsuario; // Cambiar JPanel a FondoConImagen
    FormLoginAdmin formLoginAdmin;
    FormUsuarioMedico formUsuarioMedico;
    FormUsuarioPaciente formUsuarioPaciente;
    PanelManager panel;
    JButton jButtonAdmin;
    JButton jButtonMedico;
    JButton jButtonPaciente;
    JLabel labelBienvenida;
    JPanel panelBotones; // Declarar panelBotones como atributo de la clase

    public FormSeleccionUsuario(PanelManager panel) {
        this.panel = panel;
        crearForm();
        agregarForm();
        agregarFuncionesBotones();
        diseño();
    }

    @Override
    public void crearForm() {
        // Crear el panel con una imagen de fondo
        formSeleccionUsuario = new FondoConImagen("src/Imagenes/Fondo.jpg");
        formSeleccionUsuario.setLayout(new BorderLayout()); // Usar BorderLayout para organizar componentes

        // Crear el mensaje de bienvenida
        labelBienvenida = new JLabel("<html>Bienvenidos a su sistema Medico<br>elija el usuario</html>");
        labelBienvenida.setFont(new Font("Arial", Font.BOLD, 24));
        labelBienvenida.setHorizontalAlignment(SwingConstants.CENTER); // Centrar horizontalmente
        labelBienvenida.setVerticalAlignment(SwingConstants.CENTER);   // Centrar verticalmente

        // Crear el panel de botones
        panelBotones = new JPanel(new GridLayout(3, 1, 10, 10)); // Organizar los botones verticalmente
        panelBotones.setOpaque(false); // Hacer el panel transparente

        // Crear los botones
        jButtonAdmin = new JButton("Administrador");
        jButtonMedico = new JButton("Médico");
        jButtonPaciente = new JButton("Paciente");

        // Agregar los botones al panel
        panelBotones.add(jButtonAdmin);
        panelBotones.add(jButtonMedico);
        panelBotones.add(jButtonPaciente);
    }

    @Override
    public void agregarForm() {
        // Agregar el panel de botones al lado derecho
        formSeleccionUsuario.add(panelBotones, BorderLayout.EAST);

        // Agregar el mensaje de bienvenida al lado izquierdo
        formSeleccionUsuario.add(labelBienvenida, BorderLayout.WEST);
    }

    @Override
    public void agregarFuncionesBotones() {
        jButtonMedico.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                formUsuarioMedico = new FormUsuarioMedico(panel);
                panel.mostrar(formUsuarioMedico.getForm());
            }
        });
        jButtonAdmin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                formLoginAdmin = new FormLoginAdmin(panel);
                panel.mostrar(formLoginAdmin.getForm());
            }
        });
        jButtonPaciente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                formUsuarioPaciente = new FormUsuarioPaciente(panel);
                panel.mostrar(formUsuarioPaciente.getForm());
            }
        });
    }

    @Override
    public JPanel getForm() {
        return formSeleccionUsuario;
    }

    @Override
    public void diseño() {
        // Margen para el mensaje de bienvenida
        labelBienvenida.setBorder(BorderFactory.createEmptyBorder(25, 100, 50, 200));

        // Configurar un borde visible y márgenes para los botones
        Border bordeVisible = BorderFactory.createLineBorder(Color.BLACK, 2); // Borde negro visible
        Border margenInterno = BorderFactory.createEmptyBorder(5, 1, 5, 20); // Espacio interno

        // Botón "Administrador"
        jButtonAdmin.setBorder(BorderFactory.createCompoundBorder(bordeVisible, margenInterno));
        jButtonAdmin.setPreferredSize(new Dimension(200, 75)); // Ajustar tamaño del botón

        // Botón "Médico"
        jButtonMedico.setBorder(BorderFactory.createCompoundBorder(bordeVisible, margenInterno));
        jButtonMedico.setPreferredSize(new Dimension(200, 75));

        // Botón "Paciente"
        jButtonPaciente.setBorder(BorderFactory.createCompoundBorder(bordeVisible, margenInterno));
        jButtonPaciente.setPreferredSize(new Dimension(200, 75));

        // Mover los botones un poco hacia la izquierda agregando un margen al panelBotones
        panelBotones.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 50)); // Margen derecho para alejarlo del borde
    }
}
