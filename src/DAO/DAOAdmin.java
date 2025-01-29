package DAO;
import java.sql.*;

import static DAO.DAOcreate.dropAdmin;

public class DAOAdmin {

    public boolean login(String user, String password) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            Class.forName("org.h2.Driver");

            connection = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/test", "sa", "");

            preparedStatement = connection.prepareStatement("SELECT * FROM Admin WHERE usuario=? AND contraseña =?");
            System.out.println("estoy aqui");
            preparedStatement.setString(1, user);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();
            return resultSet.next(); // me devulve true si hay una fila a menos
        } catch (ClassNotFoundException | SQLException e) {
            throw new DAOException(e.getMessage());
        }


    }
}