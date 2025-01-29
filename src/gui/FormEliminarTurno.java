package gui;

import Service.MedicoService;
import Service.PacienteService;
import Service.ServiceException;
import Service.TurnoService;
import clases.Medico;
import clases.Paciente;
import clases.Turno;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class FormEliminarTurno extends JPanel implements Formulario, DiseñoForm {
    TurnoService turnoService;
    MedicoService medicoService;
    PacienteService pacienteService;
    JPanel formEliminarTurno;
    JButton jButtonSend;
    JButton jButtonExit;
    JComboBox<String> jComboBoxLegajoMedico;
    JComboBox<String> jComboBoxDniPaciente;
    JLabel jLabelDni;
    JLabel jLabelLegajo;
    PanelManager panel;
    AdminForm adminForm;
    FormEliminarTurnoFecha formEliminarTurnoFecha;
    ArrayList<Turno> turnos;

    public FormEliminarTurno(PanelManager panel) throws ServiceException {
        this.panel = panel;
        crearForm();
        agregarForm();
        agregarFuncionesBotones();
        diseño();
    }

    @Override
    public void crearForm() throws ServiceException {
        turnoService = new TurnoService();
        formEliminarTurno = new JPanel(new GridLayout(3, 2, 5, 5));

        jButtonSend = new JButton("Buscar Turnos");
        jButtonExit = new JButton("Salir");

        jComboBoxDniPaciente = new JComboBox<>();
        jComboBoxLegajoMedico = new JComboBox<>();

        jLabelDni = new JLabel("DNI Paciente:");
        jLabelLegajo = new JLabel("Legajo Médico:");

        ArrayList<Medico> medicos = fillarrayMedicos();
        for (Medico m : medicos) {
            jComboBoxLegajoMedico.addItem(m.getLegajo() + "-" + m.getNombre() + " " + m.getApellido());
        }
        ArrayList<Paciente> pacientes = fillarrayPacientes();
        jComboBoxDniPaciente = new JComboBox();
        for (Paciente p : pacientes) {
            jComboBoxDniPaciente.addItem(p.getDni() + "-" + p.getNombre() + " " + p.getApellido());
        }
    }


    @Override
    public void agregarForm() {
        formEliminarTurno.add(jLabelDni);
        formEliminarTurno.add(jComboBoxDniPaciente);
        formEliminarTurno.add(jLabelLegajo);
        formEliminarTurno.add(jComboBoxLegajoMedico);
        formEliminarTurno.add(jButtonExit);
        formEliminarTurno.add(jButtonSend);


    }

    @Override
    public void agregarFuncionesBotones() {
        jButtonSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    // Crear objeto Turno con el DNI del paciente y el legajo del médico seleccionados
                    Turno turno = new Turno();
                    turno.setDniPaciente(Integer.parseInt(jComboBoxDniPaciente.getSelectedItem().toString().split("-")[0]));
                    turno.setLegajoMedico(Integer.parseInt(jComboBoxLegajoMedico.getSelectedItem().toString().split("-")[0]));

                    // Buscar los turnos para ese paciente y médico
                    turnos = new ArrayList<>(turnoService.buscarTurnosPorPacienteYMedico(turno));
                } catch (ServiceException ex) {
                    JOptionPane.showMessageDialog(null, "Error al seleccionar el turno");
                }
                if (turnos.size() == 0) {
                    JOptionPane.showMessageDialog(null, "No hay turnos para eliminar");
                } else {
                    formEliminarTurnoFecha = new FormEliminarTurnoFecha(panel, turnos);
                    panel.mostrar(formEliminarTurnoFecha.getForm());
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
        formEliminarTurno.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        formEliminarTurno.setBackground(Color.LIGHT_GRAY);
        formEliminarTurno.setPreferredSize(new Dimension(450, 120));
        formEliminarTurno.setOpaque(true);

        /*jLabelDni.setForeground(Color.DARK_GRAY);
        jLabelLegajo.setForeground(Color.DARK_GRAY);

        jButtonSend.setBackground(Color.GREEN);
        jButtonSend.setForeground(Color.WHITE);
        jButtonSend.setFont(new Font("Arial", Font.BOLD, 12));

        jButtonExit.setBackground(Color.RED);
        jButtonExit.setForeground(Color.WHITE);
        jButtonExit.setFont(new Font("Arial", Font.BOLD, 12));*/
    }

    @Override
    public JPanel getForm() {
        return formEliminarTurno;
    }

    public ArrayList<Paciente> fillarrayPacientes() throws ServiceException {
        ArrayList<Paciente> pacientes = new ArrayList<Paciente>();
        pacienteService = new PacienteService();
        pacientes = pacienteService.buscarTodos();
        return pacientes;
    }

    public ArrayList<Medico> fillarrayMedicos() throws ServiceException {
        ArrayList<Medico> medicos = new ArrayList<Medico>();
        medicoService = new MedicoService();
        medicos = medicoService.buscarTodos();
        return medicos;
    }
}
