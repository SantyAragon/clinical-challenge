package com.lastdance.clinical.DTOS;

import com.lastdance.clinical.models.Paciente;
import com.lastdance.clinical.models.PacienteServicio;

import java.util.HashSet;
import java.util.Set;

public class PacienteDTO {

    private Long id;
    private String nombre;
    private String apellido;
    private String email;
    private Long identificacion;
    private Set<PacienteServicio> servicios = new HashSet<>();


    public PacienteDTO() {
    }


    public PacienteDTO(Paciente paciente) {
        this.id = paciente.getId();
        this.nombre = paciente.getNombre();
        this.apellido = paciente.getApellido();
        this.email = paciente.getEmail();
        this.identificacion = paciente.getIdentificacion();
        this.servicios=paciente.getServicios();
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