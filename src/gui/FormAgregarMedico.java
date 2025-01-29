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

public class FormAgregarMedico extends JPanel implements Formulario,DiseñoForm, SetFormatoJTextField {
    FondoConImagen FormAgregarMedico;
    MedicoService medicoService;
    JPanel formAgregarMedico;
    JLabel jLabelNombre;
    JTextField  jTextFieldNombre;
    JButton jButtonSend;
    JButton jButtonExit;
    JLabel jLabelApellido;
    JTextField jTextFieldApellido;
    JLabel jLabelDni;
    JTextField jTextFieldDni;
    JLabel jLabelLegajo;
    JTextField jTextFieldLegajo;
    PanelManager panel;


    public FormAgregarMedico(PanelManager panel) {
        this.panel = panel;
        crearForm();
        agregarForm();
        agregarFuncionesBotones();
        diseño();
    }



    @Override
    public void diseño() {

        formAgregarMedico.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        formAgregarMedico.setBackground(Color.LIGHT_GRAY);
        formAgregarMedico.setPreferredSize(new Dimension(400, 200));
        formAgregarMedico.setOpaque(true);

        /*jLabelNombre.setForeground(Color.DARK_GRAY);
        jLabelApellido.setForeground(Color.DARK_GRAY);
        jLabelDni.setForeground(Color.DARK_GRAY);
        jLabelLegajo.setForeground(Color.DARK_GRAY);

        jButtonSend.setBackground(Color.GREEN);
        jButtonSend.setForeground(Color.WHITE); // Texto en blanco
        jButtonSend.setFont(new Font("Arial", Font.BOLD, 12));

        jButtonExit.setBackground(Color.RED);
        jButtonExit.setForeground(Color.WHITE); // Texto en blanco
        jButtonExit.setFont(new Font("Arial", Font.BOLD, 12));

        jTextFieldNombre.setBackground(Color.WHITE);
        jTextFieldApellido.setBackground(Color.WHITE);
        jTextFieldDni.setBackground(Color.WHITE);
        jTextFieldLegajo.setBackground(Color.WHITE);*/

    }

    @Override
    public void crearForm()  {
        formAgregarMedico= new FondoConImagen("src/Imagenes/Fondo.jpg");

        medicoService = new MedicoService();
        formAgregarMedico = new JPanel();
        formAgregarMedico.setLayout(new GridLayout(5, 2, 5, 5)); // 5 filas, 2 columnas con espaciado###### aquiii

        jLabelNombre = new JLabel("Nombre:");
        jTextFieldNombre = new JTextField();

        jLabelApellido = new JLabel("Apellido:");
        jTextFieldApellido = new JTextField();

        jLabelDni = new JLabel("DNI:");
        jTextFieldDni = new JTextField();
        setFormatoJTextField(jTextFieldDni);

        jLabelLegajo = new JLabel("Legajo:");
        jTextFieldLegajo = new JTextField();
        setFormatoJTextField(jTextFieldLegajo);

        // Botones
        jButtonSend = new JButton("Enviar");
        jButtonExit = new JButton("Salir");


    }

    @Override
    public void agregarForm() {
        formAgregarMedico.add(jLabelDni);
        formAgregarMedico.add(jTextFieldDni);
        formAgregarMedico.add(jLabelNombre);
        formAgregarMedico.add(jTextFieldNombre);
        formAgregarMedico.add(jLabelApellido);
        formAgregarMedico.add(jTextFieldApellido);
        formAgregarMedico.add(jLabelLegajo);
        formAgregarMedico.add(jTextFieldLegajo);
        formAgregarMedico.add(jButtonExit);
        formAgregarMedico.add(jButtonSend);



    }

    @Override
    public void agregarFuncionesBotones() {

        jButtonExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AdminForm adminForm = null;
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
                String legajoText = jTextFieldLegajo.getText().trim();

                if (dniText.isEmpty() || nombreText.isEmpty() || apellidoText.isEmpty() || legajoText.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Ingrese todos los campos");
                } else {
                    try {
                        Medico medico = new Medico();
                        medico.setDni(Integer.parseInt(dniText));
                        medico.setNombre(nombreText);
                        medico.setApellido(apellidoText);
                        medico.setLegajo(Integer.parseInt(legajoText));

                        medicoService.guardar(medico);
                        JOptionPane.showMessageDialog(null, "Guardado con exito");
                    } catch (ServiceException ex) {
                        JOptionPane.showMessageDialog(null, "Reintente Medico existente");
                        throw new RuntimeException(ex);
                    }
                    AdminForm adminForm=null;
                    adminForm  = new AdminForm(panel);
                    panel.mostrar(adminForm.getForm());
                }
            }
        });
    }

    @Override
    public JPanel getForm() {
        return formAgregarMedico;
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




