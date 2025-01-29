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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FormEliminarPaciente extends JPanel implements Formulario, DiseñoForm, SetFormatoJTextField {
    PacienteService medicoService;
    JPanel formEliminarPaciente;
    JButton jButtonSend;
    JButton jButtonExit;
    JLabel jLabelDni;
    JTextField jTextFieldDni;
    PanelManager panel;
    AdminForm adminForm;

    public FormEliminarPaciente(PanelManager panel) {
        this.panel = panel;
        crearForm();
        agregarForm();
        agregarFuncionesBotones();
        diseño();
    }

    @Override
    public void crearForm() {
        formEliminarPaciente  = new FondoConImagen("src/Imagenes/Fondo.jpg");
        medicoService = new PacienteService();
        formEliminarPaciente = new JPanel(new GridLayout(2, 2, 5, 5));

        jLabelDni = new JLabel("DNI:");
        jTextFieldDni = new JTextField();
        jButtonSend = new JButton("Enviar");
        jButtonExit = new JButton("Salir");
    }

    @Override
    public void agregarForm() {
        formEliminarPaciente.add(jLabelDni);
        formEliminarPaciente.add(jTextFieldDni);
        formEliminarPaciente.add(jButtonExit);
        formEliminarPaciente.add(jButtonSend);
        setFormatoJTextField(jTextFieldDni);

        this.add(formEliminarPaciente);
    }

    @Override
    public void agregarFuncionesBotones() {
        jButtonSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Paciente paciente = new Paciente();
                    paciente.setDni(Integer.parseInt(jTextFieldDni.getText()));
                    medicoService.eliminar(paciente);
                    JOptionPane.showMessageDialog(null,"Paciente eliminado");
                    adminForm = new AdminForm(panel);
                    panel.mostrar(adminForm.getForm());
                } catch (ServiceException ex) {
                    JOptionPane.showMessageDialog(null,"Error al eliminar paciente");
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
        formEliminarPaciente.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        formEliminarPaciente.setBackground(Color.LIGHT_GRAY);
        formEliminarPaciente.setPreferredSize(new Dimension(220, 80));
        formEliminarPaciente.setOpaque(true);

        /*jLabelDni.setForeground(Color.DARK_GRAY);

        jButtonSend.setBackground(Color.GREEN);
        jButtonSend.setForeground(Color.WHITE);
        jButtonSend.setFont(new Font("Arial", Font.BOLD, 12));

        jButtonExit.setBackground(Color.RED);
        jButtonExit.setForeground(Color.WHITE);
        jButtonExit.setFont(new Font("Arial", Font.BOLD, 12));*/
    }

    @Override
    public JPanel getForm() {
        return formEliminarPaciente;
    }

    @Override
    public void setFormatoJTextField(JTextField textField) {
        ((AbstractDocument) textField.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                    throws BadLocationException {
                String newText = fb.getDocument().getText(0, fb.getDocument().getLength()) + text;
                if (newText.matches("\\d*")) { // Solo permitir dígitos
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
