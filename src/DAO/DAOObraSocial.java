package DAO;


import clases.ObraSocial;

import java.sql.*;
import java.util.ArrayList;


public class DAOObraSocial implements DAO<ObraSocial>{
    private static final String DB_JDBC_DRIVER = "org.h2.Driver";
    private static final String DB_URL = "jdbc:h2:tcp://localhost/~/test";
    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "";



    @Override
    public void guardar(ObraSocial elemento) throws DAOException {
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection= DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
            preparedStatement=connection.prepareStatement("INSERT into Obrasocial Values(?,?)");
            preparedStatement.setLong(1,elemento.getCod());
            preparedStatement.setString(2, elemento.getNombre());

            int res=preparedStatement.executeUpdate();
            System.out.println("Se agregaron " + res);
        }
        catch (ClassNotFoundException | SQLException e)
        {
            throw  new DAOException(e.getMessage());
        }
    }

    @Override
    public void modificar(ObraSocial elemento) throws DAOException {

        Connection connection=null;
        PreparedStatement preparedStatement=null;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection= DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
            preparedStatement=connection.prepareStatement("UPDATE Obrasocial SET nombre=? WHERE cod=?");
            preparedStatement.setString(2, elemento.getNombre());
            preparedStatement.setLong(1,elemento.getCod());
            int res=preparedStatement.executeUpdate();
            System.out.println("Se modificaron " + res);
        }
        catch (ClassNotFoundException | SQLException e)
        {
            throw  new DAOException(e.getMessage());
        }


    }

    @Override
    public void eliminar(ObraSocial elemento) throws DAOException {
    }



    @Override
    public ObraSocial buscar(long id) throws DAOException {
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        ObraSocial obraSocial=null;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection= DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
            preparedStatement=connection.prepareStatement("SELECT * FROM Obrasocial  WHERE cod=?");
            preparedStatement.setLong(1,id);
            ResultSet resultSet =preparedStatement.executeQuery();
            if (resultSet.next()) {
                obraSocial = new ObraSocial(
                        resultSet.getString("NOMBRE"),
                        resultSet.getInt("COD")
                );
            }
        }
        catch (ClassNotFoundException | SQLException e)
        {
            throw  new DAOException(e.getMessage());
        }
        return obraSocial;
    }


    @Override
    public ArrayList buscarTodos() throws DAOException {
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        ArrayList<ObraSocial> datos=new ArrayList<>();
        ObraSocial obrasocial=null;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection= DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
            preparedStatement=connection.prepareStatement("SELECT * FROM obraSocial");
            ResultSet resultSet =preparedStatement.executeQuery();
            while (resultSet.next()) {

                obrasocial = new ObraSocial(
                        resultSet.getString("NOMBRE"),
                        resultSet.getInt("COD")
                );

                datos.add(obrasocial);
            }
        }
        catch (ClassNotFoundException | SQLException e)
        {
            throw  new DAOException(e.getMessage());
        }
        return datos;
    }
}