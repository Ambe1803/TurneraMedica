package gui;

import DAO.DAOcreate;
import static DAO.DAOcreate.dropAllTables;
import static DAO.DAOcreate.createTablas;
public class Main {
    public static void main(String[] args){

        createTablas();
        PanelManager PanelManager = new PanelManager();
    }
}


