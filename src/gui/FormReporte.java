package gui;

import Service.MedicoService;
import Service.ServiceException;
import Service.TurnoService;
import clases.Medico;
import clases.Turno;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.ArrayList;

public class FormReporte extends JPanel implements Formulario, DiseñoForm {
    MedicoService medicoService;
    ArrayList<Turno> listaTurnos;
    FormReporteFin formReporteFin;
    AdminForm adminForm;
    JPanel formReporte;
    PanelManager panel;
    JFormattedTextField jTextFieldFecha1;
    JLabel jLabelFecha1;
    JFormattedTextField jTextFieldFecha2;
    JLabel jLabelFecha2;
    JComboBox<String> jComboBoxLegajoMedico;
    JLabel jLabelLegajo;
    JButton jButtonSend;
    JButton jButtonExit;
    TurnoService turnoService;

    public FormReporte(PanelManager panel) throws ServiceException {
        this.panel = panel;
        crearForm();
        agregarForm();
        agregarFuncionesBotones();
        diseño();
    }

    @Override
    public void crearForm() throws ServiceException {
        formReporte = new JPanel();
        formReporte.setLayout(new GridLayout(4, 2, 10, 10)); // Espacio entre componentes

        // Etiquetas
        jLabelLegajo = new JLabel("Legajo del Médico");

        jLabelFecha1 = new JLabel("Fecha Inicio");

        jLabelFecha2 = new JLabel("Fecha Fin");

        // ComboBox de médicos
        jComboBoxLegajoMedico = new JComboBox<>();
        ArrayList<Medico> medicos = fillarrayMedicos();
        for (Medico m : medicos) {
            jComboBoxLegajoMedico.addItem(m.getLegajo() + "-" + m.getNombre() + " " + m.getApellido());
        }

        // Campos de texto formateados para fechas
        jTextFieldFecha1 = new JFormattedTextField(createMaskFormatter("####/##/##"));

        jTextFieldFecha2 = new JFormattedTextField(createMaskFormatter("####/##/##"));

        // Botones
        jButtonSend = new JButton("Buscar");

        jButtonExit = new JButton("Salir");

    }

    @Override
    public void agregarForm() {
        formReporte.add(jLabelLegajo);
        formReporte.add(jComboBoxLegajoMedico);
        formReporte.add(jLabelFecha1);
        formReporte.add(jTextFieldFecha1);
        formReporte.add(jLabelFecha2);
        formReporte.add(jTextFieldFecha2);
        formReporte.add(jButtonExit);
        formReporte.add(jButtonSend);
    }

    @Override
    public void agregarFuncionesBotones(){
        jButtonSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    turnoService = new TurnoService();
                    listaTurnos = new ArrayList<>();
                    ArrayList<Turno> listaTurnos = turnoService.buscarCobros(jTextFieldFecha1.getText(),jTextFieldFecha2.getText(),Integer.parseInt(jComboBoxLegajoMedico.getSelectedItem().toString().split("-")[0]));
                    formReporteFin = new FormReporteFin(panel,listaTurnos,jTextFieldFecha1.getText(),jTextFieldFecha2.getText());
                    panel.mostrar(formReporteFin.getForm());
                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(null, "No hay turnos");
                    exception.printStackTrace();
                }
            }
        });

        jButtonExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adminForm = new AdminForm(panel);
                panel.mostrar(adminForm.getForm());
            }
        });
    }




    public JPanel getForm() {
        return formReporte;
    }

    private MaskFormatter createMaskFormatter(String mask) {
        MaskFormatter formatter = null;
        try {
            formatter = new MaskFormatter(mask);
            formatter.setPlaceholderCharacter('_');
        } catch (ParseException e) {
            e.printStackTrace();

        }
        return formatter;
    }

    public ArrayList<Medico> fillarrayMedicos() throws ServiceException {
        ArrayList<Medico> medicos = new ArrayList<Medico>();
        medicoService = new MedicoService();
        medicos = medicoService.buscarTodos();
        return medicos;
    }


    @Override
    public void diseño() {
        formReporte.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Espacio interno
        formReporte.setBackground(new Color(245, 245, 245)); // Fondo gris claro
        formReporte.setPreferredSize(new Dimension(450, 200));
        formReporte.setOpaque(true);
    }
}
