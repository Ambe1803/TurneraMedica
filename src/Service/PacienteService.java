package Service;

import DAO.DAOPaciente;
import clases.Paciente;

import java.util.ArrayList;

public class PacienteService {
    private DAOPaciente daoPaciente;

    public  PacienteService(){daoPaciente = new DAOPaciente();}

    public void guardar(Paciente paciente) throws ServiceException {
        try {
            daoPaciente.guardar(paciente);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public void modificar(Paciente paciente) throws ServiceException {
        try {
            daoPaciente.modificar(paciente);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public void eliminar(Paciente paciente) throws ServiceException {
        try {
            daoPaciente.eliminar(paciente);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public Paciente buscar(Paciente paciente) throws ServiceException {
        try {
            return daoPaciente.buscar(paciente.getDni());
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public ArrayList buscarTodos() throws ServiceException {
        try {
            return daoPaciente.buscarTodos();
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }

}


