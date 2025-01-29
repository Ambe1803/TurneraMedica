package DAO;

import java.sql.*;

public class DAOcreate {

    // Configuración de conexión a la base de datos H2
    private static final String DB_JDBC_DRIVER = "org.h2.Driver";
    private static final String DB_URL = "jdbc:h2:tcp://localhost/~/test";
    //private static final String DB_URL = "jdbc:h2:./database/test"; aqui estuvo el error estaba apuntando mal la direccion

    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "";


    // Método para crear la tabla Medico
    private static void createMedico() {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "CREATE TABLE IF NOT EXISTS Medico ( nombre VARCHAR(50), apellido VARCHAR(50), legajo INT PRIMARY KEY)"
             )) {
            preparedStatement.executeUpdate();
            System.out.println("Se creó la tabla Medico");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al crear la tabla Medico: " + e.getMessage());
        }
    }

    // Método para crear la tabla Paciente
    private static void createPaciente() {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "CREATE TABLE IF NOT EXISTS Paciente (dni INT PRIMARY KEY, nombre VARCHAR(50), apellido VARCHAR(50), codObrasocial INT)"
             )) {
            preparedStatement.executeUpdate();
            System.out.println("Se creó la tabla Paciente");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al crear la tabla Paciente: " + e.getMessage());
        }
    }

    // Método para crear la tabla Turno
    private static void createTurno() {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "CREATE TABLE IF NOT EXISTS Turno (legajoMedico INT, dniPaciente INT, fecha VARCHAR(20), costo INT, " +
                             "PRIMARY KEY (legajoMedico, dniPaciente, fecha), " +
                             "FOREIGN KEY (legajoMedico) REFERENCES Medico(legajo), " +
                             "FOREIGN KEY (dniPaciente) REFERENCES Paciente(dni))"
             )) {
            preparedStatement.executeUpdate();
            System.out.println("Se creó la tabla Turno");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al crear la tabla Turno: " + e.getMessage());
        }
    }

    // Método para crear la tabla RelacionMedicoObrasocial
    private static void createRelacionMedico() {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "CREATE TABLE IF NOT EXISTS RelacionMedicoObrasocial (legajoMedico INT, codObrasocial INT, " +
                             "PRIMARY KEY (legajoMedico, codObrasocial), " +
                             "FOREIGN KEY (legajoMedico) REFERENCES Medico(legajo), " +
                             "FOREIGN KEY (codObrasocial) REFERENCES ObraSocial(cod))"
             )) {
            preparedStatement.executeUpdate();
            System.out.println("Se creó la tabla RelacionMedicoObrasocial");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al crear la tabla RelacionMedicoObrasocial: " + e.getMessage());
        }
    }

    // Método para crear la tabla ObraSocial
    private static void createO_Social() {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "CREATE TABLE IF NOT EXISTS ObraSocial (cod INT PRIMARY KEY)"
             )) {
            preparedStatement.executeUpdate();
            System.out.println("Se creó la tabla ObraSocial");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al crear la tabla ObraSocial: " + e.getMessage());
        }
    }

    // Método para crear la tabla Admin e insertar el usuario admin
    private static void crearAdmin() {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS Admin (usuario VARCHAR(50), contraseña VARCHAR(50))");
            preparedStatement.executeUpdate();
            System.out.println("Se creó la tabla Admin");

            preparedStatement = connection.prepareStatement("INSERT INTO Admin (usuario, contraseña) VALUES (?, ?)");
            preparedStatement.setString(1, "admin");
            preparedStatement.setString(2, "admin");
            preparedStatement.executeUpdate();
            System.out.println("Se insertó el usuario admin");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al crear la tabla Admin: " + e.getMessage());
        }
    }

    // Método para crear todas las tablas en el orden correcto
    public static void createTablas() {
        createMedico();
        createPaciente();
        createO_Social();
        createRelacionMedico();
        createTurno();
        crearAdmin();
    }

    // Métodos para eliminar las tablas

    public static void dropTurno() {
        dropTable("Turno");
    }

    public static void dropRelacionMedico() {
        dropTable("RelacionMedicoObrasocial");
    }

    public static void dropAdmin() {
        dropTable("Admin");
    }

    public static void dropPaciente() {
        dropTable("Paciente");
    }

    public static void dropO_Social() {
        dropTable("ObraSocial");
    }

    public static void dropMedico() {
        dropTable("Medico");
    }

    private static void dropTable(String tableName) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement("DROP TABLE IF EXISTS " + tableName)) {
            preparedStatement.executeUpdate();
            System.out.println("Se eliminó la tabla " + tableName);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al eliminar la tabla " + tableName + ": " + e.getMessage());
        }
    }

    public static void dropAllTables() {
        dropTurno();
        dropRelacionMedico();
        dropAdmin();
        dropPaciente();
        dropO_Social();
        dropMedico();
    }
}
