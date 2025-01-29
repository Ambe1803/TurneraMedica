package gui;

import Service.adminService;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class FormLoginAdmin {
    FondoConImagen formLoginAdmin;
    PanelManager panel;
    JButton jButtonLoginAdmin;
    JButton jButtonExit;
    JTextField jTextFieldUser;
    JPasswordField jPasswordField;
    JLabel jLabelUser;
    JLabel jLabelPassword;
    adminService adminService;

    public FormLoginAdmin(PanelManager panel) {
        this.panel = panel;
        crearForm();
        agregarForm();
        agregarFuncionesBotones();
        diseño();
    }

    public void crearForm() {
        formLoginAdmin = new FondoConImagen("src/Imagenes/Fondo.jpg");
        formLoginAdmin.setLayout(new GridLayout(3, 2, 10, 10)); // Añadí espacio entre componentes

        // Etiquetas
        jLabelUser = new JLabel("Usuario:");
        jLabelUser.setForeground(Color.WHITE);
        jLabelUser.setFont(new Font("Arial", Font.BOLD, 14));

        jLabelPassword = new JLabel("Contraseña:");
        jLabelPassword.setForeground(Color.WHITE);
        jLabelPassword.setFont(new Font("Arial", Font.BOLD, 14));

        // Campos de entrada
        jTextFieldUser = new JTextField();
        jTextFieldUser.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        jTextFieldUser.setPreferredSize(new Dimension(150, 25));

        jPasswordField = new JPasswordField();
        jPasswordField.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        jPasswordField.setPreferredSize(new Dimension(150, 25));

        // Botones
        jButtonLoginAdmin = new JButton("Ingresar");
        jButtonLoginAdmin.setBackground(new Color(0, 128, 0)); // Verde oscuro
        jButtonLoginAdmin.setForeground(Color.black);
        jButtonLoginAdmin.setFocusPainted(false);

        jButtonExit = new JButton("Salir");
        jButtonExit.setBackground(new Color(178, 34, 34)); // Rojo oscuro
        jButtonExit.setForeground(Color.black);
        jButtonExit.setFocusPainted(false);
    }
    public void agregarForm() {
        formLoginAdmin.add(jLabelUser);
        formLoginAdmin.add(jTextFieldUser);
        formLoginAdmin.add(jLabelPassword);
        formLoginAdmin.add(jPasswordField);
        formLoginAdmin.add(jButtonExit);
        formLoginAdmin.add(jButtonLoginAdmin);
    }

    public void agregarFuncionesBotones() {
        jButtonExit.addActionListener(e -> {
            FormSeleccionUsuario formSeleccionUsuario = new FormSeleccionUsuario(panel);
            panel.mostrar(formSeleccionUsuario.getForm());
        });

        jButtonLoginAdmin.addActionListener(e -> {
            adminService = new adminService();
            String user = jTextFieldUser.getText();
            String password = String.valueOf(jPasswordField.getPassword());
            try {
                if(adminService.login(user,password)){
                    AdminForm adminForm = new AdminForm(panel);
                    panel.mostrar(adminForm.getForm());
                }else{
                    JOptionPane.showMessageDialog(null,"Usuario o contraseña incorrectos");
                }
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(null,exception.getMessage());
            }
        });
    }

    public void diseño() {
        formLoginAdmin.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Espaciado alrededor del panel
        formLoginAdmin.setBackground(Color.lightGray); // Fondo gris claro
        formLoginAdmin.setPreferredSize(new Dimension(400, 150));
        formLoginAdmin.setOpaque(true);
    }

    public JPanel getForm() {
        return formLoginAdmin;
    }
}
