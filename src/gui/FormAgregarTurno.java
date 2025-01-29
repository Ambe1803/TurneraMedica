package gui;

import Service.MedicoService;
import Service.PacienteService;
import Service.ServiceException;
import clases.Medico;
import clases.Paciente;
import clases.Turno;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class FormAgregarTurno extends JPanel implements Formulario, DiseñoForm {

    FondoConImagen formAgregarTurno;
    FormHora formHora;
    JLabel jLabelLegajoMedico;
    JComboBox<String> jComboBoxLegajoMedico;
    JButton jButtonSend;
    JButton jButtonExit;
    JLabel jLabelDniPaciente;
    JComboBox<String> jComboBoxDniPaciente;
    JLabel jLabelFecha;
    JFormattedTextField jTextFieldFecha;
    JLabel jLabelCosto;
    JTextField jTextFieldCosto;
    PanelManager panel;
    MedicoService medicoService;
    PacienteService pacienteService;
    Turno turno;

    public FormAgregarTurno(PanelManager panel) throws ServiceException, ParseException {
        this.panel = panel;
        crearForm();
        agregarForm();
        agregarFuncionesBotones();
        diseño();
    }

    @Override
    public void crearForm() throws ParseException, ServiceException {
        medicoService = new MedicoService();
        pacienteService = new PacienteService();

        formAgregarTurno = new FondoConImagen("src/Imagenes/Fondo.jpg");
        formAgregarTurno.setLayout(new GridLayout(5,2));
        // Creación de etiquetas y campos
        jLabelLegajoMedico = new JLabel("Legajo Médico:");
        jComboBoxLegajoMedico = new JComboBox<>();
        jLabelDniPaciente = new JLabel("DNI Paciente:");
        jComboBoxDniPaciente = new JComboBox<>();

        jLabelFecha = new JLabel("Fecha ");
        jTextFieldFecha = new JFormattedTextField(createMaskFormatter("####/##/##"));
        jTextFieldFecha.setColumns(10);
        jTextFieldFecha.setFocusLostBehavior(JFormattedTextField.COMMIT);

        jLabelCosto = new JLabel("Costo:");
        jTextFieldCosto = new JTextField();

        jButtonSend = new JButton("Enviar");
        jButtonExit = new JButton("Salir");

        jTextFieldFecha.setText("yyyy/MM/dd");

        ArrayList<Medico> medicos = fillarrayMedicos();
        for (Medico m : medicos) {
            jComboBoxLegajoMedico.addItem(m.getLegajo() + "-" + m.getNombre() + " " + m.getApellido());
        }
        ArrayList<Paciente> pacientes = fillarrayPacientes();
        jComboBoxDniPaciente = new JComboBox();
        for (Paciente p : pacientes) {
            jComboBoxDniPaciente.addItem(p.getDni() + "-" + p.getNombre() + " " + p.getApellido());
        }
        // Cargar datos en los combo boxes

    }


    @Override
    public void agregarForm() {
        // Agregar los componentes al panel
        formAgregarTurno.add(jLabelLegajoMedico);
        formAgregarTurno.add(jComboBoxLegajoMedico);
        formAgregarTurno.add(jLabelDniPaciente);
        formAgregarTurno.add(jComboBoxDniPaciente);
        formAgregarTurno.add(jLabelFecha);
        formAgregarTurno.add(jTextFieldFecha);
        formAgregarTurno.add(jLabelCosto);
        formAgregarTurno.add(jTextFieldCosto);
        formAgregarTurno.add(jButtonExit);
        formAgregarTurno.add(jButtonSend);


    }

    @Override
    public void agregarFuncionesBotones() {
        jButtonExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AdminForm adminForm = new AdminForm(panel);
                panel.mostrar(adminForm.getForm());
            }
        });


        jButtonSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    turno = new Turno();
                    turno.setLegajoMedico(Integer.parseInt(jComboBoxLegajoMedico.getSelectedItem().toString().split("-")[0]));
                    turno.setDniPaciente(Integer.parseInt(jComboBoxDniPaciente.getSelectedItem().toString().split("-")[0]));

                    String fechaText = jTextFieldFecha.getText().trim();
                    String costoText = jTextFieldCosto.getText().trim();

                    if (costoText.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Por favor, ingrese el costo");
                    } else {
                        LocalDate currentDate = LocalDate.now();
                        LocalDate selectedDate = LocalDate.parse(fechaText, DateTimeFormatter.ofPattern("yyyy/MM/dd"));

                        if (selectedDate.isAfter(currentDate)) {
                            turno.setFecha(fechaText);
                            turno.setCosto(Double.parseDouble(costoText));
                            formHora = new FormHora(panel, turno);
                            panel.mostrar(formHora.getForm());
                        } else {
                            JOptionPane.showMessageDialog(null, "Por favor, ingrese una fecha posterior a la actual");
                        }
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Error en el formato de los campos numéricos");
                } catch (ServiceException ex) {
                    JOptionPane.showMessageDialog(null, "Error en el servicio: " + ex.getMessage());
                } catch (DateTimeParseException ex) {
                    JOptionPane.showMessageDialog(null, "Error en el formato de la fecha");
                }    }
        });
    }

    @Override
    public JPanel getForm() {
        return formAgregarTurno;
    }

    public ArrayList<Medico> fillarrayMedicos() throws ServiceException {
        ArrayList<Medico> medicos = new ArrayList<Medico>();
        medicos = medicoService.buscarTodos();
        return medicos;
    }
    public ArrayList<Paciente> fillarrayPacientes() throws ServiceException {
        ArrayList<Paciente> pacientes = new ArrayList<Paciente>();
        Paciente paciente = new Paciente();
        pacientes = pacienteService.buscarTodos();
        return pacientes;
    }
    public MaskFormatter createMaskFormatter(String mask){
        MaskFormatter formatter = null;
        try {
            formatter = new MaskFormatter(mask);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        formatter.setPlaceholderCharacter('_');
        return formatter;
    }

    @Override
    public void diseño() {
        formAgregarTurno.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        formAgregarTurno.setBackground(Color.LIGHT_GRAY);
        formAgregarTurno.setPreferredSize(new Dimension(450, 175));
        formAgregarTurno.setOpaque(true);

        /*jLabelLegajoMedico.setForeground(Color.DARK_GRAY);
        jLabelDniPaciente.setForeground(Color.DARK_GRAY);
        jLabelFecha.setForeground(Color.DARK_GRAY);
        jLabelCosto.setForeground(Color.DARK_GRAY);

        jButtonSend.setBackground(Color.GREEN);
        jButtonSend.setForeground(Color.WHITE);
        jButtonSend.setFont(new Font("Arial", Font.BOLD, 12));

        jButtonExit.setBackground(Color.RED);
        jButtonExit.setForeground(Color.WHITE);
        jButtonExit.setFont(new Font("Arial", Font.BOLD, 12));*/
    }
}
