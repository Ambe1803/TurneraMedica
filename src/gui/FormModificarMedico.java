package gui;

import Service.MedicoService;
import Service.ServiceException;
import clases.Medico;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;

public class FormModificarMedico implements Formulario, DiseñoForm, SetFormatoJTextField {
    JPanel formModificarMedico;
    PanelManager panel;
    AdminForm adminForm;
    JLabel jLabelLegajo;
    JTextField jtextField;
    JButton jButtonSend;
    JButton jButtonExit;
    FormModificarMedicoFin formModificarMedicoFin;
    Medico medico;
    MedicoService medicoService;

    public FormModificarMedico(PanelManager panel) {
        this.panel = panel;
        crearForm();
        agregarForm();
        agregarFuncionesBotones();
        diseño();
    }

    @Override
    public void crearForm() {
        formModificarMedico = new JPanel();
        formModificarMedico.setLayout(new GridLayout(2, 2));
        jLabelLegajo = new JLabel("Legajo del médico a modificar");
        jtextField = new JTextField();
        jButtonSend = new JButton("Enviar");
        jButtonExit = new JButton("Salir");
    }

    @Override
    public void agregarForm() {
        formModificarMedico.add(jLabelLegajo);
        formModificarMedico.add(jtextField);
        formModificarMedico.add(jButtonExit);
        formModificarMedico.add(jButtonSend);
        setFormatoJTextField(jtextField);
    }

    @Override
    public void agregarFuncionesBotones() {
        jButtonSend.addActionListener(e -> {
            medicoService = new MedicoService();
            medico = new Medico();
            try {
                medico.setLegajo(Integer.parseInt(jtextField.getText()));
                medico = medicoService.buscar(medico);
                if (medico == null) {
                    JOptionPane.showMessageDialog(null, "Médico no encontrado");
                } else {
                    formModificarMedicoFin = new FormModificarMedicoFin(panel, medico);
                    panel.mostrar(formModificarMedicoFin.getForm());
                }
            } catch (ServiceException ex) {
                throw new RuntimeException(ex);
            }
        });

        jButtonExit.addActionListener(e -> {
            adminForm = new AdminForm(panel);
            panel.mostrar(adminForm.getForm());
        });
    }

    @Override
    public void diseño() {
        formModificarMedico.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        formModificarMedico.setBackground(Color.lightGray);
        formModificarMedico.setPreferredSize(new Dimension(220, 80));
        formModificarMedico.setOpaque(true);
    }

    @Override
    public JPanel getForm() {
        return formModificarMedico;
    }



    @Override
    public void setFormatoJTextField(JTextField textField) {
        ((AbstractDocument) textField.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                    throws BadLocationException {
                String newText = fb.getDocument().getText(0, fb.getDocument().getLength()) + text;
                if (newText.matches("\\d{0,8}")) { // Permite solo hasta 8 dígitos
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        });

    }
}
