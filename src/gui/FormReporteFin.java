package gui;

import Service.ServiceException;
import clases.Turno;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class FormReporteFin extends JPanel implements DiseñoForm {
    PanelManager panel;
    JPanel formReporteFin;
    ArrayList<Turno> listaTurnos;
    JLabel jLabelTotal;
    JLabel jLabelIntro;
    DefaultTableModel model;
    GridBagConstraints gbc;
    String fecha1;
    String fecha2;

    public FormReporteFin(PanelManager panel, ArrayList<Turno> turnos, String fecha1, String fecha2) {
        this.listaTurnos = turnos;
        this.panel = panel;
        this.fecha1 = fecha1;
        this.fecha2 = fecha2;
        crearFormReporteFin();
        diseño();

    }

    private void crearFormReporteFin() {
        formReporteFin = new JPanel(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 10, 0);

        // Etiqueta de Introducción
        jLabelIntro = new JLabel("<html><div style='font-size:12px;'><center>El médico con legajo: <b>" +
                listaTurnos.get(0).getLegajoMedico() +
                "</b><br>entre las fechas <b>" +
                this.fecha1 + "</b> y <b>" +
                this.fecha2 + "</b></center></div></html>");
        jLabelIntro.setHorizontalAlignment(SwingConstants.CENTER);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        formReporteFin.add(jLabelIntro, gbc);

        // Tabla
        model = new DefaultTableModel();
        model.addColumn("DNI Paciente");
        model.addColumn("Fecha");
        model.addColumn("Costo");

        double total = 0.0;
        for (Turno turno : listaTurnos) {
            model.addRow(new Object[]{turno.getDniPaciente(), turno.getFecha(), turno.getCosto()});
            total += turno.getCosto();
        }

        JTable table = new JTable(model);


        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(450, 200));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH;
        formReporteFin.add(scrollPane, gbc);

        // Total
        jLabelTotal = new JLabel("<html><div style='font-size:12px;'>El total de dinero cobrado es: <b>" + total + "</b></div></html>");
        jLabelTotal.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        formReporteFin.add(jLabelTotal, gbc);

        // Botón de Salida
        JButton exitButton = new JButton("Salir");


        exitButton.addActionListener(new ActionListener()  {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Abrir la pestaña FormularioReporte
                FormReporte formReporte = null;
                try {
                    formReporte = new FormReporte(panel);
                } catch (ServiceException ex) {
                    throw new RuntimeException(ex);
                }
                panel.mostrar(formReporte.getForm());
            }
        });
    }

    public JPanel getForm() {
        return formReporteFin;
    }

    @Override
    public void diseño() {
        formReporteFin.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Espacio alrededor
        formReporteFin.setBackground(new Color(245, 245, 245)); // Fondo gris claro
        formReporteFin.setOpaque(true);
    }
}
