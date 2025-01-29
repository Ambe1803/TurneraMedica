package DAO;

import clases.Medico;
import clases.Paciente;
import clases.Turno;

import java.sql.*;
import java.util.ArrayList;

public class DAOTurno implements DAO<Turno> {
    private static final String DB_JDBC_DRIVER = "org.h2.Driver";
    private static final String DB_URL = "jdbc:h2:tcp://localhost/~/test";
    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "";

    @Override
    public void guardar(Turno elemento) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("INSERT into Turno Values(?,?,?,?)");
            preparedStatement.setInt(1, elemento.getLegajoMedico());
            preparedStatement.setInt(2, elemento.getDniPaciente());
            preparedStatement.setString(3, elemento.getFecha());
            preparedStatement.setDouble(4, elemento.getCosto());
            int res = preparedStatement.executeUpdate();
            System.out.println("Se agregaron " + res);
        } catch (ClassNotFoundException | SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }

    @Override
    public void modificar(Turno elemento) throws DAOException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("UPDATE turno SET costo=? WHERE legajoMedico=? and dniPaciente=? and fecha=?");
            preparedStatement.setDouble(1, elemento.getCosto());
            preparedStatement.setInt(2, elemento.getLegajoMedico());
            preparedStatement.setInt(3, elemento.getDniPaciente());
            preparedStatement.setString(4, elemento.getFecha());

            int res = preparedStatement.executeUpdate();
            System.out.println("Se modificaron " + res);
        } catch (ClassNotFoundException | SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }

    @Override
    public void eliminar(Turno turno) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("DELETE FROM turno  WHERE DniPaciente=? and LegajoMedico=? and fecha=?");
            preparedStatement.setLong(1, turno.getDniPaciente());
            preparedStatement.setLong(2, turno.getLegajoMedico());
            preparedStatement.setString(3, turno.getFecha());
            int res = preparedStatement.executeUpdate();
            System.out.println("Se elimino" + res);
        } catch (ClassNotFoundException | SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }

    @Override
    public Turno buscar(long id) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Turno turno = null;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("SELECT * FROM turno  WHERE id=?");
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
        } catch (ClassNotFoundException | SQLException e) {
            throw new DAOException(e.getMessage());
        }
        return turno;
    }

    @Override
    public ArrayList buscarTodos() throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ArrayList<Turno> datos = new ArrayList<>();
        Turno turno = null;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("SELECT * FROM turno");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                turno = new Turno();
                turno.setLegajoMedico(resultSet.getInt("Medico"));
                turno.setDniPaciente(resultSet.getInt("Paciente"));
                turno.setFecha(resultSet.getString("fecha"));
                datos.add(turno);
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new DAOException(e.getMessage());
        }
        return datos;
    }

    public ArrayList<String> buscarHorariosPorMedico(String fecha, int legajoMedico) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ArrayList<String> datos = new ArrayList<>();
        String turno = null;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("SELECT * FROM turno WHERE SUBSTRING(fecha, 1, 10) = ? AND legajoMedico = ?");
            preparedStatement.setString(1, fecha);
            preparedStatement.setInt(2, legajoMedico);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                turno = resultSet.getString("fecha");
                datos.add(turno);
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new DAOException(e.getMessage());
        }
        return datos;
    }

    public ArrayList<String> buscarHorariosPorPaciente(String fecha, int dni) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ArrayList<String> datos = new ArrayList<>();
        String turno = null;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("SELECT * FROM turno WHERE SUBSTRING(fecha, 1, 10) = ? AND dnipaciente = ?");
            preparedStatement.setString(1, fecha);
            preparedStatement.setInt(2, dni);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                turno = resultSet.getString("fecha");
                datos.add(turno);
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new DAOException(e.getMessage());
        }
        return datos;
    }

    public ArrayList<Turno> buscarTurnosMedico(String fecha, int legajoMedico) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ArrayList<Turno> datos = new ArrayList<>();
        Turno turno = new Turno();
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("SELECT * FROM turno WHERE SUBSTRING(fecha, 1, 10) = ? AND legajoMedico = ? ORDER BY fecha DESC;");
            preparedStatement.setString(1, fecha);
            preparedStatement.setInt(2, legajoMedico);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                turno = new Turno();
                turno.setLegajoMedico(resultSet.getInt("legajoMedico"));
                turno.setDniPaciente(resultSet.getInt("dniPaciente"));
                turno.setFecha(resultSet.getString("fecha"));
                turno.setCosto(resultSet.getDouble("costo"));
                datos.add(turno);
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return datos;
    }
    public ArrayList<Turno>buscarCobros(String fecha1, String fecha2, int legajo) throws DAOException {
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        Turno turno;
        ArrayList<Turno> datos=new ArrayList<>();
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection= DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
            preparedStatement=connection.prepareStatement("SELECT * FROM turno WHERE fecha BETWEEN CONCAT(?, ' 00:00') AND CONCAT(?, ' 23:59') AND legajoMedico = ? ORDER BY fecha DESC;");
            preparedStatement.setString(1, fecha1);
            preparedStatement.setString(2, fecha2);
            preparedStatement.setInt(3, legajo);
            ResultSet resultSet =preparedStatement.executeQuery();
            while (resultSet.next()) {
                turno=new Turno();
                turno.setCosto(resultSet.getDouble("costo"));
                turno.setLegajoMedico(resultSet.getInt("legajoMedico"));
                turno.setDniPaciente(resultSet.getInt("dniPaciente"));
                turno.setFecha(resultSet.getString("fecha"));
                datos.add(turno);
            }
        }
        catch (ClassNotFoundException | SQLException e)
        {
            throw  new DAOException(e.getMessage());
        }
        return datos;
    }

    public ArrayList<Turno> buscarTurnosPaciente(int dni) throws DAOException {
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        ArrayList<Turno> datos=new ArrayList<>();
        Turno turno;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection= DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
            preparedStatement=connection.prepareStatement("SELECT * FROM turno WHERE DniPaciente = ? ORDER BY fecha DESC;");
            preparedStatement.setInt(1, dni);
            ResultSet resultSet =preparedStatement.executeQuery();
            while (resultSet.next()) {
                turno = new Turno();
                turno.setLegajoMedico(resultSet.getInt("legajoMedico"));
                turno.setFecha(resultSet.getString("fecha"));
                turno.setCosto(resultSet.getDouble("costo"));
                datos.add(turno);
            }
        }
        catch (ClassNotFoundException | SQLException e)
        {
            throw  new DAOException(e.getMessage());
        }
        return datos;
    }
    public ArrayList<Turno> buscarTurnosPorPacienteYMedico(int dni,int legajo) throws DAOException {
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        ArrayList<Turno> datos=new ArrayList<>();
        Turno turno;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection= DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
            preparedStatement=connection.prepareStatement("SELECT * FROM turno WHERE DniPaciente = ? AND LegajoMedico=? ORDER BY fecha DESC;");
            preparedStatement.setInt(1, dni);
            preparedStatement.setInt(2, legajo);
            ResultSet resultSet =preparedStatement.executeQuery();
            while (resultSet.next()) {
                turno = new Turno();
                turno.setFecha(resultSet.getString("fecha"));
                turno.setMedico(new Medico(resultSet.getInt("Medico")));
                turno.setPaciente(new Paciente(resultSet.getInt("Paciente")));
                datos.add(turno);
            }
            return datos;
        }
        catch (ClassNotFoundException | SQLException e)
        {
            throw  new DAOException(e.getMessage());
        }
    }
    public ArrayList<Turno> calcularSumaCobrosPorRango(String fecha1, String fecha2) throws DAOException {
        ArrayList<Turno> sumaCobrosPorLegajo = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT t.legajoMedico, SUM(t.costo) AS suma_cobros, m.nombre, m.apellido FROM turno t INNER JOIN medico m ON t.legajoMedico = m.legajo WHERE t.fecha BETWEEN CONCAT(?, ' 00:00') AND CONCAT(?, ' 23:59') GROUP BY t.legajoMedico, m.nombre, m.apellido;\n")) {
            preparedStatement.setString(1, fecha1);
            preparedStatement.setString(2, fecha2);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Turno turno = new Turno();
                turno.setLegajoMedico(resultSet.getInt("legajoMedico"));
                turno.setCosto(resultSet.getDouble("suma_cobros"));
                turno.setNombreMedico(resultSet.getString("nombre"));
                turno.setApellidoMedico(resultSet.getString("apellido"));
                sumaCobrosPorLegajo.add(turno);
            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
        return sumaCobrosPorLegajo;
    }

}
