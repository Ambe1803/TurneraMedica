package gui;

import Service.ServiceException;
import Service.TurnoService;
import clases.Turno;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.ArrayList;


public class FormUsuarioMedico extends JPanel implements Formulario, DiseñoForm, SetFormatoJTextField {
    PanelManager panel;
    JPanel formUsuarioMedico;
    FormSeleccionUsuario formSeleccionUsuario;
    FormTurnosMedicos formTurnosMedicos;
    TurnoService turnoService;
    JLabel jLabelLegajo;
    JTextField jTextFieldLegajo;
    JLabel jLabelFecha;
    JFormattedTextField jTextFieldFecha;
    JButton jButtonSend;
    JButton jButtonExit;

    public FormUsuarioMedico(PanelManager panel) {
        this.panel = panel;
        crearForm();
        agregarForm();
        agregarFuncionesBotones();
        diseño();
    }

    @Override
    public void crearForm() {
        turnoService = new TurnoService();
        formUsuarioMedico = new JPanel(new GridLayout(3, 2, 10, 10)); // Espacio entre componentes

        // Etiquetas
        jLabelLegajo = new JLabel("Ingrese su legajo:");
        jLabelFecha = new JLabel("Fecha (AAAA/MM/DD):");


        // Campos de texto
        jTextFieldLegajo = new JTextField();
        jTextFieldFecha = new JFormattedTextField(createMaskFormatter("####/##/##"));


        // Botones
        jButtonSend = new JButton("Enviar");
        jButtonExit = new JButton("Salir");

    }

    @Override
    public void agregarForm() {
        formUsuarioMedico.add(jLabelLegajo);
        formUsuarioMedico.add(jTextFieldLegajo);
        formUsuarioMedico.add(jLabelFecha);
        formUsuarioMedico.add(jTextFieldFecha);
        formUsuarioMedico.add(jButtonExit);
        formUsuarioMedico.add(jButtonSend);

        setFormatoJTextField(jTextFieldLegajo);
    }

    @Override
    public void agregarFuncionesBotones(){
        jButtonSend.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                String fechaText = jTextFieldFecha.getText().trim();
                String legajoText = jTextFieldLegajo.getText().trim();
                try {
                    ArrayList<Turno> turnos = turnoService.buscarTurnosMedico(fechaText, Integer.parseInt(legajoText));
                    if (turnos.size() == 0) {
                        JOptionPane.showMessageDialog(null, "No hay turnos disponibles");
                    } else {
                        formTurnosMedicos = new FormTurnosMedicos(panel, turnos);
                        panel.mostrar(formTurnosMedicos.getForm());
                    }
                } catch (ServiceException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        jButtonExit.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                formSeleccionUsuario = new FormSeleccionUsuario(panel);
                panel.mostrar(formSeleccionUsuario.getForm());
            }
        });
    }

    @Override
    public JPanel getForm() {
        return formUsuarioMedico;
    }

    public MaskFormatter createMaskFormatter(String mask){
        MaskFormatter formatter = null;
        try {
            formatter = new MaskFormatter(mask);
            formatter.setPlaceholderCharacter('_');
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formatter;
    }

    @Override
    public void diseño() {
        formUsuarioMedico.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Margen alrededor del formulario
        formUsuarioMedico.setBackground(new Color(245, 245, 245)); // Fondo gris claro
        formUsuarioMedico.setPreferredSize(new Dimension(300, 150));
        formUsuarioMedico.setOpaque(true);
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
