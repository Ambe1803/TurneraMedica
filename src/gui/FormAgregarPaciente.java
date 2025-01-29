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

public class FormAgregarPaciente extends JPanel implements Formulario, DiseñoForm, SetFormatoJTextField {


    PacienteService pacienteService;
    FondoConImagen  formAgregarPaciente;
    JLabel jLabelNombre;
    JTextField jTextFieldNombre;
    JButton jButtonSend;
    JButton jButtonExit;
    JLabel jLabelApellido;
    JTextField jTextFieldApellido;
    JLabel jLabelDni;
    JTextField jTextFieldDni;
    JLabel jLabelO_Social;
    JTextField jTextFieldO_Social;
    PanelManager panel;

    public FormAgregarPaciente(PanelManager panel) {
        this.panel = panel;
        crearForm();
        agregarForm();
        agregarFuncionesBotones();
        diseño();
    }

    @Override
    public void diseño() {
        formAgregarPaciente.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        formAgregarPaciente.setBackground(Color.LIGHT_GRAY);
        formAgregarPaciente.setPreferredSize(new Dimension(400, 200));
        formAgregarPaciente.setOpaque(true);

        /*jLabelNombre.setForeground(Color.DARK_GRAY);
        jLabelApellido.setForeground(Color.DARK_GRAY);
        jLabelDni.setForeground(Color.DARK_GRAY);
        jLabelCodObraSocial.setForeground(Color.DARK_GRAY);

        jButtonSend.setBackground(Color.GREEN);
        jButtonSend.setForeground(Color.WHITE);
        jButtonSend.setFont(new Font("Arial", Font.BOLD, 12));

        jButtonExit.setBackground(Color.RED);
        jButtonExit.setForeground(Color.WHITE);
        jButtonExit.setFont(new Font("Arial", Font.BOLD, 12));

        jTextFieldNombre.setBackground(Color.WHITE);
        jTextFieldApellido.setBackground(Color.WHITE);
        jTextFieldDni.setBackground(Color.WHITE);
        jTextFieldCodObraSocial.setBackground(Color.WHITE);*/
    }

    @Override
    public void crearForm() {
        pacienteService = new PacienteService();
        formAgregarPaciente = new FondoConImagen("src/Imagenes/Fondo.jpg");
        formAgregarPaciente.setLayout(new GridLayout(5, 2, 5, 5));

        // Creación de etiquetas y campos de texto
        jLabelNombre = new JLabel("Nombre:");
        jTextFieldNombre = new JTextField();

        jLabelApellido = new JLabel("Apellido:");
        jTextFieldApellido = new JTextField();

        jLabelDni = new JLabel("DNI:");
        jTextFieldDni = new JTextField();
        setFormatoJTextField(jTextFieldDni);  // Limitar a números

        jLabelO_Social = new JLabel("Código Obra Social:");
        jTextFieldO_Social = new JTextField();
        setFormatoJTextField(jTextFieldO_Social);  // Limitar a números

        // Botones
        jButtonSend = new JButton("Enviar");
        jButtonExit = new JButton("Salir");
    }

    @Override
    public void agregarForm() {
        formAgregarPaciente.add(jLabelDni);
        formAgregarPaciente.add(jTextFieldDni);
        formAgregarPaciente.add(jLabelNombre);
        formAgregarPaciente.add(jTextFieldNombre);
        formAgregarPaciente.add(jLabelApellido);
        formAgregarPaciente.add(jTextFieldApellido);
        formAgregarPaciente.add(jLabelO_Social);
        formAgregarPaciente.add(jTextFieldO_Social);
        formAgregarPaciente.add(jButtonExit);
        formAgregarPaciente.add(jButtonSend);

        this.add(formAgregarPaciente);
    }

    @Override
    public void agregarFuncionesBotones() {
        jButtonExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AdminForm adminForm=null;
                adminForm = new AdminForm(panel);
                panel.mostrar(adminForm.getForm());
            }
        });

        jButtonSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String dniText = jTextFieldDni.getText().trim();
                String nombreText = jTextFieldNombre.getText().trim();
                String apellidoText = jTextFieldApellido.getText().trim();
                String O_SocialText = jTextFieldO_Social.getText().trim();

                if (dniText.isEmpty() || nombreText.isEmpty() || apellidoText.isEmpty() || O_SocialText.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos");
                } else {
                    try {
                        Paciente paciente = new Paciente();
                        paciente.setDni(Integer.parseInt(dniText));
                        paciente.setNombre(nombreText);
                        paciente.setApellido(apellidoText);
                        paciente.setO_Social(Integer.parseInt(O_SocialText));
                        pacienteService.guardar(paciente);
                        JOptionPane.showMessageDialog(null, "Se guardó correctamente");
                    } catch (ServiceException ex) {
                        JOptionPane.showMessageDialog(null, "Paciente ya existente");
                        throw new RuntimeException(ex);
                    }
                    AdminForm adminForm = new AdminForm(panel);
                    panel.mostrar(adminForm.getForm());
                }
            }
        });
    }

    @Override
    public JPanel getForm() {
        return formAgregarPaciente;
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
