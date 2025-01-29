package DAO;

import clases.Medico;

import java.sql.*;
import java.util.ArrayList;

public class DAOMedico implements DAO<Medico>{
    private static final String DB_JDBC_DRIVER = "org.h2.Driver";
    private static final String DB_URL = "jdbc:h2:tcp://localhost/~/test";
    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "";;

    @Override
    public void guardar(Medico elemento) throws DAOException {
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection= DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
            preparedStatement=connection.prepareStatement("INSERT into Medico Values(?,?,?,?)");
            preparedStatement.setInt(1, elemento.getDni());
            preparedStatement.setString(2, elemento.getNombre());
            preparedStatement.setString(3, elemento.getApellido());
            preparedStatement.setInt(4,elemento.getLegajo());
            int res=preparedStatement.executeUpdate();
            System.out.println("Se agregaron " + res);
        }
        catch (ClassNotFoundException | SQLException e)
        {
            throw  new DAOException(e.getMessage());
        }
    }

    @Override
    public void modificar(Medico elemento) throws DAOException {
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection= DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
            preparedStatement=connection.prepareStatement("UPDATE Medico SET nombre=?, apellido=?, dni=? WHERE legajo=?");
            preparedStatement.setString(1, elemento.getNombre());
            preparedStatement.setString(2, elemento.getApellido());
            preparedStatement.setInt(3, elemento.getDni());
            preparedStatement.setLong(4,elemento.getLegajo());
            int res=preparedStatement.executeUpdate();
            System.out.println("Se modificaron " + res);
        }
        catch (ClassNotFoundException | SQLException e)
        {
            throw  new DAOException(e.getMessage());
        }
    }

    @Override
    public void eliminar(Medico medico) throws DAOException {
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection= DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
            preparedStatement=connection.prepareStatement("DELETE FROM Medico  WHERE legajo=?");
            preparedStatement.setLong(1,medico.getLegajo());
            int res=preparedStatement.executeUpdate();
            System.out.println("Se elimino" + res);
        }
        catch (ClassNotFoundException | SQLException e)
        {
            throw  new DAOException(e.getMessage());
        }
    }

    @Override
    public Medico buscar(long id) throws DAOException {
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        Medico medico=null;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection= DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
            preparedStatement=connection.prepareStatement("SELECT * FROM Medico  WHERE legajo=?");
            preparedStatement.setLong(1,id);
            ResultSet resultSet =preparedStatement.executeQuery();
            if (resultSet.next()) {
                medico = new Medico(resultSet.getInt("DNI"),
                        resultSet.getString("NOMBRE"),
                        resultSet.getString("APELLIDO"),
                        resultSet.getInt("LEGAJO")
                );
            }
        }
        catch (ClassNotFoundException | SQLException e) {
            throw  new DAOException(e.getMessage());
        }
        return medico;
    }

    @Override
    public ArrayList buscarTodos() throws DAOException {
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        ArrayList<Medico> datos=new ArrayList<>();
        Medico medico=null;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection= DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
            preparedStatement=connection.prepareStatement("SELECT * FROM Medico");
            ResultSet resultSet =preparedStatement.executeQuery();
            while (resultSet.next()) {
                medico = new Medico(
                        resultSet.getInt("DNI"),
                        resultSet.getString("NOMBRE"),
                        resultSet.getString("APELLIDO"),
                        resultSet.getInt("LEGAJO"));
                datos.add(medico);
            }
        }
        catch (ClassNotFoundException | SQLException e) {
            throw  new DAOException(e.getMessage());
        }
        return datos;
    }
}