package gui;

import Service.ServiceException;
import clases.Turno;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class FormReporte2Fin implements DiseñoForm {
    DefaultTableModel model;
    JPanel formReporte2Fin;
    PanelManager panel;
    ArrayList<Turno> turnos;

    public FormReporte2Fin(PanelManager panel, ArrayList<Turno> turnos) throws ServiceException {
        this.panel = panel;
        this.turnos = turnos;
        crearFormReporte2Fin();
        diseño();
    }

    public void crearFormReporte2Fin() throws ServiceException {
        formReporte2Fin = new JPanel();
        formReporte2Fin.setLayout(new BorderLayout());

        // Modelo de la tabla
        model = new DefaultTableModel();
        model.addColumn("Legajo Médico");
        model.addColumn("Nombre Médico");
        model.addColumn("Apellido Médico");
        model.addColumn("Ganancia");

        // Llenar datos en la tabla
        for (Turno turno : this.turnos) {
            model.addRow(new Object[]{turno.getLegajoMedico(), turno.getNombreMedico(), turno.getApellidoMedico(), turno.getCosto()});
        }
        formReporte2Fin.add(new JScrollPane(new JTable(model)));

        // Tabla con estilo mejorado
        JTable table = new JTable(model);


        // Scroll pane para la tabla
        JScrollPane scrollPane = new JScrollPane(table);
        formReporte2Fin.add(scrollPane, BorderLayout.CENTER);
    }

    @Override
    public void diseño() {
        formReporte2Fin.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        formReporte2Fin.setBackground(new Color(245, 245, 245));
        formReporte2Fin.setOpaque(true);
    }

    public JPanel getForm() {
        return formReporte2Fin;
    }
}
