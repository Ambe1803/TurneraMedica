package gui;

import Service.PacienteService;
import Service.ServiceException;
import clases.Paciente;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;

public class FormModificarPaciente implements Formulario, DiseñoForm, SetFormatoJTextField {
    JPanel formModificarPaciente;
    PanelManager panel;
    AdminForm adminForm;
    JLabel jLabelDni;
    JTextField jTextFieldDni;
    JButton jButtonSend;
    JButton jButtonExit;
    FormModificarPacienteFin formModificarPacienteFin;
    Paciente paciente;
    PacienteService pacienteService;

    public FormModificarPaciente(PanelManager panel) {
        this.panel = panel;
        crearForm();
        agregarForm();
        agregarFuncionesBotones();
        diseño();
    }

    @Override
    public void crearForm() {
        formModificarPaciente = new JPanel();
        formModificarPaciente.setLayout(new GridLayout(2, 2));
        jLabelDni = new JLabel("DNI del paciente a modificar");
        jTextFieldDni = new JTextField();
        jButtonSend = new JButton("Enviar");
        jButtonExit = new JButton("Salir");
    }

    @Override
    public void agregarForm() {
        formModificarPaciente.add(jLabelDni);
        formModificarPaciente.add(jTextFieldDni);
        formModificarPaciente.add(jButtonExit);
        formModificarPaciente.add(jButtonSend);
        setFormatoJTextField(jTextFieldDni);
    }

    @Override
    public void agregarFuncionesBotones() {
        jButtonSend.addActionListener(e -> {
            pacienteService = new PacienteService();
            paciente = new Paciente();
            try {
                paciente.setDni(Integer.parseInt(jTextFieldDni.getText()));
                paciente = pacienteService.buscar(paciente);
                if (paciente == null) {
                    JOptionPane.showMessageDialog(null, "Paciente no encontrado");
                } else {
                    formModificarPacienteFin= new FormModificarPacienteFin(panel, paciente);
                    panel.mostrar(formModificarPacienteFin.getForm());
                }
            } catch (ServiceException ex) {
                JOptionPane.showMessageDialog(null, "Error al buscar el paciente", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Ingrese un valor válido en el campo DNI", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        jButtonExit.addActionListener(e -> {
            adminForm = new AdminForm(panel);
            panel.mostrar(adminForm.getForm());
        });
    }

    @Override
    public void diseño() {
        formModificarPaciente.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        formModificarPaciente.setBackground(Color.lightGray);
        formModificarPaciente.setPreferredSize(new Dimension(220, 80));
        formModificarPaciente.setOpaque(true);
    }

    @Override
    public JPanel getForm() {
        return formModificarPaciente;
    }

    @Override
    public void setFormatoJTextField(JTextField textField) {
        ((AbstractDocument) textField.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                    throws BadLocationException {
                String newText = fb.getDocument().getText(0, fb.getDocument().getLength()) + text;
                if (newText.matches("\\d{0,8}")) { // Limita a 8 dígitos
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        });
    }
}
