package clases;

import Service.ServiceException;
import Service.TurnoService;

import java.util.ArrayList;



public class Turno {
    private Paciente paciente;
    private Medico medico;
    private String fecha;
    private Double costo;
    private TurnoService turnoService;

    public Turno(String fecha, Double costo, Medico medico, Paciente paciente){
        this.medico = medico;
        this.paciente = paciente;
        this.fecha = fecha;
        this.costo = costo;
    }

    public Turno(){
        this.medico = new Medico();
        this.paciente = new Paciente();
        this.fecha = "";
        this.costo = 0.0;
    }

    public Turno(Medico medico, Paciente paciente){
        this.medico = medico;
        this.paciente = paciente;
        this.fecha = "";
        this.costo = 0.0;
    }

    public String getHora(){
        String[] partes = this.fecha.split(" ");
        String hora = partes[1];
        return hora;
    }

    public void setNombreMedico(String nombre) {this.medico.setNombre(nombre);}
    public String getNombreMedico() {return medico.getNombre();}
    public void setApellidoMedico(String apellido) {this.medico.setApellido(apellido);}
    public String getApellidoMedico() {return medico.getApellido();}
    public void setDniPaciente(int dniPaciente) {this.paciente.setDni(dniPaciente);}
    public void setLegajoMedico(int legajoMedico) {this.medico.setLegajo(legajoMedico);}
    public int getDniPaciente() {return paciente.getDni();}
    public void setPaciente(Paciente paciente) {this.paciente = paciente;}
    public int getLegajoMedico() {return medico.getLegajo();}
    public void setMedico(Medico medico) {this.medico = medico;}
    public String getFecha() {return fecha;}
    public void setFecha(String fecha) {this.fecha = fecha;}
    public Double getCosto() {return costo;}
    public void setCosto(Double costo) {this.costo = costo;}

    public ArrayList<String> fillarrayHoras() throws ServiceException {
        ArrayList<String> horariosTomados = horariosTomados(this.fecha, this.getLegajoMedico(), this.getDniPaciente());
        int horaInicial = 10; // Hora inicial (10:00)
        ArrayList<String> horariosTurnos = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            horariosTurnos.add(String.format("%02d:00", horaInicial));
            horariosTurnos.add(String.format("%02d:30", horaInicial));
            horaInicial++;
        }
        horariosTurnos.removeAll(horariosTomados);
        return horariosTurnos;
    }

    private ArrayList<String> horariosTomados(String fecha, int legajoMedico, int dniPaciente) throws ServiceException {
        turnoService = new TurnoService();
        ArrayList<String> fechasTomadas = new ArrayList<>();
        ArrayList<String> horariosTomados = new ArrayList<>();
        fechasTomadas = turnoService.buscarHorariosPorMedico(fecha, legajoMedico);
        fechasTomadas.addAll(turnoService.buscarHorariosPorPaciente(fecha,dniPaciente));
        for (String fechaHora : fechasTomadas) {
            String[] partes = fechaHora.split(" ");
            String hora = partes[1]; // Segunda parte es la hora
            horariosTomados.add(hora);
        }
        return horariosTomados;
    }

}
