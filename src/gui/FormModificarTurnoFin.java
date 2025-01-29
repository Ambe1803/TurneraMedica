package gui;

import Service.ServiceException;
import Service.TurnoService;
import clases.Turno;

import javax.swing.*;
import java.awt.*;

public class FormModificarTurnoFin implements Formulario, DiseñoForm {
    Turno turno;
    JPanel formModificarTurnoFin;
    JLabel jLabelCosto;
    JTextField jTextFieldCosto;
    JButton jButtonSend;
    JButton jButtonExit;
    PanelManager panel;
    AdminForm adminForm;
    FormModificarTurno formModificarTurno;
    TurnoService turnoService;

    public FormModificarTurnoFin(PanelManager panel, Turno turno) {
        this.panel = panel;
        this.turno = turno;
        crearForm();
        agregarForm();
        agregarFuncionesBotones();
        diseño();
    }

    @Override
    public void crearForm() {
        formModificarTurnoFin = new JPanel();
        formModificarTurnoFin.setLayout(new GridLayout(2, 2, 10, 10)); // Espacio entre componentes

        // Etiqueta
        jLabelCosto = new JLabel("Costo");
        jLabelCosto.setFont(new Font("Arial", Font.BOLD, 14));
        jLabelCosto.setForeground(Color.DARK_GRAY);

        // Campo de texto
        jTextFieldCosto = new JTextField();
        jTextFieldCosto.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        jTextFieldCosto.setPreferredSize(new Dimension(150, 25));

        // Botones
        jButtonSend = new JButton("Enviar");
        jButtonSend.setBackground(new Color(0, 128, 0)); // Verde oscuro
        jButtonSend.setForeground(Color.WHITE);
        jButtonSend.setFocusPainted(false);
        jButtonSend.setFont(new Font("Arial", Font.BOLD, 12));

        jButtonExit = new JButton("Salir");
        jButtonExit.setBackground(new Color(178, 34, 34)); // Rojo oscuro
        jButtonExit.setForeground(Color.WHITE);
        jButtonExit.setFocusPainted(false);
        jButtonExit.setFont(new Font("Arial", Font.BOLD, 12));
    }

    @Override
    public void agregarForm() {
        formModificarTurnoFin.add(jLabelCosto);
        formModificarTurnoFin.add(jTextFieldCosto);
        formModificarTurnoFin.add(jButtonExit);
        formModificarTurnoFin.add(jButtonSend);
    }

    @Override
    public void agregarFuncionesBotones() {
        jButtonSend.addActionListener(e -> {
            turnoService = new TurnoService();
            try {
                turno.setCosto(Double.parseDouble(jTextFieldCosto.getText()));
                turnoService.modificar(turno);

                adminForm = new AdminForm(panel);
                panel.mostrar(adminForm.getForm());
                JOptionPane.showMessageDialog(null, "Se modificó el turno correctamente");
            } catch (Exception exception) {
                if (exception instanceof NumberFormatException) {
                    JOptionPane.showMessageDialog(null, "Ingrese un valor numérico válido para el costo");
                } else {
                    exception.printStackTrace();
                }
            }
        });

        jButtonExit.addActionListener(e -> {
            try {
                formModificarTurno = new FormModificarTurno(panel);

            } catch (ServiceException ex) {
                throw new RuntimeException(ex);
            }
            panel.mostrar(formModificarTurno.getForm());
        });
    }

    @Override
    public void diseño() {
        formModificarTurnoFin.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Espacio alrededor del formulario
        formModificarTurnoFin.setBackground(new Color(245, 245, 245)); // Fondo gris claro
        formModificarTurnoFin.setPreferredSize(new Dimension(300, 120));
        formModificarTurnoFin.setOpaque(true);
    }

    @Override
    public JPanel getForm() {
        return formModificarTurnoFin;
    }
}
