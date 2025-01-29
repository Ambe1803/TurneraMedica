package DAO;

import clases.Paciente;

import java.sql.*;
import java.util.ArrayList;

public class DAOPaciente implements DAO<Paciente> {
    private static final String DB_JDBC_DRIVER = "org.h2.Driver";
    private static final String DB_URL = "jdbc:h2:tcp://localhost/~/test" ;
    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "";


    @Override
    public void guardar(Paciente elemento) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("INSERT into Paciente Values(?,?,?,?)");
            preparedStatement.setInt(1, elemento.getDni());
            preparedStatement.setString(2, elemento.getNombre());
            preparedStatement.setString(3, elemento.getApellido());
            preparedStatement.setInt(4, elemento.getO_Social());
            int res = preparedStatement.executeUpdate();
            System.out.println("Se agregaron " + res);
        } catch (ClassNotFoundException | SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }

    @Override
    public void modificar(Paciente elemento) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("UPDATE paciente SET nombre=?, apellido=?,codObrasocial=? WHERE dni=?");
            preparedStatement.setString(1, elemento.getNombre());
            preparedStatement.setString(2, elemento.getApellido());
            preparedStatement.setInt(3, elemento.getO_Social());
            preparedStatement.setInt(4, elemento.getDni());
            int res = preparedStatement.executeUpdate();
            System.out.println("Se modificaron " + res);
        } catch (ClassNotFoundException | SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }

    @Override
    public void eliminar(Paciente paciente) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("DELETE FROM paciente  WHERE dni=?");
            preparedStatement.setLong(1, paciente.getDni());
            int res = preparedStatement.executeUpdate();
            System.out.println("Se elimino" + res);
        } catch (ClassNotFoundException | SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }

    @Override
    public Paciente buscar(long id) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Paciente paciente = null;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("SELECT * FROM paciente  WHERE dni=?");
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                paciente = new Paciente(
                        resultSet.getString("nombre"),
                        resultSet.getString("apellido"),
                        resultSet.getInt("codObraSocial"),
                        resultSet.getInt("dni")
                );
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new DAOException(e.getMessage());
        }
        return paciente;
    }

    @Override
    public ArrayList buscarTodos() throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ArrayList<Paciente> datos = new ArrayList<>();
        Paciente paciente = null;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("SELECT * FROM paciente");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                paciente = new Paciente(
                        resultSet.getString("NOMBRE"),
                        resultSet.getString("APELLIDO"),
                        resultSet.getInt("CODOBRASOCIAL"),
                        resultSet.getInt("DNI")
                );
                datos.add(paciente);
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new DAOException(e.getMessage());
        }
        return datos;
    }
}

