package gui;

import Service.PacienteService;
import Service.ServiceException;
import clases.Paciente;

import javax.swing.*;
import java.awt.*;

public class FormModificarPacienteFin implements Formulario, DiseñoForm {
    JLabel jLabelNombre;
    JLabel jLabelApellido;
    JLabel jLabelO_Social;
    JTextField jTextFieldNombre;
    JTextField jTextFieldApellido;
    JTextField jTextFieldO_Social;
    JButton jButtonSend;
    JButton jButtonExit;
    JPanel formModificarPacienteFin;
    PanelManager panel;
    AdminForm adminForm;
    Paciente paciente;

    public FormModificarPacienteFin(PanelManager panel, Paciente paciente) {
        this.panel = panel;
        this.paciente = paciente;
        crearForm();
        agregarForm();
        agregarFuncionesBotones();
        diseño();
    }

    @Override
    public void crearForm() {
        formModificarPacienteFin = new JPanel();
        formModificarPacienteFin.setLayout(new GridLayout(4, 2));

        // Etiquetas
        jLabelNombre = new JLabel("Nombre");
        jLabelApellido = new JLabel("Apellido");
        jLabelO_Social = new JLabel("Código Obra Social");

        // Campos de texto
        jTextFieldNombre = new JTextField();
        jTextFieldApellido = new JTextField();
        jTextFieldO_Social = new JTextField();

        // Botones
        jButtonSend = new JButton("Enviar");
        jButtonExit = new JButton("Salir");
    }

    @Override
    public void agregarForm() {
        formModificarPacienteFin.add(jLabelNombre);
        formModificarPacienteFin.add(jTextFieldNombre);
        formModificarPacienteFin.add(jLabelApellido);
        formModificarPacienteFin.add(jTextFieldApellido);
        formModificarPacienteFin.add(jLabelO_Social);
        formModificarPacienteFin.add(jTextFieldO_Social);
        formModificarPacienteFin.add(jButtonExit);
        formModificarPacienteFin.add(jButtonSend);
    }

    @Override
    public void agregarFuncionesBotones() {
        jButtonSend.addActionListener(e -> {
            try {
                paciente.setNombre(jTextFieldNombre.getText());
                paciente.setApellido(jTextFieldApellido.getText());
                paciente.setO_Social(Integer.parseInt(jTextFieldO_Social.getText()));

                PacienteService pacienteService = new PacienteService();
                pacienteService.modificar(paciente);

                JOptionPane.showMessageDialog(null, "Paciente modificado");
                adminForm = new AdminForm(panel);
                panel.mostrar(adminForm.getForm());
            } catch (ServiceException ex) {
                JOptionPane.showMessageDialog(null, "Error al modificar el paciente", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Ingrese un valor válido en el campo Código Obra Social", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        jButtonExit.addActionListener(e -> {
            adminForm = new AdminForm(panel);
            panel.mostrar(adminForm.getForm());
        });
    }

    @Override
    public void diseño() {
        formModificarPacienteFin.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        formModificarPacienteFin.setBackground(Color.lightGray);
        formModificarPacienteFin.setPreferredSize(new Dimension(220, 160));
        formModificarPacienteFin.setOpaque(true);
    }

    @Override
    public JPanel getForm() {
        return formModificarPacienteFin;
    }
}
