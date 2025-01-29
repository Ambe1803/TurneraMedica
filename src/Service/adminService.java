package Service;

import DAO.DAOAdmin;

import java.sql.SQLException;

public class adminService {
    private DAOAdmin daoAdmin;
    public adminService ()  { daoAdmin = new DAOAdmin(); }

    public boolean login (String user ,String password) throws ServiceException{
        try {
            return daoAdmin.login(user,password);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }
}
