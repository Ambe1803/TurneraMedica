package clases;

public class ObraSocial {

    private String nombre;
    private int cod;

    public ObraSocial(String nombre, int cod) {
        this.nombre = nombre;
        this.cod = cod;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    @Override
    public String toString(){
        return this.nombre;
    }
}
