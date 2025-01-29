package gui;

import Service.TurnoService;
import clases.Turno;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class FormModificarTurnoFecha implements Formulario, DiseñoForm {
    JPanel formModificarTurnoFecha;
    ArrayList<Turno> turnos;
    JButton jButtonSend;
    JButton jButtonExit;
    JLabel jLabelFecha;
    JComboBox<String> jComboBoxFecha;
    TurnoService turnoService;
    PanelManager panel;
    Turno turno;
    FormModificarTurnoFin formModificarTurnoFin;

    public FormModificarTurnoFecha(PanelManager panel, ArrayList<Turno> turnos) {
        this.panel = panel;
        this.turnos = turnos;
        crearForm();
        agregarForm();
        agregarFuncionesBotones();
        diseño();
    }

    @Override
    public void crearForm() {
        formModificarTurnoFecha = new JPanel();
        formModificarTurnoFecha.setLayout(new GridLayout(2, 2, 10, 10)); // Espacio entre componentes

        // Etiqueta
        jLabelFecha = new JLabel("Fecha");
        jLabelFecha.setFont(new Font("Arial", Font.BOLD, 14));
        jLabelFecha.setForeground(Color.DARK_GRAY);

        // ComboBox
        jComboBoxFecha = new JComboBox<>();
        jComboBoxFecha.setBackground(Color.WHITE);
        jComboBoxFecha.setForeground(Color.DARK_GRAY);
        jComboBoxFecha.setFont(new Font("Arial", Font.PLAIN, 13));

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

        // Llenar ComboBox con las fechas de los turnos
        for (Turno turno : turnos) {
            jComboBoxFecha.addItem(turno.getFecha());
        }
    }

    @Override
    public void agregarForm() {
        formModificarTurnoFecha.add(jLabelFecha);
        formModificarTurnoFecha.add(jComboBoxFecha);
        formModificarTurnoFecha.add(jButtonExit);
        formModificarTurnoFecha.add(jButtonSend);
    }

    @Override
    public void agregarFuncionesBotones() {
        jButtonSend.addActionListener(e -> {
            turnoService = new TurnoService();
            turno = turnos.get(0);
            turno.setFecha(jComboBoxFecha.getSelectedItem().toString());
            formModificarTurnoFin = new FormModificarTurnoFin(panel, turno);
            panel.mostrar(formModificarTurnoFin.getForm());
        });

        jButtonExit.addActionListener(e -> {
            AdminForm adminForm = new AdminForm(panel);
            panel.mostrar(adminForm.getForm());
        });
    }

    @Override
    public void diseño() {
        formModificarTurnoFecha.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Margen interno
        formModificarTurnoFecha.setBackground(new Color(245, 245, 245)); // Fondo gris claro
        formModificarTurnoFecha.setPreferredSize(new Dimension(300, 120));
        formModificarTurnoFecha.setOpaque(true);
    }

    @Override
    public JPanel getForm() {
        return formModificarTurnoFecha;
    }
}
