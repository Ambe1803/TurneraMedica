package Service;

import DAO.DAOMedico;
import clases.Medico;

import java.util.ArrayList;

public class MedicoService {
    private DAOMedico daoMedico;
    public  MedicoService() { daoMedico=new DAOMedico();}
    public void guardar(Medico medico) throws ServiceException {
        try {
            daoMedico.guardar(medico);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }
    public void modificar(Medico medico) throws ServiceException {
        try {
            daoMedico.modificar(medico);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }
    public void eliminar(Medico medico) throws ServiceException {
        try {
            daoMedico.eliminar(medico);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }
    public Medico buscar(Medico medico) throws ServiceException {
        try {
            return daoMedico.buscar(medico.getLegajo());
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }
    public ArrayList buscarTodos() throws ServiceException {
        try {
            return daoMedico.buscarTodos();
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }
}
