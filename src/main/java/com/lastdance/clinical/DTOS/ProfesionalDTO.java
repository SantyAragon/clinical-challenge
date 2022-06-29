package com.lastdance.clinical.DTOS;

import com.lastdance.clinical.models.Profesional;
import com.lastdance.clinical.models.Servicio;
import com.lastdance.clinical.models.TipoEspecialidad;

public class ProfesionalDTO {

    private Long id;
    private boolean activo;
    private String nombre;
    private String apellido;
    private TipoEspecialidad especialidad;
    private String email;


//    private Servicio servicio;

    public ProfesionalDTO() {
    }

    public ProfesionalDTO(Profesional profesional) {
        this.id = profesional.getId();
        this.activo = profesional.isActivo();
        this.nombre = profesional.getNombre();
        this.apellido = profesional.getApellido();
        this.especialidad = profesional.getEspecialidad();
        this.email = profesional.getEmail();
//        this.servicio = profesional.getServicio();
    }

    public Long getId() {
        return id;
    }
    public boolean isActivo() {
        return activo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public TipoEspecialidad getEspecialidad() {
        return especialidad;
    }

    public String getEmail() { return email; }
    public void setEmail(String email) {
        this.email = email;
    }
}
