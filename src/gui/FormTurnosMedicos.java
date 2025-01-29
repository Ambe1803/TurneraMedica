package gui;

import Service.ServiceException;
import clases.Turno;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class FormTurnosMedicos implements DiseñoForm {
    DefaultTableModel model;
    JPanel formTurnosMedicos;
    PanelManager panel;
    ArrayList<Turno> turnos;

    public FormTurnosMedicos(PanelManager panel, ArrayList<Turno> turnos) throws ServiceException {
        this.panel = panel;
        this.turnos = turnos;
        crearFormTurnosMedicos();
        diseño();
    }

    public void crearFormTurnosMedicos() {
        formTurnosMedicos = new JPanel(new BorderLayout());
        formTurnosMedicos.setLayout(new GridLayout(1,1));

        // Configuración del modelo de la tabla
        model = new DefaultTableModel();
        model.addColumn("DNI Paciente");
        model.addColumn("Hora");
        model.addColumn("Costo");

        for (Turno turno : this.turnos) {
            model.addRow(new Object[]{turno.getDniPaciente(), turno.getHora(), turno.getCosto()});
        }
        formTurnosMedicos.add(new JScrollPane(new JTable(model)));


        JTable table = new JTable(model);



    }

    public JPanel getForm() {
        return formTurnosMedicos;
    }

    @Override
    public void diseño() {
        formTurnosMedicos.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Espacio alrededor del panel
        formTurnosMedicos.setBackground(new Color(245, 245, 245)); // Fondo gris claro
        formTurnosMedicos.setOpaque(true);
    }
}
