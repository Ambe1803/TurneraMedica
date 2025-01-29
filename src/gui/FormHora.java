package gui;

import Service.ServiceException;
import Service.TurnoService;
import clases.Turno;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.ArrayList;

public class FormHora extends JPanel implements Formulario, DiseñoForm {
    FormAgregarTurno formAgregarTurno;
    AdminForm adminForm;
    JPanel formHora;
    TurnoService turnoService;
    JLabel jLabelHora;
    JComboBox jComboBoxHora;
    JButton jButtonSend;
    JButton jButtonExit;
    PanelManager panel;
    Turno turno;




    public FormHora(PanelManager panel, Turno turno) throws ServiceException {
        this.panel = panel;
        this.turno = turno;
        System.out.println("hecho");
        crearForm();
        agregarForm();
        agregarFuncionesBotones();
        diseño();
    }

    @Override
    public void crearForm() throws ServiceException {
        turnoService = new TurnoService();
        formHora = new JPanel();
        formHora.setLayout(new GridLayout(2, 2));

        // Configuración de la etiqueta
        jLabelHora = new JLabel("Hora");
        jLabelHora.setForeground(Color.BLUE); // Color del texto de la etiqueta

        // Configuración del ComboBox
        jComboBoxHora = new JComboBox<>(model(turno.fillarrayHoras()));
        jComboBoxHora.setBackground(Color.WHITE);
        jComboBoxHora.setForeground(Color.DARK_GRAY);

        // Configuración de los botones
        jButtonSend = new JButton("Enviar");
        jButtonSend.setBackground(new Color(34, 139, 34)); // Verde oscuro
        jButtonSend.setForeground(Color.WHITE);

        jButtonExit = new JButton("Salir");
        jButtonExit.setBackground(new Color(220, 20, 60)); // Rojo oscuro
        jButtonExit.setForeground(Color.WHITE);

        ArrayList<String> hs = turno.fillarrayHoras();
        for (String hora : hs) {
            jComboBoxHora.addItem(hora);
        }
    }
    @Override
    public void agregarForm() {
        formHora.add(jLabelHora);
        formHora.add(jComboBoxHora);
        formHora.add(jButtonExit);
        formHora.add(jButtonSend);
    }

    @Override
    public void agregarFuncionesBotones() {
        // Acción del botón Enviar
        jButtonSend.addActionListener(new ActionListener()  {
            @Override
            public void actionPerformed(ActionEvent e) {
                Turno elemento = new Turno();
                elemento.setLegajoMedico(turno.getLegajoMedico());
                elemento.setDniPaciente(turno.getDniPaciente());
                elemento.setFecha(turno.getFecha() + " " + jComboBoxHora.getSelectedItem().toString());
                elemento.setCosto(turno.getCosto());

                try {
                    turnoService.guardar(elemento);
                    JOptionPane.showMessageDialog(null, "Se guardó correctamente");
                    // Volver al formulario de administración después de guardar
                    panel.mostrar(new AdminForm(panel).getForm());
                } catch (ServiceException ex) {
                    throw new RuntimeException(ex);
                }
                adminForm = new AdminForm(panel);
                panel.mostrar(adminForm.getForm());
            }
        });

        // Acción del botón Salir
        jButtonExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    formAgregarTurno = new FormAgregarTurno(panel);
                } catch (ServiceException ex) {
                    throw new RuntimeException(ex);
                } catch (ParseException ex) {
                    throw new RuntimeException(ex);
                }
                panel.mostrar(formAgregarTurno.getForm());
            }
        });
    }

    public DefaultComboBoxModel<String> model(ArrayList<String> horarios) {
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        model.addAll(horarios); // Agrega todos los elementos del array a la
        return model;
    }

    @Override
    public JPanel getForm() {
        return formHora;
    }

    @Override
    public void diseño() {
        formHora.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        formHora.setBackground(new Color(245, 245, 245)); // Color de fondo gris claro
        formHora.setPreferredSize(new Dimension(220, 80));
        formHora.setOpaque(true);
    }


}
