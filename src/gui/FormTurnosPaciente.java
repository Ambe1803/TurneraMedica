package gui;

import Service.ServiceException;
import Service.TurnoService;
import clases.Turno;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class FormTurnosPaciente implements DiseñoForm {
    TurnoService turnoService;
    DefaultTableModel model;
    JPanel formTurnosPaciente;
    PanelManager panel;
    ArrayList<Turno> turnos;

    public FormTurnosPaciente(PanelManager panel, ArrayList<Turno> turnos) throws ServiceException {
        this.panel = panel;
        this.turnos = turnos;
        crearForm();
        diseño();
    }

    public void crearForm() throws ServiceException {
        formTurnosPaciente = new JPanel(new BorderLayout());
        turnoService = new TurnoService();
        formTurnosPaciente.setLayout(new GridLayout(1,1));

        // Configuración del modelo de la tabla
        model = new DefaultTableModel();
        model.addColumn("Legajo Médico");
        model.addColumn("Fecha");
        model.addColumn("Costo");

        // Rellenar la tabla con los datos de los turnos
        for (Turno turno : this.turnos) {
            model.addRow(new Object[]{turno.getLegajoMedico(), turno.getFecha(), turno.getCosto()});
        }
        formTurnosPaciente.add(new JScrollPane(new JTable(model)));






    }

    public JPanel getForm() {
        return formTurnosPaciente;
    }

    @Override
    public void diseño() {
        formTurnosPaciente.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Espacio alrededor
        formTurnosPaciente.setBackground(new Color(245, 245, 245)); // Fondo gris claro
        formTurnosPaciente.setOpaque(true);
    }
}
