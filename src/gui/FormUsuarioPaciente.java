package gui;

import Service.ServiceException;
import Service.TurnoService;
import clases.Turno;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class FormUsuarioPaciente extends JPanel implements Formulario, DiseñoForm, SetFormatoJTextField {
    PanelManager panel;
    JPanel formUsuarioPaciente;
    FormSeleccionUsuario formSeleccionUsuario;
    FormTurnosPaciente formTurnosPaciente;
    JLabel jLabelDni;
    JTextField jTextFieldDni;
    JButton jButtonSend;
    JButton jButtonExit;
    TurnoService turnoService;

    public FormUsuarioPaciente(PanelManager panel) {
        this.panel = panel;
        crearForm();
        agregarForm();
        agregarFuncionesBotones();
        diseño();
    }

    @Override
    public void crearForm() {
        turnoService = new TurnoService();
        formUsuarioPaciente = new JPanel(new GridLayout(2, 2, 10, 10)); // Espacio entre componentes

        // Etiquetas y campos de texto
        jLabelDni = new JLabel("Ingrese su DNI:");


        jTextFieldDni = new JFormattedTextField();

        setFormatoJTextField(jTextFieldDni);

        // Botones
        jButtonSend = new JButton("Enviar");


        jButtonExit = new JButton("Salir");

    }

    @Override
    public void agregarForm() {
        formUsuarioPaciente.add(jLabelDni);
        formUsuarioPaciente.add(jTextFieldDni);
        formUsuarioPaciente.add(jButtonExit);
        formUsuarioPaciente.add(jButtonSend);
    }

    @Override
    public void agregarFuncionesBotones() {
        jButtonExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                formSeleccionUsuario = new FormSeleccionUsuario(panel);
                panel.mostrar(formSeleccionUsuario.getForm());
            }
        });
        jButtonSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ArrayList<Turno> turnos = turnoService.buscarTurnosPaciente(Integer.parseInt(jTextFieldDni.getText()));
                    if (turnos.size() == 0) {
                        JOptionPane.showMessageDialog(null, "No hay turnos disponibles");
                    } else {
                        formTurnosPaciente = new FormTurnosPaciente(panel, turnos);
                        panel.mostrar(formTurnosPaciente.getForm());
                    }
                } catch (ServiceException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    @Override
    public JPanel getForm() {
        return formUsuarioPaciente;
    }
    public NumberFormatter getNumberFormatter(){
        NumberFormatter formatter = new NumberFormatter();
        formatter.setValueClass(Integer.class);
        formatter.setMinimum(0);
        formatter.setMaximum(99999999); // Máximo 8 dígitos
        return formatter;
    }

    @Override
    public void diseño() {
        formUsuarioPaciente.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Margen alrededor del formulario
        formUsuarioPaciente.setBackground(new Color(245, 245, 245)); // Fondo gris claro
        formUsuarioPaciente.setPreferredSize(new Dimension(300, 100));
        formUsuarioPaciente.setOpaque(true);
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
