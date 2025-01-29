package gui;


import Service.ServiceException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

public class AdminForm extends JPanel implements Formulario,DiseñoForm {

    FondoConImagen AdminForm ;
    FormAgregarMedico formAgregarMedico;
    FormAgregarPaciente formAgregarPaciente;
    FormAgregarTurno formAgregarTurno;
    FormReporte formReporte;
    JPanel adminForm;
    PanelManager panel;

    JButton jButtonRegistrarMedico;
    JButton jButtonRegistrarPaciente;
    JButton jButtonRegistrarTurno;
    // eliminar botones

    JButton jButtonEliminarTurno;
    FormEliminarTurno formEliminarTurno;


    JButton jButtonEliminarPaciente;
    FormEliminarPaciente formEliminarPaciente;

    JButton jButtonEliminarMedico;
    FormEliminarMedico formEliminarMedico;

    //Modificar
    JButton jButtonModificarTurno;
    FormModificarTurno formModificarTurno;
    JButton jButtonModificarPaciente;
    FormModificarPaciente formModificarPaciente;
    JButton jButtonModificarMedico;
    FormModificarMedico formModificarMedico;
    //reportes
    JButton jButtonReportes;
    JButton jButtonReporte2;
    FormReporte2 formReporte2;


    public AdminForm(PanelManager panel){
        this.panel=panel;
        crearForm();
        agregarForm();
        agregarFuncionesBotones();
        diseño();
    }





    @Override
    public void diseño() {
        adminForm.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        adminForm.setBackground(Color.LIGHT_GRAY);
        adminForm.setPreferredSize(new Dimension(500, 300)); // Tamaño del panel
        adminForm.setOpaque(true);

    }

    @Override
    public void crearForm(){
        adminForm = new FondoConImagen("src/Imagenes/Fondo.jpg");
        adminForm.setLayout(new GridLayout(4, 3,5,5));
        jButtonRegistrarMedico = new JButton("Registrar Medico");
        /*jButtonRegistrarMedico.setBackground(Color.LIGHT_GRAY); // Color de fondo
        jButtonRegistrarMedico.setForeground(Color.GREEN);*/

        jButtonRegistrarPaciente = new JButton("Registrar Paciente");
        /*jButtonRegistrarPaciente.setBackground(Color.LIGHT_GRAY);
        jButtonRegistrarPaciente.setForeground(Color.GREEN);*/

        jButtonRegistrarTurno = new JButton("Registrar Turno");
        /*jButtonRegistrarTurno.setBackground(Color.LIGHT_GRAY);
        jButtonRegistrarTurno.setForeground(Color.GREEN);*/


        jButtonEliminarMedico = new JButton("Eliminar Medico");
        /*jButtonEliminarMedico.setBackground(Color.LIGHT_GRAY);
        jButtonEliminarMedico.setForeground(Color.RED);*/

        jButtonEliminarPaciente = new JButton("Eliminar Paciente");
        /*jButtonEliminarPaciente.setBackground(Color.LIGHT_GRAY);
        jButtonEliminarPaciente.setForeground(Color.RED);*/

        jButtonEliminarTurno = new JButton("Eliminar Turno");
        /*jButtonEliminarTurno.setBackground(Color.LIGHT_GRAY);
        jButtonEliminarTurno.setForeground(Color.RED);*/


        jButtonModificarMedico = new JButton("Modificar Medico");
        /*jButtonModificarPaciente.setBackground(Color.LIGHT_GRAY);
        jButtonModificarPaciente.setForeground(Color.YELLOW);*/

        jButtonModificarPaciente = new JButton("Modificar Paciente");
        /*jButtonModificarPaciente.setBackground(Color.LIGHT_GRAY);
        jButtonModificarPaciente.setForeground(Color.YELLOW);*/

        jButtonModificarTurno = new JButton("Modificar Turno");
        /*jButtonModificarTurno.setBackground(Color.LIGHT_GRAY);
        jButtonModificarTurno.setForeground(Color.yellow);*/


        jButtonReportes = new JButton("Reportes");
        /*jButtonReportes.setBackground(Color.lightGray);
        jButtonReportes.setForeground(Color.BLACK);*/

        jButtonReporte2 = new JButton("Reporte Total");
        /*jButtonReporte2.setBackground(Color.lightGray);
        jButtonReporte2.setForeground(Color.BLACK);*/

    }

    @Override
    public void agregarForm() {
        adminForm.add(jButtonRegistrarMedico);
        adminForm.add(jButtonRegistrarPaciente);
        adminForm.add(jButtonRegistrarTurno);
        adminForm.add(jButtonEliminarMedico);
        adminForm.add(jButtonEliminarPaciente);
        adminForm.add(jButtonEliminarMedico);
        adminForm.add(jButtonModificarMedico);
        adminForm.add(jButtonModificarPaciente);
        adminForm.add(jButtonModificarTurno);
        adminForm.add(jButtonReporte2);
        adminForm.add(jButtonReportes);


    }

    @Override
    public void agregarFuncionesBotones() {
        jButtonRegistrarMedico.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                formAgregarMedico = new FormAgregarMedico(panel);
                panel.mostrar(formAgregarMedico.getForm());
            }
        });

        jButtonRegistrarPaciente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                formAgregarPaciente = new FormAgregarPaciente(panel);
                panel.mostrar(formAgregarPaciente.getForm());
            }
        });
        jButtonRegistrarTurno.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    formAgregarTurno = new FormAgregarTurno(panel);
                } catch (ServiceException | ParseException ex) {
                    throw new RuntimeException(ex);
                }
                panel.mostrar(formAgregarTurno.getForm());
            }
        });
        jButtonEliminarMedico.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                formEliminarMedico = new FormEliminarMedico(panel);
                panel.mostrar(formEliminarMedico.getForm());
            }
        });
        jButtonEliminarPaciente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                formEliminarPaciente = new FormEliminarPaciente(panel);
                panel.mostrar(formEliminarPaciente.getForm());
            }
        });

        jButtonEliminarTurno.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    formEliminarTurno = new FormEliminarTurno(panel);
                } catch (ServiceException ex) {
                    throw new RuntimeException(ex);
                }
                panel.mostrar(formEliminarTurno.getForm());
            }
        });
        jButtonModificarMedico.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                formModificarMedico = new FormModificarMedico(panel);
                panel.mostrar(formModificarMedico.getForm());
            }
        });

        jButtonModificarPaciente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                formModificarPaciente = new FormModificarPaciente(panel);
                panel.mostrar(formModificarPaciente.getForm());
            }
        });

        jButtonModificarTurno.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    formModificarTurno = new FormModificarTurno(panel);
                } catch (ServiceException ex) {
                    throw new RuntimeException(ex);
                }
                panel.mostrar(formModificarTurno.getForm());
            }
        });

        jButtonReportes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    formReporte = new FormReporte(panel);
                } catch (ServiceException ex) {
                    throw new RuntimeException(ex);
                }
                panel.mostrar(formReporte.getForm());
            }
        });

        jButtonReporte2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    formReporte2 = new FormReporte2(panel);
                } catch (ServiceException ex) {
                    throw new RuntimeException(ex);
                }
                panel.mostrar(formReporte2.getForm());
            }
        });
    }


    @Override
    public JPanel getForm() {
        return adminForm;
    }
}
