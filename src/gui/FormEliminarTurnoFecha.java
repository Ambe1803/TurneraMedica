package gui;

import Service.ServiceException;
import Service.TurnoService;
import clases.Turno;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class FormEliminarTurnoFecha extends JPanel implements Formulario, DiseñoForm {
    JPanel formEliminarTurnoFecha;
    ArrayList<Turno> turnos;
    JButton jButtonSend;
    JButton jButtonExit;
    JLabel jLabelFecha;
    JComboBox<String> jComboBoxFecha;
    TurnoService turnoService;
    PanelManager panel;
    Turno turno;
    AdminForm adminForm;

    public FormEliminarTurnoFecha(PanelManager panel, ArrayList<Turno> turnos) {
        this.panel = panel;
        this.turnos = turnos;
        crearForm();
        agregarForm();
        agregarFuncionesBotones();
        diseño();
    }

    @Override
    public void crearForm() {
        formEliminarTurnoFecha = new JPanel(new GridLayout(2, 2, 5, 5));

        jLabelFecha = new JLabel("Fecha:");
        jComboBoxFecha = new JComboBox<>();

        jButtonSend = new JButton("Eliminar");
        jButtonExit = new JButton("Salir");

        // Poblar el JComboBox con las fechas de los turnos
        for (Turno turno : turnos) {
            jComboBoxFecha.addItem(turno.getFecha());
        }
    }

    @Override
    public void agregarForm() {
        formEliminarTurnoFecha.add(jLabelFecha);
        formEliminarTurnoFecha.add(jComboBoxFecha);
        formEliminarTurnoFecha.add(jButtonExit);
        formEliminarTurnoFecha.add(jButtonSend);


    }

    @Override
    public void agregarFuncionesBotones() {
        jButtonSend.addActionListener(e -> {
            turnoService = new TurnoService();

            // Obtener el turno que corresponde a la fecha seleccionada
            turno = turnos.get(0);
            turno.setFecha(jComboBoxFecha.getSelectedItem().toString());

            try {
                turnoService.eliminar(turno);
            } catch (ServiceException ex) {
                JOptionPane.showMessageDialog(null,"Error al eliminar turno ");
                throw new RuntimeException(ex);
            }
            adminForm = new AdminForm(panel);
            panel.mostrar(adminForm.getForm());
            JOptionPane.showMessageDialog(null,"Turno eliminado");
        });

        jButtonExit.addActionListener(e -> {
            AdminForm adminForm = new AdminForm(panel);
            panel.mostrar(adminForm.getForm());
        });
    }

    @Override
    public void diseño() {
        formEliminarTurnoFecha.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        formEliminarTurnoFecha.setBackground(Color.LIGHT_GRAY);
        formEliminarTurnoFecha.setPreferredSize(new Dimension(220, 80));
        formEliminarTurnoFecha.setOpaque(true);

        /*jLabelFecha.setForeground(Color.DARK_GRAY);

        jButtonSend.setBackground(Color.GREEN);
        jButtonSend.setForeground(Color.WHITE);
        jButtonSend.setFont(new Font("Arial", Font.BOLD, 12));

        jButtonExit.setBackground(Color.RED);
        jButtonExit.setForeground(Color.WHITE);
        jButtonExit.setFont(new Font("Arial", Font.BOLD, 12));*/
    }

    @Override
    public JPanel getForm() {
        return formEliminarTurnoFecha;
    }
}
