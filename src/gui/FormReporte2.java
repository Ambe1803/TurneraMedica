package gui;

import Service.ServiceException;
import Service.TurnoService;
import clases.Turno;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.ArrayList;

public class FormReporte2 implements Formulario, DiseñoForm {
    JPanel formReporte2;
    JButton jButtonSend;
    JFormattedTextField jTextFieldFecha1;
    JFormattedTextField jTextFieldFecha2;
    JLabel jLabelFecha1;
    JLabel jLabelFecha2;
    JButton jButtonExit;
    PanelManager panel;
    AdminForm adminForm;
    FormReporte2Fin formReporte2Fin;
    ArrayList<Turno> listaTurnos;

    public FormReporte2(PanelManager panel) throws ServiceException {
        this.panel = panel;
        crearForm();
        agregarForm();
        agregarFuncionesBotones();
        diseño();
    }

    @Override
    public void crearForm() throws ServiceException {
        formReporte2 = new JPanel();
        formReporte2.setLayout(new GridLayout(3, 2, 10, 10)); // Espacio entre componentes

        // Etiquetas de Fecha
        jLabelFecha1 = new JLabel("Fecha Inicio");

        jLabelFecha2 = new JLabel("Fecha Fin");

        // Campos de texto formateados para las fechas
        jTextFieldFecha1 = new JFormattedTextField(createMaskFormatter("####/##/##"));

        jTextFieldFecha2 = new JFormattedTextField(createMaskFormatter("####/##/##"));

        // Botones
        jButtonSend = new JButton("Buscar");

        jButtonExit = new JButton("Salir");

    }

    @Override
    public void agregarForm() {
        formReporte2.add(jLabelFecha1);
        formReporte2.add(jTextFieldFecha1);
        formReporte2.add(jLabelFecha2);
        formReporte2.add(jTextFieldFecha2);
        formReporte2.add(jButtonSend);
        formReporte2.add(jButtonExit);
    }

    @Override
    public void agregarFuncionesBotones() {
        jButtonSend.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                listaTurnos = new ArrayList<>();
                TurnoService turnoService = new TurnoService();
                try{
                    listaTurnos = turnoService.calcularSumaCobrosPorRango(jTextFieldFecha1.getText(),jTextFieldFecha2.getText());
                    formReporte2Fin = new FormReporte2Fin(panel,listaTurnos);
                    panel.mostrar(formReporte2Fin.getForm());
                } catch (ServiceException ex){
                    throw new RuntimeException(ex);
                }
            }
        });

        jButtonExit.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                adminForm = new AdminForm(panel);
                panel.mostrar(adminForm.getForm());
            }
        });
    }

    @Override
    public JPanel getForm() {
        return formReporte2;
    }

    private MaskFormatter createMaskFormatter(String mask) {
        MaskFormatter formatter = null;
        try{
            formatter = new MaskFormatter(mask);
            formatter.setPlaceholderCharacter('_');
        } catch (ParseException e){
            e.printStackTrace();
        }return formatter;
    }


    @Override
    public void diseño() {
        formReporte2.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Espacio interno
        formReporte2.setBackground(new Color(245, 245, 245)); // Fondo gris claro
        formReporte2.setPreferredSize(new Dimension(300, 150));
        formReporte2.setOpaque(true);
    }
}
