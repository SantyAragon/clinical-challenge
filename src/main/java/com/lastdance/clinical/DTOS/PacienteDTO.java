package com.lastdance.clinical.DTOS;

import com.lastdance.clinical.models.Paciente;

public class PacienteDTO {

    private Long id;
    private String nombre;
    private String apellido;
    private String email;
    private Long identificacion;


    public PacienteDTO() {
    }


    public PacienteDTO(Paciente paciente) {
        this.id = paciente.getId();
        this.nombre = paciente.getNombre();
        this.apellido = paciente.getApellido();
        this.email = paciente.getEmail();
        this.identificacion = paciente.getIdentificacion();
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getEmail() {
        return email;
    }

    public Long getIdentificacion() {
        return identificacion;
    }
}