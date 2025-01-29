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

public class FormModificarTurno implements Formulario, DiseñoForm {
    TurnoService turnoService;
    MedicoService medicoService;
    PacienteService pacienteService;
    JPanel formModificarTurno;
    JButton jButtonSend;
    JButton jButtonExit;
    JComboBox<String> jComboBoxLegajoMedico;
    JComboBox<String> jComboBoxDniPaciente;
    JLabel jLabelDni;
    JLabel jLabelLegajo;
    PanelManager panel;
    AdminForm adminForm;
    FormModificarTurnoFecha formModificarTurnoFecha;
    ArrayList<Turno> turnos;

    public FormModificarTurno(PanelManager panel) throws ServiceException {
        this.panel = panel;
        crearForm();
        agregarForm();
        agregarFuncionesBotones();
        diseño();
    }

    @Override
    public void crearForm() throws ServiceException {
        turnoService = new TurnoService();
        formModificarTurno = new JPanel();
        formModificarTurno.setLayout(new GridLayout(3, 2));

        // Botones y ComboBoxes
        jButtonSend = new JButton("Enviar");
        jButtonExit = new JButton("Salir");
        jComboBoxDniPaciente = new JComboBox<>();
        jComboBoxLegajoMedico = new JComboBox<>();

        // Etiquetas
        jLabelDni = new JLabel("DNI del paciente");
        jLabelLegajo = new JLabel("Legajo del médico");

        // Llenar ComboBox de médicos
        ArrayList<Medico> medicos = fillarrayMedicos();
        for (Medico m : medicos) {
            jComboBoxLegajoMedico.addItem(m.getLegajo() + "-" + m.getNombre() + " " + m.getApellido());
        }

        // Llenar ComboBox de pacientes
        ArrayList<Paciente> pacientes = fillarrayPacientes();
        for (Paciente p : pacientes) {
            jComboBoxDniPaciente.addItem(p.getDni() + "-" + p.getNombre() + " " + p.getApellido());
        }
    }

    @Override
    public void agregarForm() {
        formModificarTurno.add(jLabelDni);
        formModificarTurno.add(jComboBoxDniPaciente);
        formModificarTurno.add(jLabelLegajo);
        formModificarTurno.add(jComboBoxLegajoMedico);
        formModificarTurno.add(jButtonExit);
        formModificarTurno.add(jButtonSend);
    }

    @Override
    public void agregarFuncionesBotones(){
        jButtonSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Turno turno = new Turno();
                    turno.setDniPaciente(Integer.parseInt(jComboBoxDniPaciente.getSelectedItem().toString().split("-")[0]));
                    turno.setLegajoMedico(Integer.parseInt(jComboBoxLegajoMedico.getSelectedItem().toString().split("-")[0]));
                    turnos=new ArrayList<Turno>(turnoService.buscarTurnosPorPacienteYMedico(turno));
                } catch (ServiceException ex) {
                    JOptionPane.showMessageDialog(null,"Error al seleccionar el turno");
                }
                if (turnos.size()==0){
                    JOptionPane.showMessageDialog(null,"No hay turnos para modificar");
                }else{
                    formModificarTurnoFecha = new FormModificarTurnoFecha(panel,turnos);
                    panel.mostrar(formModificarTurnoFecha.getForm());
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
        formModificarTurno.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        formModificarTurno.setBackground(Color.lightGray);
        formModificarTurno.setPreferredSize(new Dimension(450, 120));
        formModificarTurno.setOpaque(true);
    }

    @Override
    public JPanel getForm(){
        return formModificarTurno;
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
