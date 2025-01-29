package clases;

public class Medico extends Usuario {

    private int legajo;

    public Medico(int dni, String nombre, String apellido, int legajo) {
        super(nombre, apellido, dni);
        this.legajo = legajo;
    }

    public Medico() {
        super();
    }

    public Medico(int legajo) {
        this.legajo = legajo;
    }

    public int getLegajo() {
        return legajo;
    }

    public void setLegajo(int legajo) {
        this.legajo = legajo;
    }
}