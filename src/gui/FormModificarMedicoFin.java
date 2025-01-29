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

public class FormModificarMedicoFin implements Formulario, DiseñoForm, SetFormatoJTextField {
    JLabel jLabelNombre;
    JLabel jLabelApellido;
    JLabel jLabelDni;
    JTextField jTextFieldNombre;
    JTextField jTextFieldApellido;
    JTextField jTextFieldDni;
    JButton jButtonSend;
    JButton jButtonExit;
    JPanel formModificarMedicoFin;
    PanelManager panel;
    AdminForm adminForm;
    Medico medico;

    public FormModificarMedicoFin(PanelManager panel, Medico medico) {
        this.panel = panel;
        this.medico = medico;
        crearForm();
        agregarForm();
        agregarFuncionesBotones();
        diseño();
    }

    @Override
    public void crearForm() {
        formModificarMedicoFin = new JPanel();
        formModificarMedicoFin.setLayout(new GridLayout(4, 2));

        // Etiquetas
        jLabelNombre = new JLabel("Nombre");
        jLabelApellido = new JLabel("Apellido");
        jLabelDni = new JLabel("DNI");

        // Campos de texto
        jTextFieldNombre = new JTextField();
        jTextFieldApellido = new JTextField();
        jTextFieldDni = new JTextField();

        // Botones
        jButtonSend = new JButton("Enviar");
        jButtonExit = new JButton("Salir");
    }

    @Override
    public void agregarForm() {
        formModificarMedicoFin.add(jLabelNombre);
        formModificarMedicoFin.add(jTextFieldNombre);
        formModificarMedicoFin.add(jLabelApellido);
        formModificarMedicoFin.add(jTextFieldApellido);
        formModificarMedicoFin.add(jLabelDni);
        formModificarMedicoFin.add(jTextFieldDni);
        formModificarMedicoFin.add(jButtonExit);
        formModificarMedicoFin.add(jButtonSend);
        setFormatoJTextField(jTextFieldDni);
    }

    @Override
    public void agregarFuncionesBotones() {
        jButtonSend.addActionListener(e -> {
            try {
                medico.setNombre(jTextFieldNombre.getText());
                medico.setApellido(jTextFieldApellido.getText());
                medico.setDni(Integer.parseInt(jTextFieldDni.getText()));

                MedicoService medicoService = new MedicoService();
                medicoService.modificar(medico);

                adminForm = new AdminForm(panel);
                panel.mostrar(adminForm.getForm());
                JOptionPane.showMessageDialog(null, "Se modificó el médico correctamente");
            } catch (ServiceException ex) {
                JOptionPane.showMessageDialog(null, "Error al modificar el médico");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Ingrese un valor válido en el campo DNI");
            }
        });

        jButtonExit.addActionListener(e -> {
            adminForm = new AdminForm(panel);
            panel.mostrar(adminForm.getForm());
        });
    }

    @Override
    public void diseño() {
        formModificarMedicoFin.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        formModificarMedicoFin.setBackground(Color.lightGray);
        formModificarMedicoFin.setPreferredSize(new Dimension(220, 160));
        formModificarMedicoFin.setOpaque(true);
    }

    @Override
    public JPanel getForm() {
        return formModificarMedicoFin;
    }

    @Override
    public void setFormatoJTextField(JTextField textField) {
        ((AbstractDocument) textField.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                    throws BadLocationException {
                String newText = fb.getDocument().getText(0, fb.getDocument().getLength()) + text;
                if (newText.matches("\\d{0,8}")) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        });
    }
}
