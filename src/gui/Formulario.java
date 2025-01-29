package gui;

import Service.ServiceException;

import javax.swing.*;
import java.text.ParseException;

public interface Formulario {
    void crearForm() throws ServiceException, ParseException;
    public void agregarForm();
    public void agregarFuncionesBotones();
    public JPanel getForm();


}
