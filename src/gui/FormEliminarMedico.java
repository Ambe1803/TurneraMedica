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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FormEliminarMedico extends JPanel implements Formulario, DiseñoForm, SetFormatoJTextField {
    JPanel formEliminarMedico;
    JButton jButtonSend;
    JButton jButtonExit;
    JLabel jLabelLegajo;
    JTextField jTextFieldLegajo;
    PanelManager panel;
    AdminForm adminForm;
    MedicoService medicoService;

    public FormEliminarMedico(PanelManager panel) {
        this.panel = panel;
        crearForm();
        agregarForm();
        agregarFuncionesBotones();
        diseño();
    }

    @Override
    public void crearForm() {
        formEliminarMedico  = new FondoConImagen("src/Imagenes/Fondo.jpg");
        formEliminarMedico =  new JPanel(new GridLayout(2, 2, 5, 5));
        jLabelLegajo = new JLabel("Legajo:");
        jTextFieldLegajo = new JTextField();
        jButtonSend = new JButton("Enviar");
        jButtonExit = new JButton("Salir");
    }

    @Override
    public void agregarForm() {
        formEliminarMedico.add(jLabelLegajo);
        formEliminarMedico.add(jTextFieldLegajo);
        formEliminarMedico.add(jButtonExit);
        formEliminarMedico.add(jButtonSend);
        setFormatoJTextField(jTextFieldLegajo);

        this.add(formEliminarMedico);
    }

    @Override
    public void agregarFuncionesBotones() {
        jButtonSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Medico medico = new Medico();
                    medicoService = new MedicoService();
                    medico.setLegajo(Integer.parseInt(jTextFieldLegajo.getText()));
                    medicoService.eliminar(medico);
                    JOptionPane.showMessageDialog(null,"Medico eliminado");
                    adminForm = new AdminForm(panel);
                    panel.mostrar(adminForm.getForm());
                } catch (ServiceException ex) {
                    JOptionPane.showMessageDialog(null,"Error al eliminar medico");
                } catch (NumberFormatException ex){
                    JOptionPane.showMessageDialog(null,"Ingrese un numero");
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

    @Override
    public void diseño() {
        formEliminarMedico.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        formEliminarMedico.setBackground(Color.LIGHT_GRAY);
        formEliminarMedico.setPreferredSize(new Dimension(220, 80));
        formEliminarMedico.setOpaque(true);

        /*jLabelLegajo.setForeground(Color.DARK_GRAY);

        jButtonSend.setBackground(Color.GREEN);
        jButtonSend.setForeground(Color.WHITE);
        jButtonSend.setFont(new Font("Arial", Font.BOLD, 12));

        jButtonExit.setBackground(Color.RED);
        jButtonExit.setForeground(Color.WHITE);
        jButtonExit.setFont(new Font("Arial", Font.BOLD, 12));*/
    }

    @Override
    public JPanel getForm() {
        return formEliminarMedico;
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

            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
                    throws BadLocationException {
                if (string.matches("\\d*")) { // Solo permitir dígitos
                    super.insertString(fb, offset, string, attr);
                }
            }
        });
    }
}
