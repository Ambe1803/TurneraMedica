package clases;


public class Paciente extends Usuario {

    private int O_Social;

    public Paciente(String nombre, String apellido, int O_Social, int dni){
        super(nombre, apellido, dni);
        this.O_Social = O_Social;
    }

    public int getO_Social() {
        return O_Social;
    }

    public void setO_Social(int O_Social) {
        this.O_Social = O_Social;
    }

    public Paciente(){
        super();
    }

    public Paciente(int dni){
        super(dni);
    }

    @Override
    public void setDni(int dni) {
        super.setDni(dni);
    }
}